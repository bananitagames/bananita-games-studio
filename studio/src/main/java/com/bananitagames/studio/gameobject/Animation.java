package com.bananitagames.studio.gameobject;

import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.TIME;
import com.bananitagames.studio.TextureRegion;
import com.bananitagames.studio.math.Vector2;

public class Animation 
{
	public static final float DEFAULT_FRAMETIME = 0.25f;
	private GOTransform transform;
	private Vector2 relativePosition;
	private TextureRegion[] regions;
	private int index;
	private float width, height;
	private float storedTime, frameTime;
	private int color = 0xFFFFFFFF;

    ///////////////////////////////////////////////////
    //
    // CONSTRUCTORS
    //
    ///////////////////////////////////////////////////

	public Animation(float width, float height, TextureRegion region)
	{
		this(0, 0, width, height, region, DEFAULT_FRAMETIME);
	}

	public Animation(float width, float height, TextureRegion[] region)
	{
		this(0, 0, width, height, region, DEFAULT_FRAMETIME);
	}

	public Animation(float x, float y, float width, float height, TextureRegion region)
	{
		this(x, y, width, height, region, DEFAULT_FRAMETIME);
	}

	public Animation(float x, float y, float width, float height, TextureRegion[] region)
	{
		this(x, y, width, height, region, DEFAULT_FRAMETIME);
	}
	
	public Animation(float x, float y, float width, float height, TextureRegion region, float frameTime)
	{
		this(x, y, width, height, new TextureRegion[]{region}, frameTime);
	}
	
	public Animation(float x, float y, float width, float height, TextureRegion[] region, float frameTime)
	{
		this.transform = new GOTransform();
		this.relativePosition = new Vector2();
		this.transform.position.set(x, y);
		this.width = width;
		this.height = height;
		regions = region;
	}
	
    ///////////////////////////////////////////////////
    //
    // SETTERS
    //
    ///////////////////////////////////////////////////
	
	public Animation setRelativePosition(float x, float y)
	{
		this.transform.position.set(x, y);
		return this;
	}
	
	public Animation setSize(float width, float height)
	{
		this.width = width;
		this.height = height;
		return this;
	}
	
	public Animation setColor(int color)
	{
		this.color = color;
		return this;
	}
	
	public Animation setTextureRegion(TextureRegion region)
	{
		return setTextureRegion(new TextureRegion[]{region});
	}
	

	public Animation setTextureRegion(TextureRegion[] regions)
	{
		this.regions = regions;
		return this;
	}
	
	public Animation setFrameTime(float frameTime)
	{
		this.frameTime = frameTime;
		return this;
	}
	
    ///////////////////////////////////////////////////
    //
    // UPDATE METHODS
    //
    ///////////////////////////////////////////////////
	
	public void update()
	{
		transform.update();
		storedTime += TIME.deltaTime;
		if(storedTime > frameTime)
		{
			storedTime = 0f;
			index = (index+1)%regions.length;
		}
	}
	
	public void present(GameObject g)
	{
		relativePosition.set(transform.position);
		if(g.transform.rotation != 0)
		{
			relativePosition.rotate(g.transform.rotation);
		}
		g.drawSprite(
				regions[index],
				g.transform.position.x + relativePosition.x,
				g.transform.position.y + relativePosition.y, 
				width, 
				height, 
				g.transform.rotation + transform.rotation, 
				color);
	}
	
}
