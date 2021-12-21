package iuh.doancuoiki.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import iuh.doancuoiki.R
import iuh.doancuoiki.objects.Chord

class ChordAdapter(private val chord: List<Chord>): RecyclerView.Adapter<ChordAdapter.IntroSlideViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        return IntroSlideViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_chord,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(chord[position])
    }
    override fun getItemCount(): Int {

        return chord.size
    }

    inner class IntroSlideViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)
        private val imageIcon = view.findViewById<ImageView>(R.id.imageSlideIcon)
        fun bind(chord: Chord){
            textTitle.text = chord.title
            imageIcon.setImageResource(chord.icon)
        }
    }
}