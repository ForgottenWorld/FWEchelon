package it.forgottenworld.echelon.utils

private val STRING_CHARACTERS = ('0'..'z')

fun getRandomString(length: Int) =
        (1..length).map { STRING_CHARACTERS.random() }.joinToString("")