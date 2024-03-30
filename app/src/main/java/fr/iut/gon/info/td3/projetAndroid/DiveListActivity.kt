package fr.iut.gon.info.td3.projetAndroid

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DiveListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.dive_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // RECYCLER VIEW
        val recyclerView = findViewById<RecyclerView>(R.id.diveRecyclerView)

        // LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val fakeDiveList = listOf(
            FakeDive("12/05/2023", "PA-12"),
            FakeDive( "25/07/2023", "PA"),
            FakeDive( "18/09/2023","PO"),
            FakeDive( "02/11/2023","PB")
        )

        // adapter
        val adapter = DiveAdapter(fakeDiveList)
        recyclerView.adapter = adapter
    }


}