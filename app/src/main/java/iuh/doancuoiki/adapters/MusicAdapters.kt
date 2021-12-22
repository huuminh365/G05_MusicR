package iuh.doancuoiki.adapters
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import iuh.doancuoiki.R
import iuh.doancuoiki.objects.Song
import iuh.doancuoiki.utils.FirebaseUtils
import iuh.doancuoiki.views.MusicDetailsActivity


class MusicAdapters(val context: Context, val layoutId: Int, val songs: ArrayList<Song>):
    RecyclerView.Adapter<MusicAdapters.ViewHolder>(){
    public val fav_songs = ArrayList<String>();
    val string_id = ArrayList<String>()
    val list_id = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View = LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false)
        return ViewHolder(layoutId, view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Apply data into each view
        val song = songs[position]
        holder.name.text = song.name
        holder.singer.text = song.singer
        song.setPic(context, holder.image)
        if(song.status == true) holder.btnFavorite.isChecked = true

        holder.btnFavorite.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked) {
                fav_songs.add(song.id.toString())
                FirebaseUtils.db
                    .collection("users")
                    .document(FirebaseUtils.firebaseAuth.currentUser!!.uid)
                    .update(
                        "fav_songs",
                        FieldValue.arrayUnion(song.id.toString())
                    )
                FirebaseUtils.db
                    .collection("PTUD")
                    .document(song.id.toString())
                    .update(
                        "status",
                        true)
                Toast.makeText(context, "add to favorite songs successfully!!", Toast.LENGTH_SHORT).show()
            }else{

                fav_songs.remove(song.id.toString())
                FirebaseUtils.db
                    .collection("users")
                    .document(FirebaseUtils.firebaseAuth.currentUser!!.uid)
                    .update(
                        "fav_songs",
                        FieldValue.arrayRemove(song.id.toString())
                    )
                FirebaseUtils.db
                    .collection("PTUD")
                    .document(song.id.toString())
                    .update(
                        "status",
                        false)
                refresh()
                Toast.makeText(context, "remove favorite song huhu", Toast.LENGTH_SHORT).show()
            }
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, MusicDetailsActivity::class.java)
            val bundle = Bundle()
            bundle.putString("song", song.id)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    }
    override fun getItemCount(): Int {
        return songs.size
    }

    class ViewHolder(layoutId: Int, itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView
        var singer : TextView
        var image : ImageView
        var btnFavorite : CheckBox
        var status : Boolean ?= false

        init {
            name = itemView.findViewById(R.id.name)
            singer = itemView.findViewById(R.id.singer)
            image = itemView.findViewById(R.id.image)
            btnFavorite = itemView.findViewById(R.id.cbHeart)
        }
    }
    fun refresh() {
        songs.clear()
        this.notifyDataSetChanged()
        FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseUtils.firebaseAuth.currentUser!!.uid).get()
            .addOnSuccessListener { querySnapshot ->
                val documents = querySnapshot.data
                if (documents != null) {
                    for (doc in documents) {
                        string_id.add(doc.toString())
                        val regex = Regex("[0-9]+")
                        val matches = regex.findAll(doc.toString() as CharSequence).map{it.value}.toList()
                        for(id in matches){
                            list_id.add(id)
                            Song.get(id)
                                .addOnSuccessListener { documentSnapShot ->
                                    val songa = Song(documentSnapShot!!)

                                    songs.add(songa)
                                    Log.d("name: ", songa.name.toString())
                                    this.notifyItemInserted(songs.size - 1)

                                }
                                .addOnFailureListener { e ->
                                    Log.e(
                                        "",
                                        "fromCloudFirestore: Error loading ContactInfo data from Firestore - " + e.message
                                    );
                                };
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e(
                    "",
                    "fromCloudFirestore: Error loading ContactInfo data from Firestore - " + e.message
                );
            };
    }


}