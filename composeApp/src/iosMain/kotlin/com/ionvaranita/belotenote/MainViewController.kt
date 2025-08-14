import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.ionvaranita.belotenote.App
import com.ionvaranita.belotenote.createDataStore
import com.ionvaranita.belotenote.database.getDatabaseBuilder
import com.ionvaranita.belotenote.datalayer.database.getRoomDatabase

fun MainViewController() = ComposeUIViewController {
    val appDatabase = remember {
        getRoomDatabase(getDatabaseBuilder())
    }
    App(appDatabase = appDatabase, prefs = remember {
        createDataStore()
    })
}