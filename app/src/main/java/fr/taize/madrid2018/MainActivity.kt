package fr.taize.madrid2018

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import fr.taize.madrid2018.map.MapFragment
import fr.taize.madrid2018.prayers.PrayersFragment
import fr.taize.madrid2018.workshops.WorkshopFragment
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter

class MainActivity : AppCompatActivity(), MapFragment.OnFragmentInteractionListener {

    private val mPrayersFragment = PrayersFragment.newInstance(PRAYERS_FRAGMENT_COLUMNS_COUNT)
    private val mWorkshopFragment = WorkshopFragment.newInstance(WORKSHOPS_FRAGMENT_COLUMNS_COUNT)

    private val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_prayers -> {
                switchToFragmentPrayers()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_workshops -> {
                switchToFragmentWorkshops()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                switchToFragmentMap("", "", 0.0, 0.0)
                return@OnNavigationItemSelectedListener true
            }
//            R.id.navigation_proposals -> {
//                //message.setText(R.string.title_notifications)
//                switchToFragmentProposals()
//                return@OnNavigationItemSelectedListener true
//            }
        }
        false
    }

    companion object {
        const val PRAYERS_FRAGMENT_COLUMNS_COUNT = 1
        const val WORKSHOPS_FRAGMENT_COLUMNS_COUNT = 1

        const val APP_HIDDEN_CODE = "APP"

        const val ACTION_VIEW_PLACE = "fr.taize.madrid2018.viewplace"
        const val EXTRA_TYPE = "TYPE"
        const val EXTRA_TEXT = "text"
        const val EXTRA_LAT = "lat"
        const val EXTRA_LNG = "lng"

        const val VALUE_TYPE_PRAYER = "prayer"
        const val VALUE_TYPE_PARISH = "parish"
        const val VALUE_TYPE_WORKSHOP = "workshop"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        supportActionBar!!.setTitle(R.string.app_title)
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.setLogo(R.mipmap.ic_launcher_round);
        supportActionBar!!.setDisplayUseLogoEnabled(true);
        supportActionBar!!.setIcon(R.mipmap.ic_launcher_round);

        switchToFragmentPrayers()
        navigation.menu.getItem(0).isChecked = true
    }

    override fun onResume() {
        val filter = IntentFilter()
        filter.addAction(ACTION_VIEW_PLACE)
        registerReceiver(receiver, filter)
        super.onResume()
    }

    override fun onPause() {
        unregisterReceiver(receiver)
        super.onPause()
    }

    override fun onFragmentInteraction(uri: Uri) {
        //("not implemented")
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (VALUE_TYPE_PRAYER.equals(intent.getStringExtra(EXTRA_TYPE))) {
                val lText = intent.getStringExtra(EXTRA_TEXT)
                val lLat = intent.getDoubleExtra(EXTRA_LAT, 0.0)
                val lLng = intent.getDoubleExtra(EXTRA_LNG, 0.0)
                switchToFragmentMap(VALUE_TYPE_PRAYER, lText, lLat, lLng)
            } else if (VALUE_TYPE_PARISH.equals(intent.getStringExtra(EXTRA_TYPE))) {
                val lText = intent.getStringExtra(EXTRA_TEXT)
                switchToFragmentMap(VALUE_TYPE_PARISH, lText, 0.0, 0.0)
            } else if (VALUE_TYPE_WORKSHOP.equals(intent.getStringExtra(EXTRA_TYPE))) {
                val lText = intent.getStringExtra(EXTRA_TEXT)
                switchToFragmentMap(VALUE_TYPE_WORKSHOP, lText, 0.0, 0.0)
            } else {
                switchToFragmentMap("", "", 0.0, 0.0)
            }
            navigation.menu.getItem(2).isChecked = true
        }
    }

    private fun switchToFragmentPrayers() {
        supportFragmentManager.beginTransaction().replace(R.id.content, mPrayersFragment).commit()
    }

    private fun switchToFragmentWorkshops() {
        supportFragmentManager.beginTransaction().replace(R.id.content, mWorkshopFragment).commit()
    }

    fun switchToFragmentMap(aType: String, aText: String, aLat: Double, aLng: Double) {
        val mSupportMapFragment = MapFragment.newInstance(aType, aText, aLat, aLng)
        supportFragmentManager.beginTransaction().replace(R.id.content, mSupportMapFragment).commit()
    }

//    private fun switchToFragmentProposals() {
//        val manager = supportFragmentManager
//        val lProposal = Proposal(resources.getString(R.string.first_proposal_title),
//                resources.getString(R.string.first_proposal_text))
//        manager.beginTransaction().replace(R.id.content, ProposalsFragment.newInstance(lProposal)).commit()
//    }

}
