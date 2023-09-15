package com.example.githubuserhaki.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserhaki.R
import com.example.githubuserhaki.adapter.FollowAdapter
import com.example.githubuserhaki.databinding.FragmentFollowBinding
import com.example.githubuserhaki.response.FollowResponse
import com.example.githubuserhaki.viewModel.FollowViewModel

class FollowFragment : Fragment() {
    var strtext = ""
    private lateinit var binding: FragmentFollowBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val nganu = arguments
        strtext = nganu?.getString("edttext") ?: ""

        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowBinding.bind(view)

        val followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowViewModel::class.java]

        followViewModel.listFollow.observe(viewLifecycleOwner) { listFollow ->
            if (listFollow != null) setFollow(listFollow)
        }

        followViewModel.loading.observe(viewLifecycleOwner){loading ->
            showLoading(loading)
        }

        followViewModel.empty.observe(viewLifecycleOwner){empty ->
            noFollow(empty)
        }

        followViewModel.indikasi.observe(viewLifecycleOwner){indikasi ->
            binding.indikasi.text = indikasi
        }

        followViewModel.errorText.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {theMsg ->
                Toast.makeText(context, theMsg, Toast.LENGTH_LONG).show()
            }
        }

        val name = arguments?.getString(ARG_USERNAME)
        val index = arguments?.getInt(ARG_POSITION, 0)

        val linearLayoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = linearLayoutManager


        if (name != null && savedInstanceState == null) {
            followViewModel.showFollowers(name, index == 1)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvFollow.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }

    private fun noFollow(noFoll: Boolean) {
        binding.rvFollow.visibility = if (noFoll) View.GONE else View.VISIBLE
        binding.indikasi.visibility = if (noFoll) View.VISIBLE else View.GONE
    }

    private fun setFollow(followList: List<FollowResponse>) {
        val adapter = FollowAdapter(followList)
        binding.rvFollow.adapter = adapter
    }

    companion object {
        val ARG_POSITION: String? = "ARG_POSITION"
        val ARG_USERNAME: String? = "ARG_USERNAME"
    }

}
