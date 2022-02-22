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


    internal class locationListener(private val isMock: MutableLiveData<Boolean>) :
        LocationListener {
        override fun onLocationChanged(location: Location) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                isMock.postValue(location.isMock)
            } else {
                isMock.postValue(location.isFromMockProvider)
            }
        }

    }


}