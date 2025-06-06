package com.fernando.pokedex.data.exceptions

import com.fernando.pokedex.data.exceptions.ApiException

/**
 * Exception representing a server-side error.
 *
 * @param message A message describing the exception.
 */
class ServerException(override val message: String) : ApiException(message)
