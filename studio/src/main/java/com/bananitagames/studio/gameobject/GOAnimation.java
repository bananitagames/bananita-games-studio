package com.bananitagames.studio.gameobject;

import java.util.ArrayList;

import com.bananitagames.studio.GameObject;

public class GOAnimation 
{
	
	private ArrayList<Animation> animations = new ArrayList<Animation>();

	public GOAnimation()
	{
		
	}
	
	public GOAnimation(Animation animation)
	{
		this();
		animations.add(animation);
	}
	
	public GOAnimation addAnimation(Animation animation)
	{
		animations.add(animation);
		return this;
	}
	
	public GOAnimation addAnimation(Animation[] animations)
	{
		for(Animation animation : animations)
			this.animations.add(animation);
		return this;
	}
	
	public boolean removeAnimation(Animation animation)
	{
		return animations.remove(animation);
	}
	
	public void update()
	{
		for(Animation animation : animations)
			animation.update();
	}
	
	public void present(GameObject g)
	{
		for(Animation animation : animations)
			animation.present(g);
	}
	
}
