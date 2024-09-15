package com.example.neshanfinalproject

import org.neshan.common.model.LatLng
import org.neshan.mapsdk.MapView
import org.neshan.mapsdk.model.Marker

interface MapInterface {
//    fun moveCamera(latLng: LatLng, zoom: Float)
//    fun setZoom(zoom: Float, animate: Float)
//    fun addMarker(marker: Marker)
//    fun removeMarker(marker: Marker)

    fun getMapView(): MapView

    fun clearMarkers()

}