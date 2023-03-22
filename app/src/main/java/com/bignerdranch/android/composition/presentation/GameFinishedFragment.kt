package com.bignerdranch.android.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.composition.R
import com.bignerdranch.android.composition.databinding.FragmentGameFinishedBinding

class GameFinishedFragment() : Fragment() {
    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding")
//    private val args = GameFinishedFragmentArgs.fromBundle(requireArguments())
private val args by navArgs<GameFinishedFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
        binding.tvRequiredAnswers.text = args.gameResult.gameSettings.minCountOfRightAnswers.toString()
        binding.gameResult = args.gameResult
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retryGame() {
       findNavController().popBackStack()
    }

}