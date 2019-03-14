package com.bananitagames.studio.math;


public class Circle 
{
    public final Vector2 center = new Vector2();
    public float radius;

    public Circle()
    {
        this.center.set(0,0);
        this.radius = 0;
    }
    
    public Circle(float x, float y, float radius) 
    {
        this.center.set(x,y);
        this.radius = radius;
    }
    
}
