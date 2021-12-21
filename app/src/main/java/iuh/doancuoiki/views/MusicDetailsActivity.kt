package iuh.doancuoiki.views

import android.content.Intent
import android.os.Bundle
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import iuh.doancuoiki.R
import iuh.doancuoiki.adapters.ViewPagerAdapter
import iuh.doancuoiki.objects.Song
import kotlinx.android.synthetic.main.activity_music_details.*


class MusicDetailsActivity : AppCompatActivity() {
    var song : Song ?=null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_details)
        ic_back.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }
        val bundle = intent.extras
        val id = bundle!!.getString("song")
        Song.get(id!!)
            .addOnSuccessListener{ documentSnapshot ->
                song = Song(documentSnapshot)
                namesong.text =   song!!.name
                singer.text =  song!!.singer
                text_view.text = song!!.view.toString()
                song!!.setPic(this,imageSong)
                ratingBar.rating = song!!.rating!!.toFloat()
                rating_number.text = song!!.rating!!.toFloat().toString()
            }
        setUpTabs()
        }
    private fun setUpTabs() {
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val tabs = findViewById<TabLayout>(R.id.tab_layout)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(LyricsFragment(), "Lyrics")
        adapter.addFragment(RecommendFragment(), "Recommend")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
    @JvmName("getSong1")
    public fun getSong(): Song? {
        return song
    }

}