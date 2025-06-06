package com.fernando.pokedex.infrastructure.handlers.error

import com.fernando.pokedex.data.exceptions.BadRequestException
import com.fernando.pokedex.data.exceptions.NotAuthorizedException
import com.fernando.pokedex.data.exceptions.NotFoundException
import com.fernando.pokedex.data.exceptions.ServerException
import java.io.Reader
import java.net.HttpURLConnection
import javax.inject.Inject

class ApiErrorHandler @Inject constructor() : ErrorHandler {
    override fun throwFromCode(errorCode: Int, reader: Reader?, message: String?, url: String) {
        when (errorCode) {
            HttpURLConnection.HTTP_FORBIDDEN -> {
                throw NotAuthorizedException("Not authorized to realize this action ")
            }

            HttpURLConnection.HTTP_BAD_REQUEST -> {
                throw BadRequestException(message = "Bad request Exception")
            }

            HttpURLConnection.HTTP_NOT_FOUND -> {
                throw NotFoundException(message = "Not found Exception")
            }

            HttpURLConnection.HTTP_INTERNAL_ERROR, HttpURLConnection.HTTP_UNAVAILABLE -> {
                throw ServerException(
                    "Sorry, there was a problem in the server. Try again later."
                )
            }
        }
    }
}


