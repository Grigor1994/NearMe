package com.grigor.nearme.ui.home

import android.annotation.SuppressLint
import android.content.IntentSender
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.grigor.nearme.HomeActivity
import com.grigor.nearme.R
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.get
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(), OnMapReadyCallback, PermissionsListener {

    val viewModel: HomeViewModel = get()

    private var permissionsManager: PermissionsManager = PermissionsManager(this)
    private lateinit var mapBoxMap: MapboxMap

    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    //location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var mLastLocation: Location

    //
    private val calendar = Calendar.getInstance()
    private val timeZoneId = TimeZone.getDefault().id
    private val timeZoneFromApi = TimeZone.getTimeZone(timeZoneId)
    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    private val formattedDateForApi = formatter.format(calendar.time)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

// Mapbox access token is configured here. This needs to be called either in your application
// object or in the same activity which contains the mapview.
        Mapbox.getInstance(requireContext(), getString(R.string.access_token))
//        Log.i("HomeFragment","${longitude.toString()}")
//        Log.i("HomeFragment","${latitude.toString()}")
// This contains the MapView in XML and needs to be called after the access token is configured.
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        showEnableLocationSetting()
        createLocationRequest()
        buildLocationCallback()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )


//        viewModel.sunResponseLiveData.observe(viewLifecycleOwner, Observer {
//            Log.i("HomeFragment", "${it.results.sunrise}")
//            Log.i("HomeFragment", "${it.results.sunset}")
//        })
        fab.setOnClickListener {
            getCurrentLocation()
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest().apply {
            interval = 10000
            fastestInterval = 5000
            numUpdates = 1
        }
    }

    private fun showEnableLocationSetting() {
        activity?.let {
            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

            val task = LocationServices.getSettingsClient(it)
                .checkLocationSettings(builder.build())

            task.addOnSuccessListener { response ->
                response.locationSettingsStates
            }
            task.addOnFailureListener { e ->
                if (e is ResolvableApiException) {
                    try {
                        // Handle result in onActivityResult()
                        e.startResolutionForResult(
                            it,
                            HomeActivity.LOCATION_SETTING_REQUEST
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                    }
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapBoxMap = mapboxMap

        /**
         * The API result for sunset/sunrise times doesn't include the date, so we
         * concatenate the date we used in the request earlier.
         */
        viewModel.sunResponseLiveData.observe(viewLifecycleOwner, Observer {
//            val sunriseApiDateString = formattedDateForApi + " " + it.results.sunrise
            val sunsetApiDateString = formattedDateForApi + " " + it.results.sunset
            val currentTime: Date = Calendar.getInstance().time
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.ENGLISH)
            simpleDateFormat.applyPattern("hh:mm aa")
            if (simpleDateFormat.format(currentTime) == "12:58 PM") {
                mapboxMap.setStyle(Style.DARK) { style ->
// Map is set up and the style has loaded.
                    enableLocationComponent(style)
                }
            } else {
                mapboxMap.setStyle(Style.OUTDOORS) { style ->
                    enableLocationComponent(style)
                }
            }
//            Log.i(
//                "HomeFragment",
//                "Sunrise: ${formatDateResultFromApi(
//                    sunriseApiDateString,
//                    timeZoneFromApi
//                )}"
//            )
//            Log.i(
//                "HomeFragment",
//                "Sunset: ${formatDateResultFromApi(
//                    sunsetApiDateString,
//                    timeZoneFromApi
//                )}"
//            )

        })

    }

    @SuppressLint("MissingPermission")
    private fun enableLocationComponent(loadedMapStyle: Style) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(requireContext())) {

// Create and customize the LocationComponent's options
            val customLocationComponentOptions = LocationComponentOptions.builder(requireContext())
                .trackingGesturesManagement(true).elevation(5f)
                .accuracyAlpha(.6f)
                .accuracyColor(ContextCompat.getColor(requireContext(), R.color.mediumPurple))
                .build()

            val locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(requireContext(), loadedMapStyle)
                    .locationComponentOptions(customLocationComponentOptions)
                    .build()

// Get an instance of the LocationComponent and then adjust its settings
            mapBoxMap.locationComponent.apply {

// Activate the LocationComponent with options
                activateLocationComponent(locationComponentActivationOptions)

// Enable to make the LocationComponent visible
                isLocationComponentEnabled = true

// Set the LocationComponent's camera mode
                cameraMode = CameraMode.TRACKING

// Set the LocationComponent's render mode
                renderMode = RenderMode.NORMAL
            }
        } else {
            permissionsManager = PermissionsManager(this)
            permissionsManager.requestLocationPermissions(requireActivity())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onExplanationNeeded(permissionsToExplain: List<String>) {
//        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show()
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            enableLocationComponent(mapBoxMap.style!!)
        } else {
            requireActivity().finish()
        }
    }

    private fun buildLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                mLastLocation = p0!!.locations[p0.locations.size - 1]
                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude


                viewModel.getSunData("$latitude", "$longitude", formattedDateForApi)
                Log.i("HomeFragment", latitude.toString())
                Log.i("HomeFragment", longitude.toString())
            }
        }
    }

    private fun getCurrentLocation() {
        val position = CameraPosition.Builder()
            .target(LatLng(latitude, longitude))
            .zoom(17.0)
            .bearing(360.0)
            .tilt(30.0)
            .build()
        mapView.getMapAsync { map ->
            map.animateCamera(CameraUpdateFactory.newCameraPosition(position), 7000)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.search_menu, menu)
//        val searchItem = menu?.findItem(R.id.menu_search)
//        if (searchItem != null) {
//            val searchView = searchItem.actionView as SearchView
//            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    return true
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    return true
//                }
//            })
//        }
    }

    //    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.menu_search -> {
//                val action = MainFragmentDirections.actionMainFragmentToSearchFragment()
//                findNavController().navigate(action)
//                return true
//            }
//            else -> {
//                return super.onOptionsItemSelected(item)
//            }
//        }
//    }
    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mapView != null) {
            mapView.onDestroy()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
