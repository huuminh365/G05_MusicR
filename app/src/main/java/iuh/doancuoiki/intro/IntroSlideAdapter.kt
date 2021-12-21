package iuh.doancuoiki.intro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import iuh.doancuoiki.R


class IntroSlideAdapter(private val inTroSlides: List<IntroSlide>): RecyclerView.Adapter<IntroSlideAdapter.IntroSlideViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        return IntroSlideViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_container,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(inTroSlides[position])
    }
    override fun getItemCount(): Int {
        return inTroSlides.size
    }
    inner class IntroSlideViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)
        private val textDescription = view.findViewById<TextView>(R.id.textDescription)
        private val imageIcon = view.findViewById<ImageView>(R.id.imageSlideIcon)
        fun bind(inTroSlide: IntroSlide){
            textTitle.text = inTroSlide.title
            textDescription.text = inTroSlide.desc
            imageIcon.setImageResource(inTroSlide.icon)
        }
    }
}