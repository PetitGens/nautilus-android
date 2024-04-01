package fr.iut.gon.info.td3.projetAndroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.iut.gon.info.td3.projetAndroid.ui.theme.ProjetAndroidTheme
import kotlinx.coroutines.launch
import kotlin.random.Random

class Nautilus : ComponentActivity() {
    private val navigationViewModel : NavigationViewModel by viewModels()
    private val divesViewModel : DivesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var pageState : MutableState<NautilusPage?>? = null
        var divesSnapshot: SnapshotStateList<DiveDataclass>? = null
        var adapter = DiveAdapter(emptyList())

        val pageSetter : (NautilusPage) -> (Unit) = {
            navigationViewModel.currentPage.value = it
        }

        val pageObserver = Observer<NautilusPage>{
            pageState?.value = it
        }

        val divesObserver = Observer<List<DiveDataclass>> {
            divesSnapshot?.clear()
            divesSnapshot?.addAll(it)
            adapter.setDives(it)
            adapter!!.notifyDataSetChanged()
            Log.d("DEBUG", "updating dives")
        }

        navigationViewModel.currentPage.observe(this, pageObserver)
        divesViewModel.divesLiveData().observe(this, divesObserver)

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

                    //adapter = remember {DiveAdapter(divesViewModel.getDives())}

                    divesSnapshot = remember {
                        mutableStateListOf<DiveDataclass>().apply {
                            this.addAll(divesViewModel.getDives())
                        }
                    }

                    if (pageState != null && pageState!!.value != null && adapter != null) {
                        MainComponent(
                            page = pageState!!.value!!,
                            pageSetter = pageSetter,
                            dives = divesSnapshot!!,
                            adapter = adapter!!
                        ){
                            divesViewModel.fetchDives()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainComponent(
    modifier: Modifier = Modifier,
    page: NautilusPage,
    adapter: DiveAdapter,
    pageSetter: (NautilusPage) -> Unit,
    dives: SnapshotStateList<DiveDataclass>,
    onFetchDives: () -> Unit
){
    val menuState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val couroutineScope = rememberCoroutineScope()
    ModalNavigationDrawer(drawerContent = {NavigationMenu(
        modifier = Modifier,
        page = page,
        pageSetter = pageSetter,
        onCloseMenu = {
            couroutineScope.launch {
                menuState.close()
            }
        }
    )}, drawerState = menuState) {
        Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { couroutineScope.launch {
                    menuState.open()
                } }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = "ouvrir menu-glissant"
                    )
                }
            }
            PageComponent(modifier, page = page, adapter, dives, onFetchDives)
        }
    }
}

@Composable
fun NavigationMenu(
    modifier: Modifier,
    page: NautilusPage,
    pageSetter: (NautilusPage) -> Unit,
    onCloseMenu: () -> Unit,
){
    Column(modifier = modifier.background(color = Color.Gray).fillMaxHeight()) {
        TextButton(enabled = page != NautilusPage.DIVES_LIST, onClick = {
            pageSetter(NautilusPage.DIVES_LIST)
            onCloseMenu()
        }) {
            Text(text = "Liste des plongées")
        }
        TextButton(enabled = page != NautilusPage.CREATE_DIVE, onClick = {
            pageSetter(NautilusPage.CREATE_DIVE)
            onCloseMenu()
        }) {
            Text(text = "Création de plongée")
        }
        TextButton(onClick = {
            onCloseMenu()
        }) {
            Text(text = "Déconnexion")
        }
    }
}

@Composable
fun PageComponent(
    modifier: Modifier,
    page: NautilusPage,
    adapter: DiveAdapter,
    dives: SnapshotStateList<DiveDataclass>,
    onFetchDives: () -> Unit
){
    when(page){
        NautilusPage.DIVES_LIST -> DiveListView(adapter, dives, onFetchDives)
        NautilusPage.CREATE_DIVE -> NewDive().DiveForm {}
        NautilusPage.LOGIN -> LoginComponent(Modifier)
    }
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
    DiveList(dives = randomDivesSnapshot, adapter = DiveAdapter(randomDivesSnapshot))
}

@Composable
fun DiveList(
    dives: List<DiveDataclass>,
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
fun DiveListView(adapter: DiveAdapter, dives: List<DiveDataclass>, onFetchDives: () -> Unit) {
    val error = remember {
        mutableStateOf("")
    }

    OutlinedButton(onClick = {
        Thread{
            try{
                onFetchDives()
                error.value = ""
            } catch (ex: Exception){
                error.value = ex.message.toString()
            }
        }.start()
    }) {
        Text(text = "Fetch dives")
    }

    Text(text = error.value, color = Color.Red)

    DiveList(dives = dives, adapter = adapter)
}

@Preview(showBackground = true)
@Composable
fun CreateDivePreview(){
    ProjetAndroidTheme {
        //MainComponent(page = NautilusPage.CREATE_DIVE, adapter = DiveAdapter(mutableListOf()), pageSetter = {}, null, onFetchDives = {})
    }
}