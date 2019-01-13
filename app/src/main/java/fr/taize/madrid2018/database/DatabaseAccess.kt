package fr.taize.madrid2018.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

import java.util.ArrayList

class DatabaseAccess
/**
 * Private constructor to aboid object creation from outside classes.
 *
 * @param context
 */
private constructor(context: Context?) {
    private val openHelper: DatabaseOpenHelper
    private var database: SQLiteDatabase? = null

    init {
        this.openHelper = DatabaseOpenHelper(context!!)

    }

    /**
     * Open the database connection.
     */
    fun open() {
        openHelper.createDatabase()
        this.database = openHelper.readableDatabase
    }

    /**
     * Close the database connection.
     */
    fun close() {
        if (database != null) {
            this.database!!.close()
        }
    }

    fun getPlacesByName(aQuery: String): List<Place> {
        val lList = ArrayList<Place>()
        val lCursor = database!!.rawQuery("SELECT * FROM " + Place.TABLE
                + " WHERE " + Place.COLUMN_NAME + " LIKE '%" + aQuery + "%'"
                + " AND " + Place.COLUMN_PARTICIPATION + " = 1", null)
        lCursor.moveToFirst()
        while (!lCursor.isAfterLast) {
            val lCode = lCursor.getString(lCursor.getColumnIndex(Place.COLUMN_CODE))
            val lName = lCursor.getString(lCursor.getColumnIndex(Place.COLUMN_NAME))
            val lStreet = lCursor.getString(lCursor.getColumnIndex(Place.COLUMN_STREET))
            val lTown = lCursor.getString(lCursor.getColumnIndex(Place.COLUMN_TOWN))
            val lPostCode = lCursor.getString(lCursor.getColumnIndex(Place.COLUMN_CODE))
            val lParticipation = lCursor.getString(lCursor.getColumnIndex(Place.COLUMN_PARTICIPATION))
            val lLatitude = lCursor.getDouble(lCursor.getColumnIndex(Place.COLUMN_LATITUDE))
            val lLongitude = lCursor.getDouble(lCursor.getColumnIndex(Place.COLUMN_LONGITUDE))

            lList.add(Place(lCode, lName, lStreet, lTown, lPostCode, lParticipation, lLatitude,
                    lLongitude))
            lCursor.moveToNext()
        }
        lCursor.close()
        return lList
    }

    fun getPlacesByCode(aQuery: String): List<Place> {
        val lList = ArrayList<Place>()
        val lCursor = database!!.rawQuery("SELECT * FROM " + Place.TABLE
                + " WHERE " + Place.COLUMN_CODE + " = '" + aQuery + "'"
                + " AND " + Place.COLUMN_PARTICIPATION + " = 1", null)
        lCursor.moveToFirst()
        while (!lCursor.isAfterLast) {
            val lCode = lCursor.getString(lCursor.getColumnIndex(Place.COLUMN_CODE))
            val lName = lCursor.getString(lCursor.getColumnIndex(Place.COLUMN_NAME))
            val lStreet = lCursor.getString(lCursor.getColumnIndex(Place.COLUMN_STREET))
            val lTown = lCursor.getString(lCursor.getColumnIndex(Place.COLUMN_TOWN))
            val lPostCode = lCursor.getString(lCursor.getColumnIndex(Place.COLUMN_CODE))
            val lParticipation = lCursor.getString(lCursor.getColumnIndex(Place.COLUMN_PARTICIPATION))
            val lLatitude = lCursor.getDouble(lCursor.getColumnIndex(Place.COLUMN_LATITUDE))
            val lLongitude = lCursor.getDouble(lCursor.getColumnIndex(Place.COLUMN_LONGITUDE))

            lList.add(Place(lCode, lName, lStreet, lTown, lPostCode, lParticipation, lLatitude,
                    lLongitude))
            lCursor.moveToNext()
        }
        lCursor.close()
        return lList
    }

    companion object {
        private var instance: DatabaseAccess? = null
        /**
         * Return a singleton instance of DatabaseAccess.
         *
         * @param context the Context
         * @return the instance of DabaseAccess
         */
        fun getInstance(context: Context?): DatabaseAccess {
            if (instance == null) {
                instance = DatabaseAccess(context)
            }
            return instance!!
        }
    }
}