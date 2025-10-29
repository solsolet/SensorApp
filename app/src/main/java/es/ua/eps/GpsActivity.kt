package es.ua.eps.sensorapp

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.health.connect.datatypes.ExerciseRoute
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import es.ua.eps.sensorapp.databinding.GpsActivityBinding

class GpsActivity : AppCompatActivity() {

    private lateinit var bindings : GpsActivityBinding
    private lateinit var locationListener : LocationListener
    private lateinit var locationManager : LocationManager
    private lateinit var provider : String

    private val LOCATION_PERMISSION_REQUEST = 1001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindings = GpsActivityBinding.inflate(layoutInflater)
        with(bindings) {
            setContentView(root)
            buttonLocation.setOnClickListener { showLocation() }
        }
    }

    private fun showLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Make sure gPS active
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!isGPSEnabled) {
            requestLocationServicesActivation()
            return
        }

        // Make sure permissions active
        if (ContextCompat.checkSelfPermission(
                this@GpsActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
            return
        }

        // If we got permission
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                updateLocation(location)
                // Si vols, pots deixar d’escoltar després de rebre la primera ubicació:
                locationManager.removeUpdates(this)
            }

            override fun onProviderDisabled(provider: String) {}
            override fun onProviderEnabled(provider: String) {}
            @Deprecated("Deprecated in API 29")
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }
        // Sol·licita actualitzacions de posició
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0L,
            0f,
            locationListener
        )
    }
    private fun updateLocation(location: Location) {
        val lat = location.latitude
        val lon = location.longitude
        bindings.textViewLocation.text = "Latitud: $lat\nLongitud: $lon"
    }
    private fun requestLocationServicesActivation() {
        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert))
            .setMessage(getString(R.string.locationServiceNotEnabled))
            .setPositiveButton(getString(R.string.openLocationSettings)) { _: DialogInterface, _: Int ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .create()

        dialog.show()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            showLocation()
        } else {
            Toast.makeText(this, "Denied location permission", Toast.LENGTH_SHORT).show()
        }
    }

}