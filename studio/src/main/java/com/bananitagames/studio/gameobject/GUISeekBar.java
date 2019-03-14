package com.bananitagames.studio.gameobject;

import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.INPUT;
import com.bananitagames.studio.Scene;
import com.bananitagames.studio.TextureRegion;
import com.bananitagames.studio.TouchEvent;
import com.bananitagames.studio.interfaces.GUISeekBarListener;
import com.bananitagames.studio.math.OverlapTester;
import com.bananitagames.studio.math.Rectangle;
import com.bananitagames.studio.math.Vector2;

import java.util.List;

/**
 * A GUI SeekBar, a default seekBar, very simple, easy to create.
 * @author Party Powered
 *
 */
public class GUISeekBar extends GameObject
{
	
	// SEEKBAR
	protected float percentage = 1f;
	protected GUISeekBarListener listener;
	protected boolean isVisible = true;
	protected boolean isEnabled = true;
	protected boolean isFocused = false;
	protected boolean isHorizontal = true;
	// TOUCH
	protected int pointer = -1;
	protected Vector2 touchPoint = new Vector2();
	protected Vector2 lastTouchPoint = new Vector2();
	// SIZE FOR BUTTON
	protected float sizeWidth;
	protected float sizeHeight;
	// COLLISION MASK
	protected Rectangle maskRectangle;
	// TEXTURE REGIONS
	protected TextureRegion regionBar;
	protected boolean isCustomTextureRegion = false;
	
	/**
	 * Creates a default seekBar with no parameters specified.
	 * By default, the seekBar will be centered in screen.
	 * Its size will be width = screen_width / 4 and
	 * height = screen_height / 4
	 */
	public GUISeekBar()
	{
		super();
		transform.position.set(scene.cam.position);
		sizeWidth = scene.cam.frustumWidth/4f;
		sizeHeight = scene.cam.frustumHeight/4f;
		maskRectangle = new Rectangle(transform.position.x-sizeWidth/2f, transform.position.y-sizeHeight/2f, sizeWidth, sizeHeight);
	}
	
	public GUISeekBar setPosition(float x, float y)
	{
		transform.position.set(x, y);
		return this;
	}
	
	public GUISeekBar setTextureRegion(TextureRegion region)
	{
		if(region==null)
			throw new NullPointerException("You called GUISeekBar.setTextureRegions(TextureRegion) with a null parameter.");
		isCustomTextureRegion = true;
		regionBar = region;
		return this;
	}
	
	public GUISeekBar setSize(float width, float height)
	{
		sizeWidth = width;
		sizeHeight = height;
		return this;
	}
	
	public GUISeekBar setGUIButtonListener(GUISeekBarListener listener)
	{
		this.listener = listener;
		return this;
	}
	
	public GUISeekBar setVisible(boolean visible)
	{
		this.isVisible = visible;
		return this;
	}
	
	public GUISeekBar setEnabled(boolean enabled)
	{
		this.isEnabled = enabled;
		return this;
	}
	
	public GUISeekBar setOrientation(boolean isHorizontal)
	{
		this.isHorizontal = isHorizontal;
		return this;
	}
	
	public GUISeekBar setOrientationHorizontal()
	{
		this.isHorizontal = true;
		return this;
	}
	
	public GUISeekBar setOrientationVertical()
	{
		this.isHorizontal = false;
		return this;
	}
	
	@Override
	public void update()
	{
		super.update();
		updateMasksTransform();
		updateTouch();
		updateSeekBarStatus();
	}
	
	@Override
	public void present()
	{
		super.present();
		if(isVisible)
		{
			if(isCustomTextureRegion)
			{
				drawSprite(regionBar, transform.position.x, transform.position.y, sizeWidth, sizeHeight);
			}
			else
			{
				drawSprite(Scene.regionSquare, transform.position.x, transform.position.y, sizeWidth, sizeHeight, 0xaa000000);
				if(isHorizontal)
				{
					drawSprite(Scene.regionSquare, transform.position.x - sizeWidth*(1f-percentage)/2f, transform.position.y, sizeWidth*percentage, sizeHeight, 0xffffffff);
				}
				else
				{
					drawSprite(Scene.regionSquare, transform.position.x, transform.position.y - sizeHeight*(1f-percentage)/2f, sizeWidth, sizeHeight*percentage, 0xffffffff);
				}
			}
		}
	}
	
	private void updateMasksTransform()
	{
		maskRectangle.lowerLeft.set(transform.position.x-sizeWidth/2f, transform.position.y-sizeHeight/2f);
		maskRectangle.width = sizeWidth;
		maskRectangle.height = sizeHeight;
	}
	
	private void updateTouch()
	{
		List<TouchEvent> touchEvents = INPUT.getTouchEvents();
		// Touch operations only if button is enabled
		if(isEnabled)
		{	
			int len = touchEvents.size();
			for(int i = 0; i < len; i++)
	        {
	            TouchEvent event = touchEvents.get(i);
	            if(event.pointer == pointer || pointer == -1)
	            {
	            	touchPoint.set(event.x, event.y);
	                scene.cam.touchToWorld(touchPoint);
	            	if(event.type == TouchEvent.TOUCH_DOWN && isSeekBarFocused())
            		{
	            		if(listener!=null) listener.onPressed(this);
	            		lastTouchPoint.set(touchPoint);
	            		pointer = event.pointer;
            		}
	            	else if(event.pointer == pointer && event.type == TouchEvent.TOUCH_UP)
	            	{
	            		pointer = -1;
	            		lastTouchPoint.set(touchPoint);
	            	}
	            	else if(event.pointer == pointer && event.type == TouchEvent.TOUCH_DRAGGED && !isFocused && isSeekBarFocused())
	            	{
	            		isFocused = true;
	            		lastTouchPoint.set(touchPoint);
	            		if(listener!=null) listener.onGainedFocus(this);
	            	}
	            	else if(event.pointer == pointer && event.type == TouchEvent.TOUCH_DRAGGED && isFocused && !isSeekBarFocused())
	            	{
	            		isFocused = false;
	            		lastTouchPoint.set(touchPoint);
	            		if(listener!=null) listener.onLostFocus(this);
	            	}
	            	else if(event.pointer == pointer && event.type == TouchEvent.TOUCH_DRAGGED)
	            	{
	            		lastTouchPoint.set(touchPoint);
	            	}
	            }
	        }
		}
	}
	
	private void updateSeekBarStatus()
	{
		if(pointer != -1)
		{
			if(isHorizontal)
			{
				percentage = (lastTouchPoint.x - maskRectangle.lowerLeft.x) / (maskRectangle.width);
			}
			else
			{
				percentage = (lastTouchPoint.y - maskRectangle.lowerLeft.y) / (maskRectangle.height);
			}
			percentage = percentage > 1f ? 1 : (percentage < 0f ? 0f : percentage);
		}
	}
	
	private boolean isSeekBarFocused()
	{
		return OverlapTester.pointInRectangle(maskRectangle, touchPoint);
	}
	
	
}
