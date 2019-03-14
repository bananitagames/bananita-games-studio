package com.bananitagames.studio.math;

/**
 * Created by luis on 16/07/2018.
 */

public abstract class Function
{

	public float storedTime;

	public abstract void reset();

	public abstract float getValue();

	public abstract void update(float deltaTime);
}
