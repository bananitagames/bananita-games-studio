package com.bananitagames.studio.math;

/**
 * Created by luis on 22/05/2018.
 */

public final class IntConstant extends IntParameter
{

	private final int value;

	public IntConstant(int constantValue)
	{
		this.value = constantValue;
	}

	@Override
	public int getValue()
	{
		return value;
	}
}
