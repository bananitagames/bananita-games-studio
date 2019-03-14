package com.bananitagames.studio.math;


public class Rectangle {
    public final Vector2 lowerLeft;
    public float width, height;
    
    /**
     * This constructor instantiates a Rectangle to be used
     * in some math operations
     * @param x lowerleft-x position
     * @param y lowerlet-y position
     * @param width
     * @param height
     */
    public Rectangle(float x, float y, float width, float height) {
        this.lowerLeft = new Vector2(x,y);
        this.width = width;
        this.height = height;
    }
}
