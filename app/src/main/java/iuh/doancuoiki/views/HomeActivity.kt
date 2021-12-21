package iuh.doancuoiki.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import iuh.doancuoiki.R
import iuh.doancuoiki.adapters.FilterAdapters
import iuh.doancuoiki.adapters.MusicAdapters
import iuh.doancuoiki.objects.Song
import iuh.doancuoiki.utils.FirebaseUtils
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    val songs = ArrayList<Song>()
    var adapter: FilterAdapters? = null
    var adapterQuickView: MusicAdapters? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userbtn.setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
        }
        top_music_btn.setOnClickListener{
            startActivity(Intent(this, TopMusicActivity::class.java))
        }
        lovebtn.setOnClickListener {
            startActivity(Intent(this, FavActivity::class.java))
        }
        chord_btn.setOnClickListener {
            startActivity(Intent(this, ChordActivity::class.java))
        }
        adapter = FilterAdapters(getFindSong(), this)
        songList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapterQuickView = MusicAdapters(this, R.layout.song_information, songs)
        songList.adapter = adapterQuickView
        songList.adapter = adapter
        find.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter?.filter?.filter("")
            }

            override fun onTextChanged(cs: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                adapter?.filter?.filter(s)
            }
        })

    }
    private fun getFindSong(): MutableList<Song> {
        val mdList = mutableListOf<Song>()

        val query = FirebaseUtils.db.collection("PTUD")
        query
            .orderBy("name", Query.Direction.DESCENDING)
            .limit(50)
            .get()
            .addOnSuccessListener { docs ->
//                val documents = querySnapshot.documents
                var i = 0
                for (doc in docs) {
//                    toast(i.toString())
                    val songa = Song(doc)
                    mdList.add(songa)
                }
            }
        return mdList
    }


}