package com.bananitagames.studio;

import android.util.DisplayMetrics;

import com.bananitagames.studio.math.Vector2;

/**
 * STATIC class that brings you access to some device screen measure methods
 */
public final class DIMENSIONS
{

	private static final float CENTIMETERS_PER_INCH = 2.54f;

	private DIMENSIONS(){}

	public static void getScreenInches(Vector2 vector)
	{
		ActivityGame a = ActivityGameManager.getInstance().getActivity();
		vector.set(-1, -1);
		DisplayMetrics dm = new DisplayMetrics();
		a.getWindowManager().getDefaultDisplay().getMetrics(dm);
		vector.set(dm.widthPixels/dm.xdpi, dm.heightPixels/dm.ydpi);
	}

	public static void getScreenCentimeters(Vector2 vector)
	{
		getScreenInches(vector);
		vector.mul(CENTIMETERS_PER_INCH);
	}

}
