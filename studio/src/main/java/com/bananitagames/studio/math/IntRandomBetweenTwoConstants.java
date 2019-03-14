package com.bananitagames.studio.math;

import java.util.Random;

/**
 * Created by luis on 22/05/2018.
 */

public final class IntRandomBetweenTwoConstants extends IntParameter
{

	private final int startValue, endValue;
	private final Random random;

	public IntRandomBetweenTwoConstants(int startValue, int endValue)
	{
		this.startValue = startValue;
		this.endValue = endValue;
		this.random = new Random();
	}

	@Override
	public int getValue()
	{
		return random.nextInt(endValue - startValue) + startValue;
	}
}
