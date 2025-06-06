package com.fernando.pokedex.infrastructure.handlers.error

import java.io.Reader

interface ErrorHandler {
    fun throwFromCode(errorCode: Int, reader: Reader?, message: String?, url: String)
}