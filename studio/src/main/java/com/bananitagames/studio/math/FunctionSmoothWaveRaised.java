package com.bananitagames.studio.math;

/**
 * This class sets a wave function, that has an initial value of 0 and finish at 1.
 * During the first quarter of the wave period, the value raises quick to 1 and starts
 * fluctuating as a sinusoidal wave, smoothing till the end.
 */
public final class FunctionSmoothWaveRaised extends Function
{
	private static final double PI2 = Math.PI*2.0;

	private float 	totalTime,
					wavePeriod,
					quarterPeriod,
					amplitude,
					value,
					extraAmplitude,
			deviation;

	public FunctionSmoothWaveRaised(float totalTime, float wavePeriod, float deviation)
	{
		init( totalTime, wavePeriod, deviation);
	}

	public void init(float totalTime, float wavePeriod, float deviation)
	{
		this.totalTime = totalTime;
		this.wavePeriod = wavePeriod;
		this.quarterPeriod = wavePeriod / 4.0f;
		this.deviation = deviation;
		reset();
	}

	@Override
	public void reset()
	{
		storedTime = 0.0f;
		amplitude = deviation;
		value = 0.0f;
		
	}

	@Override
	public void update(float deltaTime)
	{
		storedTime += deltaTime;
		if(storedTime >= totalTime)
		{
			amplitude = 0.0f;
			value = 1.0f;
		}
		else
		{
			extraAmplitude = storedTime < quarterPeriod ? (float) Math.sin(PI2 / wavePeriod * storedTime) : 1.0f;
			amplitude = deviation * (totalTime-storedTime)/totalTime;
			value = (float)(extraAmplitude + amplitude * Math.sin(PI2/wavePeriod*storedTime));
		}
	}

	@Override
	public float getValue()
	{
		return value;
	}
}

