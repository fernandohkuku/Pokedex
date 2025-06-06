package com.fernando.pokedex.data.exceptions

import com.fernando.pokedex.data.exceptions.ApiException

/**
 * Exception representing a bad request in the API.
 *
 * @param message A message describing the exception. Default is "Bad request".
 */
class BadRequestException(
    override val message: String = "Bad request"
) : ApiException(message)