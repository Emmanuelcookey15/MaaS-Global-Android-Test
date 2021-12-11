package com.maasglobal.whimtest.presentation.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.maasglobal.whimtest.BuildConfig
import com.maasglobal.whimtest.R
import com.maasglobal.whimtest.databinding.FragmentRouteBinding
import com.maasglobal.whimtest.presentation.util.State
import com.maasglobal.whimtest.presentation.util.bitmapDescriptorFromVector
import com.maasglobal.whimtest.presentation.util.snack
import com.maasglobal.whimtest.presentation.viewmodel.RouteViewModel
import com.maasglobal.whimtest.presentation.viewmodel.ShareViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RouteFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentRouteBinding? = null

    private val binding get() = _binding!!

    private val viewModel: RouteViewModel by viewModels()

    private lateinit var sharedModel: ShareViewModel

    private lateinit var map: GoogleMap
    private lateinit var deviceLatLng: LatLng
    private lateinit var poiLatLng: LatLng


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRouteBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.mapView.onCreate(savedInstanceState)

        binding.mapView.onResume() // needed to get the map to display immediately

        binding.mapView.getMapAsync(this)




        try {
            MapsInitializer.initialize(requireActivity().applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedModel = activity?.run {
            ViewModelProvider(this)[ShareViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

    }

    override fun onMapReady(p0: GoogleMap) {

        map = p0

        val deviceLatLng = sharedModel.latLngDevice.value
        val poiLatLng = sharedModel.latLngPOI.value

        val markerDeviceLatLng = MarkerOptions()
            .position(deviceLatLng!!)
            .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_round_near_me_24))
            .title("")
        val markerPoiLatLng = MarkerOptions()
            .position(poiLatLng!!)
            .title("")
            .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_baseline_place_24))

        map.addMarker(markerDeviceLatLng)
        map.addMarker(markerPoiLatLng)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poiLatLng, 12f))

        val fromDeviceLatLng = deviceLatLng.latitude.toString() + "," + deviceLatLng.longitude.toString()
        val toPoiLatLng = poiLatLng.latitude.toString() + "," + poiLatLng.longitude.toString()

        viewModel.getRoutes(fromDeviceLatLng, toPoiLatLng, BuildConfig.GMP_KEY)


        viewModel.pointData.observe(viewLifecycleOwner, Observer { it ->

            when (it.status) {
                State.Status.SUCCESS -> {
                    val point = it?.data?.routes?.get(0)?.overviewPolyline?.points

                    val direction = buildString {
                        it?.data?.routes?.get(0)?.legs?.get(0)?.steps?.forEach { step ->
                            this.append("- ")
                            this.append(step.htmlInstructions?.let { it1 -> HtmlCompat.fromHtml(it1, 0) })
                            this.append("\n")
                        }
                    }

                    point?.let { it1 -> drawPolyline(it1) }

                    binding.tvDirection.text = direction

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


    private fun drawPolyline(points: String){
        val polyline = PolylineOptions()
            .addAll(PolyUtil.decode(points))
            .width(8f)
            .color(Color.BLUE)
        map.addPolyline(polyline)
    }


    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.onDestroy()
        _binding = null
    }


    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }


}