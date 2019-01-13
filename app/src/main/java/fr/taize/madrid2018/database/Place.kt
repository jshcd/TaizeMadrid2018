package fr.taize.madrid2018.database

/**
 * Created by jshernan on 10/3/18.
 */
class Place (private var mCode: String, private var mName: String,
             private var mStreet: String, private var mTown: String, private var mPostCode: String,
             private var mParticipation: String, private var mLat: Double,
             private var mLng: Double) {

    companion object {
        const val TABLE = "parish"
        const val COLUMN_CODE = "code"
        const val COLUMN_NAME = "name"
        const val COLUMN_STREET = "street"
        const val COLUMN_TOWN = "town"
        const val COLUMN_POSTCODE = "postcode"
        const val COLUMN_PARTICIPATION = "participation"
        const val COLUMN_LATITUDE = "lat"
        const val COLUMN_LONGITUDE = "lng"
    }

    fun getCode(): String? {
        return mCode
    }

    fun getName(): String? {
        return mName
    }

    fun getStreet(): String? {
        return mStreet
    }

    fun getTown(): String? {
        return mTown;
    }

    fun getPostCode(): String? {
        return mPostCode
    }

    fun getParticipation(): String {
        return mParticipation
    }

    fun getLatitude(): Double? {
        return mLat
    }

    fun getLongitude(): Double? {
        return mLng
    }
}