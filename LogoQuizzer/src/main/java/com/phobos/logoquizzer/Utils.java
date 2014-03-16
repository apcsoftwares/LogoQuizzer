package com.phobos.logoquizzer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.preference.PreferenceManager;

/**
 * Created by Anthony on 3/15/14.
 */
public class Utils
{
	boolean rightAnswerLoaded = false;
	boolean wrongAnswerLoaded = false;
	boolean clickSoundLoaded = false;
	private AudioManager mAudioManager;
	private SoundPool mSoundPool;
	private int rightAnswerSoundID, wrongAnswerSoundID, clickSoundID;

	private Context context;

	private SharedPreferences settings;
	private Editor editor;

	private boolean dialogResult;

	public Utils(Context context)
	{
		this.context = context;
		settings = PreferenceManager.getDefaultSharedPreferences(context);
		editor = settings.edit();
	}

	public void InitializeSounds()
	{
		mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

		//mSoundPool = new SoundPool()
		mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener()
		{
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status)
			{
				if (sampleId == rightAnswerSoundID) rightAnswerLoaded = true;
				if (sampleId == wrongAnswerSoundID) wrongAnswerLoaded = true;
				if (sampleId == clickSoundID) clickSoundLoaded = true;
			}
		});
		rightAnswerSoundID = mSoundPool.load(context, R.raw.right_sound, 1);
		wrongAnswerSoundID = mSoundPool.load(context, R.raw.wrong_sound_buzz, 1);
		clickSoundID = mSoundPool.load(context, R.raw.click_sound, 1);
	}

	public void PlaySound(SoundType soundType)
	{
		if (!settings.getBoolean("audio_checkbox", true)) return;

		float actualVolume = (float) mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = (float) mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = actualVolume / maxVolume;
		switch (soundType)
		{
			case RIGHT_ANSWER:
				if (rightAnswerLoaded)
					mSoundPool.play(rightAnswerSoundID, volume, volume, 1, 0, 1f);
				break;
			case WRONG_ANSWER:
				if (wrongAnswerLoaded)
					mSoundPool.play(wrongAnswerSoundID, volume, volume, 1, 0, 1f);
				break;
			case CLICK:
				if (clickSoundLoaded) mSoundPool.play(clickSoundID, volume, volume, 1, 0, 1f);
				break;
		}
	}

	public enum SoundType
	{
		RIGHT_ANSWER, WRONG_ANSWER, CLICK;
	}

	public void resetAllProgress()
	{
		editor.clear();
		editor.apply();
	}

	public void saveLogo(String logoName)
	{
		editor.putBoolean(logoName, true);
		editor.apply();
	}

	public void savePlayed()
	{
		editor.putBoolean("played", true);
		editor.apply();
	}

	public boolean isGamePlayed()
	{
		return settings.getBoolean("played", false);
	}

	public boolean isLogoAnswered(String logoName)
	{
		return settings.getBoolean(logoName, false);
	}

}
