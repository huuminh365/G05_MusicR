package iuh.doancuoiki.adapters


import android.content.Context
import android.content.Intent
import android.os.Bundle
import iuh.doancuoiki.objects.Song
import iuh.doancuoiki.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.song_information.view.*
import com.squareup.picasso.Picasso
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import iuh.doancuoiki.views.MusicDetailsActivity
import java.util.*
import kotlin.collections.ArrayList

class FilterAdapters(var countryList: MutableList<Song>, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var countryFilterList = ArrayList<Song>()

    init {
        countryFilterList = countryList as ArrayList<Song>
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(countryFilterList.get(position));
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.song_information, parent, false))
    }

    override fun getItemCount(): Int {
        return countryFilterList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: Song): Unit {
            itemView.singer.text = model.singer
            itemView.name.text = model.name
            Picasso.get().load(model.image)
                .placeholder(R.drawable.ic_broken_img)
                .into(itemView.image)
            itemView.setOnClickListener {
                val intent = Intent(context, MusicDetailsActivity::class.java)
                val bundle = Bundle()
                bundle.putString("song", model.id)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }

        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    countryFilterList = countryList as ArrayList<Song>
                } else {
                    val resultList = ArrayList<Song>()
                    for (row in countryList) {
                        if (row.name?.toLowerCase(Locale.ROOT)
                                ?.contains(constraint.toString().toLowerCase()) == true ||
                            row.lyrics?.toLowerCase(Locale.ROOT)
                                ?.contains(constraint.toString().toLowerCase()) == true ||
                            row.singer?.toLowerCase(Locale.ROOT)
                                ?.contains(constraint.toString().toLowerCase()) == true
                        ) {
                            resultList.add(row)
                        }
                    }
                    countryFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                if (countryFilterList.isEmpty()){
                    Toast.makeText(context, "The song is not found!! \n Recommend: \n Try to find another keyword", Toast.LENGTH_LONG).show()
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<Song>
                notifyDataSetChanged()
            }
        }
    }
}
