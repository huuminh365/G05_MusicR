package iuh.doancuoiki.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import iuh.doancuoiki.R
import iuh.doancuoiki.adapters.MusicAdapters
import iuh.doancuoiki.extensions.Extensions.toast
import iuh.doancuoiki.objects.Song
import kotlinx.android.synthetic.main.activity_song_home.*
import kotlinx.android.synthetic.main.activity_song_home.loveButton
import kotlinx.android.synthetic.main.activity_song_home.userView
import kotlinx.android.synthetic.main.search_error.*
import org.json.JSONObject

import java.lang.Exception


class MainActivity : AppCompatActivity() {

    val songs = ArrayList<Song>()
    var adapterQuickView: MusicAdapters? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_home)
        loveButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        userView.setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
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