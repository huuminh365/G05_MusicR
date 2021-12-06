package iuh.doancuoiki.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import iuh.doancuoiki.R
import iuh.doancuoiki.extensions.Extensions.toast
import iuh.doancuoiki.objects.Song
import iuh.doancuoiki.utils.FirebaseUtils.firebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.login_button
import org.json.JSONObject

import java.lang.Exception


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_listsong)
        callAPI()

    }

    fun responseApiSuccess(response: JSONObject) {
        Log.i("request-success", response.toString());
    }

    fun responseApiError(error: Exception) {
        Log.e("request-error", error.toString());
    }

    fun callAPI() {
        var queryObject: JSONObject = JSONObject();
        queryObject.put("id", "1");

        try {
            RequestJSON.instance().setURL("https://ptud2.herokuapp.com/").setMethod("GET").setQuery(queryObject).send(this, this::responseApiSuccess, this::responseApiError);
            toast("succesfully")
        } catch (error: Exception) {
            error.printStackTrace();

        }
    }
}