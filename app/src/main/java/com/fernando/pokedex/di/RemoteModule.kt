package com.fernando.pokedex.di

import android.content.Context
import com.fernando.pokedex.data.exceptions.NetworkInternetException
import com.fernando.pokedex.data.remote.api.PokemonService
import com.fernando.pokedex.infrastructure.handlers.error.ErrorHandler
import com.fernando.pokedex.ktx.isInternetAvailable
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

const val INTERCEPTOR_ERROR = "InterceptorError"
const val INTERCEPTOR_INTERNET_CONNECTION = "InterceptorInternetConnection"

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    @Named(INTERCEPTOR_ERROR)
    fun provideInterceptorError(errorHandler: ErrorHandler): Interceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val responseBody = response.body

        val responseBodyString = responseBody?.string()
        val responseReader = responseBodyString?.reader()

        errorHandler.throwFromCode(
            response.code,
            responseReader,
            responseBodyString,
            response.request.url.toString()
        )

        val newResponseBody = responseBodyString?.toResponseBody(responseBody.contentType())
        response.newBuilder().body(newResponseBody).build()
    }


    @Singleton
    @Provides
    @Named(INTERCEPTOR_INTERNET_CONNECTION)
    fun provideInterceptorInternetConnection(@ApplicationContext context: Context): Interceptor =
        Interceptor { chain ->
            if (!context.isInternetAvailable()) {
                throw NetworkInternetException()
            }
            chain.proceed(chain.request())
        }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideMoshiConvertFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(
        @Named(INTERCEPTOR_ERROR) interceptorError: Interceptor,
        @Named(INTERCEPTOR_INTERNET_CONNECTION) interceptorInternetConnection: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptorInternetConnection)
        addInterceptor(interceptorError)
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
    }.build()

    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(okHttpClient)


    @Singleton
    @Provides
    fun provideRetrofit(retrofitBuilder: Retrofit.Builder): Retrofit = retrofitBuilder.build()

    @Singleton
    @Provides
    fun providePokemonService(retrofit: Retrofit): PokemonService =
        retrofit.create(PokemonService::class.java)

}