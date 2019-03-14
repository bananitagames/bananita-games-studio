package com.bananitagames.studio.gameobject;


import android.graphics.Color;

import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.TIME;
import com.bananitagames.studio.TextureRegion;

public class GOEffect extends GameObject
{
	private static final float FRAMETIME_DEFAULT = 0.16f;
	private static final int COLOR_DEFAULT = Color.argb(255, 255, 255, 255);
	TextureRegion[] region;
	float regionSize;
	int colorStart;
	int colorEnd;
	int colorCurrent;
	float cDeltaAlpha, cDeltaRed, cDeltaGreen, cDeltaBlue;
	float frameTime;
	int frameCurrent;
	int deltaFrames;
	
	private float storedTime;
	
	public GOEffect init()
	{
		this.transform.reset();
		this.region = null;
		this.regionSize = 0;
		this.frameTime = FRAMETIME_DEFAULT;
		this.frameCurrent = 0;
		this.setColor(COLOR_DEFAULT, COLOR_DEFAULT);
		this.storedTime = 0;
		return this;
	}
	
	public GOEffect setPosition(float x, float y)
	{
		this.transform.position.set(x, y);
		return this;
	}
	
	public GOEffect setVelocity(float x, float y)
	{
		this.transform.velocity.set(x, y);
		return this;
	}
	
	public GOEffect setColor(int colorStart, int colorEnd)
	{
		this.colorStart = colorStart;
		this.colorEnd = colorEnd;
		return this;
	}
	
	public GOEffect setSize(float size)
	{
		this.regionSize = size;
		return this;
	}
	
	public GOEffect setRegion(TextureRegion[] region)
	{
		this.region = region;
		float lifetime = region.length;
		cDeltaAlpha = (Color.alpha(colorStart)-Color.alpha(colorEnd))/lifetime;
		cDeltaRed = (Color.red(colorStart)-Color.red(colorEnd))/lifetime;
		cDeltaGreen = (Color.green(colorStart)-Color.green(colorEnd))/lifetime;
		cDeltaBlue = (Color.blue(colorStart)-Color.blue(colorEnd))/lifetime;
		return this;
	}
	
	public GOEffect setFrameTime(float frameTime)
	{
		this.frameTime = frameTime;
		return this;
	}

	/////////////////////////////////////////////////
	// GAME OBJECTS
	/////////////////////////////////////////////////
	
	@Override
	public void update()
	{
		super.update();
		float deltaTime = TIME.deltaTime;
		storedTime += deltaTime;
		if(storedTime >= frameTime)
		{
			deltaFrames = (int) (storedTime/frameTime);
			frameCurrent+=deltaFrames;
			storedTime = storedTime-deltaTime*frameTime;
			if(frameCurrent >= region.length) 
				onDestroyObject();
		}
		int alpha = Color.alpha(colorStart) + (int)(frameCurrent*cDeltaAlpha);
		int red = Color.red(colorStart) + (int)(frameCurrent*cDeltaRed);
		int green = Color.green(colorStart) + (int)(frameCurrent*cDeltaGreen);
		int blue = Color.blue(colorStart) + (int)(frameCurrent*cDeltaBlue);
		// Set current color
		colorCurrent = Color.argb(
				alpha, 
				red, 
				green, 
				blue);
		
	}
	
	@Override
	public void present()
	{
		super.present();
		if(region!=null && frameCurrent < region.length)
		{
			drawSprite(
					region[frameCurrent],
					transform.position.x, 
					transform.position.y, 
					regionSize, 
					regionSize,
					colorCurrent);
		}
	}
	
	/**
	 * This method calls scene.destroyGameObject.
	 * If you are pooling effects objects( very common), you
	 * can override this method and <code>FREE</code> this object
	 * before calling its super method;
	 */
	public void onDestroyObject()
	{
		destroy();
	}
}
