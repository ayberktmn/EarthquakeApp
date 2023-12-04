package com.ayberk.earthquakeapp

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ayberk.earthquakeapp.databinding.FragmentDetailsBinding
import com.ayberk.earthquakeapp.model.Earthquake
import com.ayberk.earthquakeapp.model.Result
import com.ayberk.earthquakeapp.viewmodel.EarthquakeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    lateinit var resultList: Result
    private val viewDetailsModel: EarthquakeViewModel by viewModels()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        fetchMovies()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDetailsModel.getEarthquake().observe(viewLifecycleOwner, object : Observer<Earthquake> {
            override fun onChanged(value: Earthquake) {
                if (value != null) {

                    arguments?.let {

                        val id = DetailsFragmentArgs.fromBundle(it).id
                        println("gelenid:${id}")
                        resultList = value.result[id]
                        binding.txtDetailTitle.text = resultList.title

                        // Parse the date and time
                        val dateTimeFormat =
                            SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault())
                        val dateObj = dateTimeFormat.parse(resultList.date)

                        // Format date
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val formattedDate = dateFormat.format(dateObj)

                        // Format time
                        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                        val formattedTime = timeFormat.format(dateObj)

                        binding.txtDetailTitle.text = resultList.title
                        binding.txtDetailDate.text = formattedDate
                        binding.txtDetailTime.text = formattedTime
                        binding.txtDetailMag.text = resultList.mag.toString()
                        startBlinking()
                        handler.postDelayed({
                            stopBlinking()
                        }, 5000)
                        binding.txtDetailDepth.text = resultList.depth.toString()
                        binding.txtDetailLocation.text = resultList.geojson.coordinates.joinToString(separator = "\n")


                        binding.imgLocation.setOnClickListener {
                            val coordinates = floatArrayOf(
                            resultList.geojson.coordinates[0].toFloat(),
                            resultList.geojson.coordinates[1].toFloat()
                        )
                            stopBlinking()
                            val locationId = DetailsFragmentDirections.actionDetailsFragmentToMapFragment(coordinates)
                            findNavController().navigate(locationId)
                            println("kordinatlar:${coordinates[0]}, ${coordinates[1]}")
                        }
                    }
                } else {
                    binding.txtDetailTitle.text = "Error!"
                }
            }

        })
    }

    fun fetchMovies() {

        CoroutineScope(Dispatchers.Main).async {
            viewDetailsModel.LoadEarthquake()

        }
    }


    private val handler = Handler()
    private val blinkRunnable = object : Runnable {
        override fun run() {
            toggleVisibility(binding.txtDetailMag)
            handler.postDelayed(this, BLINK_INTERVAL.toLong())
        }
    }

    // Start blinking when needed (e.g., in onViewCreated)
    private fun startBlinking() {
        handler.postDelayed(blinkRunnable, BLINK_INTERVAL.toLong())
    }

    // Stop blinking when needed (e.g., in onDestroyView)
    private fun stopBlinking() {
        handler.removeCallbacks(blinkRunnable)
        binding.txtDetailMag.visibility =
            View.VISIBLE // Ensure the TextView is visible when not blinking
    }

    // Toggle the visibility of the TextView
    private fun toggleVisibility(textView: TextView) {
        if (textView.visibility == View.VISIBLE) {
            textView.visibility = View.INVISIBLE
        } else {
            textView.visibility = View.VISIBLE
        }
    }
}

private const val BLINK_INTERVAL = 500
