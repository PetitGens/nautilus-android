package fr.iut.gon.info.td3.projetAndroid
/*
import android.content.Context
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

class DiveList {
    @Composable
    fun DiveListCompose(
        viewGroup: ViewGroup,
        dives: List<FakeDive>
    ) {
        ComposeView(viewGroup.context).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val recyclerView = RecyclerView(viewGroup.context)
                recyclerView.layoutManager = LinearLayoutManager(viewGroup.context)
                val adapter = DiveAdapter(dives)
                recyclerView.adapter = adapter
            }
        }
    }
    @Composable
    fun PlongeesList(
        plongees: List<FakeDive>,
        modifier: Modifier = Modifier
    ) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                RecyclerView(context).apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = PlongeeAdapter(plongees)
                }
            }
        )
    }
}*/