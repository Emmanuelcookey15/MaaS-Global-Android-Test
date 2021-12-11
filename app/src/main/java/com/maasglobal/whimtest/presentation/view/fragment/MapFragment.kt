package com.maasglobal.whimtest.presentation.view.fragment

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.maasglobal.whimtest.R
import com.maasglobal.whimtest.databinding.FragmentMapBinding
import com.maasglobal.whimtest.presentation.util.State
import com.maasglobal.whimtest.presentation.util.bitmapDescriptorFromVector
import com.maasglobal.whimtest.presentation.util.snack
import com.maasglobal.whimtest.presentation.viewmodel.MapViewModel
import com.maasglobal.whimtest.presentation.viewmodel.ShareViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var _binding: FragmentMapBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MapViewModel by viewModels()

    private lateinit var sharedModel: ShareViewModel


    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var lastLocation: Location


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.mapView.onCreate(savedInstanceState)

        binding.mapView.onResume() // needed to get the map to display immediately

        binding.mapView.getMapAsync(this)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

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

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        setUpMap()

        setUpObservers()

    }



    private fun setUpMap() {
        if (!(ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val coord = "${location.latitude}|${location.longitude}"
                viewModel.loadArticlesNearby(coord)
                val currentLatLng = LatLng(location.latitude, location.longitude)
                sharedModel.latLngDevice.value = currentLatLng
                placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }



    private fun setUpObservers() {
        viewModel.listOfGeoSearchData.observe(viewLifecycleOwner, Observer {

            when (it.status) {
                State.Status.SUCCESS -> {
                    val data = it?.data?.query?.geoSearches
                    data?.forEach {
                        createMultipleMarkers(it.lat, it.lon, it.title, it.pageid)
                    }
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



    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)

        val titleStr = getAddress(location)  // add these two lines
        markerOptions.title(titleStr)
        markerOptions.icon(bitmapDescriptorFromVector(requireActivity(), R.drawable.ic_round_near_me_24))

        map.addMarker(markerOptions)
    }



    private fun createMultipleMarkers(
        latitude: Double?,
        longitude: Double?,
        title: String?,
        pageId: Int?
    ): Marker? {
        return map.addMarker(
            MarkerOptions()
                .position(LatLng(latitude!!, longitude!!))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(pageId.toString())
                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_baseline_place_24))

        )
    }




    override fun onMarkerClick(p0: Marker): Boolean {

        if (p0.position != sharedModel.latLngDevice.value) {
            sharedModel.idPOI.value = p0.snippet
            sharedModel.latLngPOI.value = p0.position
            findNavController().navigate(R.id.action_mapFragment_to_locationDetailFragment)
        }



        return false
    }








    private fun getAddress(latLng: LatLng): String {

        val geocoder = Geocoder(requireContext())
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                for (i in 0 until address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                }
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }

        return addressText
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