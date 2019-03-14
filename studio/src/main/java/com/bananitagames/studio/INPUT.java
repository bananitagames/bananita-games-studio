package com.bananitagames.studio;

import java.util.List;

public final class INPUT
{

	private INPUT(){}

	//TOUCH EVENTS

	public static List<TouchEvent> getTouchEvents()
	{
		return ActivityGameManager.getInstance().getActivity().getInput().getTouchEvents();
	}

	// KEY EVENTS

	public static List<KeyEvent> getKeyEvents()
	{
		return ActivityGameManager.getInstance().getActivity().getInput().getKeyEvents();
	}

	// ROTATION

	public static float getAzimuth()
	{
		return ActivityGameManager.getInstance().getActivity().getInput().getAzimut();
	}

	public static float getPitch()
	{
		return ActivityGameManager.getInstance().getActivity().getInput().getPitch();
	}

	public static float getRoll()
	{
		return ActivityGameManager.getInstance().getActivity().getInput().getRoll();
	}

	public static float[] getRotation()
	{
		return ActivityGameManager.getInstance().getActivity().getInput().getRotation();
	}

	// ACCELERATION

	public static float getAccelX()
	{
		return ActivityGameManager.getInstance().getActivity().getInput().getAccelX();
	}

	public static float getAccelY()
	{
		return ActivityGameManager.getInstance().getActivity().getInput().getAccelY();
	}

	public static float getAccelZ()
	{
		return ActivityGameManager.getInstance().getActivity().getInput().getAccelZ();
	}

	public static float[] getAcceleration()
	{
		return ActivityGameManager.getInstance().getActivity().getInput().getAcceleration();
	}
}
