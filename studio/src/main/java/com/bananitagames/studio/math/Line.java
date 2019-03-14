package com.bananitagames.studio.math;


public class Line 
{
	
	public float x1,y1,x2,y2;
	public Vector2 normal;

	public Line() 
	{
		init();
	}
	
	public Line(Vector2 v1, Vector2 v2)
	{
		init(v1, v2);
	}
	
	public Line(float x1, float y1, float x2, float y2)
	{
		init(x1, y1, x2, y2);
	}
	
	public Line init()
	{
		return init(0, 0, 0, 0);
	}
	
	public Line init(Vector2 v1, Vector2 v2)
	{
		return init(v1.x, v1.y, v2.x, v2.y);
	}
	
	public Line init(float x1, float y1, float x2, float y2)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		normal = new Vector2(this.y2-this.y1, this.x1-this.x2).nor();
		return this;
	}
	
	public Line moveRelative(float x, float y)
	{
		init(x1+x, y1+y, x2+x, y2+y);
		return this;
	}
	
}
