package com.bananitagames.studio.interfaces;

import com.bananitagames.studio.gameobject.GUISeekBar;

public interface GUISeekBarListener
{
	
	void onGainedFocus(GUISeekBar seekBar);
	
	void onLostFocus(GUISeekBar seekBar);
	
	void onPressed(GUISeekBar seekBar);

	void onClick(GUISeekBar seekBar);
}
