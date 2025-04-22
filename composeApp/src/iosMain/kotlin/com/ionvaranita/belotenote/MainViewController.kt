import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.ionvaranita.belotenote.App
import database.getPeopleDatabase

fun MainViewController() = ComposeUIViewController {
    val dao = remember {
        getPeopleDatabase()
    }
    App(dao)
}