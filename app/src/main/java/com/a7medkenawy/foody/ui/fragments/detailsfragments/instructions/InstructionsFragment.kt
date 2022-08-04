package com.a7medkenawy.foody.ui.fragments.detailsfragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.a7medkenawy.foody.R
import com.a7medkenawy.foody.databinding.FragmentInstructionsBinding
import com.a7medkenawy.foody.models.Result


class InstructionsFragment : Fragment() {

    private lateinit var binding: FragmentInstructionsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInstructionsBinding.inflate(layoutInflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipesBundle")

        binding.instructionWebview.webViewClient = object : WebViewClient() {}
        binding.instructionWebview.loadUrl(myBundle!!.sourceUrl!!)



        return binding.root
    }

}