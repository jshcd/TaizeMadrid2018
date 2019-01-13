package fr.taize.madrid2018.workshops.model

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
class Workshop (val items : MutableList<WorkshopItem>) {

    private fun addItem(item: WorkshopItem) {
        items.add(item)
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class WorkshopItem(val date: String, val title: String, val description: String,
                            val details: String, val code: String, val place: String,
                            val subway: String, val bus: String) {
        override fun toString(): String = description
    }
}
