package com.maasglobal.whimtest.presentation.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.maasglobal.whimtest.R
import com.maasglobal.whimtest.databinding.FragmentWikiWebBinding
import com.maasglobal.whimtest.presentation.util.MyWebClient
import com.maasglobal.whimtest.presentation.util.NetworkHelper
import com.maasglobal.whimtest.presentation.util.snack
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WikiWebFragment : Fragment() {

    @Inject
    lateinit var networkHelper: NetworkHelper

    private var _binding: FragmentWikiWebBinding? = null

    private val binding get() = _binding!!

    private var url:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString("url").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWikiWebBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(networkHelper.isNetworkConnected()){
            setUpWebView(url)
        }
        else{
            view.snack("No Internet Connection. Please check that you are connected to the network")
        }

        actionListener(view)

    }


    private fun actionListener(view: View) {
        binding.imageView44.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_wikiWebFragment_to_locationDetailFragment)
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView(url: String){
        binding.wv.webViewClient = context?.let { MyWebClient(binding.pgLoading, it) }!!

        binding.wv.apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                settings.safeBrowsingEnabled = true
            }
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}