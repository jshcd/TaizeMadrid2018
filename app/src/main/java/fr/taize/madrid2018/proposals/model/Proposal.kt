package fr.taize.madrid2018.proposals.model

class Proposal(private var mTitle: String, private var mText: String) {

    fun getTitle(): String {
        return mTitle
    }

    fun getText(): String {
        return mText
    }
}