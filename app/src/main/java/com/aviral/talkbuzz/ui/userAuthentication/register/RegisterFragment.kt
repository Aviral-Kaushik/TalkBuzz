package com.aviral.talkbuzz.ui.userAuthentication.register

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
import com.aviral.talkbuzz.databinding.FragmentRegisterBinding
import com.aviral.talkbuzz.ui.BindingFragment
import com.aviral.talkbuzz.utils.navigateSafely
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RegisterFragment : BindingFragment<FragmentRegisterBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentRegisterBinding::inflate

    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfirm.setOnClickListener {
            if (binding.etPassword.text.toString()
                == binding.etConfirmPassword.text.toString()) {

                setupConnectingUiState()

                viewModel.connectUser(
                    binding.etUsername.text.toString().trim(),
                    binding.etPassword.text.toString().trim()
                )

            } else {
                binding.etConfirmPassword.error = "Password Doesn't Match"
            }
        }

        binding.etUsername.addTextChangedListener {
            binding.etUsername.error = null
        }

        binding.etPassword.addTextChangedListener {
            binding.etPassword.error = null
        }

        binding.etConfirmPassword.addTextChangedListener {
            binding.etConfirmPassword.error = null
        }

        binding.tvNavigateSignIn.setOnClickListener {
            findNavController().navigateSafely(
                R.id.action_registerFragment_to_loginFragment
            )
        }

        subscribeToRegisterEvents()

    }

    private fun subscribeToRegisterEvents() {

        lifecycleScope.launch {

            viewModel.registerEvent.collect { event ->

                when (event) {

                    is RegisterEvent.Success -> {
                        setupIdleUiState()

                        findNavController().navigateSafely(
                            R.id.action_registerFragment_to_channelFragment
                        )
                    }

                    is RegisterEvent.RegistrationFails -> {
                        setupIdleUiState()

                        Snackbar.make(
                            binding.root,
                            event.error,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    is RegisterEvent.UsernameTooShort -> {
                        setupIdleUiState()

                        binding.etUsername.error = "The username length is too short"
                    }

                    is RegisterEvent.InvalidPassword -> {
                        setupIdleUiState()

                        binding.etPassword.error = "Invalid Password"
                    }

                    is RegisterEvent.Loading -> {
                        setupIdleUiState()

                        if (event.isLoading)
                            binding.progressBar.visibility = View.VISIBLE
                        else
                            binding.progressBar.visibility = View.GONE

                    }
                }

            }

        }

    }

    private fun setupConnectingUiState() {
        binding.progressBar.isVisible = true
        binding.btnConfirm.isEnabled = false
    }

    private fun setupIdleUiState() {
        binding.progressBar.isVisible = false
        binding.btnConfirm.isEnabled = true
    }
}