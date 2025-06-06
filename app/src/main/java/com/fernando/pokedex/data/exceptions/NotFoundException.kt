package com.fernando.pokedex.data.exceptions

import com.fernando.pokedex.data.exceptions.ApiException

/**
 * Exception representing a resource not found error.
 *
 * @param message A message describing the exception.
 */
class NotFoundException(override val message: String) : ApiException(message)