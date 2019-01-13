package fr.taize.madrid2018

import fr.taize.madrid2018.database.Place
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PlaceUnitTest {
    var mPlace: Place
    init {
        mPlace = Place(PLACE_CODE, PLACE_NAME, PLACE_STREET, PLACE_TOWN, PLACE_POSTCODE,
                PLACE_PARTICIPATION, PLACE_LATITUDE, PLACE_LONGITUDE)
    }

    @Test
    fun getCode_isCorrect() {
        assertEquals(PLACE_CODE, mPlace.getCode())
    }

    @Test
    fun getName_isCorrect() {
        assertEquals(PLACE_NAME, mPlace.getName())
    }

    @Test
    fun getStreet_isCorrect() {
        assertEquals(PLACE_STREET, mPlace.getStreet())
    }

    @Test
    fun getTown_isCorrect() {
        assertEquals(PLACE_TOWN, mPlace.getTown())
    }

    @Test
    fun getPostCode_isCorrect() {
        assertEquals(PLACE_POSTCODE, mPlace.getPostCode())
    }

    @Test
    fun getParticipation_isCorrect() {
        assertEquals(PLACE_PARTICIPATION, mPlace.getParticipation())
    }

    @Test
    fun getLatitude_isCorrect() {
        assertEquals(PLACE_LATITUDE, mPlace.getLatitude())
    }

    @Test
    fun getLongitude_isCorrect() {
        assertEquals(PLACE_LONGITUDE, mPlace.getLongitude())
    }

    companion object {
        const val PLACE_CODE = "CP30"
        const val PLACE_NAME = "Nuestra Señora de Moratalaz"
        const val PLACE_STREET = "Entre arroyos 19"
        const val PLACE_TOWN = "Madrid"
        const val PLACE_POSTCODE = "28030"
        const val PLACE_PARTICIPATION = "yes"
        const val PLACE_LATITUDE = 40.4086689
        const val PLACE_LONGITUDE = -3.6544372
    }
}
