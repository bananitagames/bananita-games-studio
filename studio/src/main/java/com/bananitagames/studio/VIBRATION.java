package com.bananitagames.studio;

import android.app.Activity;
import android.os.Vibrator;


public final class VIBRATION
{
	private static Vibrator v;
	private static boolean vibratorServiceAlreadyCalled = false;
	private static boolean _enabled = true;
	
	private VIBRATION(){}
	
	public static void vibrate(long millis)
	{
		if(!vibratorServiceAlreadyCalled)
		{
			v = (Vibrator) ActivityGameManager.getInstance().getActivity().getSystemService(Activity.VIBRATOR_SERVICE);
			vibratorServiceAlreadyCalled = true;
		}
		if(v != null && _enabled)
			v.vibrate(millis);

	}

	public static void setVibrationEnabled(boolean enabled)
	{
		_enabled = enabled;
	}
}
