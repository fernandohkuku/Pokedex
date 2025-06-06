package com.fernando.pokedex.data.exceptions

import com.fernando.pokedex.data.exceptions.ApiException

/**
 * Exception representing a lack of internet connection.
 *
 * @param message A message describing the exception. Default is "the internet connection is not available".
 */
class NetworkInternetException(
    override val message: String = "",
) : ApiException(message)