package fr.taize.madrid2018.prayers

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fr.taize.madrid2018.BuildConfig
import fr.taize.madrid2018.MainActivity
import fr.taize.madrid2018.R
import fr.taize.madrid2018.prayers.model.PrayerPlace

class PrayersRecyclerViewAdapter(
        private val mContext: Context,
        private val items: MutableList<PrayerPlace.PrayerPlaceItem>,
        private val listener: PrayersFragment.OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<PrayersRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as PrayerPlace.PrayerPlaceItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            listener?.onListFragmentInteraction(item)

            if (BuildConfig.DEBUG) {
                Log.d("PrayersRVAdapter", item.coordinates)
            }
            val coordinatesArray = item.coordinates.split(",")

            val lViewPlaceIntent = Intent()
            lViewPlaceIntent.action = MainActivity.ACTION_VIEW_PLACE
            lViewPlaceIntent.putExtra(MainActivity.EXTRA_TYPE, MainActivity.VALUE_TYPE_PRAYER)
            lViewPlaceIntent.putExtra(MainActivity.EXTRA_LAT, coordinatesArray[0].toDouble())
            lViewPlaceIntent.putExtra(MainActivity.EXTRA_LNG, coordinatesArray[1].toDouble())
            lViewPlaceIntent.putExtra(MainActivity.EXTRA_TEXT, item.name)
            mContext.sendBroadcast(lViewPlaceIntent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayersRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_prayers_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(aHolder: PrayersRecyclerViewAdapter.ViewHolder, position: Int) {
        val item = items[position]
        aHolder.mName.text = item.name
        aHolder.mAddress.text = item.address

        with(aHolder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder (val mView: View) : RecyclerView.ViewHolder(mView) {
        val mName: TextView = mView.findViewById(R.id.prayer_place_name)
        val mAddress: TextView = mView.findViewById(R.id.prayer_place_address)

        override fun toString(): String {
            return super.toString() + " '" + mName.text + "'"
        }
    }

}
