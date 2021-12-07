package com.maasglobal.whimtest.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.maasglobal.whimtest.R
import com.maasglobal.whimtest.databinding.FragmentLocationDetailBinding
import com.maasglobal.whimtest.presentation.util.State
import com.maasglobal.whimtest.presentation.util.snack
import com.maasglobal.whimtest.presentation.view.adapter.ImageWikiAdapter
import com.maasglobal.whimtest.presentation.viewmodel.DetailsViewModel
import com.maasglobal.whimtest.presentation.viewmodel.ShareViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationDetailFragment : Fragment() {


    private var _binding: FragmentLocationDetailBinding? = null

    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()

    private lateinit var sharedModel: ShareViewModel

    private lateinit var adapter: ImageWikiAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLocationDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedModel = activity?.run {
            ViewModelProvider(this).get(ShareViewModel::class.java)
        } ?: throw Exception("Invalid Activity")


        setupViews()

        actionListener(view)

        setUpObservers()

        binding.cardGoToRoute.setOnClickListener {
            findNavController().navigate(R.id.action_locationDetailFragment_to_routeFragment)
        }

        binding.btnGetThere.setOnClickListener {
            findNavController().navigate(R.id.action_locationDetailFragment_to_routeFragment)
        }

    }

    private fun setupViews() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = ImageWikiAdapter( mutableListOf(), requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun actionListener(view: View) {
        binding.imageView44.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_locationDetailFragment_to_mapFragment)
        }
    }

    private fun setUpObservers() {

        val pageId = sharedModel.idPOI.value

        viewModel.getDetailOfPOI(pageId)


        viewModel.geoDetailData.observe(viewLifecycleOwner, Observer {

            when (it.status) {
                State.Status.SUCCESS -> {

                    binding.tvPoiTitle.text = it.data?.title
                    binding.tvPoiDescription.text = it?.data?.contentmodel
                    adapter.setImageWikiList(it?.data?.images)
                }
                State.Status.ERROR -> {
                    val message = it.message.toString()
                    view?.snack(message)
                }
                State.Status.LOADING -> {
                    view?.snack("Please wait!!...")
                }
            }


        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}