package com.example.cricdekho.ui.schedulepoints.points

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricdekho.data.model.getPointsTable.Group
import com.example.cricdekho.databinding.FragmentPointsTableBinding

class PointsTableFragment : Fragment() {
    private lateinit var binding: FragmentPointsTableBinding
    private lateinit var pointTableAdapter: PointTableAdapter
    private val pointsTableViewModel: PointsTableViewModel by viewModels()
    private var responsePointsTable: MutableList<Group> = mutableListOf()
    private lateinit var tournamentSlug: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tournamentSlug = it.getString("tournament_slug").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPointsTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        callPointSTableApi(tournamentSlug)
    }

    private fun callPointSTableApi(matchSlug: String) {
        pointsTableViewModel.getPointsTable(matchSlug)

        pointsTableViewModel.dataPointsTable.observe(viewLifecycleOwner, Observer {
            responsePointsTable.clear()
            responsePointsTable.addAll(it)
            setUpAdapter()
        })
    }

    private fun setUpAdapter() {
        pointTableAdapter = PointTableAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        pointTableAdapter.addAll(responsePointsTable, false)
        binding.recyclerView.adapter = pointTableAdapter
        pointTableAdapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance(tournamentSlug: String) = PointsTableFragment().apply {
            arguments = Bundle().apply {
                putString("tournament_slug", tournamentSlug)
            }
        }
    }
}