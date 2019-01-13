package fr.taize.madrid2018.proposals

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fr.taize.madrid2018.R

class ProposalFragment() : Fragment() {

    private var mTitle : String? = null
    private var mText : String? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_proposal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lTitle = view.findViewById<TextView>(R.id.tvTitle)
        val lText = view.findViewById<TextView>(R.id.tvText)
        lTitle.setText(mTitle)
        lText.setText(mText)

    }

    fun setTitle(aTitle : String) {
        mTitle = aTitle
    }

    fun setText(aText : String) {
        mText = aText
    }
}