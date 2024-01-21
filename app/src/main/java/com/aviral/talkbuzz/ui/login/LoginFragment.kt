package com.aviral.talkbuzz.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.aviral.talkbuzz.R
import com.aviral.talkbuzz.databinding.FragmentLoginBinding
import com.aviral.talkbuzz.ui.BindingFragment
import com.aviral.talkbuzz.utils.Constants.MIN_USERNAME_LENGTH
import com.aviral.talkbuzz.utils.navigateSafely
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BindingFragment<FragmentLoginBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentLoginBinding::inflate

    private val viewModel:  LoginViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfirm.setOnClickListener {
            setupConnectingUiState()
            viewModel.connectUser(binding.etUsername.text.toString())
        }

        binding.etUsername.addTextChangedListener {
            binding.etUsername.error = null
        }

        subscribeToEvent()
    }

    private fun setupConnectingUiState() {
        binding.progressBar.isVisible = true
        binding.btnConfirm.isEnabled = false
    }

    private fun setupIdleUiState() {
        binding.progressBar.isVisible = false
        binding.btnConfirm.isEnabled = true
    }

    private fun subscribeToEvent() {
        lifecycleScope.launch {
            viewModel.loginEvent.collect { event ->

                when(event) {
                    is LoginEvent.ErrorInputTooShort -> {
                        setupIdleUiState()
                        binding.etUsername.error = getString(R.string.error_username_too_short,
                            MIN_USERNAME_LENGTH)
                    }

                    is LoginEvent.ErrorLogin -> {
                        setupIdleUiState()

                        Snackbar.make(
                            binding.root,
                            event.error,
                            Snackbar.LENGTH_SHORT
                        ).show()

                    }

                    is LoginEvent.Success -> {
                        setupIdleUiState()

                        findNavController().navigateSafely(
                            R.id.action_loginFragment_to_channelFragment
                        )

                    }

                }

            }
        }
    }
}