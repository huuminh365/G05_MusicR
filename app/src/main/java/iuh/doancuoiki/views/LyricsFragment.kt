package iuh.doancuoiki.views


import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.ScrollingMovementMethod
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import iuh.doancuoiki.R
import iuh.doancuoiki.objects.Song
import kotlinx.android.synthetic.main.fragment_lyrics.*


class LyricsFragment : Fragment() {
    var song : Song?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val id = requireActivity().intent.extras!!.getString("song")
        Song.get(id!!)
            .addOnSuccessListener{ documentSnapshot ->
                song = Song(documentSnapshot)
                val lyris = song!!.lyrics
                val regex = Regex("\\[[A-Z|a-z|0-9|#|/]*\\]")
                val spannable = SpannableString(lyris)
                val matches = regex.findAll(lyris as CharSequence)
                matches.forEach {match ->
                    match.range.forEach {
                        val startl = it
                        val endl = it + match.value.length/match.range.count()
                        spannable.setSpan(ForegroundColorSpan(Color.rgb(70,166,25)),startl,endl, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                }
                lyrics.setText(spannable, TextView.BufferType.SPANNABLE)
            }
        return inflater.inflate(R.layout.fragment_lyrics, container, false)
    }
}