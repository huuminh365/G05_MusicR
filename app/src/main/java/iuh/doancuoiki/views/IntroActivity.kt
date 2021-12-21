package iuh.doancuoiki.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import iuh.doancuoiki.R
import iuh.doancuoiki.intro.IntroSlide
import iuh.doancuoiki.intro.IntroSlideAdapter
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {
    private val introSlideAdapter = IntroSlideAdapter(
        listOf(
            IntroSlide(
                "Home App",
                "Welcome to our app!! Here you can view and search songs",
                R.drawable.home
            ),
            IntroSlide(
                "Favorite Songs",
                "This page displays your favorite songs",
                R.drawable.favorite
            ),
            IntroSlide(
                "Lyrics",
                "This page displays lyrics and song information",
                R.drawable.lyrics
            ),
            IntroSlide(
                "Recommended Song",
                "This page recommend songs similar to the one you're watching",
                R.drawable.recommend
            ),
            IntroSlide(
                "Chords",
                "This page shows basic guitar chords",
                R.drawable.chord_image
            )
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        introSliderViewPaper.adapter = introSlideAdapter
        setupIndicators()
        setCurrentIndicator(0)
        introSliderViewPaper.registerOnPageChangeCallback(object:
            ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        buttonNext.setOnClickListener{
            if(introSliderViewPaper.currentItem + 1 < introSlideAdapter.itemCount){
                introSliderViewPaper.currentItem +=1
            }else{
                Intent(applicationContext, SplashActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        textSkipIntro.setOnClickListener {
            Intent(applicationContext, SplashActivity::class.java).also {
                startActivity(it)
            }
        }

    }
    private fun setupIndicators(){
        val indicator = arrayOfNulls<ImageView>(introSlideAdapter.itemCount)
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
