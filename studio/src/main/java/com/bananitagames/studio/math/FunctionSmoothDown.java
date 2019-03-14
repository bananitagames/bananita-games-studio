package com.bananitagames.studio.math;

public class FunctionSmoothDown extends Function
{
	private static final double PI2 = Math.PI*2;

	private float 	totalTime,
					quarterTime,
					deviation,
					value;

	public FunctionSmoothDown(float totalTime, float deviation)
	{
		init(totalTime, deviation);
	}
	
	public void init(float totalTime, float deviation)
	{
		this.totalTime = totalTime;
		this.quarterTime = totalTime / 4.0f;
		this.deviation = deviation;
		reset();
	}

	@Override
	public void reset()
	{
		storedTime = 0f;
		value = 1;
	}

	@Override
	public void update(float deltaTime)
	{
		storedTime += deltaTime;
		// IN TIME
		if(storedTime < totalTime)
		{
			// IN FIRST QUARTER, GO UP, QUARTER OF SIN WAVE
			if(storedTime < quarterTime)
			{
				value = (float)(1.0f + deviation * Math.sin(Math.PI / 2.0f * storedTime / quarterTime));
			}
			// IN LATER QUARTERS, GO DOWN, FIRST HALF OF COS WAVE
			else
			{
				value = (float)((1.0f + deviation) * (0.5f + 0.5f*Math.cos(Math.PI * (storedTime-quarterTime)/(totalTime-quarterTime))));
			}
			//value = (float)(amplitude * Math.sin(PI2/wavePeriod*storedTime));
		}
		// OUT OF TIME
		else
		{
			value = 0.0f;
		}
	}

	@Override
	public float getValue()
	{
		return value;
	}
}

