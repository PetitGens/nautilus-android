package fr.iut.gon.info.td3.projetAndroid

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

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
            FakeDive(date="MAUVAISE ACTIVITÉ", hour="9h", depth=50, location="Astan", nbTakenSpots=5, nbSpots=15),
            FakeDive(date="MAUVAISE ACTIVITÉ", hour="18h", depth=20, location="L'ile Verte", nbTakenSpots=1, nbSpots=10),
            FakeDive(date="MAUVAISE ACTIVITÉ", hour="9h", depth=40, location="Les Trepieds", nbTakenSpots=2, nbSpots=2),
            FakeDive(date="MAUVAISE ACTIVITÉ", hour="18h", depth=20, location="L'lle Verte", nbTakenSpots=1, nbSpots=4),
            FakeDive(date="MAUVAISE ACTIVITÉ", hour="18h", depth=20, location="L'ile Verte", nbTakenSpots=1, nbSpots=12),
            FakeDive(date="MAUVAISE ACTIVITÉ", hour="18h", depth=20, location="Lille Verte", nbTakenSpots=1, nbSpots=4)
        )
        val randomDives = mutableListOf<FakeDive>()

        for (i in 1..100) {
            val year = Random.nextInt(2024, 2031)
            val month = Random.nextInt(1, 13)
            val day = Random.nextInt(1, 29)
            val date = String.format("%04d-%02d-%02d", year, month, day)

            val hour = if (Random.nextBoolean()) "9h" else "18h"
            val depth = Random.nextInt(10, 51)
            val location = listOf("Astan", "L'ile Verte", "Les Trepieds", "Lille Verte").random()
            val nbTakenSpots = Random.nextInt(1, 11)
            val nbSpots = Random.nextInt(nbTakenSpots, 21)

            randomDives.add(FakeDive(date="MAUVAISE ACTIVITÉ", hour=hour, depth=depth, location=location, nbTakenSpots=nbTakenSpots, nbSpots=nbSpots))
        }

        val combinedList = fakeDiveList + randomDives


        // adapter
        val adapter = DiveAdapter(combinedList)
        recyclerView.adapter = adapter
    }


}