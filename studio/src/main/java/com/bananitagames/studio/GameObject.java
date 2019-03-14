package com.bananitagames.studio;

import com.bananitagames.studio.actions.Behavior;
import com.bananitagames.studio.gameobject.Animation;
import com.bananitagames.studio.gameobject.GOAnimation;
import com.bananitagames.studio.gameobject.GOTransform;
import com.bananitagames.studio.interfaces.Texture;


public class GameObject
{

	private static final int _DEFAULT_FILTER_COLOR = 0xffffffff;
	
	public static 	int			globalId;
	public			int			id;
	public 			int 		filterColor;
	public 			boolean 	destroyed;
	public			Behavior	behavior;
	public 			Scene		scene;
    public final 	GOTransform transform;
    public 			GOAnimation animation;

    
    ///////////////////////////////////////////////////
    //
    // CONSTRUCTORS
    //
    ///////////////////////////////////////////////////
    
    public GameObject()
    {
		globalId++;
    	this.id = globalId;
		this.scene = ActivityGameManager.getInstance().getActivity().getScene();
		this.filterColor = _DEFAULT_FILTER_COLOR;
        this.transform = new GOTransform();
    }

    public GameObject(float x, float y)
    {
    	this();
    	this.transform.position.set(x,y);
    }

	///////////////////////////////////////////////////
	//
	// BEHAVIOR SETTER/GETTER
	//
	///////////////////////////////////////////////////	
    
    public final GameObject setBehavior(Behavior behavior)
    {
    	this.behavior = behavior;
    	return this;
    }
    
    public final Behavior getBehavior()
    {
    	return behavior;
    }

	///////////////////////////////////////////////////
	//
	// ANIMATION SETTER/GETTER
	//
	///////////////////////////////////////////////////	

    public final GameObject setAnimation(float width, float height, TextureRegion region)
    {
    	return setAnimation(new Animation(width, height, region));
    }

    public final GameObject setAnimation(Animation animation)
    {
    	return setAnimation(new GOAnimation(animation));
    }
    
    public final GameObject setAnimation(GOAnimation animation)
    {
    	this.animation = animation;
    	return this;
    }
    
    public final GOAnimation getAnimation()
    {
    	if(animation == null) animation = new GOAnimation();
    	return animation;
    }
    
	///////////////////////////////////////////////////
	//
	// UPDATE
	//
	///////////////////////////////////////////////////	
    
    public void update()
    {
    	transform.update();
    	if(behavior != null) behavior.update(this);
    	if(animation != null) animation.update();
    }

	public void destroy()
	{
		destroyed = true;
	}
    
	///////////////////////////////////////////////////
	//
    // PRESENT
	//
	///////////////////////////////////////////////////	

    public void present()
    {
    	if(animation != null) animation.present(this);
    }

	///////////////////////////////////////////////////
	//
	// FINALS
	//
	///////////////////////////////////////////////////

	public final void drawSprite(TextureRegion region, float width, float height)
	{
		scene.drawTextureRegion(SceneLayer.getCurrentGlobalLayerId(), region.texture, region.u1, region.u2, region.v1, region.v2, transform.position.x, transform.position.y, width, height, 0, filterColor, filterColor, filterColor, filterColor);
	}

	public final void drawSprite(TextureRegion region, float width, float height, int color)
	{
		scene.drawTextureRegion(SceneLayer.getCurrentGlobalLayerId(), region.texture, region.u1, region.u2, region.v1, region.v2, transform.position.x, transform.position.y, width, height, 0, color, color, color, color);
	}

	public final void drawSprite(TextureRegion region, float width, float height, float angle)
	{
		scene.drawTextureRegion(SceneLayer.getCurrentGlobalLayerId(), region.texture, region.u1, region.u2, region.v1, region.v2, transform.position.x, transform.position.y, width, height, angle, filterColor, filterColor, filterColor, filterColor);
	}

	public final void drawSprite(TextureRegion region, float x, float y, float width, float height)
	{
		scene.drawTextureRegion(SceneLayer.getCurrentGlobalLayerId(), region.texture, region.u1, region.u2, region.v1, region.v2, x, y, width, height, 0, filterColor, filterColor, filterColor, filterColor);
	}

	public final void drawSprite(TextureRegion region, float x, float y, float width, float height, float angle)
	{
		scene.drawTextureRegion(SceneLayer.getCurrentGlobalLayerId(), region.texture, region.u1, region.u2, region.v1, region.v2, x, y, width, height, angle, filterColor, filterColor, filterColor, filterColor);
	}

	public final void drawSprite(TextureRegion region, float x, float y, float width, float height, int color)
	{
		scene.drawTextureRegion(SceneLayer.getCurrentGlobalLayerId(), region.texture, region.u1, region.u2, region.v1, region.v2, x, y, width, height, 0, color, color, color, color);
	}

	public final void drawSprite(TextureRegion region, float x, float y, float width, float height, float angle, int color)
	{
		scene.drawTextureRegion(SceneLayer.getCurrentGlobalLayerId(), region.texture, region.u1, region.u2, region.v1, region.v2, x, y, width, height, angle, color, color, color, color);
	}

	public final void drawSprite(TextureRegion region, float x, float y, float width, float height, float angle, int colorTopLeft, int colorTopRight, int colorBottonLeft, int colorBottomRight)
	{
		scene.drawTextureRegion(SceneLayer.getCurrentGlobalLayerId(), region.texture, region.u1, region.u2, region.v1, region.v2, x, y, width, height, angle, colorTopLeft, colorTopRight, colorBottonLeft, colorBottomRight);
	}

	public final void drawTextureRegion(Texture texture, float u1, float u2, float v1, float v2, float x, float y, float width, float height, float angle, int colorTopLeft, int colorTopRight, int colorBottonLeft, int colorBottomRight)
	{
		scene.drawTextureRegion(SceneLayer.getCurrentGlobalLayerId(), texture, u1, u2, v1, v2, x, y, width, height, angle, colorTopLeft, colorTopRight, colorBottonLeft, colorBottomRight);
	}

	public final void drawSprite(int layerId, TextureRegion region, float width, float height)
	{
		scene.drawTextureRegion(layerId, region.texture, region.u1, region.u2, region.v1, region.v2, transform.position.x, transform.position.y, width, height, 0, filterColor, filterColor, filterColor, filterColor);
	}

	public final void drawSprite(int layerId, TextureRegion region, float width, float height, int color)
	{
		scene.drawTextureRegion(layerId, region.texture, region.u1, region.u2, region.v1, region.v2, transform.position.x, transform.position.y, width, height, 0, color, color, color, color);
	}

	public final void drawSprite(int layerId, TextureRegion region, float width, float height, float angle)
	{
		scene.drawTextureRegion(layerId, region.texture, region.u1, region.u2, region.v1, region.v2, transform.position.x, transform.position.y, width, height, angle, filterColor, filterColor, filterColor, filterColor);
	}

	public final void drawSprite(int layerId, TextureRegion region, float x, float y, float width, float height)
	{
		scene.drawTextureRegion(layerId, region.texture, region.u1, region.u2, region.v1, region.v2, x, y, width, height, 0, filterColor, filterColor, filterColor, filterColor);
	}

	public final void drawSprite(int layerId, TextureRegion region, float x, float y, float width, float height, float angle)
	{
		scene.drawTextureRegion(layerId, region.texture, region.u1, region.u2, region.v1, region.v2, x, y, width, height, angle, filterColor, filterColor, filterColor, filterColor);
	}

	public final void drawSprite(int layerId, TextureRegion region, float x, float y, float width, float height, int color)
	{
		scene.drawTextureRegion(layerId, region.texture, region.u1, region.u2, region.v1, region.v2, x, y, width, height, 0, color, color, color, color);
	}

	public final void drawSprite(int layerId, TextureRegion region, float x, float y, float width, float height, float angle, int color)
	{
		scene.drawTextureRegion(layerId, region.texture, region.u1, region.u2, region.v1, region.v2, x, y, width, height, angle, color, color, color, color);
	}

	public final void drawSprite(int layerId,TextureRegion region, float x, float y, float width, float height, float angle, int colorTopLeft, int colorTopRight, int colorBottonLeft, int colorBottomRight)
	{
		scene.drawTextureRegion(layerId, region.texture, region.u1, region.u2, region.v1, region.v2, x, y, width, height, angle, colorTopLeft, colorTopRight, colorBottonLeft, colorBottomRight);
	}

	public final void drawTextureRegion(int layerId, Texture texture, float u1, float u2, float v1, float v2, float x, float y, float width, float height, float angle, int colorTopLeft, int colorTopRight, int colorBottonLeft, int colorBottomRight)
	{
		scene.drawTextureRegion(layerId, texture, u1, u2, v1, v2, x, y, width, height, angle, colorTopLeft, colorTopRight, colorBottonLeft, colorBottomRight);
	}

	public final void drawTextCentered(Font font, String text, float charWidth, float x, float y, int colorFilter)
	{
		drawText(SceneLayer.getCurrentGlobalLayerId(), font, text, charWidth, x - (text.length()/2f - 0.5f) * charWidth, y, colorFilter);
	}

	public final void drawTextCentered(int layerId, Font font, String text, float charWidth, float x, float y, int colorFilter)
	{
		drawText(layerId, font, text, charWidth, x - (text.length()/2f - 0.5f) * charWidth, y, colorFilter);
	}

	public final void drawText(Font font, String text, float charWidth, float x, float y, int colorFilter)
	{
		drawText(SceneLayer.getCurrentGlobalLayerId(), font, text, charWidth, x, y, colorFilter);
	}

	public final void drawText(int layerId, Font font, String text, float charWidth, float x, float y, int colorfilter)
	{
		for(int i = 0, len = text.length(); i < len; i++) {
			int c = text.charAt(i) - ' ';
			if(c < 0 || c > font.glyphs.length - 1)
				continue;

			TextureRegion glyph = font.glyphs[c];
			drawTextureRegion(layerId, glyph.texture, glyph.u1, glyph.u2, glyph.v1, glyph.v2, x, y, charWidth, charWidth*font.glyphFactor, 0f, colorfilter, colorfilter, colorfilter, colorfilter);
			x += charWidth;
		}
	}
}
