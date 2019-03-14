package com.bananitagames.studio.backgrounds;

import com.bananitagames.studio.Scene;
import com.bananitagames.studio.TextureRegion;
import com.bananitagames.studio.GameObject;

public class GOScrollableBackground extends GameObject 
{
	private static final float _EXTRA_SIZE_FACTOR = 1.001f;

	// BASIC PARAMETERS
	private TextureRegion region;
	private float regionWidth, regionHeight;
	private int color = 0xFFFFFFFF;
	
	// INTERMEDIATE PARAMETERS
	private int lengthX, lengthY;
	private float offsetX, offsetY;
	private float startX, startY;
	
	public GOScrollableBackground setRegion(TextureRegion region)
	{
		this.region = region;
		return this;
	}
	
	public GOScrollableBackground setRegionSize(float width, float height)
	{
		this.regionWidth = width;
		this.regionHeight = height;
		resetIntermediateParameters();
		return this;
	}
	
	public GOScrollableBackground setColor(int color)
	{
		this.color = color;
		return this;
	}
	
	private void resetIntermediateParameters()
	{
		lengthX = (int) (scene.cam.frustumWidth/regionWidth) + 2;
		lengthY = (int) (scene.cam.frustumHeight/regionHeight) + 2;
	}
	
	private void updateOffset()
	{
		offsetX = transform.position.x;
		offsetY = transform.position.y;
		if(offsetX < 0) offsetX = regionWidth + offsetX%regionWidth;
		else offsetX = offsetX%regionWidth;
		if(offsetY < 0) offsetY = regionHeight + offsetY%regionHeight;
		else offsetY = offsetY%regionHeight;
	}
	
	@Override
	public void update()
	{
		super.update();
	}
	
	@Override
	public void present()
	{
		super.present();
		updateOffset();
		startX = scene.cam.position.x-scene.cam.frustumWidth/2f-regionWidth/2f;
		startY = scene.cam.position.y-scene.cam.frustumHeight/2f-regionHeight/2f;
		for(int i = 0; i < lengthX; i++)
			for(int  j = 0; j < lengthY; j++)
				drawSprite(
						region,
						startX + offsetX + i*regionWidth,
						startY + offsetY + j*regionHeight,
						regionWidth*_EXTRA_SIZE_FACTOR,
						regionHeight*_EXTRA_SIZE_FACTOR,
						color);
				
	}
	
}
