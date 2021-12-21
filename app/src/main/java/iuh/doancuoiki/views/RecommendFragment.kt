package iuh.doancuoiki.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import iuh.doancuoiki.R
import iuh.doancuoiki.adapters.FilterAdapters
import iuh.doancuoiki.adapters.MusicAdapters
import iuh.doancuoiki.api.APIViewModelFactory
import iuh.doancuoiki.objects.Recommend
import iuh.doancuoiki.objects.Song
import iuh.doancuoiki.repository.Repository
import kotlinx.android.synthetic.main.fragment_recommend.*

class RecommendFragment : Fragment() {
    val songs = ArrayList<Song>()
    var adapterQuickView: MusicAdapters? = null
    val recommendList = ArrayList<Int>()
    private lateinit var viewModel : APIViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val repository = Repository()
        var view = inflater.inflate(R.layout.fragment_recommend, container, false)
        adapterQuickView = MusicAdapters(requireContext(), R.layout.song_information, songs)
        view.findViewById<RecyclerView>(R.id.songList).adapter = adapterQuickView
        val viewModelFactory = APIViewModelFactory(repository)
        viewModel = ViewModelProvider(this , viewModelFactory).get(APIViewModel::class.java)
        viewModel.getRecommend()
        viewModel.myResponse.observe(requireActivity(), Observer { response ->
            if(response.isSuccessful){
                var recommends : Recommend?
                recommends = response.body()?.get((requireContext() as MusicDetailsActivity).getSong()!!.id!!.toInt())
                recommendList.addAll(recommends!!.recommend)
                for(id in recommendList){
                    Song.get(id.toString())
                        .addOnSuccessListener { documentSnapShot ->
                            val songa = Song(documentSnapShot!!)
                            songs.add(songa)
                            Log.d("name: ", songa.name.toString())
                            adapterQuickView!!.notifyItemInserted(songs.size - 1)

                        }
                        .addOnFailureListener { e ->
                            Log.e(
                                "",
                                "fromCloudFirestore: Error loading ContactInfo data from Firestore - " + e.message
                            );
                        };
                }
                Log.d("Response" , response.body()?.get(2)?.recommend.toString())
            }else{
                Log.d("Response" , response.errorBody().toString())
            }
        })
        return view
    }


}