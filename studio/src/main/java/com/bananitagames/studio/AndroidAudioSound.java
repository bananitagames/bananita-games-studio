package com.bananitagames.studio;

import android.media.SoundPool;

import com.bananitagames.studio.interfaces.Sound;


final class AndroidAudioSound implements Sound {
    int soundId;
    SoundPool soundPool;
	private String fileName;
    
    public AndroidAudioSound(SoundPool soundPool, int soundId, String fileName) {
		this.soundId = soundId;
        this.soundPool = soundPool;
		this.fileName = fileName;
    }

    @Override
    public void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    public void dispose() {
        soundPool.unload(soundId);
    }

	public String getFileName()
	{
		return fileName;
	}

	private boolean isUnused;

	public void setUnused(boolean isUnused)
	{
		this.isUnused = isUnused;
	}

	public boolean isUnused()
	{
		return isUnused;
	}

	private boolean isDeprecated = false;

	public void setDeprecated(boolean isDeprecated)
	{
		this.isDeprecated = isDeprecated;
	}

	public boolean isDeprecated()
	{
		return this.isDeprecated;
	}

}
