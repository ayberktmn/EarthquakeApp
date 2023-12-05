package com.ayberk.earthquakeapp

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_WIFI_STATE
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ayberk.earthquakeapp.databinding.FragmentMapBinding
import com.huawei.hms.location.FusedLocationProviderClient
import com.huawei.hms.location.LocationCallback
import com.huawei.hms.location.LocationRequest
import com.huawei.hms.location.LocationResult
import com.huawei.hms.location.LocationServices
import com.huawei.hms.location.SettingsClient
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapView
import com.huawei.hms.maps.MapsInitializer
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.model.CameraPosition
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.Marker
import com.huawei.hms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var settingsClient: SettingsClient
    private var _binding: FragmentMapBinding? = null
    private var mLocationCallback: LocationCallback? = null
    private val binding get() = _binding!!
    private var hMap: HuaweiMap? = null
    private var mMapView: MapView? = null
    private var currentMarker: Marker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions()


        val mapViewBundle: Bundle? = null
        var mSupportMapFragment: SupportMapFragment? = null
        mSupportMapFragment =
            childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        mSupportMapFragment?.getMapAsync(this@MapFragment)
        mMapView = view.findViewById(R.id.mapView)

        mMapView?.apply {
            onCreate(mapViewBundle)
            getMapAsync(this@MapFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    private fun getLocation() {

        val mLocationRequest = LocationRequest()
        mLocationRequest.interval = 180000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        Toast.makeText(context, "Konumunuz Bulunuyor", Toast.LENGTH_SHORT).show()

        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val lastLocation = locationResult.lastLocation
                val currentLatitude = lastLocation.latitude
                val currentLongitude = lastLocation.longitude
                //  println("Latitude: $currentLatitude, Longitude: $currentLongitude")

                // Haritaya marker ekleme
                addMarker()
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.getMainLooper()
        ).addOnSuccessListener {}.addOnFailureListener {}
    }

    private fun addMarker() {
        // Koordinatları al
        currentMarker?.remove()
        val args = MapFragmentArgs.fromBundle(requireArguments())
        val coordinate = args.coordinates

        // Koordinatı latitude ve longitude olarak ayır
        val latitude = coordinate[1].toDouble()
        val longitude = coordinate[0].toDouble()

        println("Latitude: $latitude, Longitude: $longitude")

        // Marker oluştur
        val markerOptions = MarkerOptions()
            .position(LatLng(latitude, longitude))
            .title("Gerçekleşen Deprem Bölgesi")
            .snippet("${longitude}, ${latitude}")

        // Haritaya ekle
        currentMarker = hMap?.addMarker(markerOptions)

        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 15f)
        hMap?.moveCamera(cameraUpdate)

        Toast.makeText(context, "Deprem Bölgesi Yükleniyor...", Toast.LENGTH_SHORT).show()
    }


    fun checkPermissions() {
        val permissions = arrayOf(
            ACCESS_FINE_LOCATION,
            ACCESS_WIFI_STATE
        )

        val missingPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (missingPermissions.isNotEmpty()) {
            // Eksik izinleri kullanıcıdan iste
            requestPermissions(missingPermissions, PERMISSION_GRANTED)
        } else {
            // İzinler zaten verilmiş, haritayı yükle
        }
    }

    // İzin iste sonuçları için onRequestPermissionsResult yöntemi
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_GRANTED) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // Kullanıcı tüm izinleri kabul etti, haritayı yükle

            } else {
                // Kullanıcı izinleri reddetti, bir mesaj göster
                Toast.makeText(requireContext(), "İzinler reddedildi!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresPermission(allOf = [ACCESS_FINE_LOCATION, ACCESS_WIFI_STATE])
    override fun onMapReady(map: HuaweiMap) {
        hMap = map
        hMap!!.isMyLocationEnabled = true
        getLocation()
        Toast.makeText(requireContext(), "Harita Yükleniyor...", Toast.LENGTH_SHORT).show()
        hMap!!.uiSettings.isMyLocationButtonEnabled = true

    }
    override fun onStart() {
        super.onStart()
        mMapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView?.onDestroy()
    }

    override fun onPause() {
        mMapView?.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mMapView?.onResume()

    }
}