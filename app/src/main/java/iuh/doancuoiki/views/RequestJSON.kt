package iuh.doancuoiki.views

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import okhttp3.*
import org.json.JSONObject
import com.android.volley.*;
import com.android.volley.Request
import iuh.doancuoiki.extensions.Extensions.toast
import java.lang.Exception


class RequestJSON {
    private val baseURL: String = "[PROTOCOL]://[HOST]:[PORT]/";
    private var url: String = "";
    private var method: String = "GET";
    private var requestData: JSONObject = JSONObject();
    private var queryString: String = "";

    companion object Factory {
        fun instance(): RequestJSON = RequestJSON();
    }

    fun setURL(url: String): RequestJSON {
        this.url = url;
        return this;
    }

    fun setMethod(method: String): RequestJSON {
        this.method = method.toLowerCase();
        return this;
    }

    fun setData(data: JSONObject): RequestJSON {
        this.requestData = data;
        return this;
    }

    private fun appendQuery(array: Array<String>, element: String): Array<String> {
        val list: MutableList<String> = array.toMutableList();
        list.add(element);

        return list.toTypedArray();
    }

    fun setQuery(query: JSONObject) : RequestJSON {
        // limpa o queryString
        this.queryString = "";

        // obtendo as chaves do json
        val keys = query.keys();
        // criando array para conter as querys
        var querys: Array<String> = arrayOf();

        // obtendo os valores atravéz da chave e adicionando no array
        for(key in keys) {
            querys = this.appendQuery(querys, key + "=" + query.get(key));
        }

        // verifica se existe valores no array
        // para conversão em stringQuery
        if (querys.size > 0) {
            this.queryString += "?";
            val size = querys.size;
            var count = 0;

            while (count < size) {
                var querystring = "";

                if (count == 0) {
                    querystring = querys[count];
                } else {
                    querystring = "&" + querys[count];
                }

                this.queryString += querystring;
                count++;
            }
        }

        return this;
    }

    private fun getMethod(): Int {
        return when(this.method) {
            "get" -> {
                Request.Method.GET;
            }
            "post" -> {
                Request.Method.POST;
            }
            "put" -> {
                Request.Method.PUT;
            }
            "delete" -> {
                Request.Method.DELETE;
            }
            else -> Request.Method.GET;
        }
    }

    fun send(context: Activity, responseListiner: (response: JSONObject) -> Unit, errorListiner: (error: Exception) -> Unit) {
        val queue = Volley.newRequestQueue(context);
        var url = this.baseURL + this.url + this.queryString;
        var data: JSONObject = this.requestData;

        // limpando queryString após ser utilizado
        this.queryString = "";
        // limpando url após ser utilizado
        this.url = "";
        // limpando requestData após ser utilizado
        this.requestData = JSONObject();

        val jsonObjectRequest = JsonObjectRequest(this.getMethod(), url, data, fun (response) {
            responseListiner(response);
        }, fun (error) {
            errorListiner(error);
        })
        // adicionando requesição ao queue
        queue.add(jsonObjectRequest);
    }

}