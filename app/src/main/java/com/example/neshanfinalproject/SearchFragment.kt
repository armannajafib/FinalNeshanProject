package com.example.neshanfinalproject


import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carto.styles.MarkerStyle
import com.carto.styles.MarkerStyleBuilder
import com.carto.ui.MapView
import com.carto.utils.BitmapUtils
import org.neshan.common.model.LatLng
import org.neshan.kotlinsample.adapter.SearchAdapter
import org.neshan.mapsdk.model.Marker
import org.neshan.servicessdk.search.NeshanSearch
import org.neshan.servicessdk.search.model.Item
import org.neshan.servicessdk.search.model.Location
import org.neshan.servicessdk.search.model.NeshanSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment() : Fragment(), SearchAdapter.OnSearchItemListener {

    private val TAG = "Search"
    private lateinit var editText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var items: MutableList<Item>
    private lateinit var adapter: SearchAdapter

    // map UI element
    private var centerMarker: Marker = Marker(LatLng(0.0, 0.0), null)
    private var userLocation: LatLng? = null
    private lateinit var mapListenerI: MapInterface
    private val markers = ArrayList<Marker>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.search_fragment, container, false)

if(arguments == null){
    Log.i("argument","argument is null")
}
        arguments?.let {
            val latitude = it.getDouble("latitude")
            val longitude = it.getDouble("longitude")
            userLocation = LatLng(latitude, longitude)
        }

        initLayoutReferences(view)
        return view
    }
    fun onLocationUpdated(latLng: LatLng) {
        // Update your Fragment with the new location
        userLocation = latLng
        // Do something with the updated location
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MapInterface) {
            mapListenerI = context
        } else {
            throw RuntimeException("$context must implement MapInteractionListener")
        }
    }

    private fun initLayoutReferences(view: View) {
        // Initializing views
        initViews(view)
        // Initializing mapView element
        //        initMap()

        //listen for search text change
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                search(s.toString())
                Log.i(TAG, "afterTextChanged: $s")
            }
        })

        editText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                    //                    closeKeyBoard()
                    search(editText.text.toString())
                }
                return false
            }
        })
    }

    private fun initViews(view: View) {
        editText= view.findViewById(R.id.editText)
        recyclerView = view.findViewById(R.id.recyclerViewF)
        items = mutableListOf()
        adapter = SearchAdapter(items, this@SearchFragment)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }


    private fun search(term: String) {
        val searchPosition = userLocation
        if (searchPosition == null) {
            Toast.makeText(requireContext(), "موقعیت کاربر مشخص نشده است.", Toast.LENGTH_SHORT).show()
            return
        }
        updateCenterMarker(searchPosition)
        NeshanSearch.Builder("service.6eb3511fca1a48b0ad77c792a17bc337")
            .setLocation(searchPosition)
            .setTerm(term)
            .build().call(object : Callback<NeshanSearchResult?> {
                override fun onResponse(
                    call: Call<NeshanSearchResult?>,
                    response: Response<NeshanSearchResult?>
                ) {
                    if (response.code() == 403) {
                        Toast.makeText(
                            requireContext(),
                            "کلید دسترسی نامعتبر",
                            Toast.LENGTH_LONG
                        ).show()
                        return
                    }
                    if (response.body() != null) {
                        val result = response.body()
                        items = result!!.items
                        adapter.updateList(items)
                    }
                }

                override fun onFailure(call: Call<NeshanSearchResult?>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)
                    Toast.makeText(requireContext(), "ارتباط برقرار نشد!", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun addMarker(LatLng: LatLng, size: Float): Marker? {
        val marker = Marker(LatLng, getMarkerStyle(size))
        mapListenerI.getMapView().addMarker(marker)
        markers.add(marker)
        return marker
    }

    private fun updateCenterMarker(latLng: LatLng?) {
        latLng?.let {
            centerMarker.latLng = it
        } ?: run {
            Log.e(TAG, "LatLng is null")
        }
    }

    private fun getMarkerStyle(size: Float): MarkerStyle? {
        val styleCreator = MarkerStyleBuilder()
        styleCreator.size = size
        styleCreator.bitmap = BitmapUtils.createBitmapFromAndroidBitmap(
            BitmapFactory.decodeResource(
                resources, R.drawable.ic_marker
            )
        )
        return styleCreator.buildStyle()
    }

    override fun onSearchItemClick(LatLng: LatLng?,title:String?) {
//      closeKeyBoard()
//        mapListenerI.clearMarkers()
        adapter.updateList(java.util.ArrayList())
        mapListenerI.getMapView().moveCamera(LatLng, 0f)
      mapListenerI.getMapView().setZoom(16f, 0f)
      addMarker(LatLng!!, 30f)
    }
}