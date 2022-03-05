package com.shifthackz.android.attacker.presentation.fragment.ddos

import android.os.Bundle
import android.view.View
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.shifthackz.android.attacker.R
import com.shifthackz.android.attacker.attack.contract.AttackProtocol
import com.shifthackz.android.attacker.attack.contract.AttackType
import com.shifthackz.android.attacker.base.BaseFragment
import com.shifthackz.android.attacker.databinding.FragmentDdosBinding
import com.shifthackz.android.attacker.extensions.observeNonNull
import com.shifthackz.android.attacker.presentation.widget.BandwidthGraphAdapter
import com.shifthackz.android.attacker.presentation.widget.LogAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DdosFragment : BaseFragment<FragmentDdosBinding>() {

    override val layoutId = R.layout.fragment_ddos

    private val viewModel: DdosViewModel by viewModel()

    private val logAdapter = LogAdapter()
    private val graphAdapter = BandwidthGraphAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.chart.let(graphAdapter::setLineChart)
        binding.rvLog.adapter = logAdapter
        binding.rvLog.itemAnimator = null
        binding.etType.setOnItemClickListener { _, _, position, _ ->
            viewModel.setAttackType(AttackType.byIndex(position))
        }
        binding.etProtocol.setOnItemClickListener { _, _, position, _ ->
            viewModel.setProtocol(AttackProtocol.byIndex(position))
        }

        viewModel.logsLiveData.observeNonNull(viewLifecycleOwner, logAdapter::submitList)
        viewModel.ppsLiveData.observeNonNull(viewLifecycleOwner, graphAdapter::onNewValue)
        viewModel.scrollTopEvent.observeNonNull(viewLifecycleOwner) {
            binding.rvLog.scrollToPosition(logAdapter.itemCount - 1)
        }
    }
}