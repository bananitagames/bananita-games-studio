package com.bananitagames.studio.math;

/**
 * This class sets a wave function, that has an initial Amplitude of 1, and decreases to 0.
 * The value follows a sinusoidal function that will start and end at zero too.
 */
public class FunctionSmoothWave extends Function
{
	private static final double PI2 = Math.PI*2;
	
	private float 	totalTime,
					wavePeriod,
					amplitude,
					value;
	
	public FunctionSmoothWave(float totalTime, float wavePeriod)
	{
		init( totalTime, wavePeriod);
	}
	
	public void init(float totalTime, float wavePeriod)
	{
		this.totalTime = totalTime;
		this.wavePeriod = wavePeriod;
		reset();
	}

	@Override
	public void reset()
	{
		storedTime = 0f;
		amplitude = 1f;
		value = 0f;
		
	}

	@Override
	public void update(float deltaTime)
	{
		storedTime += deltaTime;
		if(storedTime >= totalTime)
		{
			amplitude = 0;
			value = 0;
		}
		else
		{
			amplitude = (totalTime-storedTime)/totalTime;
			value = (float)(amplitude * Math.sin(PI2/wavePeriod*storedTime));
		}
	}

	@Override
	public float getValue()
	{
		return value;
	}

	public float getAmplitude()
	{
		return amplitude;
	}
}

