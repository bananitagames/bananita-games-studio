package com.bananitagames.studio.gameobject;

import java.util.List;

import android.graphics.Color;

import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.Scene;
import com.bananitagames.studio.math.Rectangle;
import com.bananitagames.studio.math.Vector2;

public class GOWindow extends GameObject {

	public static final int COLOR_DEFAULT = Color.argb(255, 40, 40, 255);
	
	private Rectangle rectangle;
	private int color = COLOR_DEFAULT;
	
	/**
	 * This constructor instantiates a window that will be 
	 * presented, by default, at transform.position(0,0)
	 * with <code>guicam.size/2</code>
	 * with a <code>Blue color #2828FF ARGB(255, 40, 40, 255)</code>
	 */
	public GOWindow()
	{
		super();
		transform.position.set(scene.cam.position);
		rectangle = new Rectangle(
				scene.cam.position.x - scene.cam.frustumWidth/4f,
				scene.cam.position.y - scene.cam.frustumHeight/4f,
				scene.cam.frustumWidth/2f,
				scene.cam.frustumHeight/2f);
	}
	
	/**
	 * This method sets the position of the lower-left Corner of the window
	 * @param position
	 * @return GOWindow
	 */
	public GOWindow setPosition(Vector2 position)
	{
		return setPosition(position.x, position.y);
	}
	
	/**
	 * This method sets the position of the lower-left Corner of the window
	 * @return GOWindow
	 */
	public GOWindow setPosition(float x, float y)
	{
		this.transform.position.set(x, y);
		this.rectangle.lowerLeft.set(this.transform.position);
		return this;
	}
	
	/**
	 * This method sets the size of the window.
	 * By default, any GOWindow has <code>size=scene.guicam.size/2</code>
	 * @param width
	 * @param height
	 * @return GOWindow
	 */
	public GOWindow setSize(float width, float height)
	{
		this.rectangle.width = width;
		this.rectangle.height = height;
		return this;
	}
	
	/**
	 * This method sets the color of the window
	 * By default, <code>GOWindow.color=Blue color;HEX=#2828FF;ARGB=(255, 40, 40, 255)</code>
	 * @param color
	 * @return GOWindow
	 */
	public GOWindow setColor(int color)
	{
		this.color = color;
		return this;
	}
	
	@Override
	public void update()
	{
		super.update();
		// Update rectangle position according to the gameobject transform position
		this.rectangle.lowerLeft.set(this.transform.position);
	}
	
	@Override
	public void present()
	{
		super.present();
		// Draw basic square as main window Frame
		drawSprite(
				Scene.regionSquare, // TextureRegion
				transform.position.x + rectangle.width/2f, // x
				transform.position.y + rectangle.height/2f,  // y
				rectangle.width, // Width
				rectangle.height,  // Height
				color); // Color
		// Draw basic gradient shadow for the main Window Frame
		drawSprite(
				Scene.regionSquare, // TextureRegion
				transform.position.x + rectangle.width/2f, // x
				transform.position.y + rectangle.height/2f,  // y
				rectangle.width, // Width
				rectangle.height, // Height
				Color.BLACK); // Color
	}

}
