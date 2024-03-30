package fr.iut.gon.info.td3.projetAndroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class DiveAdapter(private val dives: List<FakeDive>) : RecyclerView.Adapter<DiveAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val diveDate: TextView = itemView.findViewById(R.id.dive_date)
        val diveHourLocation: TextView = itemView.findViewById(R.id.dive_hour_location)
        val diveDepth: TextView = itemView.findViewById(R.id.dive_depth)
        val diveSpots: TextView = itemView.findViewById(R.id.dive_spots)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dive_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dives.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dive = dives[position]
        holder.diveDate.text = dive.date
        holder.diveHourLocation.text = "${dive.hour} à \"${dive.location}\""
        holder.diveDepth.text = "${dive.depth}m de profondeur"
        holder.diveSpots.text = "${dive.nbTakenSpots}/${dive.nbSpots}"

        if (dive.isRegistered) {
            val backgroundColor = ContextCompat.getColor(holder.itemView.context, R.color.purple_200)
            holder.itemView.setBackgroundColor(backgroundColor)
        }
        else {
            val backgroundColor = ContextCompat.getColor(holder.itemView.context, android.R.color.transparent)
            holder.itemView.setBackgroundColor(backgroundColor)
        }




        holder.itemView.setOnClickListener {

        }
    }
}