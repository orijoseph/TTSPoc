package com.example.ttspoc.ui.nativeTTS

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ttspoc.R
import kotlinx.android.synthetic.main.voices_dialog.*
import java.util.*

class LanguagesDialog : DialogFragment() {

    private var listener: ILanguagesCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = (parentFragment as? ILanguagesCallback)
    }

    companion object {
        const val TAG = "LanguagesDialog"
        private const val LANGUAGES = "languages"

        fun newInstance(languages: MutableList<Locale>) =
            LanguagesDialog().apply {
                arguments = bundleOf(
                    LANGUAGES to languages.map {
                        it.displayName
                    }
                )
            }
    }

    private val languages by lazy {
        arguments?.getStringArrayList(LANGUAGES)
    }

    private val voicesAdapter: VoicesAdapter = VoicesAdapter { pos ->
        listener?.onLanguageSelected(pos)
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

        voicesAdapter.submitList(languages)
    }
}

interface ILanguagesCallback {
    fun onLanguageSelected(pos: Int)
}