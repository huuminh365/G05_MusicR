package iuh.doancuoiki.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import iuh.doancuoiki.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.search_error.*
import kotlinx.android.synthetic.main.search_error.homeButton
import kotlinx.android.synthetic.main.search_error.loveButton
import kotlinx.android.synthetic.main.search_error.recommendButton
import kotlinx.android.synthetic.main.search_error.userView


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        homeButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        loveButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        userView.setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
        }
        recommendButton.setOnClickListener{
            startActivity(Intent(this, RecommendActivity::class.java))
        }
    }
}