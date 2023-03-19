package com.bignerdranch.android.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bignerdranch.android.composition.R
import com.bignerdranch.android.composition.databinding.FragmentGameBinding
import com.bignerdranch.android.composition.domain.entity.GameResult
import com.bignerdranch.android.composition.domain.entity.GameSettings
import com.bignerdranch.android.composition.domain.entity.Level


class GameFragment:Fragment() {
    private lateinit var level: Level
    private var _binding: FragmentGameBinding? = null
    private val binding:FragmentGameBinding
    get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsArgs()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvSum.setOnClickListener {
            launchGameFinishedFragment(
                GameResult(false,4,44,
                    GameSettings(44,4,44,4)))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun parsArgs(){
        requireArguments().getParcelable<Level>(LEVEL_GAME)?.let {
            level = it
        }
    }
    private fun launchGameFinishedFragment(gameResult: GameResult){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container,GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }
    companion object{
        private const val LEVEL_GAME = "level"
        fun newInstance(level: Level): GameFragment{

            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LEVEL_GAME, level)
                }
            }
        }
    }
}