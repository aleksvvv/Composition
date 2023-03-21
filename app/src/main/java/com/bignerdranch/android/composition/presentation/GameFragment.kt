package com.bignerdranch.android.composition.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.composition.R
import com.bignerdranch.android.composition.databinding.FragmentGameBinding
import com.bignerdranch.android.composition.domain.entity.GameResult
import com.bignerdranch.android.composition.domain.entity.GameSettings
import com.bignerdranch.android.composition.domain.entity.Level


class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var level: Level
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private lateinit var tvSum: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsArgs()


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

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        initView()
        viewModel.startGame(level)
        observeViewModel()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parsArgs() {
        requireArguments().getParcelable<Level>(LEVEL_GAME)?.let {
            level = it
        }
    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            val options = it.options
            val index = 0
            for (i in options){

            }
            binding.tvOption1.text = options[0].toString()
            binding.tvOption2.text = options[1].toString()
            binding.tvOption3.text = options[2].toString()
            binding.tvOption4.text = options[3].toString()
            binding.tvOption5.text = options[4].toString()
            binding.tvOption6.text = options[5].toString()
        }
        viewModel.formattedTime.observe(viewLifecycleOwner){
            binding.tvTimer.text = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner){
                      launchGameFinishedFragment( it)
        }
    }

    private fun initView() {

    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val LEVEL_GAME = "level"
        fun newInstance(level: Level): GameFragment {

            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LEVEL_GAME, level)
                }
            }
        }
    }
}