package iuh.doancuoiki.views
import kotlinx.android.synthetic.main.activity_create_account.*


import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import iuh.doancuoiki.R
import iuh.doancuoiki.extensions.Extensions.toast
import iuh.doancuoiki.utils.FirebaseUtils.firebaseAuth
import iuh.doancuoiki.utils.FirebaseUtils.firebaseUser

class CreateAccountActivity : AppCompatActivity() {
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        createAccountInputsArray = arrayOf(username, password, confirm_password)
        create_account.setOnClickListener {
            signIn()
        }

        sign_in.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            toast("please sign into your account")
            finish()
        }
    }

    /* check if there's a signed-in user*/

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            startActivity(Intent(this, MainActivity::class.java))
            toast("welcome back")
        }
    }

    private fun notEmpty(): Boolean = username.text.toString().trim().isNotEmpty() &&
            password.text.toString().trim().isNotEmpty() &&
            confirm_password.text.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() &&
            password.text.toString().trim() == confirm_password.text.toString().trim()
        ) {
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        } else {
            toast("passwords are not matching !")
        }
        return identical
    }

    private fun signIn() {
        if (identicalPassword()) {
            // identicalPassword() returns true only  when inputs are not empty and passwords are identical
            userEmail = username.text.toString().trim()
            userPassword = password.text.toString().trim()

            /*create a user*/
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        toast("created account successfully !")
                        sendEmailVerification()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        toast("failed to Authenticate !")
                    }
                }
        }
    }

    /* send verification email to the new user. This will only
    *  work if the firebase user is not null.
    */

    private fun sendEmailVerification() {
        firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("email sent to $userEmail")
                }
            }
        }
    }
}