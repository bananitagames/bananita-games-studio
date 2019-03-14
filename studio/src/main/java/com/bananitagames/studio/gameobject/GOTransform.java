package com.bananitagames.studio.gameobject;

import com.bananitagames.studio.TIME;
import com.bananitagames.studio.math.Vector2;

public class GOTransform
{
	public Vector2 position;
	public Vector2 velocity;
	public Vector2 acceleration;
	public float rotation;
	public float rotationVelocity;
	public float rotationAcceleration;
	public Vector2 scale;
	
	public GOTransform()
	{
		this.position = new Vector2();
		this.velocity = new Vector2();
		this.acceleration = new Vector2();
		this.rotation = 0;
		this.rotationVelocity = 0;
		this.rotationAcceleration = 0;
		this.scale = new Vector2(1, 1);
	}
	
	public GOTransform reset()
	{
		this.position.set(0, 0);
		this.velocity.set(0, 0);
		this.acceleration.set(0, 0);
		this.rotation = 0;
		this.rotationVelocity = 0;
		this.rotationAcceleration = 0;
		this.scale.set(1, 1);
		return this;
	}
	
	public void update()
	{
		this.velocity.x += acceleration.x * TIME.deltaTime;
		this.velocity.y += acceleration.y * TIME.deltaTime;
		this.position.x += velocity.x * TIME.deltaTime;
		this.position.y += velocity.y * TIME.deltaTime;
		this.rotationVelocity += rotationAcceleration * TIME.deltaTime;
		this.rotation += rotationVelocity * TIME.deltaTime;
	}
	
}
