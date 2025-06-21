package com.ionvaranita.belotenote.ui.viewmodel.match

sealed interface SideEffect {
    data class ShowWinner(val winnerName: String) : SideEffect
    data class ShowExtended(val maxPoints: String, val winner: Winner ): SideEffect
    data class ShowExtendedMandatory(val maxPoints: String ): SideEffect
}

data class Winner(val id: Int, val name: String)