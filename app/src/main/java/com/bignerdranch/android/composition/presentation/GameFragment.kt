package com.bignerdranch.android.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.composition.databinding.FragmentGameBinding
import com.bignerdranch.android.composition.domain.entity.GameResult
import com.bignerdranch.android.composition.domain.entity.Level


class GameFragment : Fragment() {

    private lateinit var level: Level
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private lateinit var tvSum: TextView

    private val viewModelFactory by lazy {
        val args = GameFragmentArgs.fromBundle(requireArguments())
        GameViewModelFactory(args.level, requireActivity().application)
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this, viewModelFactory
        )[GameViewModel::class.java]
    }
    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
//        setOnClickView()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
//        viewModel.question.observe(viewLifecycleOwner) {
//
//        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
    }

    private fun setOnClickView() {
        for (i in tvOptions) {
            i.setOnClickListener {
                viewModel.chooseAnswer(i.text.toString().toInt())
            }
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {

        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult)
        )

    }

}