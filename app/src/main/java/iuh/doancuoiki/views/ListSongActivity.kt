package iuh.doancuoiki.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import iuh.doancuoiki.R
import iuh.doancuoiki.adapters.MusicAdapters
import iuh.doancuoiki.objects.Song
import kotlinx.android.synthetic.main.activity_listsong.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.create_account
import kotlinx.android.synthetic.main.lyrics.*
import kotlinx.android.synthetic.main.song_information.*

class ListSongActivity : AppCompatActivity() {
    val songs = ArrayList<Song>()
    var adapterQuickView: MusicAdapters? = null
//    var  clickChecker: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listsong)

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

//        button_loveSong.setOnClickListener {
//            clickChecker = clickChecker xor true
//            if (clickChecker) {
//                button_loveSong.setBackgroundResource(R.drawable.heart_on)
//            }
//            else {
//                button_loveSong.setBackgroundResource(R.drawable.heart_off)
//            }
//        }
    }
}