package com.ionvaranita.belotenote.review

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.StoreKit.SKStoreReviewController
import platform.UIKit.UIApplication
import platform.UIKit.UIWindowScene
// Importa la costante e il typealias
import platform.UIKit.UISceneActivationStateForegroundActive

actual class InAppReviewManager {
    actual suspend fun requestReview() {
        println("InAppReviewManager [iOS]: Chiamata a requestReview() ricevuta.")

        // Cerca la scena attiva
        val windowScene = UIApplication.sharedApplication.connectedScenes
            .mapNotNull { it as? UIWindowScene }
            .firstOrNull {
                // ECCO LA CORREZIONE:
                // Confronta il valore (Long) di activationState
                // con la costante globale.
                it.activationState == UISceneActivationStateForegroundActive
            }

        if (windowScene != null) {
            println("InAppReviewManager [iOS]: Trovata windowScene attiva! Mostro il pop-up.")
            SKStoreReviewController.requestReviewInScene(windowScene)
        } else {
            // Se vedi questo log, hai trovato il problema
            println("InAppReviewManager [iOS]: ERRORE! windowScene è 'null'. Impossibile mostrare il pop-up.")
        }
    }
}

@Composable
actual fun rememberInAppReviewManager(): InAppReviewManager {
    // Per iOS, l'implementazione non ha bisogno di contesto,
    // quindi possiamo semplicemente crearla e ricordarla.
    return remember { InAppReviewManager() }
}
