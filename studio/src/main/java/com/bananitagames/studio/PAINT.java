package com.bananitagames.studio;

/**
 * Created by luis on 07/02/2018.
 */

public final class PAINT
{

	private static final float _LINE_SIZE_FACTOR = 1.0f / 400.0f;

	private PAINT(){}

	public static void drawCrossLine(final GameObject gameObject, int layer, int color)
	{
		drawCrossLine(gameObject, layer, gameObject.transform.position.x, gameObject.transform.position.y, color);
	}

	public static void drawCrossLine(final GameObject gameObject, int layer, float centerX, float centerY, int color)
	{
		Scene scene = gameObject.scene;
		float lineWidth = getDefaultLineWidth(gameObject);
		drawLineByCenter(gameObject, layer, centerX, scene.cam.position.y, scene.cam.frustumHeight, lineWidth, 90.0f, color);
		drawLineByCenter(gameObject, layer, scene.cam.position.x, centerY, scene.cam.frustumWidth , lineWidth,  0.0f, color);
	}

	public static void drawLineByPoints(final GameObject gameObject, int layer, float startX, float startY, float endX, float endY, float lineWidth, int color)
	{
		float dx,dy,centerX, centerY, length, rotation;
		dx = endX - startX;
		dy = endY - startY;
		centerX = startX + dx / 2.0f;
		centerY = startY + dy / 2.0f;
		length = (float)Math.sqrt(dx*dx + dy*dy);
		rotation = (float)Math.atan2(dy,dx);
		drawLineByCenter(gameObject, layer, centerX, centerY, length, lineWidth, rotation, color);
	}

	public static void drawLineByCenter(final GameObject gameObject, int layer, float centerX, float centerY, float length, float lineWidth, float rotation, int color)
	{
		gameObject.drawSprite(layer, Scene.regionSquare, centerX, centerY, length, lineWidth, rotation, color);
	}

	private static float getDefaultLineWidth(GameObject gameObject)
	{
		Scene scene = gameObject.scene;
		float min = scene.cam.frustumHeight < scene.cam.frustumWidth ? scene.cam.frustumHeight : scene.cam.frustumWidth;
		float lineWidth = min * _LINE_SIZE_FACTOR;
		return lineWidth;
	}

}
