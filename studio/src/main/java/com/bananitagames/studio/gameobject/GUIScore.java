package com.bananitagames.studio.gameobject;

import java.util.List;

import android.graphics.Color;

import com.bananitagames.studio.Font;
import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.Scene;
import com.bananitagames.studio.TIME;
import com.bananitagames.studio.TextureRegion;

public class GUIScore extends GameObject
{

	private static final Font _DEFAULT_FONT = Scene.font;
	private static final int DEFAULT_COLOR = Color.WHITE;
	private static final int DEFAULT_BACKGROUND_COLOR = Color.LTGRAY;
	private static final float SECONDS_TO_ADD_SCORE = 1f;
	// Score
	protected boolean scoreLeftToRight;
	protected float scoreCurrent;
	protected float scoreToAddLeft;
	protected int scoreLimit;
	// Numbers
	protected int numberColor;
	protected int numberAmount;
	protected float numberSize;
	protected String[] numberDigits;
	protected Font numberFont;
	// Background region variables
	protected TextureRegion backgroundRegion;
	protected boolean backgroundRegionVisible;
	protected int backgroundColor;

	
	public GUIScore()
	{
		super();
		this.transform.position.set(scene.cam.position);
		// Score
		this.scoreLeftToRight = true;
		this.scoreCurrent = 0;
		this.scoreToAddLeft = 0;
		this.scoreLimit = 9;
		// Number
		this.numberColor = DEFAULT_COLOR;
		this.numberAmount = 1;
		this.numberSize = scene.cam.frustumHeight/20f;
		this.numberDigits = new String[this.numberAmount];
		this.numberFont = _DEFAULT_FONT;
		// Background
		this.backgroundRegion = Scene.regionSquare;
		this.backgroundRegionVisible = true;
		this.backgroundColor = DEFAULT_BACKGROUND_COLOR;
	}
	
	// Transform

	public GUIScore setPosition(float x, float y)
	{
		this.transform.position.set(x, y);
		return this;
	}
	
	// Score
	
	public GUIScore setScoreLeftToRight(boolean leftToRight)
	{
		this.scoreLeftToRight = leftToRight;
		return this;
	}
	
	public GUIScore setScore(int score)
	{
		this.scoreCurrent = score;
		this.scoreToAddLeft = 0;
		return this;
	}
	
	public GUIScore addScore(int amount)
	{
		this.scoreToAddLeft += amount;
		return this;
	}
	
	public float getScore()
	{
		return scoreCurrent;
	}

	public float getTotalScore() { return scoreCurrent + scoreToAddLeft; }

	// Number
	
	public GUIScore setNumberColor(int color)
	{
		this.numberColor = color;
		return this;
	}

	public GUIScore setNumberAmount(int digitsAmount)
	{
		this.numberAmount = digitsAmount; 
		this.numberDigits = new String[this.numberAmount];
		// Sets new score limit
		this.scoreLimit = 1;
		for(int i = 0; i < digitsAmount; i++)
			this.scoreLimit = this.scoreLimit*10;
		this.scoreLimit -= 1;		
		return this;
	}

	public GUIScore setNumberSize(float size)
	{
		this.numberSize = size;
		return this;
	}
	
	// Background
	
	public GUIScore setNumberBackground(TextureRegion region)
	{
		this.backgroundRegion = region;
		return this;
	}
	
	public GUIScore setNumberBackgroundVisibility(boolean visible)
	{
		this.backgroundRegionVisible = visible;
		return this;
	}
	
	public GUIScore setNumberBackgroundColor(int color)
	{
		this.backgroundColor = color;
		return this;
	}

	public GUIScore setNumberFont(Font font)
	{
		this.numberFont = font;
		return this;
	}

	public GUIScore setNumberFontDefault()
	{
		this.numberFont = _DEFAULT_FONT;
		return this;
	}
	
	@Override
	public void update()
	{
		super.update();
		float deltaTime = TIME.deltaTime;
		float scoreBeingAdded = deltaTime/SECONDS_TO_ADD_SCORE*scoreToAddLeft;
		scoreBeingAdded = scoreBeingAdded <= 0.1f ? 0.1f : scoreBeingAdded;
		scoreBeingAdded = scoreBeingAdded > scoreToAddLeft ? scoreToAddLeft : scoreBeingAdded;
		scoreToAddLeft -= scoreBeingAdded;
		scoreCurrent += scoreBeingAdded;
		/////////////////////////////////////////////////////////
		// Don't let the score go out of its bounds
		if(scoreCurrent < 0) scoreCurrent = 0;
		if(scoreCurrent > scoreLimit) scoreCurrent = scoreLimit;
		/////////////////////////////////////////////////////////
		// Convert score to array of chars(String in this case)
		char[] currentScore = Integer.toString((int)scoreCurrent).toCharArray();
		for(int i = 0; i < numberDigits.length; i++)
			numberDigits[i] = "0";
		for(int i = numberDigits.length-1, j = currentScore.length-1; i >= 0 && j>=0; i--, j--)
		{
			numberDigits[i] = String.valueOf(currentScore[j]);
		}
	}
	
	@Override
	public void present()
	{
		super.present();
		int sign = scoreLeftToRight ? 1 : -1;
		if(backgroundRegionVisible) for(int i = 0; i < numberAmount; i++)
		{
			drawSprite(
					backgroundRegion,
					transform.position.x + sign*i*numberSize,
					transform.position.y,
					numberSize,
					numberSize,
					backgroundColor);
		}
		float deltaTransform = scoreLeftToRight ? 0 : -(numberAmount-1)*numberSize;
		for(int i = 0, len = numberDigits.length; i < len; i++)
		{
				drawText(
					numberFont,
					numberDigits[i], 
					numberSize, 
					transform.position.x + deltaTransform + i*numberSize, 
					transform.position.y,
					numberColor);
		}
		
	}
	
}
