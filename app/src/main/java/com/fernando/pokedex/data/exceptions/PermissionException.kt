package com.fernando.pokedex.data.exceptions

internal open class PermissionException(
    override val message: String = "You don't have enough permissions"
) : RuntimeException(message)