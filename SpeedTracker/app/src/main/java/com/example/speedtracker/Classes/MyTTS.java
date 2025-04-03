package com.example.speedtracker.Classes;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class MyTTS {

    private TextToSpeech TTS;

    TextToSpeech.OnInitListener initListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                TTS.setLanguage(Locale.ENGLISH);
            }
        }
    };

    public MyTTS( Context context ) {
        TTS = new TextToSpeech( context , initListener);
    }

    public void speak(String message){
        TTS.speak( message , TextToSpeech.QUEUE_ADD , null , null);
    }

}
