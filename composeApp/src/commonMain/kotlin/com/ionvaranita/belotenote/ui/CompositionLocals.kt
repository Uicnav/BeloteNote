package com.ionvaranita.belotenote.ui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import com.ionvaranita.belotenote.datalayer.database.AppDatabase

internal val LocalNavHostController = compositionLocalOf<NavHostController> { error("Nav host controller not provided") }
internal val LocalAppDatabase = staticCompositionLocalOf<AppDatabase> {
    error("No AppDatabase provided")
}