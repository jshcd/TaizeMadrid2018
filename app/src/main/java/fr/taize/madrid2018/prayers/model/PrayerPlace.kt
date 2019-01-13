package fr.taize.madrid2018.prayers.model

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
class PrayerPlace (val items : MutableList<PrayerPlaceItem>) {

    private fun addItem(item: PrayerPlaceItem) {
        items.add(item)
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class PrayerPlaceItem(val name: String, val address: String, val coordinates: String) {
        override fun toString(): String = name
    }
}