package iuh.doancuoiki.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import iuh.doancuoiki.R
import iuh.doancuoiki.objects.Song
import kotlinx.android.synthetic.main.song_information.*

class MusicDetailsActivity : AppCompatActivity() {
    var song : Song? =  null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.song_information)

        var bundle = intent.extras

        var id = bundle!!.getString("PTUD")
        Song.get(id!!)
            .addOnSuccessListener{ documentSnapshot ->
                song = Song(documentSnapshot)
                name.text = song!!.name
                singer.text = song!!.singer
                topic.text = song!!.topic
            }
    }
}