package com.bananitagames.studio;

import com.bananitagames.studio.interfaces.Texture;

public class TextureRegion
{

    public final float u1, v1;
    public final float u2, v2;
    public final Texture texture;
    public final float width, height;
	/** Values of width and height normalized */
	public final float wNormal, hNormal;
	/** Values between 0 and 1 */
	public final float xCenter, yCenter;

	public TextureRegion(Texture texture, float x, float y, float width, float height) {
		this.u1 = x / texture.getWidth();
		this.v1 = y / texture.getHeight();
		this.u2 = this.u1 + width / texture.getWidth();
		this.v2 = this.v1 + height / texture.getHeight();
		this.texture = texture;
		this.width = width;
		this.height = height;
		this.wNormal = u2 - u1;
		this.hNormal = v2 - v1;
		this.xCenter = 0.5f;
		this.yCenter = 0.5f;
	}

	public TextureRegion(Texture texture, float x, float y, float width, float height, float xCenter, float yCenter) {
		this.u1 = x / texture.getWidth();
		this.v1 = y / texture.getHeight();
		this.u2 = this.u1 + width / texture.getWidth();
		this.v2 = this.v1 + height / texture.getHeight();
		this.texture = texture;
		this.width = width;
		this.height = height;
		this.wNormal = u2 - u1;
		this.hNormal = v2 - v1;
		this.xCenter = (xCenter-x)/width;
		this.yCenter = (yCenter-y)/height;
	}

}
