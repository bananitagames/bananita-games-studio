package com.bananitagames.studio.math;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by luis on 22/05/2018.
 */

public final class ColorRandomBetweenTwoConstants extends IntParameter
{

	private final int colorStart, colorEnd;
	private final Random random;

	public ColorRandomBetweenTwoConstants(int colorStart, int colorEnd)
	{
		this.colorStart = colorStart;
		this.colorEnd = colorEnd;
		this.random = new Random();
	}

	@Override
	public int getValue()
	{
		int alpha,red,green,blue;
		alpha = Color.alpha(colorStart) + (int)(random.nextFloat()*(Color.alpha(colorEnd)-Color.alpha(colorStart)));
		red = Color.red(colorStart) + (int)(random.nextFloat()*(Color.red(colorEnd)-Color.red(colorStart)));
		green = Color.green(colorStart) + (int)(random.nextFloat()*(Color.green(colorEnd)-Color.green(colorStart)));
		blue = Color.blue(colorStart) + (int)(random.nextFloat()*(Color.blue(colorEnd)-Color.blue(colorStart)));

		alpha = alpha < 0 ? 0 : (alpha > 255 ? 255 : alpha);
		red = red < 0 ? 0 : (red > 255 ? 255 : red);
		green = green < 0 ? 0 : (green > 255 ? 255 : green);
		blue = blue < 0 ? 0 : (blue > 255 ? 255 : blue);
		return Color.argb(alpha, red, green, blue);
	}
}
