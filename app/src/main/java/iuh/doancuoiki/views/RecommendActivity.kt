package iuh.doancuoiki.views

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView
import iuh.doancuoiki.R
import iuh.doancuoiki.adapters.MusicAdapters
import iuh.doancuoiki.objects.Song
import kotlinx.android.synthetic.main.activity_music_details.*
import kotlinx.android.synthetic.main.activity_recommend.*
import kotlinx.android.synthetic.main.activity_recommend.imageSong

class RecommendActivity : AppCompatActivity() {
    val songs = ArrayList<Song>()
    var song : Song ?=null
    var adapterQuickView: MusicAdapters? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)
        lyrics_btn.setOnClickListener {
            startActivity(Intent(this, MusicDetailsActivity::class.java))
        }
        val bundle = intent.extras
        val id = bundle!!.getString("song")
        Song.get(id!!)
            .addOnSuccessListener{ documentSnapshot ->
                song = Song(documentSnapshot)
                namesong.text = "Name song: " +  song!!.name
                singer.text = "Name singer: " + song!!.singer
                text_view.text = "View: " + song!!.view

                song!!.setPic(this,imageSong)
            }
        adapterQuickView = MusicAdapters(this, R.layout.song_information, songs)
        songList.adapter = adapterQuickView
        Song.getRecent()
            .addOnSuccessListener { querySnapshot ->
                val documents = querySnapshot.documents
                for (doc in documents) {
                    val songa = Song(doc)
                    songs.add(songa)
                    adapterQuickView!!.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { e ->
                Log.e(
                    "",
                    "fromCloudFirestore: Error loading ContactInfo data from Firestore - " + e.message
                );
            };
    }
}