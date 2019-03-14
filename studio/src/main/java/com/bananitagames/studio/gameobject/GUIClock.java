package com.bananitagames.studio.gameobject;

import java.util.List;

import android.graphics.Color;

import com.bananitagames.studio.Font;
import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.Scene;
import com.bananitagames.studio.TIME;

public class GUIClock extends GameObject
{
	static final int COLOR_DEFAULT = Color.argb(255, 255, 255, 255);
	Font font;
	boolean clockLeftToRight;
	boolean clockHoursVisible;
	boolean clockMinutesVisible;
	boolean clockSecondsVisible;
	boolean clockCentisVisible;
	float   timeStored;
	boolean timeStopped;
	boolean timeForward;
	int numberColor;
	float numberSize;
	String numberToString;
	StringBuilder numberBuilder;
	String[] numberToArray;

	public GUIClock()
	{
		super();
		this.font = Scene.font;
		this.clockLeftToRight = true;
		this.clockHoursVisible = false;
		this.clockMinutesVisible = true;
		this.clockSecondsVisible = true;
		this.clockCentisVisible = false;
		this.timeStored = 0;
		this.timeStopped = false;
		this.timeForward = true;
		this.numberColor = COLOR_DEFAULT;
		this.numberBuilder = new StringBuilder();
		this.numberToArray = new String[0];
	}

	///////////////////////////////////////////////////
	// SETTERS
	///////////////////////////////////////////////////
	
	public GUIClock setPosition(float x, float y)
	{
		this.transform.position.set(x, y);
		return this;
	}
	
	public GUIClock setClockLeftToRight(boolean leftToRight)
	{
		this.clockLeftToRight = leftToRight;
		return this;
	}
	
	public GUIClock setNumberColor(int color)
	{
		this.numberColor = color;
		return this;
	}
	
	public GUIClock setNumberSize(float size)
	{
		this.numberSize = size;
		return this;
	}
	
	public GUIClock setTimeStored(float time)
	{
		this.timeStored = time;
		return this;
	}
	
	public GUIClock addTimeStored(float time)
	{
		this.timeStored += time;
		return this;
	}
	
	public GUIClock setTimeStopped(boolean stop)
	{
		this.timeStopped = stop;
		return this;
	}
	
	public GUIClock setTimeForward(boolean forward)
	{
		this.timeForward = forward;
		return this;
	}
	
	public GUIClock setHoursVisible(boolean visible)
	{
		this.clockHoursVisible = visible;
		return this;
	}
	
	public GUIClock setMinutesVisible(boolean visible)
	{
		this.clockMinutesVisible = visible;
		return this;
	}
	
	public GUIClock setSecondsVisible(boolean visible)
	{
		this.clockSecondsVisible = visible;
		return this;
	}
	
	public GUIClock setCentisecondsVisible(boolean visible)
	{
		this.clockCentisVisible = visible;
		return this;
	}

	///////////////////////////////////////////////////
	// GAME OBJECT
	///////////////////////////////////////////////////	
	
	@Override
	public void update()
	{
		super.update();
		float deltaTime = TIME.deltaTime;
		if(!timeStopped)
		{
			if(timeForward)timeStored += deltaTime;
			else timeStored -= deltaTime;
			timeStored = timeStored < 0f ? 0f : timeStored;
		}
		numberToString = "";
		if(numberBuilder.length()!=0) numberBuilder.delete(0, numberBuilder.length());
		if(clockHoursVisible)
		{
			int hours = (int)timeStored/3600;
			hours = hours > 99 ? 99 : hours;
			hours = hours < 0 ? 0 : hours;
			if(hours<10) numberBuilder.append('0');
			numberBuilder.append(hours + ":");
		}
		if(clockMinutesVisible)
		{
			int minutes = (int)timeStored/60%60;
			if(minutes<10) numberBuilder.append('0');
			numberBuilder.append(minutes + ":");
		}
		if(clockSecondsVisible)
		{
			int seconds = (int)timeStored%60;
			if(seconds<10) numberBuilder.append('0');
			numberBuilder.append(seconds + ":");
		}
		if(clockCentisVisible)
		{
			int centis = (int)(timeStored*100)%100;
			if(centis<10) numberBuilder.append('0');
			numberBuilder.append(centis + ":");
		}
		if(numberBuilder.length()!=0)
		{
			numberBuilder.deleteCharAt(numberBuilder.length()-1);
			numberToString = numberBuilder.toString();
		}
	}
	
	@Override
	public void present()
	{
		super.present();
		float deltaTransform = clockLeftToRight ? 0 : -(numberToString.length()-1)*numberSize;
		drawText(font, numberToString, numberSize, transform.position.x + deltaTransform, transform.position.y, numberColor);
	}
	
}
