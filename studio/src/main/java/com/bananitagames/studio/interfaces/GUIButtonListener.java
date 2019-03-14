package com.bananitagames.studio.interfaces;

import com.bananitagames.studio.gameobject.GUIButton;

public interface GUIButtonListener
{

	void onGainedFocus(GUIButton button);

	void onLostFocus(GUIButton button);

	void onPressed(GUIButton button);

	void onClick(GUIButton button);

}
