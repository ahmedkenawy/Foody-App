package com.a7medkenawy.foody.ui.fragments.detailsfragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.a7medkenawy.foody.R
import com.a7medkenawy.foody.models.Result
import kotlinx.android.synthetic.main.fragment_over_view.view.*
import org.jsoup.Jsoup


class OverViewFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_over_view, container, false)

        val args = arguments
        val myBundle: Result = args?.getParcelable("recipesBundle")!!

        view.main_imageView.load(myBundle?.image)
        view.title_textView.text = myBundle?.title
        view.likes_textView.text = myBundle?.aggregateLikes.toString()
        view.time_textView.text = myBundle?.readyInMinutes.toString()
        myBundle.summary.let { description ->
            val summary = Jsoup.parse(description).text()
            view.summary_textView.text = summary
        }


        if (myBundle?.vegetarian == true) {
            view.vegetarian_imageView.setColorFilter(ContextCompat.getColor(requireContext(),
                R.color.green))
            view.vegetarian_textView.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.green))
        }

        if (myBundle?.vegan == true) {
            view.vegan_imageView.setColorFilter(ContextCompat.getColor(requireContext(),
                R.color.green))
            view.vegan_textView.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.green))
        }

        if (myBundle?.glutenFree == true) {
            view.gluten_free_imageView.setColorFilter(ContextCompat.getColor(requireContext(),
                R.color.green))
            view.gluten_free_textView.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.green))
        }

        if (myBundle?.dairyFree == true) {
            view.dairy_free_imageView.setColorFilter(ContextCompat.getColor(requireContext(),
                R.color.green))
            view.dairy_free_textView.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.green))
        }

        if (myBundle?.veryHealthy == true) {
            view.healthy_imageView.setColorFilter(ContextCompat.getColor(requireContext(),
                R.color.green))
            view.healthy_textView.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.green))
        }

        if (myBundle?.cheap == true) {
            view.cheap_imageView.setColorFilter(ContextCompat.getColor(requireContext(),
                R.color.green))
            view.cheap_textView.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.green))
        }


        return view
    }

}