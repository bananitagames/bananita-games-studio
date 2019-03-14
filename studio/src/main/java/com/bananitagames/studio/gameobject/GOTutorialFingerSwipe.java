package com.bananitagames.studio.gameobject;

import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.Scene;
import com.bananitagames.studio.TIME;
import com.bananitagames.studio.math.Interpolator;
import com.bananitagames.studio.math.Line;
import com.bananitagames.studio.math.Vector2;

/**
 * Created by luis on 06/10/2017.
 */

public class GOTutorialFingerSwipe extends GameObject
{

	public static final float _DEFAULT_TIME_TOUCHING = 0.5f;
	public static final float _DEFAULT_TIME_SLIDING = 2.0f;
	public static final float _DEFAULT_TIME_UNTOUCHING = 0.5f;
	public static final float _DEFAULT_TIME_UNSLIDING = 1.0f;

	public boolean enabled = true;

	private static final float _LEFT = -0.5f, _DOWN = -0.5f, _MIDDLE = 0.0f, _TOP = 0.5f, _RIGHT = 0.5f, _UP = 0.1f;

	private enum state {touching, sliding, untouching, unsliding};
	public enum slideMode {left_right,topleft_downright, top_down, topright_downleft, right_left, downright_topleft, down_top, downleft_top_right};

	private state currentState;
	private Vector2 startPosition = new Vector2();
	private Vector2 endPosition = new Vector2();
	private Vector2 fingerPosition = new Vector2();
	private Vector2 shadowPosition = new Vector2();
	private Vector2 linePosition = new Vector2();
	private float boardSize;
	private float fingerSize;
	private float shadowSize;
	private float storedTime;
	private float timeTouching;
	private float timeSliding;
	private float timeUntouching;
	private float timeUnsliding;
	private float timeToEnd;
	private float timeProgress;
	private int lineAlpha;
	private float lineAngle;
	private float lineSize;

	public GOTutorialFingerSwipe()
	{
		super();
		reset();
	}

	/**
	 * Resets all object parameters to its default values
	 * @return the object itself
	 */
	public GOTutorialFingerSwipe reset()
	{
		transform.reset();
		transform.position.set(scene.cam.position);
		currentState = state.touching;
		setSlideMode(slideMode.left_right);
		boardSize = Math.min(scene.cam.frustumHeight, scene.cam.frustumWidth)*5f/7f;
		fingerSize = boardSize/5f;
		storedTime = 0f;
		setTimeSliding(_DEFAULT_TIME_SLIDING);
		setTimeTouching(_DEFAULT_TIME_TOUCHING);
		setTimeUnsliding(_DEFAULT_TIME_UNSLIDING);
		setTimeUnTouching(_DEFAULT_TIME_UNTOUCHING);
		enabled = true;
		return this;
	}

	/**
	 * Sets the finger movement as a custom template
	 * @param mode 8 possible directions
	 * @return the object itself
	 */
	public GOTutorialFingerSwipe setSlideMode(slideMode mode)
	{
		switch (mode)
		{
			case left_right:
				startPosition.set(_LEFT,_MIDDLE);
				endPosition.set(_RIGHT,_MIDDLE);
				break;
			case topleft_downright:
				startPosition.set(_LEFT,_TOP);
				endPosition.set(_RIGHT,_DOWN);
				break;
			case top_down:
				startPosition.set(_MIDDLE,_TOP);
				endPosition.set(_MIDDLE,_DOWN);
				break;
			case topright_downleft:
				startPosition.set(_RIGHT,_TOP);
				endPosition.set(_LEFT,_DOWN);
				break;
			case right_left:
				startPosition.set(_RIGHT,_MIDDLE);
				endPosition.set(_LEFT,_MIDDLE);
				break;
			case downright_topleft:
				startPosition.set(_RIGHT,_TOP);
				endPosition.set(_LEFT,_DOWN);
				break;
			case down_top:
				startPosition.set(_MIDDLE,_DOWN);
				endPosition.set(_MIDDLE,_TOP);
				break;
			case downleft_top_right:
				startPosition.set(_LEFT,_DOWN);
				endPosition.set(_RIGHT,_TOP);
				break;

		}
		return this;
	}

	public GOTutorialFingerSwipe setSlidePositions(Vector2 startPosition, Vector2 endPosition)
	{
		setSlideStartPosition(startPosition.x, startPosition.y);
		setSlideEndPosition(endPosition.x, endPosition.y);
		return this;
	}

	public GOTutorialFingerSwipe setSlidePositions(float startX, float startY, float endX, float endY)
	{
		setSlideStartPosition(startX, startY);
		setSlideEndPosition(endX, endY);
		return this;
	}

	public GOTutorialFingerSwipe setSlideStartPosition(Vector2 position)
	{
		setSlideStartPosition(position.x, position.y);
		return this;
	}

	public GOTutorialFingerSwipe setSlideEndPosition(Vector2 position)
	{
		setSlideEndPosition(position.x, position.y);
		return this;
	}

	public GOTutorialFingerSwipe setSlideStartPosition(float x, float y)
	{
		x = x < _LEFT ? _LEFT : (x > _RIGHT ? _RIGHT : x);
		y = y < _DOWN ?_DOWN : (y > _TOP ? _TOP : y);
		startPosition.set(x,y);
		return this;
	}

	public GOTutorialFingerSwipe setSlideEndPosition(float x, float y)
	{
		x = x < _LEFT ? _LEFT : (x > _RIGHT ? _RIGHT : x);
		y = y < _DOWN ?_DOWN : (y > _TOP ? _TOP : y);
		endPosition.set(x,y);
		return this;
	}

	private GOTutorialFingerSwipe setTimeTouching(float value)
	{
		this.timeTouching = value;
		return this;
	}

	private GOTutorialFingerSwipe setTimeUnTouching(float value)
	{
		this.timeUntouching = value;
		return this;
	}

	private GOTutorialFingerSwipe setTimeSliding(float value)
	{
		this.timeSliding = value;
		return this;
	}

	private GOTutorialFingerSwipe setTimeUnsliding(float value)
	{
		this.timeUnsliding = value;
		return this;
	}

	@Override
	public void update()
	{
		super.update();
		if(enabled)
		{
			updateStates();
			updateTimeToEnd();
			updateFingerPosition();
			updateShadowPosition();
			updateShadowSize();
			updateLinePosition();
			updateLineAlpha();
			updateLineAngle();
			updateLineSize();
		}
	}

	@Override
	public void present()
	{
		super.present();
		if(enabled)
		{
			//Draw line
			drawSprite(Scene.regionSquare, transform.position.x + linePosition.x * boardSize, transform.position.y + linePosition.y * boardSize, lineSize, boardSize / 40f, lineAngle, filterColor & ((lineAlpha << 24) | 0x00ffffff));
			//Draw finger shadow
			drawSprite(Scene.regionCircleGradient, transform.position.x + shadowPosition.x * boardSize, transform.position.y + shadowPosition.y * boardSize, shadowSize, shadowSize, 0xff000000);
			//Draw finger
			drawSprite(Scene.regionFinger, transform.position.x + fingerPosition.x * boardSize, transform.position.y + fingerPosition.y * boardSize, fingerSize, fingerSize);
		}
	}

	private void updateStates()
	{
		storedTime += TIME.deltaTime;
		if(currentState == state.touching && storedTime >= timeTouching)
		{
			storedTime = 0;
			currentState = state.sliding;
		}
		else if(currentState == state.sliding && storedTime >= timeSliding)
		{
			storedTime = 0;
			currentState = state.untouching;
		}
		else if(currentState == state.untouching && storedTime >= timeUntouching)
		{
			storedTime = 0;
			currentState = state.unsliding;
		}
		else if(currentState == state.unsliding && storedTime >= timeUnsliding)
		{
			storedTime = 0;
			currentState = state.touching;
		}
	}

	private void updateTimeToEnd()
	{
		if(currentState == state.touching)
			timeToEnd = timeTouching;
		else if(currentState == state.sliding)
			timeToEnd = timeSliding;
		else if(currentState == state.untouching)
			timeToEnd = timeUntouching;
		else if(currentState == state.unsliding)
			timeToEnd = timeUnsliding;
		timeProgress = storedTime/timeToEnd;
	}

	private void updateFingerPosition()
	{
		if(currentState == state.touching)
			fingerPosition.set(Interpolator.easyInOut(timeProgress, startPosition.x, startPosition.x), Interpolator.easyInOut(timeProgress, startPosition.y + _UP, startPosition.y - _UP));
		else if(currentState == state.sliding)
			fingerPosition.set(Interpolator.easyInOut(timeProgress, startPosition.x, endPosition.x), Interpolator.easyInOut(timeProgress, startPosition.y - _UP, endPosition.y - _UP));
		else if(currentState == state.untouching)
			fingerPosition.set(Interpolator.easyInOut(timeProgress, endPosition.x, endPosition.x), Interpolator.easyInOut(timeProgress, endPosition.y - _UP, endPosition.y + _UP));
		else if(currentState == state.unsliding)
			fingerPosition.set(Interpolator.easyInOut(timeProgress, endPosition.x, startPosition.x), Interpolator.easyInOut(timeProgress, endPosition.y + _UP, startPosition.y + _UP));
	}

	private void updateShadowPosition()
	{
		if(currentState == state.touching)
			shadowPosition.set(startPosition.x, startPosition.y);
		else if(currentState == state.sliding)
			shadowPosition.set(Interpolator.easyInOut(timeProgress, startPosition.x, endPosition.x), Interpolator.easyInOut(timeProgress, startPosition.y, endPosition.y));
		else if(currentState == state.untouching)
			shadowPosition.set(endPosition.x, endPosition.y);
		else if(currentState == state.unsliding)
			shadowPosition.set(Interpolator.easyInOut(timeProgress, endPosition.x, startPosition.x), Interpolator.easyInOut(timeProgress, endPosition.y, startPosition.y));
	}

	private void updateShadowSize()
	{
		if(currentState == state.touching)
			shadowSize = Interpolator.easyInOut(timeProgress, fingerSize, 0);
		else if(currentState == state.sliding)
			shadowSize = 0;
		else if(currentState == state.untouching)
			shadowSize = Interpolator.easyInOut(timeProgress, 0, fingerSize);
		else if(currentState == state.unsliding)
			shadowSize = fingerSize;
	}

	private void updateLinePosition()
	{
		if(currentState == state.touching)
			linePosition.set(startPosition.x, startPosition.y);
		else if(currentState == state.sliding)
			linePosition.set(startPosition.x + (Interpolator.easyInOut(timeProgress, startPosition.x, endPosition.x)-startPosition.x)/2f, startPosition.y + (Interpolator.easyInOut(timeProgress, startPosition.y, endPosition.y)-startPosition.y)/2f);
		else if(currentState == state.untouching)
			linePosition.set(startPosition.x + (endPosition.x - startPosition.x)/2f, startPosition.y + (endPosition.y - startPosition.y)/2f);
		else if(currentState == state.unsliding)
			linePosition.set(startPosition.x + (endPosition.x - startPosition.x)/2f, startPosition.y + (endPosition.y - startPosition.y)/2f);
	}

	private void updateLineSize()
	{
		if(currentState == state.touching)
			lineSize = 0;
		else if(currentState == state.sliding)
			lineSize = Interpolator.easyInOut(timeProgress, 0 , Vector2.len(startPosition,endPosition));
		else if(currentState == state.untouching)
			lineSize = Vector2.len(startPosition,endPosition);
		else if(currentState == state.unsliding)
			lineSize = Vector2.len(startPosition,endPosition);
		lineSize *= boardSize;
	}

	private void updateLineAngle()
	{
		lineAngle = (float) Math.atan2(endPosition.y-startPosition.y, endPosition.x-startPosition.x);
		lineAngle *= 180f/Math.PI;
	}

	private void updateLineAlpha()
	{
		if(currentState == state.touching)
			lineAlpha = 0;
		else if(currentState == state.sliding)
			lineAlpha = 255;
		else if(currentState == state.untouching)
			lineAlpha = 255;
		else if(currentState == state.unsliding)
			lineAlpha =255 - (int)(timeProgress * 255);
		lineAlpha = lineAlpha < 0 ? 0 : (lineAlpha > 255 ? 255 : lineAlpha);
	}
}
