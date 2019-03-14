package com.bananitagames.studio;

import android.util.Log;

import com.bananitagames.studio.interfaces.Music;
import com.bananitagames.studio.interfaces.Sound;

import java.util.ArrayList;

final class AndroidResources
{

	private ArrayList<AndroidTexture> textures;
	private ArrayList<AndroidAudioSound> sounds;
	private ArrayList<AndroidAudioMusic> musics;

	public AndroidResources()
	{
		textures = new ArrayList<>();
		sounds = new ArrayList<>();
		musics = new ArrayList<>();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	///
	///	GETTERS
	///
	////////////////////////////////////////////////////////////////////////////////////////////////

	public ArrayList<AndroidTexture> getTextures()
	{
		return textures;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	///
	///	ADDERS
	///
	////////////////////////////////////////////////////////////////////////////////////////////////

	public AndroidTexture addTexture(String fileName)
	{
		AndroidTexture texture = getTextureIfExists(fileName);
		if(texture == null)
		{
			texture = new AndroidTexture(fileName);
			textures.add(texture);
		}
		else if(texture.isDeprecated())
		{
			texture.init();
			texture.setDeprecated(false);
		}
		texture.setUnused(false);
		return texture;
	}

	public AndroidTexture addTexture(int resourceId)
	{
		AndroidTexture texture = getTextureIfExists(resourceId);
		if(texture == null)
		{
			texture = new AndroidTexture(resourceId);
			textures.add(texture);
		}
		else if(texture.isDeprecated())
		{
			texture.init();
			texture.setDeprecated(false);
		}
		texture.setUnused(false);
		return texture;
	}

	public Sound addSound(String fileName)
	{
		AndroidAudioSound sound = getSoundIfExists(fileName);
		if(sound == null)
		{
			sound = ActivityGameManager.getInstance().getActivity().getAudio().newSound(fileName);
			sounds.add(sound);
		}
		else if(sound.isDeprecated())
		{
			sound = ActivityGameManager.getInstance().getActivity().getAudio().newSound(fileName);
			sound.setDeprecated(false);
		}
		sound.setUnused(false);
		return sound;
	}

	public Music addMusic(String fileName)
	{
		AndroidAudioMusic music = getMusicIfExists(fileName);
		if(music == null)
		{
			music = ActivityGameManager.getInstance().getActivity().getAudio().newMusic(fileName);
			musics.add(music);
		}
		else if(music.isDeprecated())
		{
			music = ActivityGameManager.getInstance().getActivity().getAudio().newMusic(fileName);
			music.setDeprecated(false);
		}
		music.setUnused(false);
		return music;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	///
	///	UNUSED RESOURCES MARKING AND DELETING
	///
	////////////////////////////////////////////////////////////////////////////////////////////////

	public void markAllResourcesUnused()
	{
		for(AndroidTexture texture : textures)
		{
			texture.setUnused(true);
		}
		for(AndroidAudioSound sound : sounds)
		{
			sound.setUnused(true);
		}
		for(AndroidAudioMusic music : musics)
		{
			music.setUnused(true);
		}
	}

	/**
	 * Used to maintain resource referencing but force to reload them
	 */
	public void markAllResourcesDeprecated()
	{
		for(AndroidTexture texture : textures)
		{
			texture.setDeprecated(true);
		}
		for(AndroidAudioSound sound : sounds)
		{
			sound.setDeprecated(true);
		}
		for(AndroidAudioMusic music : musics)
		{
			music.setDeprecated(true);
		}
	}

	public void removeAllResourcesUnused()
	{
		int i;
		// Textures deleting
		for(i = textures.size()-1; i > -1; i--)
		{
			AndroidTexture texture = textures.get(i);
			if(CONSTANTS._DEBUG_RESOURCES)
				Log.d(CONSTANTS._TAG, "Texture [" + texture.getTextureName() + "] is being [" + (texture.isUnused() ? "deleted" : "recycled") + "]");
			if(texture.isUnused())
			{
				texture.dispose();
				textures.remove(i);
			}
		}
		// Sound deleting
		for(i = sounds.size()-1; i > -1; i--)
		{
			AndroidAudioSound sound = sounds.get(i);
			if(CONSTANTS._DEBUG_RESOURCES)
				Log.d(CONSTANTS._TAG, "Sound [" + sound.getFileName() + "] is being [" + (sound.isUnused() ? "deleted" : "recycled") + "]");
			if(sound.isUnused())
			{
				sound.dispose();
				sounds.remove(i);
			}
		}
		// Music deleting
		for(i = musics.size()-1; i > -1; i--)
		{
			AndroidAudioMusic music = musics.get(i);
			if(CONSTANTS._DEBUG_RESOURCES)
				Log.d(CONSTANTS._TAG, "Music [" + music.getFileName() + "] is being [" + (music.isUnused() ? "deleted" : "recycled") + "]");
			if(music.isUnused())
			{
				music.dispose();
				musics.remove(i);
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	///
	///	PRIVATES
	///
	////////////////////////////////////////////////////////////////////////////////////////////////

	private AndroidTexture getTextureIfExists(String fileName)
	{
		AndroidTexture result = null;
		for(AndroidTexture texture : textures)
		{
			if(!texture.isLoadedFromResourceId())
			{
				if(texture.getFileName().equals(fileName))
				{
					result = texture;
					break;
				}
			}
		}
		return result;
	}

	private AndroidTexture getTextureIfExists(int resourceId)
	{
		AndroidTexture result = null;
		for(AndroidTexture texture : textures)
		{
			if(texture.isLoadedFromResourceId())
			{
				if(texture.getResourceId() == resourceId)
				{
					result = texture;
					break;
				}
			}
		}
		return result;
	}

	private AndroidAudioSound getSoundIfExists(String fileName)
	{
		AndroidAudioSound result = null;
		for(AndroidAudioSound sound : sounds)
		{
			if(sound.getFileName().equals(fileName))
			{
				result = sound;
				break;
			}
		}
		return result;
	}

	private AndroidAudioMusic getMusicIfExists(String fileName)
	{
		AndroidAudioMusic result = null;
		for(AndroidAudioMusic music : musics)
		{
			if(music.getFileName().equals(fileName))
			{
				result = music;
				break;
			}
		}
		return result;
	}

}
