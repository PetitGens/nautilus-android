package fr.iut.gon.info.td3.projetAndroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.internal.NavigationMenu
import fr.iut.gon.info.td3.projetAndroid.ui.theme.ProjetAndroidTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.iut.gon.info.td3.projetAndroid.ui.theme.ProjetAndroidTheme
import kotlin.random.Random

class Nautilus : ComponentActivity() {
    private val navigationViewModel : NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var pageState : MutableState<NautilusPage?>? = null
        val pageSetter : (NautilusPage) -> (Unit) = {
            navigationViewModel.currentPage.value = it
        }

        val pageObserver = Observer<NautilusPage>{
            pageState?.value = it
        }
        navigationViewModel.currentPage.observe(this, pageObserver)

        setContent {
            ProjetAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    pageState = remember {
                        mutableStateOf(navigationViewModel.currentPage.value)
                    }

                    if (pageState!!.value != null) {
                        MainComponent(page = pageState!!.value!!, pageSetter = pageSetter)
                    }
                }
            }
        }
    }
}

@Composable
fun MainComponent(modifier: Modifier = Modifier, page: NautilusPage, pageSetter: (NautilusPage) -> Unit){

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        NavigationMenu(Modifier, page, pageSetter)
        PageComponent(modifier, page = page)
    }
}

@Composable
fun NavigationMenu(modifier: Modifier, page: NautilusPage, pageSetter: (NautilusPage) -> Unit){
    Row {
        TextButton(onClick = {
            pageSetter(NautilusPage.DIVES_LIST)
        }) {
            Text(text = "Liste des plongées")
        }
        TextButton(onClick = {
            pageSetter(NautilusPage.CREATE_DIVE)
        }) {
            Text(text = "Création de plongée")
        }
    }
}

@Composable
fun PageComponent(modifier: Modifier, page: NautilusPage){
    TestView()
    /*when(page){
        NautilusPage.DIVES_LIST -> DiveListPlaceHolder()
        NautilusPage.CREATE_DIVE -> NewDive().DiveForm {}
        NautilusPage.LOGIN -> LoginComponent(Modifier)
    }*/
}

@Composable
fun DiveListPlaceHolder(){
    Text(text = "Bonjour, voici la liste")

}


@Composable
fun FakeDiveList()
{
    /*
    val fakeDiveList = listOf(
        FakeDive(date="2024-04-04", hour="9h", depth=50, location="Astan", nbTakenSpots=5, nbSpots=15),
        FakeDive(date="2024-04-12", hour="18h", depth=20, location="L'ile Verte", nbTakenSpots=1, nbSpots=10),
        FakeDive(date="2024-05-22", hour="9h", depth=40, location="Les Trepieds", nbTakenSpots=2, nbSpots=2, isRegistered = true),
        FakeDive(date="2026-01-01", hour="18h", depth=20, location="L'lle Verte", nbTakenSpots=1, nbSpots=4),
        FakeDive(date="2026-12-30", hour="18h", depth=20, location="L'ile Verte", nbTakenSpots=1, nbSpots=12, isRegistered = true),
        FakeDive(date="2030-12-26", hour="18h", depth=20, location="Lille Verte", nbTakenSpots=1, nbSpots=4, isRegistered = true)
    )*/
    val randomDives = mutableListOf<DiveDataclass>()

    for (i in 1..100) {
        val year = Random.nextInt(2024, 2031)
        val month = Random.nextInt(1, 13)
        val day = Random.nextInt(1, 29)
        val date = String.format("%04d-%02d-%02d", year, month, day)

        val hour = if (Random.nextBoolean()) "9h" else "18h"
        val depth = Random.nextInt(10, 51)
        val location = listOf("Astan", "L'ile Verte", "Les Trepieds").random()
        val nbTakenSpots = Random.nextInt(1, 11)
        val nbSpots = Random.nextInt(nbTakenSpots, 21)
        val isRegistered = Random.nextInt(1,5) == 1

        randomDives.add(DiveDataclass(date=date, hour=hour, depth=depth, location=location, nbTakenSpots=nbTakenSpots, nbSpots=nbSpots, isRegistered = isRegistered))
    }
    val randomDivesSnapshot = SnapshotStateList<DiveDataclass>()
    randomDivesSnapshot.addAll(randomDives)
    DiveListView(dives = randomDivesSnapshot, adapter = DiveAdapter(randomDivesSnapshot))
}


@Composable
fun DiveListView(
    dives: SnapshotStateList<DiveDataclass>,
    adapter: DiveAdapter,
    modifier: Modifier = Modifier
) {
    Text(text = dives.size.toString() + " plongées trouvées")

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            RecyclerView(context).apply {
                layoutManager = LinearLayoutManager(context)
                this.adapter = adapter
            }
        }
    )
}

@Composable
fun TestView() {
    val dives: SnapshotStateList<DiveDataclass> = remember {
        mutableStateListOf()
    }

    val error = remember {
        mutableStateOf("")
    }

    val adapter = remember { DiveAdapter(dives) }

    OutlinedButton(onClick = {
        Thread {
            try {
                val fetchedDives = APICall.fetchDives()
                dives.clear()
                dives.addAll(fetchedDives)
                adapter.notifyDataSetChanged()
                error.value = ""
            } catch (ex: Exception) {
                error.value = ex.message.toString()
            }
        }.start()
    }) {
        Text(text = "Fetch dives")
    }

    Text(text = error.value, color = Color.Red)

    DiveListView(dives = dives, adapter = adapter)
}

@Preview(showBackground = true)
@Composable
fun CreateDivePreview(){
    ProjetAndroidTheme {
        MainComponent(page = NautilusPage.CREATE_DIVE){

        }
    }
}