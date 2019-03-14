package com.bananitagames.studio.math;

/**
 * Created by luis on 22/05/2018.
 */

public final class FloatConstant extends FloatParameter
{

	private final float value;

	public FloatConstant(float constantValue)
	{
		this.value = constantValue;
	}

	@Override
	public float getValue()
	{
		return value;
	}
}
