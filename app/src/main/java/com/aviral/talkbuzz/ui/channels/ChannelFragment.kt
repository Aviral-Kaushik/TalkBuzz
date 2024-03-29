package com.aviral.talkbuzz.ui.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.aviral.talkbuzz.R
import com.aviral.talkbuzz.databinding.FragmentChannelBinding
import com.aviral.talkbuzz.ui.BindingFragment
import com.aviral.talkbuzz.utils.navigateSafely
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.ui.channel.list.header.viewmodel.ChannelListHeaderViewModel
import io.getstream.chat.android.ui.channel.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ChannelFragment : BindingFragment<FragmentChannelBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentChannelBinding::inflate

    private val viewModel : ChannelViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = viewModel.getUser()
        if (user == null) {
            findNavController().popBackStack()
            return
        }

        val factory = ChannelListViewModelFactory(
            filter = Filters.and(
                Filters.eq("type", "messaging"),
            ),
            sort = ChannelListViewModel.DEFAULT_SORT,
            limit = 30
        )

        val channelListViewModel: ChannelListViewModel by viewModels { factory }

        val channelHeaderViewModel: ChannelListHeaderViewModel by viewModels()

        channelListViewModel.bindView(binding.channelListView, viewLifecycleOwner)
        channelHeaderViewModel.bindView(binding.channelListHeaderView, viewLifecycleOwner)

        binding.channelListHeaderView.setOnUserAvatarClickListener {
            viewModel.logout()
            findNavController().popBackStack()
        }

        binding.channelListView.setChannelItemClickListener { channel ->
            findNavController().navigateSafely(
                R.id.action_channelFragment_to_chatFragment,
                Bundle().apply {
                    putString("channelId", channel.cid)
                }
            )
        }

        binding.channelListHeaderView.setOnActionButtonClickListener {
//            findNavController().navigateSafely(
//                R.id.action_channelFragment_to_createChannelDialog2
//            )
            CreateChannelDialog().show(parentFragmentManager, "Dialog")
        }

        lifecycleScope.launchWhenStarted {

            viewModel.createChannelEvent.collect { event ->

                when (event) {

                    is CreateChannelEvent.Error -> {

                        Toast.makeText(
                            requireContext(),
                            event.error,
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    is CreateChannelEvent.Success -> {
                        Toast.makeText(
                            requireContext(),
                            R.string.channel_created,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is CreateChannelEvent.Loading -> {
                        if (event.isLoading)
                            binding.progressBar.visibility = View.VISIBLE
                        else
                            binding.progressBar.visibility = View.GONE

                    }

                }
            }
        }
    }
}