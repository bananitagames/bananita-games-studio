package com.bananitagames.studio.math;

public class FunctionArmonicWave extends Function
{
	private static final double PI2 = Math.PI*2;
	
	private float 	wavePeriod,
					amplitude,
					value;
	
	/**
	 * Creates an armonic wave with default amplitude value = 1
	 * @param wavePeriod
	 */
	public FunctionArmonicWave(float wavePeriod)
	{
		init(wavePeriod);
	}
	
	public FunctionArmonicWave setAmplitude(float amplitude)
	{
		this.amplitude = amplitude;
		return this;
	}
	
	public FunctionArmonicWave setWavePeriod(float wavePeriod)
	{
		this.wavePeriod = wavePeriod;
		return this;
	}
	
	public FunctionArmonicWave setWaveFrequency(float waveFrequency)
	{
		this.wavePeriod = (float) (PI2/waveFrequency);
		return this;
	}
	
	public void init(float wavePeriod)
	{
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
		value = (float)(amplitude * Math.sin(PI2/wavePeriod*storedTime));
	}
	
	
	public float getWavePeriod()
	{
		return wavePeriod;
	}
	
	public float getAmplitude()
	{
		return amplitude;
	}

	@Override
	public float getValue()
	{
		return value;
	}
}

