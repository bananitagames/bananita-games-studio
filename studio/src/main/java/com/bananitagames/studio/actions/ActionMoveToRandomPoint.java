package com.bananitagames.studio.actions;

import com.bananitagames.studio.TIME;
import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.math.Rectangle;
import com.bananitagames.studio.math.Vector2;

import java.util.Random;

public class ActionMoveToRandomPoint extends Action 
{

	private float x, y, speed, distance, distanceLeft;
	private Rectangle bound;
	
	public ActionMoveToRandomPoint(Rectangle bound, float speed)
	{
		this.speed = speed;
		this.bound = bound;
		resetRandomPoint();
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
			resetRandomPoint();
			endAction();
		}
		else
		{
			gameObject.transform.position.add(Vector2.tmp(x-gameObject.transform.position.x, y-gameObject.transform.position.y).nor().mul(speed*deltaTime));
		}
	}
	
	public void resetRandomPoint()
	{
		Random random = new Random();
		x = random.nextFloat()*bound.width + bound.lowerLeft.x;
		y = random.nextFloat()*bound.height + bound.lowerLeft.y;
	}
}
