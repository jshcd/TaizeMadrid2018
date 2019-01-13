package fr.taize.madrid2018.workshops

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fr.taize.madrid2018.BuildConfig
import fr.taize.madrid2018.MainActivity
import fr.taize.madrid2018.R


import fr.taize.madrid2018.workshops.WorkshopFragment.OnListFragmentInteractionListener
import fr.taize.madrid2018.workshops.model.Workshop.WorkshopItem

import kotlinx.android.synthetic.main.fragment_workshop_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class WorkshopRecyclerViewAdapter(
        private val mContext: Context,
        private val mValues: List<WorkshopItem>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<WorkshopRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as WorkshopItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
            if (BuildConfig.DEBUG) {
                Log.d("WorkshopRVAdaper", item.place)
            }
            val lViewPlaceIntent = Intent()
            lViewPlaceIntent.action = MainActivity.ACTION_VIEW_PLACE
            lViewPlaceIntent.putExtra(MainActivity.EXTRA_TYPE, MainActivity.VALUE_TYPE_WORKSHOP)
            lViewPlaceIntent.putExtra(MainActivity.EXTRA_LAT, 0.0)
            lViewPlaceIntent.putExtra(MainActivity.EXTRA_LNG, 0.0)
            lViewPlaceIntent.putExtra(MainActivity.EXTRA_TEXT, item.code)
            mContext.sendBroadcast(lViewPlaceIntent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_workshop_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.title
        holder.mContentView.text = item.description
        holder.mDate.text = item.date
        holder.mCode.text = item.code
        holder.mLocation.text = item.place
        holder.mSubway.text = item.subway
        holder.mBus.text = item.bus

        if (TextUtils.isEmpty(item.bus)) {
            holder.mBus.visibility = View.GONE
        }

        if (item.code.startsWith(MainActivity.APP_HIDDEN_CODE)) {
            holder.mCode.visibility = View.GONE
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
        val mDate: TextView = mView.tvDate
        val mCode: TextView = mView.tvCode
        val mLocation: TextView = mView.tvLocation
        val mSubway: TextView = mView.tvSubway
        val mBus: TextView = mView.tvBus

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
