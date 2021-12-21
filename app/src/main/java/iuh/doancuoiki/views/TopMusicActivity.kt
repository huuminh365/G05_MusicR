package iuh.doancuoiki.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import iuh.doancuoiki.R
import iuh.doancuoiki.adapters.MusicAdapters
import iuh.doancuoiki.objects.Song
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_top_music.*
import kotlinx.android.synthetic.main.activity_top_music.chord_btn
import kotlinx.android.synthetic.main.activity_top_music.homeButton
import kotlinx.android.synthetic.main.activity_top_music.songList

class TopMusicActivity : AppCompatActivity() {
    val songs = ArrayList<Song>()
    val songviews = ArrayList<Song>()
    var adapterQuickView: MusicAdapters ?= null
    var adapterViewSong : MusicAdapters ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_music)
        userView.setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
        }
        homeButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        loveButton.setOnClickListener {
            startActivity(Intent(this, FavActivity::class.java))
        }
        chord_btn.setOnClickListener {
            startActivity(Intent(this, ChordActivity::class.java))
        }
        adapterQuickView = MusicAdapters(this, R.layout.songs_top, songs)
        songList.adapter = adapterQuickView

        Song.getRating()
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
        adapterViewSong = MusicAdapters(this, R.layout.songs_top, songviews)
        songList_view.adapter = adapterViewSong
        Song.getView()
            .addOnSuccessListener {  querySnapshot ->
                val documents = querySnapshot.documents
                for (doc in documents) {
                    val songa = Song(doc)
                    songviews.add(songa)
                    adapterViewSong!!.notifyDataSetChanged()
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