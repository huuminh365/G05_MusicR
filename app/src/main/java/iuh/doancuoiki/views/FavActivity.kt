package iuh.doancuoiki.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import iuh.doancuoiki.R
import iuh.doancuoiki.adapters.MusicAdapters
import iuh.doancuoiki.objects.Song
import iuh.doancuoiki.utils.FirebaseUtils
import kotlinx.android.synthetic.main.activity_fav.*
import kotlinx.android.synthetic.main.activity_fav.chord_btn
import kotlinx.android.synthetic.main.activity_fav.homeButton
import kotlinx.android.synthetic.main.activity_fav.songList
import kotlinx.android.synthetic.main.activity_fav.top_music_btn
import kotlinx.android.synthetic.main.activity_fav.userbtn
import kotlinx.android.synthetic.main.activity_home.*

class FavActivity : AppCompatActivity() {
    val string_id = ArrayList<String>()
    val list_id = ArrayList<String>()
    val songs = ArrayList<Song>()
    var adapterQuickView: MusicAdapters? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav)
        userbtn.setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
        }
        top_music_btn.setOnClickListener{
            startActivity(Intent(this, TopMusicActivity::class.java))
        }
        homeButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        chord_btn.setOnClickListener {
            startActivity(Intent(this, ChordActivity::class.java))
        }


        adapterQuickView = MusicAdapters(this, R.layout.song_information, songs)
        songList.adapter = adapterQuickView
        // Get drawable object

        // Create a DividerItemDecoration whose orientation is vertical

        // Set the drawable on it

        FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseUtils.firebaseAuth.currentUser!!.uid).get()
            .addOnSuccessListener { querySnapshot ->
                val documents = querySnapshot.data
                if (documents != null) {
                    for (doc in documents) {
                        string_id.add(doc.toString())
                        val regex = Regex("[0-9]+")
                        val matches = regex.findAll(doc.toString() as CharSequence).map{it.value}.toList()
                        for(id in matches){
                            list_id.add(id)
                            Song.get(id)
                                .addOnSuccessListener { documentSnapShot ->
                                    val songa = Song(documentSnapShot!!)

                                    songs.add(songa)
                                    Log.d("name: ", songa.name.toString())
                                    adapterQuickView!!.notifyItemInserted(songs.size - 1)

                                }
                                .addOnFailureListener { e ->
                                    Log.e(
                                        "",
                                        "fromCloudFirestore: Error loading ContactInfo data from Firestore - " + e.message
                                    );
                                };
                        }
                    }
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