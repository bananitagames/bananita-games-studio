package com.bananitagames.studio.actions;

import com.bananitagames.studio.TIME;
import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.math.Vector2;

public class ActionMoveToPoint extends Action 
{
	private float x, y, speed, distance, distanceLeft;
	
	public ActionMoveToPoint(float x, float y, float speed)
	{
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	@Override
	public void update(GameObject gameObject)
	{
		super.update(gameObject);
		float deltaTime = TIME.deltaTime;
		distance = speed * deltaTime;
		distanceLeft = Vector2.tmp(x-gameObject.transform.position.x, y-gameObject.transform.position.y).len();
		if(distance > distanceLeft)
		{
			gameObject.transform.position.set(x, y);
			endAction();
		}
		else
		{
			gameObject.transform.position.add(Vector2.tmp(x-gameObject.transform.position.x, y-gameObject.transform.position.y).nor().mul(speed*deltaTime));
		}
	}

}
