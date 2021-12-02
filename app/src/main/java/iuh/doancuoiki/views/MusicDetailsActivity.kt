package iuh.doancuoiki.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import iuh.doancuoiki.R
import iuh.doancuoiki.objects.Song
import kotlinx.android.synthetic.main.lyrics.*

class MusicDetailsActivity : AppCompatActivity() {
    var song : Song? =  null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lyrics)

        val bundle = intent.extras
        val id = bundle!!.getString("song")

        Song.get(id!!)
            .addOnSuccessListener{ documentSnapshot ->
                song = Song(documentSnapshot)
                namesong.text = song!!.name
                singer.text = song!!.singer
                song!!.setPic(this, imageSong)
                lyrics.text = song!!.lyrics
            }
    }
}