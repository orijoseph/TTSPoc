package com.example.ttspoc.ui.nativeTTS

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.ttspoc.R
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.util.*

const val MY_DATA_CHECK_CODE = 12

class NativeTTSFragment : Fragment(R.layout.fragment_dashboard),
    TextToSpeech.OnInitListener, ILanguagesCallback, IVoicesCallback {

    private var mTts: TextToSpeech? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTTS()

        read_now_button.setOnClickListener {
            mTts?.speak(example_text.text.toString(), TextToSpeech.QUEUE_FLUSH, null)
        }

        available_languages.setOnClickListener {
            mTts?.let {
                LanguagesDialog.newInstance(it.availableLanguages.toMutableList()).show(
                    childFragmentManager,
                    LanguagesDialog.TAG
                )
            }
        }

        available_voices.setOnClickListener {
            mTts?.let {
                VoicesDialog.newInstance(it.voices.toMutableList()).show(
                    childFragmentManager,
                    VoicesDialog.TAG
                )
            }
        }

        speech_seekbar.max = 10
        speech_seekbar.progress = 2
        speech_seekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                current_speech_speed.text = getConvertedValue(progress).toString()
                stopTTS()
                mTts?.setSpeechRate(getConvertedValue(progress))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    fun getConvertedValue(intVal: Int): Float {
        var floatVal = 0.0f
        floatVal = .5f * intVal
        return floatVal
    }

    private fun initTTS() {
        val checkIntent = Intent()
        checkIntent.action = TextToSpeech.Engine.ACTION_CHECK_TTS_DATA
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                mTts = TextToSpeech(requireContext(), this)

            } else {
                val installIntent = Intent()
                installIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
                startActivity(installIntent)
            }
        }
    }

    override fun onInit(status: Int) {
        mTts?.language = Locale.US
        mTts?.setSpeechRate(2f)
    }

    fun stopTTS(){
        if (mTts?.isSpeaking == true) mTts?.stop()
    }

    override fun onLanguageSelected(pos: Int) {
        stopTTS()
        mTts?.language = mTts?.availableLanguages?.toMutableList()?.get(pos)
    }

    override fun onVoiceSelected(pos: Int) {
        stopTTS()
        mTts?.voice = mTts?.voices?.toMutableList()!![pos]
    }
}
