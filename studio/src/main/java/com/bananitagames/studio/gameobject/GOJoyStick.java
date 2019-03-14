package com.bananitagames.studio.gameobject;

import java.util.List;

import android.graphics.Color;
import android.util.Log;

import com.bananitagames.studio.CONSTANTS;
import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.INPUT;
import com.bananitagames.studio.Scene;
import com.bananitagames.studio.TextureRegion;
import com.bananitagames.studio.TouchEvent;
import com.bananitagames.studio.math.Circle;
import com.bananitagames.studio.math.OverlapTester;
import com.bananitagames.studio.math.Vector2;

public class GOJoyStick extends GameObject
{
	private static final int COLOR_FILTER_DEFAULT =  0xffffffff;
	public TextureRegion region;
	public float centerX;
	public float centerY;
	public float size;
	public float radius;
	int pointer;
	public int colorFilter;
	public boolean isVisibleShadow;
	public boolean isLoggingOffset;
	Vector2 touchPoint;
	protected Vector2 offsetPrivate;
	public Vector2 offset;
	
	// Support variables
	private Circle joystickArea;

	//////////////////////////////////////////////////////
	// CONSTRUCTORS
	//////////////////////////////////////////////////////
	
	/**
	 * This constructor generates a gameObject which represents
	 * a joystick.
	 * This construction is empty, so you will have to use setter
	 * methods, or use the other construction, to get the desired effects.
	 * Anyway, there won't be any exceptions by only constructing the object
	 * this way
	 */
	public GOJoyStick()
	{
		super();
		this.region = Scene.regionButtonCircle;
		init();
	}
	
	/**
	 * This constructor generates a gameObject which represents
	 * a joystick.
	 * The main function of this object is its touchability.
	 * So, to get the right information during runtime.
	 * Access <code>offset</code> variable, which is a vector2 type
	 * that stores the value of the joystick offset, from [-1,1] in 
	 * both axis <code>x y</code>
	 * @param centerX 
	 * @param centerY
	 * @param radius The radius of the touchable area. Don't confuse
	 * this parameter with <code>size</code>
	 * @param size The size that will have the joystick when drawn
	 * @param region The region that will be presented as joystick sprite
	 */
	public GOJoyStick(float centerX, float centerY, float radius, float size, TextureRegion region)
	{
		super();
		this.region = region;
		this.centerX = centerX;
		this.centerY = centerY;
		this.size = size;
		this.radius = radius;
		init();
	}

	private void init()
	{
		this.colorFilter = COLOR_FILTER_DEFAULT;
		this.isVisibleShadow = true;
		this.isLoggingOffset = false;
		this.touchPoint = new Vector2();
		this.offsetPrivate = new Vector2();
		this.offset = new Vector2();
		this.joystickArea = new Circle(0, 0, 0);
		this.pointer = -1;
	}
	
	//////////////////////////////////////////////////////
	// SETTERS
	//////////////////////////////////////////////////////
	
	public GOJoyStick setCenter(float centerX, float centerY)
	{
		this.centerX = centerX;
		this.centerY = centerY;
		return this;
	}

	public GOJoyStick setCenter(Vector2 center)
	{
		return setCenter(center.x, center.y);
	}

	public GOJoyStick setRadius(float radius)
	{
		this.radius = radius;
		return this;
	}

	public GOJoyStick setSize(float size)
	{
		this.size = size;
		return this;
	}

	public GOJoyStick setRegion(TextureRegion region)
	{
		this.region = region;
		return this;
	}

	public GOJoyStick setShadowVisibility(boolean visible)
	{
		this.isVisibleShadow = visible;
		return this;
	}
	
	public GOJoyStick setColorFilter(int color)
	{
		this.colorFilter = color;
		return this;
	}

	public GOJoyStick setLoggingOffset(boolean logOffset)
	{
		this.isLoggingOffset = logOffset;
		return this;
	}
	
	//////////////////////////////////////////////////////
	// UPDATE & PRESENT
	//////////////////////////////////////////////////////
	@Override
	public void update()
	{
		super.update();
		List<TouchEvent> touchEvents = INPUT.getTouchEvents();
		int len = touchEvents.size();
		transform.position.set(centerX, centerY);
		joystickArea.radius = radius;
		joystickArea.center.set(transform.position.x, transform.position.y);
        for(int i = 0; i < len; i++)
        {
            TouchEvent event = touchEvents.get(i);
            if(event.pointer == pointer || pointer == -1)
            {
            	touchPoint.set(event.x, event.y);
                scene.cam.touchToWorld(touchPoint);
            	if(event.type == TouchEvent.TOUCH_DOWN && OverlapTester.pointInCircle(joystickArea, touchPoint))
            	{
            		pointer = event.pointer;
            		offsetPrivate.set(touchPoint.x-joystickArea.center.x, touchPoint.y-joystickArea.center.y);
            		float module = offsetPrivate.len() > radius ? radius : offsetPrivate.len();
            		float angle = offsetPrivate.angle()*Vector2.TO_RADIANS;
            		offsetPrivate.set((float)(module*Math.cos(angle)),(float)(module*Math.sin(angle)));
            	}
            	else if(event.pointer == pointer && event.type == TouchEvent.TOUCH_UP)
            	{
            		pointer = -1;
            		offsetPrivate.set(0, 0);
            	}
            	else
            	{
            		if(event.pointer == pointer)
            		{
                		offsetPrivate.set(touchPoint.x-joystickArea.center.x, touchPoint.y-joystickArea.center.y);
                		float module = offsetPrivate.len() > radius ? radius : offsetPrivate.len();
                		float angle = offsetPrivate.angle()*Vector2.TO_RADIANS;
                		offsetPrivate.set((float)(module*Math.cos(angle)),(float)(module*Math.sin(angle)));
            		}
            	}
            }
        }
        offset.set(offsetPrivate.x/radius, offsetPrivate.y/radius);
        if(CONSTANTS._DEBUG && isLoggingOffset) Log.d(CONSTANTS._TAG, "GOJoystick offset [x=" + offset.x + ", y=" + offset.y + "];");
	}
	@Override
	public void present() {
		super.present();
		// Draw the gradient background
		if(isVisibleShadow) drawSprite(Scene.regionCircle, transform.position.x + offsetPrivate.x, transform.position.y + offsetPrivate.y, size, size, COLOR_FILTER_DEFAULT);
		// Draw the wheel
		drawSprite(region, transform.position.x + offsetPrivate.x, transform.position.y + offsetPrivate.y, size, size, colorFilter);
	}
}
