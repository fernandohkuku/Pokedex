package com.fernando.pokedex.data.exceptions

import com.fernando.pokedex.data.exceptions.ApiException

/**
 * Exception representing a lack of authorization for performing an action.
 *
 * @param message A message describing the exception. Default is "Not authorized".
 */
class NotAuthorizedException(
    override val message: String = ""
) : ApiException(message)
