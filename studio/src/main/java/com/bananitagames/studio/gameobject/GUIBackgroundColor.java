package com.bananitagames.studio.gameobject;

import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.Scene;

/**
 * Created by luis on 16/08/2018.
 */

public final class GUIBackgroundColor extends GameObject
{

	public GUIBackgroundColor(int color)
	{
		super();
		this.filterColor = color;
	}

	@Override
	public void present()
	{
		super.present();
		drawSprite(Scene.regionSquare, scene.cam.position.x, scene.cam.position.y, scene.cam.frustumWidth, scene.cam.frustumHeight);
	}
}
