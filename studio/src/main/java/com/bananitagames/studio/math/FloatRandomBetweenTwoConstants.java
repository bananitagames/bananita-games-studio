package com.bananitagames.studio.math;

import java.util.Random;

/**
 * Created by luis on 22/05/2018.
 */

public final class FloatRandomBetweenTwoConstants extends FloatParameter
{

	private final float start, end;
	private final Random random;

	public FloatRandomBetweenTwoConstants(float start, float end)
	{
		this.start = start;
		this.end = end;
		this.random = new Random();
	}

	@Override
	public float getValue()
	{
		return random.nextFloat() * (end - start) + start;
	}
}
