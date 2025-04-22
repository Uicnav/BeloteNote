package com.ionvaranita.belotenote
interface Platform {
    val name: String
}

expect fun getPlatform(): Platform