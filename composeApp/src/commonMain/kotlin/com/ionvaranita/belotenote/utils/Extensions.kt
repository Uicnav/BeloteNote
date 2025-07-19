package com.ionvaranita.belotenote.utils


fun String.capitalizeName(): String {
    return this.lowercase().replaceFirstChar { it.uppercase() }
}