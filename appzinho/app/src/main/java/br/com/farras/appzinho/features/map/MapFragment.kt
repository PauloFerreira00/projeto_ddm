package br.com.farras.appzinho.features.map

import android.app.Activity
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.farras.appzinho.R
import br.com.farras.appzinho.helpers.getMarkerIconFromDrawable
import br.com.farras.appzinho.models.Event
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.support.v4.toast
import org.koin.android.viewmodel.ext.android.viewModel

class MapFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: MapViewModel by viewModel()

    private lateinit var mMap: GoogleMap
    private lateinit var mLastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private lateinit var activity: AppCompatActivity

    companion object {
        private const val MY_PERMISSION_CODE = 1000
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity as AppCompatActivity
    }

    override fun onStop() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        super.onStop()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isAccessFineLocationPermissionGranted()) {
                mMap.isMyLocationEnabled = true
            }
        } else {
            mMap.isMyLocationEnabled = true
        }

        mMap.setOnMarkerClickListener { marker ->
            toast(marker!!.tag.toString())
            false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isAccessFineLocationPermissionGranted()) {
                        if (checkLocationPermission())
                            setupLocationResources()
                        mMap.isMyLocationEnabled = true

                    }
                } else {
                    toast("Permissão negada")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
        markEvents()
    }

    private fun markEvents() {
        viewModel.getEvents().observe(this, Observer { result ->
            if (result.success != null) {
                val events = result.success
                events.forEach { event ->
                    addMarker(event)
                }
            } else {
                result.failure?.message?.let { toast(it) }
            }
        })
    }

    private fun addMarker(event: Event) {
        val markerOptions = MarkerOptions()
        markerOptions.title(event.name)
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.marker, null)
        drawable?.let {
            val icon = activity.getMarkerIconFromDrawable(it)
            markerOptions.icon(icon)
            markerOptions.position(LatLng(event.latitude, event.longitude))
            val marker = mMap.addMarker(markerOptions)
        }
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkLocationPermission()) {
                setupLocationResources()
            } else {
                setupLocationResources()
            }
        }
    }

    private fun isAccessFineLocationPermissionGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this.activity.applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun setupLocationResources() {
        buildLocationRequest()
        buildLocationCallback()

        context?.let { context ->
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val cu = CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            location.latitude,
                            location.longitude
                        ), 18f
                    )
                    mMap.animateCamera(cu)
                }
            }
            .addOnFailureListener {
//                toast("Erro ao buscar localização atual!")
            }
    }

    private fun buildLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                mLastLocation = locationResult!!.locations[locationResult.locations.size - 1]
            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }

    private fun checkLocationPermission(): Boolean {
        if (isAccessFineLocationPermissionGranted().not()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                setupRequestPermissions()
            } else {
                setupRequestPermissions()
            }
            return false
        } else
            return true
    }

    private fun setupRequestPermissions() {
        ActivityCompat.requestPermissions(
            this.activity, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            MY_PERMISSION_CODE
        )
    }
}