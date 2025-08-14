package com.ionvaranita.belotenote.ui.viewmodel.game

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import com.ionvaranita.belotenote.ui.GamePath

abstract class GameViewModelCommon : ViewModel() {
    protected abstract val prefs: DataStore<Preferences>
    protected abstract val gamePath: GamePath


    protected suspend fun deleteLastWinnerByIdGame(idGame: Int) {
        prefs.edit { dataStore ->
            val winnerKey = intPreferencesKey(gamePath.name + idGame)
            dataStore.remove(winnerKey)
        }
    }

}