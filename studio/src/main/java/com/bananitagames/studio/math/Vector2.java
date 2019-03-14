package com.bananitagames.studio.math;

public class Vector2 {
    public static float TO_RADIANS = (1 / 180.0f) * (float)Math.PI;
    public static float TO_DEGREES = (1 / (float)Math.PI) * 180;
    public float x, y;
    private static Vector2 tmpVector = new Vector2();
    
    public Vector2() {    
    }       
    
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
    }
    
    public Vector2 cpy() {
        return new Vector2(x, y);
    }       
    
    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    public Vector2 set(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
        return this;
    }
    
    public Vector2 add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }
    
    public Vector2 add(Vector2 other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }
    
    public Vector2 sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }
    
    public Vector2 sub(Vector2 other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }
    
    public Vector2 mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }
    
    public float len() {               
        return (float) Math.sqrt(x*x + y*y);
    }

    public Vector2 nor() {
        float len = len();
        if(len!=0) {
            this.x /= len;
            this.y /= len;
        }
        return this;
    }

	/**
	 * Gives the angle of a vector centered in the zero coordinates
     * @return float angle int degrees
     */
    public float angle() {
        float angle = (float)Math.atan2(y, x) * TO_DEGREES;
        if(angle < 0)
            angle += 360;
        return angle;
    }       
    
    /**
     * 
     * @param angle in degrees
     */
    public Vector2 rotate(float angle) {
        float rad = angle * TO_RADIANS;
        
        float cos = (float) Math.cos(rad);
        float sin = (float) Math.sin(rad);
        
        float newX = this.x * cos - this.y * sin;
        float newY = this.x * sin + this.y * cos;
        
        this.x = newX;
        this.y = newY;
        
        return this;
    }

    /**
     * Rotate anti-clockwise
     * @param angle in degrees
     */
    public Vector2 rotateAroundPoint(float angle, Vector2 rotationCenter)
    {
    	float rad = angle * TO_RADIANS;
        float cos = (float) Math.cos(rad);
        float sin = (float) Math.sin(rad);
        
        // translate point back to origin
        this.x -= rotationCenter.x;
        this.y -= rotationCenter.y;
        
        // rotate and translate point back
        float newX = this.x * cos - this.y * sin + rotationCenter.x;
        float newY = this.x * sin + this.y * cos + rotationCenter.y;
        
        this.x = newX;
        this.y = newY;
    	return this;
    }
    
    public float dist(Vector2 other) {
        float distX = this.x - other.x;
        float distY = this.y - other.y;        
        return (float) Math.sqrt(distX*distX + distY*distY);
    }   
    
    public float dist(float x, float y) {
        float distX = this.x - x;
        float distY = this.y - y;        
        return (float) Math.sqrt(distX*distX + distY*distY);
    }   
    
    public float distSquared(Vector2 other) {
        float distX = this.x - other.x;
        float distY = this.y - other.y;        
        return distX*distX + distY*distY;
    }

    public float distSquared(float x, float y) {
        float distX = this.x - x;
        float distY = this.y - y;        
        return distX*distX + distY*distY;
    }    
    
    public static float dot(Vector2 vector1, Vector2 vector2)
    {
    	return (vector1.x*vector2.x+vector1.y*vector2.y);
    }
    
    public static Vector2 tmp(float x, float y)
    {
    	return tmpVector.set(x, y);
    }

    public static  float len(Vector2 p1, Vector2 p2)
	{
		return (float) Math.sqrt(Math.pow(p2.x-p1.x,2)+Math.pow(p2.y-p1.y,2));
	}
}