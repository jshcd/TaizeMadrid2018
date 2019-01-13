package fr.taize.madrid2018.map

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.support.v4.widget.CursorAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fr.taize.madrid2018.R
import android.view.LayoutInflater
import fr.taize.madrid2018.MainActivity
import fr.taize.madrid2018.database.Place


/**
 * Created by jshernan on 11/3/18.
 */
class PlacesCursorAdapter : CursorAdapter {

    private val mLayoutInflater: LayoutInflater

    constructor(context: Context, c:Cursor) : super(context, c) {
        mLayoutInflater = LayoutInflater.from(context)
    }

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return mLayoutInflater.inflate(R.layout.fragment_map_search_result_item, parent, false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        if (context == null) {
            return
        }
        if (cursor == null) {
            return
        }
        val deal = cursor.getString(cursor.getColumnIndexOrThrow(Place.COLUMN_NAME))
        if (view == null) {
            return
        }
        val dealsTv = view.findViewById<TextView>(R.id.search_result_name)
        dealsTv.text = deal

        view.setOnClickListener(View.OnClickListener { view ->
            //take next action based user selected item
            val dealText = view.findViewById<TextView>(R.id.search_result_name)
            //mSearchView.setIconified(true)

            val lViewPlaceIntent = Intent()
            lViewPlaceIntent.action = MainActivity.ACTION_VIEW_PLACE
            lViewPlaceIntent.putExtra(MainActivity.EXTRA_TYPE, MainActivity.VALUE_TYPE_PARISH)
            lViewPlaceIntent.putExtra(MainActivity.EXTRA_LAT, 0.0)
            lViewPlaceIntent.putExtra(MainActivity.EXTRA_LNG, 0.0)
            lViewPlaceIntent.putExtra(MainActivity.EXTRA_TEXT, dealText.text)
            mContext.sendBroadcast(lViewPlaceIntent)

        })
    }

}