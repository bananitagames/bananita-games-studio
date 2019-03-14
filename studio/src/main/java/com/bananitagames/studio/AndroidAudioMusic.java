package com.bananitagames.studio;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

import com.bananitagames.studio.interfaces.Music;


final class AndroidAudioMusic implements OnCompletionListener, Music {
	public MediaPlayer mediaPlayer;
	MediaMetadataRetriever mediaRetriever;
    boolean isPrepared = false;
	private String fileName;

    public AndroidAudioMusic(AssetFileDescriptor assetDescriptor, String fileName) {
		this.fileName = fileName;
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
                    assetDescriptor.getStartOffset(),
                    assetDescriptor.getLength());
            mediaPlayer.prepare();
            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);

            mediaRetriever = new MediaMetadataRetriever();
        	mediaRetriever.setDataSource(assetDescriptor.getFileDescriptor(),
                    assetDescriptor.getStartOffset(),
                    assetDescriptor.getLength());
        } catch (Exception e) {
            throw new RuntimeException("Couldn't load music");
        }
    }

    public void dispose() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
    }

	@Override
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

	@Override
    public boolean isPlaying() {
    	try 
    	{
    		boolean bool = mediaPlayer.isPlaying();
            return bool;
		} catch (IllegalStateException e) {
			if(CONSTANTS._DEBUG) Log.w(CONSTANTS._TAG, "Assets.music.isplaying() threw IllegalStateException"
					+" cause mediaplayer was not initialized");
			return false;
		}
    }

	@Override
    public boolean isStopped() {
        return !isPrepared;
    }

	@Override
    public void pause() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

	@Override
    public void play() {
    	try {
    		if (mediaPlayer.isPlaying())
                return;
		} catch (IllegalStateException e) {
			if(CONSTANTS._DEBUG) Log.w(CONSTANTS._TAG, "Assets.music.play() threw IllegalStateException"
					+" cause mediaplayer was not initialized");
		}

        try {
            synchronized (this) {
                if (!isPrepared)
				{
					mediaPlayer.prepare();
				}
                mediaPlayer.start();
            }
        } catch (IllegalStateException e) {
        	if(CONSTANTS._DEBUG) Log.w(CONSTANTS._TAG, "Assets.music.play() threw IllegalStateException"
					+" cause mediaplayer was called in invalid state");
        } catch (IOException e) {
        	if(CONSTANTS._DEBUG) Log.w(CONSTANTS._TAG, "Assets.music.play() threw IOException"
					+" cause mediaplayer was called in invalid state");
        }
    }

	@Override
    public void setLooping(boolean isLooping) {
        mediaPlayer.setLooping(isLooping);
    }

	@Override
    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume, volume);
    }

	@Override
    public void stop() {
        mediaPlayer.stop();
        synchronized (this) {
            isPrepared = false;
        }
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        synchronized (this) {
            isPrepared = false;
        }
    }

    @Override
    public String getMetaData(int type) {
    	return mediaRetriever.extractMetadata(type);
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
