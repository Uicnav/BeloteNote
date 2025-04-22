import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.ionvaranita.belotenote.App
import com.ionvaranita.belotenote.database.getDatabaseBuilder
import com.ionvaranita.belotenote.datalayer.database.getRoomDatabase

fun MainViewController() = ComposeUIViewController {
    val dao = remember {
        getRoomDatabase(getDatabaseBuilder())
    }
    App(dao)
}