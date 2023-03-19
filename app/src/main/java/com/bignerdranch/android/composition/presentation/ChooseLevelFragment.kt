package com.bignerdranch.android.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bignerdranch.android.composition.R
import com.bignerdranch.android.composition.databinding.FragmentChooseLevelBinding
import com.bignerdranch.android.composition.domain.entity.Level


class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding:FragmentChooseLevelBinding
    get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.testLevel.setOnClickListener {
            launchGameFragment(Level.TEST)
        }
        binding.lightLevel.setOnClickListener {
            launchGameFragment(Level.EASY)
        }
        binding.normalLevel.setOnClickListener {
            launchGameFragment(Level.NORMAL)
        }
        binding.hardLevel.setOnClickListener {
            launchGameFragment(Level.HARD)
        }
         }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun launchGameFragment(level: Level){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container,GameFragment.newInstance(level))
            .addToBackStack(null)
            .commit()
    }
    companion object {
        const val NAME = "ChooseLevelFragment"
        fun newInstance(): ChooseLevelFragment{
            return ChooseLevelFragment()
        }
    }

}