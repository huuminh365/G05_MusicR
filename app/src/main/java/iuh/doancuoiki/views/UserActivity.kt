package iuh.doancuoiki.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import iuh.doancuoiki.R
import iuh.doancuoiki.utils.FirebaseUtils.firebaseAuth


import kotlinx.android.synthetic.main.layout_user.*

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_user)
        homeButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        loveButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        recommendButton.setOnClickListener{
            startActivity(Intent(this, RecommendActivity::class.java))
        }
        sing_out.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
        }
        sing_in.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}