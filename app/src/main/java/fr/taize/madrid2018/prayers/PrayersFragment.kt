package fr.taize.madrid2018.prayers

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.taize.madrid2018.R
import fr.taize.madrid2018.prayers.model.PrayerPlace
import fr.taize.madrid2018.workshops.WorkshopFragment

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PrayersFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PrayersFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PrayersFragment : Fragment() {
    private var columnCount = 1
    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(WorkshopFragment.ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_prayers, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                val res = resources
                val prayerPlaceNames = res.getStringArray(R.array.prayer_place_names)
                val prayerPlaceAddresses = res.getStringArray(R.array.prayer_place_addresses)
                val prayerPlaceCoordinates = res.getStringArray(R.array.prayer_place_coordinates)

                val ITEMS: MutableList<PrayerPlace.PrayerPlaceItem> = ArrayList()
                for (counter in 0 until prayerPlaceNames.size) {
                    ITEMS.add(PrayerPlace.PrayerPlaceItem(
                            prayerPlaceNames[counter],
                            prayerPlaceAddresses[counter],
                            prayerPlaceCoordinates[counter]))
                }

                adapter = PrayersRecyclerViewAdapter(context, ITEMS, listener)
            }
        }

        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            //throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: PrayerPlace.PrayerPlaceItem?)
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
                PrayersFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
