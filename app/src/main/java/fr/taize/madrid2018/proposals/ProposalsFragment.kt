package fr.taize.madrid2018.proposals

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.taize.madrid2018.R
import android.support.v4.view.ViewPager
import fr.taize.madrid2018.proposals.model.Proposal

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProposalsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProposalsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ProposalsFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            proposal = it.getParcelable<Proposal>(ARG_PARAM1)
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_proposals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mViewPager = view.findViewById<ViewPager>(R.id.proposals_viewpager) as ViewPager
        val adapter = MyProposalsAdapter(fragmentManager!!)

        val firstProposal = ProposalFragment()
        //firstProposal.setTitle(getContext().resources.getString(R.string.first_proposal_title))
        //firstProposal.setText(Html.fromHtml(
        //        getContext().resources.getString(R.string.first_proposal_text)).toString())
        //adapter.addFragment( firstProposal, "")

        val secondProposal = ProposalFragment()
        //secondProposal.setTitle(getContext().resources.getString(R.string.second_proposal_title))
        //secondProposal.setText(getContext().resources.getString(R.string.second_proposal_text))
        //adapter.addFragment( secondProposal, "")

        val thirdProposal = ProposalFragment()
        //thirdProposal.setTitle(getContext().resources.getString(R.string.third_proposal_title))
        //thirdProposal.setText(getContext().resources.getString(R.string.third_proposal_text))
        //adapter.addFragment( thirdProposal, "")

        mViewPager.adapter = adapter
        mViewPager.setCurrentItem(0, true)
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            //throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment ProposalsFragment.
         */
        @JvmStatic
        fun newInstance(proposal: Proposal) =
                ProposalsFragment().apply {
                    arguments = Bundle().apply {
                        //putParcelable(ARG_PARAM1, proposal)
                    }
                }
    }
}
