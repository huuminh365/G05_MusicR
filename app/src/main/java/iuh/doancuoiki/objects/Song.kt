package iuh.doancuoiki.objects

import android.content.Context
import android.widget.ImageView
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import iuh.doancuoiki.utils.FirebaseUtils
import com.squareup.picasso.Picasso
import iuh.doancuoiki.R

class Song() {
    var id : String?  = null
    var name : String? = null
    var singer : String? = null
    var topic : String? = null
    var image : String? = null
    var rating : Double = 0.0

    constructor(doc : DocumentSnapshot) : this() {
        id = doc.id
        name = doc.getString("name")
        singer = doc.getString("singer")?.replace("['","")
            ?.replace("'","")?.replace("]","")

        topic = doc.getString("topic")
        image = doc.getString("image")
        rating = doc.getDouble("rating")!!
    }

    fun set() : Task<String> {
        val song = hashMapOf(
            "name" to name,
            "singer" to singer,
            "topic" to topic,
            "image" to image,
            "rating" to rating
        )

        if(id != null){
            return FirebaseUtils.db.collection(collection).document(id!!).set(song)
                .continueWith{
                    return@continueWith id
                }
        }else{
            // Return id from the new DocumentReference,
            // if null : return empty string

            return FirebaseUtils.db.collection(collection).add(song)
                .continueWith { task ->
                    return@continueWith task.result?.path ?: ""
                }
        }
    }

    fun setPic(context : Context, imageView : ImageView) {
        if (image != null) {
            // Get download url, and let Picasso load the image url into imageView
            Picasso.get().load(image)
                .placeholder(R.drawable.ic_error)
//                .resize(50, 50)
//                .centerCrop()
                .into(imageView)
        } else {
            Picasso.get().load(R.drawable.ic_error).into(imageView)
        }
    }



    companion object {
        const val collection = "PTUD"

        fun get() : Task<QuerySnapshot> {
            return FirebaseUtils.db.collection(collection).get()
        }

        fun getRecent() : Task<QuerySnapshot> {
            // Get 10 first songs
            return FirebaseUtils.db.collection(collection)
                .orderBy("rating")
                .limit( 10)
                .get()

        }

        fun get(id : String) : Task<DocumentSnapshot> {
            return FirebaseUtils.db.collection(collection).document(id).get()
        }
    }
}