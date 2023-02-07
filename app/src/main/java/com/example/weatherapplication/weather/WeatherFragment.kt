package com.example.weatherapplication.weather

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapplication.R
import com.example.weatherapplication.dagger.MainApp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.weatherapplication.databinding.FragmentWeatherBinding
import com.example.weatherapplication.history.HistoryRecord
import com.example.weatherapplication.weather.model.GeographicalObject
import javax.inject.Inject

class WeatherFragment : Fragment(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding: FragmentWeatherBinding

    @Inject
    lateinit var viewModel: WeatherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainApp.appComponent.inject(this)


        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.geographicObject.observe(viewLifecycleOwner) {
            if (it == null) {
                setViewErrorMessage()
            } else {
                setGeographicalObjectView(it)
                viewModel.saveRecord(
                    HistoryRecord(it.locationName, it.latitude, it.longitude)
                )
            }
        }

        binding.insertButton.setOnClickListener {
            val locationName = binding.objectNameEditText.text.toString()
            if (locationName.isNotBlank())
                viewModel.directGeocode(locationName)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.disposeAll()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val record = getRecordFromArguments()
        record?.run { viewModel.reverseGeocode(latitude, longitude) }

        map.setOnMapClickListener { marker ->
            val latitude = marker.latitude
            val longitude = marker.longitude

            viewModel.reverseGeocode(latitude, longitude)
        }
    }

    private fun setGeographicalObjectView(geographicalObject: GeographicalObject) {
        with(geographicalObject) {
            setLocationName(locationName)
            setCoordinates(latitude, longitude)
            setTemperature(temperature)
            setMarker(LatLng(latitude, longitude))
        }
    }

    private fun setLocationName(locationName: String) {
        binding.cityTextView.text = locationName
    }

    private fun setViewErrorMessage() {
        binding.objectNameTextView.text = getString(R.string.object_name_error)
        binding.cityTextView.text = getString(R.string.object_name)
        binding.latitudeTextView.text = getString(R.string.latitude)
        binding.longitudeTextView.text = getString(R.string.longitude)
        map.clear()
    }

    private fun setTemperature(temperature: Double) {
        binding.objectNameTextView.text = getString(R.string.temperature, temperature.toString())
    }

    private fun setCoordinates(latitude: Double, longitude: Double) {
        binding.latitudeTextView.text = getString(R.string.latitude_template, latitude)
        binding.longitudeTextView.text = getString(R.string.longitude_template, longitude)
    }

    private fun setMarker(latLng: LatLng) {
        map.clear()
        map.addMarker(MarkerOptions().position(latLng))
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
    }

    private fun getRecordFromArguments(): HistoryRecord? =
        if (isDeprecated())
            @Suppress("DEPRECATION")
            arguments?.getParcelable(HistoryRecord::class.simpleName)
        else
            arguments?.getParcelable(HistoryRecord::class.simpleName, HistoryRecord::class.java)

    private fun isDeprecated() = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU

    companion object {
        fun newInstance(record: HistoryRecord): WeatherFragment {
            val args = Bundle()
            args.putParcelable(HistoryRecord::class.simpleName, record)
            val fragment = WeatherFragment()
            fragment.arguments = args

            return fragment
        }
    }
}