package com.bananitagames.studio.gameobject;

import java.util.List;

import android.graphics.Color;
import android.util.Log;

import com.bananitagames.studio.CONSTANTS;
import com.bananitagames.studio.Font;
import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.INPUT;
import com.bananitagames.studio.Scene;
import com.bananitagames.studio.TIME;
import com.bananitagames.studio.TextureRegion;
import com.bananitagames.studio.TouchEvent;
import com.bananitagames.studio.interfaces.GUIButtonListener;
import com.bananitagames.studio.math.Circle;
import com.bananitagames.studio.math.OverlapTester;
import com.bananitagames.studio.math.Rectangle;
import com.bananitagames.studio.math.Vector2;

/**
 * A GUI Button, a default button, very simple, easy to create.
 * And extremely customizable
 * @author Party Powered
 *
 */
public class GUIButton extends GameObject
{
	
	private static final float _DEF_TIME_CLICKED_STATE = 0.25f; // Seconds which state pressed is maintained
	private static final int STATE_NORMAL = 0;
	private static final int STATE_HOVER = 1;
	private static final int STATE_CLICK = 2;
	private static final int STATE_BLOCKED = 3;
	// BUTTON
	private int state = 0;// 0 = normal; 1 = hover; 2 = click; 3 = blocked
	private int customId;
	private GUIButtonListener listener;
	private boolean isVisible = true;
	private boolean isEnabled = true;
	private boolean isFocused = false;
	private boolean isCustomColor = false;
	private int colorNormal;
	private int colorHover;
	private int colorClick;
	private int colorBlocked;
	// TOUCH
	private int pointer = -1;
	private Vector2 touchPoint = new Vector2();
	private float storedTimeClicked;
	// SIZE FOR BUTTON
	private float sizeWidth;
	private float sizeHeight;
	private float sizeRadius;
	// COLLISION MASK
	private boolean isMaskRectangle = true;
	private Rectangle maskRectangle;
	private Circle maskCircle;	
	// TEXTURE REGIONS
	private TextureRegion regions[];
	private boolean isCustomTextureRegion = false;
	private int regionColorFilter;
	private int currentIndex;
	// TEXT
	private String text = "";
	private Font textFont;
	private int textColor = 0xffffffff;
	
	/**
	 * Creates a default button with no parameters specified.
	 * By default, the button will be centered in screen.
	 * Its size will be width = screen_width / 4 and
	 * height = screen_height / 4
	 */
	public GUIButton()
	{
		super();
		transform.position.set(scene.cam.position);
		sizeWidth = scene.cam.frustumWidth/4f;
		sizeHeight = scene.cam.frustumHeight/4f;
		sizeRadius = sizeWidth/2f;
		maskRectangle = new Rectangle(transform.position.x-sizeWidth/2f, transform.position.y-sizeHeight/2f, sizeWidth, sizeHeight);
		maskCircle = new Circle(transform.position.x, transform.position.y, sizeRadius);
		textFont = Scene.font;
	}
	
	public GUIButton setPosition(float x, float y)
	{
		transform.position.set(x, y);
		return this;
	}
	
	public GUIButton setTextureRegions(TextureRegion regionNormal, TextureRegion regionHover, TextureRegion regionClick, TextureRegion regionBlocked)
	{
		if(regionNormal==null || regionHover==null || regionClick==null || regionBlocked==null)
			throw new NullPointerException("You called GUIButton.setTextureRegions(TextureRegion,TextureRegion,TextureRegion,TextureRegion) with a null"
					+" parameter. ");
		isCustomTextureRegion = true;
		regions = new TextureRegion[4];
		regions[0] = regionNormal;
		regions[1] = regionHover;
		regions[2] = regionClick;
		regions[3] = regionBlocked;
		return this;
	}
	
	public GUIButton setSize(float width, float height)
	{
		sizeWidth = width;
		sizeHeight = height;
		sizeRadius = width/2f;
		return this;
	}
	
	public GUIButton setRadius(float radius)
	{
		sizeRadius = radius;
		sizeWidth = radius*2f;
		sizeHeight = radius*2f;
		return this;
	}
	
	public GUIButton setMaskCircle()
	{
		isMaskRectangle = false;
		return this;
	}
	
	public GUIButton setMaskRectangle()
	{
		isMaskRectangle = true;
		return this;
	}
	
	public GUIButton setGUIButtonListener(GUIButtonListener listener)
	{
		this.listener = listener;
		return this;
	}
	
	public GUIButton setVisible(boolean visible)
	{
		this.isVisible = visible;
		return this;
	}
	
	public GUIButton setEnabled(boolean enabled)
	{
		this.isEnabled = enabled;
		if(enabled && this.state == STATE_BLOCKED)
			this.state = STATE_NORMAL;
		else if(!enabled && this.state != STATE_BLOCKED)
			this.state = STATE_BLOCKED;
		return this;
	}

	public GUIButton setColor(int color)
	{
		return setColor(color, color | 0x00aaaaaa, color | 0x00cccccc, color & 0xff444444);
	}

	public GUIButton setColor(int colorNormal, int colorHover, int colorClick, int colorBlocked)
	{
		this.isCustomColor = true;
		this.colorNormal = colorNormal;
		this.colorHover = colorHover;
		this.colorClick = colorClick;
		this.colorBlocked = colorBlocked;
		return this;
	}
	
	public GUIButton setCustomId(int id)
	{
		this.customId = id;
		return this;
	}

	public GUIButton setText(String text)
	{
		this.text = text;
		return this;
	}

	public GUIButton setTextFont(Font font)
	{
		this.textFont = font;
		return this;
	}

	public GUIButton setTextColor(int color)
	{
		this.textColor = color;
		return this;
	}
	
	public int getCustomId()
	{
		return customId;
	}

	public int getState() { return state;}
	
	@Override
	public void update()
	{
		super.update();
		updateMasksTransform();
		updateTouch();
		if(state == STATE_CLICK) updateTimeClicked(TIME.deltaTime);
	}
	
	@Override
	public void present()
	{
		super.present();
		if(isVisible)
		{
			switch (state) {
			case STATE_NORMAL:
				currentIndex = 0;
				regionColorFilter = isCustomColor ? colorNormal : Color.rgb(59, 89, 152);
				break;
			case STATE_HOVER:
				currentIndex = 1;
				regionColorFilter = isCustomColor ? colorHover : Color.rgb(99, 129, 192);
				break;
			case STATE_CLICK:
				currentIndex = 2;
				regionColorFilter = isCustomColor ? colorClick : Color.rgb(170, 130, 41);
				break;
			case STATE_BLOCKED:
				currentIndex = 3;
				regionColorFilter = isCustomColor ? colorBlocked : Color.rgb(200, 200, 200);
				break;
			}
			if(isCustomTextureRegion)
			{
				drawSprite(regions[currentIndex], transform.position.x, transform.position.y, sizeWidth, sizeHeight, transform.rotation);
			}
			else
			{
				drawSprite(Scene.regionButtonRectangle, transform.position.x, transform.position.y, sizeWidth, sizeHeight, transform.rotation, regionColorFilter);
			}
			float textSize = sizeHeight*0.5f;
			float textLength = text.length();
			if(textSize * textLength > sizeWidth - textSize)
				textSize = (sizeWidth - textSize)/textLength;
			drawTextCentered(textFont, text, textSize, transform.position.x, transform.position.y, textColor);
		}
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
	            	if(event.type == TouchEvent.TOUCH_DOWN && isButtonFocused())
            		{
	            		if(listener!=null) listener.onPressed(this);
	            		state = STATE_HOVER;
	            		pointer = event.pointer;
            		}
	            	else if(event.pointer == pointer && event.type == TouchEvent.TOUCH_UP)
	            	{
	            		pointer = -1;
	            		if(isButtonFocused())
	            		{
	            			storedTimeClicked = 0;
	            			state = STATE_CLICK;
		            		if(listener!=null) listener.onClick(this);
	            		}
	            		else
	            		{
	            			state = STATE_NORMAL;
	            		}
	            	}
	            	else if(event.pointer == pointer && event.type == TouchEvent.TOUCH_DRAGGED && !isFocused && isButtonFocused())
	            	{
	            		isFocused = true;
	            		state = STATE_HOVER;
	            		if(listener!=null) listener.onGainedFocus(this);
	            	}
	            	else if(event.pointer == pointer && event.type == TouchEvent.TOUCH_DRAGGED && isFocused && !isButtonFocused())
	            	{
	            		isFocused = false;
	            		state = STATE_NORMAL;
	            		if(listener!=null) listener.onLostFocus(this);
	            	}
	            }
	        }
		}
		else
		{
			state = STATE_BLOCKED;
		}
	}
	
	private void updateMasksTransform()
	{
		maskRectangle.lowerLeft.set(transform.position.x-Math.abs(sizeWidth)/2f, transform.position.y-Math.abs(sizeHeight)/2f);
		maskRectangle.width = Math.abs(sizeWidth);
		maskRectangle.height = Math.abs(sizeHeight);
		maskCircle.center.set(transform.position);
		maskCircle.radius = Math.abs(sizeWidth)/2f;
	}
	
	private void updateTimeClicked(float deltaTime)
	{
		storedTimeClicked += deltaTime;
		if(storedTimeClicked >= _DEF_TIME_CLICKED_STATE)
		{
			state = STATE_NORMAL;
		}
	}
	
	private boolean isButtonFocused()
	{
		return ((isMaskRectangle && OverlapTester.pointInRectangle(maskRectangle, touchPoint))
				|| !isMaskRectangle && OverlapTester.pointInCircle(maskCircle, touchPoint));
	}
	
}
