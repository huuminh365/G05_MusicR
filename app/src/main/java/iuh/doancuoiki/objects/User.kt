package iuh.doancuoiki.objects

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import iuh.doancuoiki.utils.FirebaseUtils

class User() {
    var id: String? = null
    var fav_songs = ArrayList<String>()

    constructor(doc: DocumentSnapshot) : this() {
        id = doc.id
        fav_songs = doc.get("fav_songs") as ArrayList<String>
    }


    companion object {
        const val collection = "users"

        fun get(): Task<QuerySnapshot> {
            return FirebaseUtils.db.collection(collection).get()
        }

        fun getRecent(): Task<DocumentSnapshot> {
            return FirebaseUtils.db.collection("users").document(
                FirebaseUtils.firebaseAuth.currentUser!!.uid
            ).get()

        }

        fun get(id: String): Task<DocumentSnapshot> {
            return FirebaseUtils.db.collection(collection).document(id).get()
        }

        fun get(fav_songs: IntArray): Task<QuerySnapshot> {
            return FirebaseUtils.db.collection(collection).get()
        }
    }

}