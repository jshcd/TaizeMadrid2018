package fr.taize.madrid2018.workshops

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

import fr.taize.madrid2018.workshops.model.Workshop.WorkshopItem

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [WorkshopFragment.OnListFragmentInteractionListener] interface.
 */
class WorkshopFragment : Fragment() {
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_workshop_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                val res = resources
                val workshop_titles = res.getStringArray(R.array.workshop_titles)
                val workshop_descriptions = res.getStringArray(R.array.workshop_descriptions)
                val workshop_dates = res.getStringArray(R.array.workshop_dates)
                val workshop_codes = res.getStringArray(R.array.workshop_codes)
                val workshop_places = res.getStringArray(R.array.workshop_places)
                val workshop_subways = res.getStringArray(R.array.workshop_subways)
                val workshop_buses = res.getStringArray(R.array.workshop_buses)

                val ITEMS: MutableList<WorkshopItem> = ArrayList()
                for (i in 0 until workshop_titles.size) {
                    ITEMS.add(WorkshopItem(workshop_dates[i], workshop_titles[i],
                            workshop_descriptions[i], "", workshop_codes[i], workshop_places[i],
                            workshop_subways[i], workshop_buses[i]))
                }

                adapter = WorkshopRecyclerViewAdapter(context, ITEMS, listener)
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
        fun onListFragmentInteraction(item: WorkshopItem?)
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
                WorkshopFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
