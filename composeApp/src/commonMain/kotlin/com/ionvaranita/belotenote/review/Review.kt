package com.ionvaranita.belotenote.review

import androidx.compose.runtime.Composable

// In src/commonMain/kotlin/tuo/pacchetto/ReviewManager.kt

expect class InAppReviewManager {
    /**
     * Avvia il flusso di richiesta di revisione.
     * La logica interna di ciascuna piattaforma deciderà se mostrare o meno il pop-up.
     */
    suspend fun requestReview()
}

// Potresti anche aver bisogno di un modo per passare il contesto/activity da Android
// Un pattern comune è avere un'altra funzione expect per creare l'istanza.
@Composable
expect fun rememberInAppReviewManager(): InAppReviewManager