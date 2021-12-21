package iuh.doancuoiki.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import iuh.doancuoiki.R
import iuh.doancuoiki.adapters.ChordAdapter
import iuh.doancuoiki.intro.IntroSlide
import iuh.doancuoiki.intro.IntroSlideAdapter
import iuh.doancuoiki.objects.Chord
import kotlinx.android.synthetic.main.activity_chord.*
import kotlinx.android.synthetic.main.activity_chord.indicatorsContainer
import kotlinx.android.synthetic.main.activity_chord.introSliderViewPaper
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.homeButton
import kotlinx.android.synthetic.main.activity_home.lovebtn
import kotlinx.android.synthetic.main.activity_home.top_music_btn
import kotlinx.android.synthetic.main.activity_home.userbtn
import kotlinx.android.synthetic.main.activity_intro.*

class ChordActivity : AppCompatActivity() {
    private val chordAdapter = ChordAdapter(
        listOf(
            Chord(
                "AM",
                R.drawable.chord_am
            ),
            Chord(
                "B7",
                R.drawable.chord_b7
            ),
            Chord(
                "BBMAJOR",
                R.drawable.chord_bbmajor
            ),
            Chord(
                "CMAJOR",
                R.drawable.chord_cmajor
            ),
            Chord(
                "DM",
                R.drawable.chord_dm
            ),
            Chord(
                "B7",
                R.drawable.chord_b7
            ),
            Chord(
                "E7",
                R.drawable.chord_e7
            ),
            Chord(
                "EM",
                R.drawable.chord_em
            ),
            Chord(
                "FMAJOR",
                R.drawable.chord_fmajor
            ),
            Chord(
                "G7",
                R.drawable.chord_g7
            ),
            Chord(
                "GM",
                R.drawable.chord_gm
            ),
            Chord(
                "GMAJOR",
                R.drawable.chord_gmajor
            ),

        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chord)
        userbtn.setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
        }
        top_music_btn.setOnClickListener{
            startActivity(Intent(this, TopMusicActivity::class.java))
        }
        lovebtn.setOnClickListener {
            startActivity(Intent(this, FavActivity::class.java))
        }
        homeButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        introSliderViewPaper.adapter = chordAdapter
        setupIndicators()
        setCurrentIndicator(0)
        introSliderViewPaper.registerOnPageChangeCallback(object:
            ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
    }
    private fun setupIndicators(){
        val indicator = arrayOfNulls<ImageView>(chordAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8,0,8,0)
        for(i in indicator.indices){
            indicator[i] = ImageView(applicationContext)
            indicator[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_2
                    )
                )
                this?.layoutParams = layoutParams
            }
            indicatorsContainer.addView(indicator[i])
        }
    }
    private fun setCurrentIndicator(index:Int){
        val childCount = indicatorsContainer.childCount
        for ( i in 0 until childCount){
            val imageView = indicatorsContainer[i] as ImageView
            if (i == index){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator
                    )
                )
            } else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_2
                    )
                )
            }

        }
    }
}
