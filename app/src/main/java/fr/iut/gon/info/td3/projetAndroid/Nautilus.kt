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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.internal.NavigationMenu
import fr.iut.gon.info.td3.projetAndroid.ui.theme.ProjetAndroidTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

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
    when(page){
        NautilusPage.DIVES_LIST -> DiveListPlaceHolder()
        NautilusPage.CREATE_DIVE -> NewDive().DiveForm {}
        NautilusPage.LOGIN -> LoginComponent(Modifier)
    }
}

@Composable
fun DiveListPlaceHolder(){
    Text(text = "Bonjour, voici la liste")
}

@Preview(showBackground = true)
@Composable
fun CreateDivePreview(){
    ProjetAndroidTheme {
        MainComponent(page = NautilusPage.CREATE_DIVE){

        }
    }
}