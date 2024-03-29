package com.aviral.talkbuzz.ui.channels

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.aviral.talkbuzz.R
import com.aviral.talkbuzz.databinding.DialogChannelNameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateChannelDialog : DialogFragment() {

//    private var _binding: DialogChannelNameBinding? = null
    private lateinit var binding: DialogChannelNameBinding
//        get() = _binding!!

    private val viewModel: ChannelViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        binding = DialogChannelNameBinding.inflate(layoutInflater)
        return null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogChannelNameBinding.inflate(layoutInflater)

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.choose_channel_name)
            .setView(binding.root)
            .setPositiveButton(R.string.create) { _, _ ->
                viewModel.createChannel(binding.etChannelName.text.toString())
            }
            .setNegativeButton(R.string.cancel) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()

    }

    override fun onDestroy() {
        super.onDestroy()
//        binding = null
    }

}