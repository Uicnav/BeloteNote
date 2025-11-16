package com.ionvaranita.belotenote.review

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.play.core.review.ReviewManagerFactory
import kotlinx.coroutines.tasks.await // <-- IMPORTANTE!

actual class InAppReviewManager(private val activity: Activity) {

    private val reviewManager = ReviewManagerFactory.create(activity)

    actual suspend fun requestReview() {
        try {
            // CORRETTO: usa .await() per sospendere
            val reviewInfo = reviewManager.requestReviewFlow().await()
            // CORRETTO: usa .await() anche qui
            reviewManager.launchReviewFlow(activity, reviewInfo).await()
        } catch (e: Exception) {
            // Gestisci l'errore, ma non informare l'utente
            // (come da linee guida di Google)
            e.printStackTrace()
        }
    }
}

@Composable
actual fun rememberInAppReviewManager(): InAppReviewManager {
    val context = LocalContext.current
    val activity = context as? Activity
        ?: throw IllegalStateException("Il contesto non è un'Activity. Assicurati di essere in un'Activity.")

    return remember(activity) {
        InAppReviewManager(activity)
    }
}