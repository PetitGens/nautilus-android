package fr.iut.gon.info.td3.projetAndroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiveAdapter(private val dives: List<FakeDive>) : RecyclerView.Adapter<DiveAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val diveDate: TextView = itemView.findViewById(R.id.dive_date)
        val diveLevel: TextView = itemView.findViewById(R.id.dive_level)
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
        holder.diveLevel.text = dive.level
    }
}