package com.ayberk.earthquakeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ayberk.earthquakeapp.adapter.EarthquakeAdapter
import com.ayberk.earthquakeapp.databinding.FragmentHomeBinding
import com.ayberk.earthquakeapp.viewmodel.EarthquakeViewModel
import com.huawei.hms.maps.MapView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var isBackPressed = false
    private lateinit var adapter: EarthquakeAdapter
    private val viewModel : EarthquakeViewModel by viewModels()
    private var mMapView: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            isBackPressed = true
        }

        initRecycler()
        fecthEarthquakes()

        viewModel.getEarthquake().observe(viewLifecycleOwner, Observer { t ->
            t?.let {
                adapter.setEarthquakeList(t.result)
            }
        })
        return view
    }

    fun initRecycler(){
        adapter = EarthquakeAdapter()
        binding.rcyclerEarthquakes.adapter = adapter

        binding.rcyclerEarthquakes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    fun fecthEarthquakes(){
        CoroutineScope(Dispatchers.Main).async {
            viewModel.LoadEarthquake()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mMapView?.onDestroy()
    }

}