package com.example.ttspoc.ui.nativeTTS

import android.content.Context
import android.os.Bundle
import android.speech.tts.Voice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ttspoc.R
import kotlinx.android.synthetic.main.voices_dialog.*

class VoicesDialog : DialogFragment() {

    private var listener: IVoicesCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = (parentFragment as? IVoicesCallback)
    }

    companion object {
        const val TAG = "VoicesDialog"
        private const val VOICES = "voices"

        fun newInstance(voices: MutableList<Voice>) =
            VoicesDialog().apply {
                arguments = bundleOf(
                    VOICES to voices.map {
                        it.name
                    }
                )
            }
    }

    private val voices by lazy {
        arguments?.getStringArrayList(VOICES)
    }

    private val voicesAdapter: VoicesAdapter = VoicesAdapter { pos ->
        listener?.onVoiceSelected(pos)
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.voices_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        voices_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = voicesAdapter
        }

        voicesAdapter.submitList(voices)
    }
}

interface IVoicesCallback {
    fun onVoiceSelected(pos: Int)
}