package iuh.doancuoiki.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import iuh.doancuoiki.R
import iuh.doancuoiki.utils.FirebaseUtils.firebaseAuth
import kotlinx.android.synthetic.main.activity_signout.*

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signout)
        homeButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        loveButton.setOnClickListener {
            startActivity(Intent(this,FavActivity::class.java))
        }
        sing_out.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
        }
        sing_in.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
        }
        top_music_btn.setOnClickListener {
            startActivity(Intent(this, TopMusicActivity::class.java))
        }
        chord_btn.setOnClickListener {
            startActivity(Intent(this, ChordActivity::class.java))
        }
    }
}