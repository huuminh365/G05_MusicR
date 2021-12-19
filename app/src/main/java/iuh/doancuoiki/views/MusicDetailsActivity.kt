package iuh.doancuoiki.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import iuh.doancuoiki.R
import iuh.doancuoiki.adapters.MusicAdapters
import iuh.doancuoiki.objects.Song
import kotlinx.android.synthetic.main.activity_music_details.*


class MusicDetailsActivity : AppCompatActivity() {
    var song: Song?=null
    val songs = ArrayList<Song>()
    var adapterQuickView: MusicAdapters? = null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_details)
        recommend_button.setOnClickListener {
            startActivity(Intent(this, RecommendActivity::class.java))
        }
        val bundle = intent.extras
        val id = bundle!!.getString("song")
        Song.get(id!!)
            .addOnSuccessListener{ documentSnapshot ->
                song = Song(documentSnapshot)
                namesong.text = "Name song: " +  song!!.name
                singer.text = "Name singer: " + song!!.singer
                text_view.text = "View: " + song!!.view
                val lyris = song!!.lyrics

                val regex = Regex("\\[[A-Z|a-z|0-9|#|/]*\\]")
                val spannable = SpannableString(lyris)
                val matches = regex.findAll(lyris as CharSequence)
                matches.forEach {match ->
                    match.range.forEach {
                        val startl = it
                        val endl = it + match.value.length/match.range.count()
                        spannable.setSpan(ForegroundColorSpan(Color.rgb(70,166,25)),startl,endl, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                }
                lyrics_view.setText(spannable, TextView.BufferType.SPANNABLE)
                song!!.setPic(this,imageSong)
                ratingBar.rating = song!!.rating!!.toFloat()
                rating_number.text = song!!.rating!!.toFloat().toString()
            }

    }
}