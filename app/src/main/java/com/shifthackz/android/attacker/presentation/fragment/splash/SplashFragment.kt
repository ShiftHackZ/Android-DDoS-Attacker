package com.shifthackz.android.attacker.presentation.fragment.splash

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.shifthackz.android.attacker.R
import com.shifthackz.android.attacker.base.BaseFragment
import com.shifthackz.android.attacker.databinding.FragmentSplashBinding
import com.shifthackz.android.attacker.extensions.observeNonNull
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override val layoutId = R.layout.fragment_splash

    private val viewModel: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onNextRoute.observeNonNull(viewLifecycleOwner) {
            findNavController().navigate(R.id.routeDDoS)
        }
    }
}