package fr.taize.madrid2018.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.taize.madrid2018.R
import android.app.SearchManager
import android.database.MatrixCursor
import android.support.v7.widget.SearchView
import android.text.TextUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import fr.taize.madrid2018.database.DatabaseAccess
import fr.taize.madrid2018.BuildConfig
import fr.taize.madrid2018.MainActivity
import fr.taize.madrid2018.database.Place


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Map.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Map.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private val TAG = "MapFragment";

    private lateinit var mMap: GoogleMap
    private lateinit var mSearchView: SearchView
    private lateinit var mSearchManager: SearchManager
    private lateinit var mPlacesCursorAdapter: PlacesCursorAdapter
    private lateinit var databaseAccess: DatabaseAccess
    private lateinit var mInitType: String
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mListener: OnFragmentInteractionListener? = null
    private var mLat: Double = 0.0
    private var mLng: Double = 0.0
    private var mMarkerText: String = ""

    companion object {
        const val CITY_CENTER_LATITUDE = 40.4169514
        const val CITY_CENTER_LONGITUDE = -3.7057172
        const val MIN_ZOOM_LEVEL = 13.0f
        const val MAX_ZOOM_LEVEL = 17.0f

        const val RECORD_REQUEST_CODE = 101

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment Map.
         */
        fun newInstance(aType: String, aText: String, aLat: Double, aLng: Double): MapFragment {
            val fragment = MapFragment()
            val args = Bundle()
            args.putString(MainActivity.EXTRA_TYPE, aType)
            args.putString(MainActivity.EXTRA_TEXT, aText)
            args.putDouble(MainActivity.EXTRA_LAT, aLat)
            args.putDouble(MainActivity.EXTRA_LNG, aLng)
            fragment.setArguments(args)
            return fragment
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        databaseAccess = DatabaseAccess.getInstance(context)

        // Get back arguments
        mInitType = arguments!!.getString(MainActivity.EXTRA_TYPE, "")
        if (MainActivity.VALUE_TYPE_PRAYER.equals(mInitType)) {
            mLat = arguments!!.getDouble(MainActivity.EXTRA_LAT, 0.0)
            mLng = arguments!!.getDouble(MainActivity.EXTRA_LNG, 0.0)
            mMarkerText = arguments!!.getString(MainActivity.EXTRA_TEXT)
        } else if (MainActivity.VALUE_TYPE_PARISH.equals(mInitType)) {
            val lPlaceName = arguments!!.getString(MainActivity.EXTRA_TEXT)

            // Search on the database for the parish data and show a marker
            databaseAccess.open()
            val parishes = databaseAccess.getPlacesByName(lPlaceName)
            databaseAccess.close()

            val lParish = parishes.get(0)
            mLat = lParish.getLatitude()!!
            mLng = lParish.getLongitude()!!

            val lCode = lParish.getCode()
            if (lCode!!.startsWith(MainActivity.APP_HIDDEN_CODE)) {
                mMarkerText = lPlaceName
            } else {
                mMarkerText = lCode + " - " + lPlaceName
            }
        } else if (MainActivity.VALUE_TYPE_WORKSHOP.equals(mInitType)) {
            val lPlaceCode = arguments!!.getString(MainActivity.EXTRA_TEXT)
            databaseAccess.open()
            val parishes = databaseAccess.getPlacesByCode(lPlaceCode)
            databaseAccess.close()

            val lParish = parishes.get(0)
            mLat = lParish.getLatitude()!!
            mLng = lParish.getLongitude()!!

            if (lPlaceCode!!.startsWith(MainActivity.APP_HIDDEN_CODE)) {
                mMarkerText = lParish.getName()!!
            } else {
                mMarkerText = lPlaceCode + " - " + lParish.getName()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        mSearchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager;
        mSearchView = activity!!.findViewById<SearchView>(R.id.search_field)

        val smf = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        smf.getMapAsync(this)

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //              if (searchView.isExpanded() && TextUtils.isEmpty(newText)) {
                callSearch(newText)
                //              }
                return true
            }

            fun callSearch(aQuery: String) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "Searching for " + aQuery);
                }
                databaseAccess.open()
                val parishes = databaseAccess.getPlacesByName(aQuery)
                databaseAccess.close()

                val columns = arrayOf("_id", Place.COLUMN_NAME)
                val temp = arrayOf(0, "default")
                val cursor = MatrixCursor(columns)
                for (i in 0 until parishes.size) {
                    temp[0] = i
                    temp[1] = parishes.get(i).getName()!!
                    cursor.addRow(temp)
                }
                mPlacesCursorAdapter = PlacesCursorAdapter(activity!!, cursor)
                mSearchView.setSuggestionsAdapter(mPlacesCursorAdapter);
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        if (mListener != null) {
//            mListener!!.onFragmentInteraction(uri)
//        }
//    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setUpMap()
    }

    override fun onMarkerClick(p0: Marker?) = false

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.w(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                    try {
                        mMap.isMyLocationEnabled = true
                    } catch (exception : SecurityException) {
                        //TODO ask for permission
                    }
                }
            }
        }
    }

    private fun makeRequest(array: Array<String>) {
        ActivityCompat.requestPermissions(activity!!,
                array,
                RECORD_REQUEST_CODE)
    }

    private fun setupPermissions() {
        val fineLocationPermission = ContextCompat.checkSelfPermission(activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION)

        if (fineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            Log.w(TAG, "Permission to access to fine location denied")
            makeRequest(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
        } else {
            mMap.isMyLocationEnabled = true
        }
    }

    private fun setUpMap() {
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.isTrafficEnabled = false
        mMap.isIndoorEnabled = false
        mMap.isBuildingsEnabled = false
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        mMap.setMinZoomPreference(MIN_ZOOM_LEVEL); // Set a preference for minimum zoom (Zoom out).
        mMap.setMaxZoomPreference(MAX_ZOOM_LEVEL); // Set a preference for maximum zoom (Zoom In).
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition(LatLng(CITY_CENTER_LATITUDE, CITY_CENTER_LONGITUDE), MIN_ZOOM_LEVEL, 0F, 0F)))
        setupPermissions()

        if (!TextUtils.isEmpty(mMarkerText) && mLat > 0.0) {
            val lMarkerPosition = LatLng(mLat, mLng)
            mMap.addMarker(MarkerOptions().position(lMarkerPosition).title(mMarkerText))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lMarkerPosition))
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

}