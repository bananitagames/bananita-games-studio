package com.bananitagames.studio.math;

import android.graphics.Color;

/**
 * Created by luis on 22/09/2017.
 */

public final class Interpolator
{

	private Interpolator(){}

	public static int color(float progress, int colorSrc, int colorDst)
	{
		int alpha,red,green,blue;
		alpha = Color.alpha(colorSrc) + (int)(progress*(Color.alpha(colorDst)-Color.alpha(colorSrc)));
		red = Color.red(colorSrc) + (int)(progress*(Color.red(colorDst)-Color.red(colorSrc)));
		green = Color.green(colorSrc) + (int)(progress*(Color.green(colorDst)-Color.green(colorSrc)));
		blue = Color.blue(colorSrc) + (int)(progress*(Color.blue(colorDst)-Color.blue(colorSrc)));

		alpha = alpha < 0 ? 0 : (alpha > 255 ? 255 : alpha);
		red = red < 0 ? 0 : (red > 255 ? 255 : red);
		green = green < 0 ? 0 : (green > 255 ? 255 : green);
		blue = blue < 0 ? 0 : (blue > 255 ? 255 : blue);
		return Color.argb(alpha, red, green, blue);
	}

	/**
	 * This function will result in an interpolated value with a smoothed borders
	 * @param progress a value that should be between [0-1] float range
	 * @param initialValue initial value
	 * @param endValue end value
	 * @return a value that is the progress expressed between the initial and end value ranges
	 */
	public static float easyInOut(float progress, float initialValue, float endValue)
	{
		progress = progress < 0.0f ? 0.0f : (progress > 1.0f ? 1.0f : progress);
		return (float) (initialValue + (endValue-initialValue) * 0.5 * (1.0 + Math.cos((1.0 + progress) * Math.PI)));
	}

	public static float progress(float currentValue, float initialValue, float endValue)
	{

		float result = (currentValue - initialValue) / (endValue - initialValue);
		result = result < 0.0f ? 0.0f : (result > 1.0f ? 1.0f : result);
		return result;
	}
}
