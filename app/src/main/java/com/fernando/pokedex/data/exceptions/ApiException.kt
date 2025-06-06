package com.fernando.pokedex.data.exceptions

import java.io.IOException

/**
 * Represents an exception that occurs during an API operation.
 *
 * This class extends [IOException] and provides additional context for API-related exceptions.
 *
 * @property message The detail message of this exception.
 * @constructor Creates an [ApiException] with the specified detail [message].
 */
open class ApiException(override val message: String) : IOException(message)
