package com.bananitagames.studio.math;

import java.util.Random;

public class FunctionSpike extends Function
{
	private Random r;
	
	private float 	normalValue,
					spikeValue,
					normalTime,
					spikeTime,
					deviationTime,
					value,
					storedTime,
					currentTime;
	private boolean isNormal;
	
	public FunctionSpike(float normalValue, float spikeValue, float normalTime, float spikeTime, float deviationTime)
	{
		init(normalValue, spikeValue, normalTime, spikeTime, deviationTime);
		r = new Random();
	}
	
	public void init(float normalValue, float spikeValue, float normalTime, float spikeTime, float deviationTime)
	{
		this.normalValue = normalValue;
		this.spikeValue = spikeValue;
		this.normalTime = normalTime;
		this.spikeTime = spikeTime;
		this.deviationTime = deviationTime;
		reset();
	}

	@Override
	public void reset()
	{
		storedTime = 0f;
		value = normalValue;
		currentTime = normalTime + r.nextFloat()*deviationTime;
		isNormal = true;
		
	}

	@Override
	public void update(float deltaTime)
	{
		storedTime += deltaTime;
		if(storedTime >= currentTime)
		{
			isNormal = !isNormal;
			if(isNormal)
			{
				currentTime = normalTime + r.nextFloat()*deviationTime;
				value = normalValue;
			}
			else
			{
				currentTime = spikeTime + r.nextFloat()*deviationTime;
				value = spikeValue;
			}
			storedTime = 0f;
		}
		else
		{
			if(isNormal)
			{
				value = normalValue;
			}
			else
			{
				value = normalValue + (spikeValue-normalValue)*((currentTime-storedTime)/currentTime);
			}
		}
	}

	@Override
	public float getValue()
	{
		return value;
	}
}
