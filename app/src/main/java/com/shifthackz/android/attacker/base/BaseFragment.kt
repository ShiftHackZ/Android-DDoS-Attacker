package com.shifthackz.android.attacker.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.shifthackz.android.attacker.R

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    abstract val layoutId: Int

    open val navBarColor: Int = R.color.colorSecondary

    lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavBar()
    }

    private fun setupNavBar() = activity?.apply {
        window?.navigationBarColor = ContextCompat.getColor(this, navBarColor)
    }
}