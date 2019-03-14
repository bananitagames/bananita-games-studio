package com.bananitagames.studio.actions;

import com.bananitagames.studio.TIME;
import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.math.Vector2;

public class ActionFollowGameObject extends Action 
{
	
	private float speed, distance, distanceLeft, time, storedTime;
	private GameObject gameObjectToFollow;
	
	/**
	 * Constructs an action that makes the behaviored object
	 * to follow another game object
	 * @param gameObject The game object to follow
	 * @param speed The velocity of the gameObject that performs the action
	 * @param time The limited time that this action will take.
	 */
	public ActionFollowGameObject(GameObject gameObject, float speed, float time)
	{
		this.gameObjectToFollow = gameObject;
		this.speed = speed;
		this.time = time;
	}
	
	@Override
	public void update(GameObject gameObject)
	{
		super.update(gameObject);
		float deltaTime = TIME.deltaTime;
		distance = speed * deltaTime;
		distanceLeft = Vector2.tmp(gameObjectToFollow.transform.position.x-gameObject.transform.position.x, gameObjectToFollow.transform.position.y-gameObject.transform.position.y).len();
		if(distance > distanceLeft)
		{
			gameObject.transform.position.set(gameObjectToFollow.transform.position.x, gameObjectToFollow.transform.position.y);
		}
		else
		{
			gameObject.transform.position.add(Vector2.tmp(gameObjectToFollow.transform.position.x-gameObject.transform.position.x, gameObjectToFollow.transform.position.y-gameObject.transform.position.y).nor().mul(speed*deltaTime));
		}
		// As ActionWait. End the action after reaching the time limit
		storedTime += deltaTime;
		if(storedTime >= time) 
		{
			storedTime = 0f;
			endAction();
		}
	}
}
