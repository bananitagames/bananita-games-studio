package com.bananitagames.studio.interfaces;

public interface Music
{
	boolean isLooping();
	boolean isPlaying();
	boolean isStopped();
	void play();
	void pause();
	void stop();
	void setLooping(boolean isLooping);
	void setVolume(float volume);
	String getMetaData(int type);
}
