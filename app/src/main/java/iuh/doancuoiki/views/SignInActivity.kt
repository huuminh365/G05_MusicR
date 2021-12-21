package iuh.doancuoiki.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseUser
import iuh.doancuoiki.R
import iuh.doancuoiki.extensions.Extensions.toast
import iuh.doancuoiki.utils.FirebaseUtils.firebaseAuth
/** fix missing imports **/
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception

class SignInActivity : AppCompatActivity() {
    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInInputsArray: Array<EditText>
    lateinit var callbackManage : CallbackManager
    private var EMAIL = "email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInInputsArray = arrayOf(username, password)
        create_account.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
            finish()
        }

        sign_in.setOnClickListener {
            signInUser()

        }

        login_button.setOnClickListener {
            login_button.setReadPermissions(listOf(EMAIL))
            callbackManage = CallbackManager.Factory.create()

            LoginManager.getInstance().registerCallback(callbackManage, object :
                FacebookCallback<LoginResult> {
                override fun onCancel() {
                    TODO("Not yet implemented")
                }

                override fun onError(error: FacebookException) {
                    TODO("Not yet implemented")
                }

                override fun onSuccess(result: LoginResult) {
                    val  graphRequest = GraphRequest.newMeRequest(result?.accessToken){ obj, response ->

                        try{

                            if(obj?.has("id") == true){
                                Log.d("FACEBOOKDATA", obj.getString("name"))
                                Log.d("FACEBOOKDATA", obj.getString("email"))
                                Log.d("FACEBOOKDATA", obj.getString("picture"))
                            }
                            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                            toast("signed in successfully")

                        }catch (e: Exception){
                            toast("sign in failed")
                        }
                    }

                    val param  = Bundle()
                    param.putString("fields", "name, email, id, picture.type(large)")
                    graphRequest.parameters = param
                    graphRequest.executeAsync()
                }
            })

        }
    }


    /* check if there's a signed-in user*/

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser // check xem da dang nhap chua, yes-> home
        user?.let {
            startActivity(Intent(this, SplashActivity::class.java))
            toast("welcome back")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)  {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManage.onActivityResult(requestCode, resultCode, data)
        setContentView(R.layout.activity_home)
    }


    private fun notEmpty(): Boolean = signInEmail.isNotEmpty() && signInPassword.isNotEmpty()

    private fun signInUser() {
        signInEmail = username.text.toString().trim()
        signInPassword = password.text.toString().trim()
        if (notEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        startActivity(Intent(this, HomeActivity::class.java))
                        toast("signed in successfully")
                        finish()
                    } else {
                        toast("sign in failed")
                    }
                }
        } else {
            signInInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        }
    }
}