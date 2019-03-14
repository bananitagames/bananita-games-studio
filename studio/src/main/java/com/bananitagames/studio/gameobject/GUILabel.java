package com.bananitagames.studio.gameobject;

import android.graphics.Color;

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

import java.util.List;

/**
 * Created by luis on 16/08/2018.
 */

public class GUILabel extends GameObject
{
	private boolean isVisible = true;
	// SIZE FOR LABEL
	private float sizeWidth;
	private float sizeHeight;
	// TEXT
	private String text = "";
	private Font textFont;
	private int textColor = 0xffffffff;

	/**
	 * Creates a default label with no parameters specified.
	 * By default, the label will be centered in screen.
	 * Its size will be width = screen_width / 4 and
	 * height = screen_height / 4
	 */
	public GUILabel()
	{
		super();
		transform.position.set(scene.cam.position);
		sizeWidth = scene.cam.frustumWidth/4f;
		sizeHeight = scene.cam.frustumHeight/4f;
		textFont = Scene.font;
	}

	public GUILabel setPosition(float x, float y)
	{
		transform.position.set(x, y);
		return this;
	}

	public GUILabel setSize(float width, float height)
	{
		sizeWidth = width;
		sizeHeight = height;
		return this;
	}

	public GUILabel setVisible(boolean visible)
	{
		this.isVisible = visible;
		return this;
	}

	public GUILabel setText(String text)
	{
		this.text = text;
		return this;
	}

	public GUILabel setTextFont(Font font)
	{
		this.textFont = font;
		return this;
	}

	public GUILabel setTextColor(int color)
	{
		this.textColor = color;
		return this;
	}

	@Override
	public void present()
	{
		super.present();
		if(isVisible)
		{
			float textSize = sizeHeight*0.5f;
			float textLength = text.length();
			if(textSize * textLength > sizeWidth - textSize)
				textSize = (sizeWidth - textSize)/textLength;
			drawTextCentered(textFont, text, textSize, transform.position.x, transform.position.y, textColor);
		}
	}

}
