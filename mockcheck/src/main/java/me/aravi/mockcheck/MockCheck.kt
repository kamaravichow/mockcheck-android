package me.aravi.mockcheck

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@SuppressLint("MissingPermission")
class MockCheck(val context: Context) {
    private val isMockLocation: MutableLiveData<Boolean> = MutableLiveData(false)
    private val listener = locationListener(isMockLocation)
    private var locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager


    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            locationManager.requestLocationUpdates(
                LocationManager.FUSED_PROVIDER,
                2000L,
                0f,
                listener
            )
        } else {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                2000L,
                0f,
                listener
            )
        }
    }

    fun isMockedLocation(): Boolean? {
        return isMockLocation.value
    }

    fun observerMockChanges(): LiveData<Boolean> {
        return isMockLocation
    }

    fun developerOptionsStatus(): Boolean {
        return DevOpts.developerEnabled(context)
    }

    fun isEmulated(): Boolean {
        return EmulatorOpts.isEmulator()
    }


    internal class locationListener(private val isMockL: MutableLiveData<Boolean>) :
        LocationListener {
        private var numGoodReadings: Int = 0
        private var lastMockLocation: Location? = null

        override fun onLocationChanged(location: Location) {
            var isMock = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                isMock = location.isMock
            } else {
                isMock = location.isFromMockProvider
            }
            isMockL.postValue(isMock)

            if (isMock) {
                lastMockLocation = location
                numGoodReadings = 0
            } else numGoodReadings = Math.min(numGoodReadings + 1, 1000000) // Prevent overflow

            // We only clear that incident record after a significant show of good behavior
            if (numGoodReadings >= 20) lastMockLocation = null

            // If there's nothing to compare against, we have to trust it
            if (lastMockLocation == null) return isMockL.postValue(true)

            // And finally, if it's more than 1km away from the last known mock, we'll trust it
            val d: Float = location.distanceTo(lastMockLocation)
            isMockL.postValue(d > 1000)
        }

    }


}