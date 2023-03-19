package com.bignerdranch.android.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.bignerdranch.android.composition.databinding.FragmentGameFinishedBinding
import com.bignerdranch.android.composition.domain.entity.GameResult

class GameFinishedFragment() :Fragment() {
    private lateinit var gameResult: GameResult
    private var _binding:FragmentGameFinishedBinding? = null
    private val binding:FragmentGameFinishedBinding
    get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        putArgs()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.
        addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                retryGame()
            }
         })
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
private fun retryGame(){
    requireActivity().supportFragmentManager.popBackStack(ChooseLevelFragment.NAME,0)
}
    private fun putArgs(){
         requireArguments().getParcelable<GameResult>(KEY_RESULT_GAME)?.let {
            gameResult = it
        }
    }
    companion object{
        private const val KEY_RESULT_GAME = "key_result"
        fun newInstance(gameResult: GameResult):GameFinishedFragment{
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_RESULT_GAME,gameResult)
                }
            }

        }

    }
}