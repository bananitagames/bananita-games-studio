package com.bananitagames.studio;

import android.graphics.Color;
import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Created by luis on 04/11/2017.
 */

final class SceneLayerBucket
{

	public  static final int	_SHIFT_BITS				= 8;
	public  static final int 	_SPRITES_MAX 			= 1 << _SHIFT_BITS;
	private static final int 	_SPRITE_VERTEX 			= 4;
	private static final int 	_SPRITE_INDICES 		= 6;
	private static final int 	_VERTEX_POSITIONS 		= 2;
	private static final int 	_VERTEX_TEX_COORS 		= 2;
	private static final int 	_VERTEX_SIZE 			= 2;
	private static final int 	_VERTEX_ROTATION 		= 1;
	private static final int 	_VERTEX_COLORS 			= 4;
	private static final int	_BYTES_PER_FLOAT		= 4;
	private static final int	_BYTES_PER_INT			= 4;
	private static final int	_BYTES_PER_SHORT		= 2;

	private static final FloatBuffer _VERTEX_BUFFER  = getInitialVertexBuffer();
	private static final ShortBuffer   _INDICES_BUFFER = getInitialIndicesBuffer();

	private float[]      posArray = new float[_SPRITES_MAX * _SPRITE_VERTEX * _VERTEX_POSITIONS];
	private float[]      texArray = new float[_SPRITES_MAX * _SPRITE_VERTEX * _VERTEX_TEX_COORS];
	private float[]     sizeArray = new float[_SPRITES_MAX * _SPRITE_VERTEX * _VERTEX_SIZE];
	private float[] rotationArray = new float[_SPRITES_MAX * _SPRITE_VERTEX * _VERTEX_ROTATION];
	private float[]    colorArray = new float[_SPRITES_MAX * _SPRITE_VERTEX * _VERTEX_COLORS];

	private int      posIndex = 0;
	private int      texIndex = 0;
	private int     sizeIndex = 0;
	private int rotationIndex = 0;
	private int    colorIndex = 0;
	private FloatBuffer      posBuffer = ByteBuffer.allocateDirect(_SPRITES_MAX * _SPRITE_VERTEX * _VERTEX_POSITIONS * _BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
	private FloatBuffer      texBuffer = ByteBuffer.allocateDirect(_SPRITES_MAX * _SPRITE_VERTEX * _VERTEX_TEX_COORS * _BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
	private FloatBuffer     sizeBuffer = ByteBuffer.allocateDirect(_SPRITES_MAX * _SPRITE_VERTEX * _VERTEX_SIZE      * _BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
	private FloatBuffer rotationBuffer = ByteBuffer.allocateDirect(_SPRITES_MAX * _SPRITE_VERTEX * _VERTEX_ROTATION  * _BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
	private FloatBuffer    colorBuffer = ByteBuffer.allocateDirect(_SPRITES_MAX * _SPRITE_VERTEX * _VERTEX_COLORS    * _BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();

	private final SceneLayer sceneLayer;
	private final int textureIdentifier;
	private int numSprites = 0;

	public SceneLayerBucket(SceneLayer sceneLayer, int textureIdentifier)
	{
		this.sceneLayer = sceneLayer;
		this.textureIdentifier = textureIdentifier;
	}

	public void drawTextureRegion(float u1, float u2, float v1, float v2, float x, float y, float width, float height, float angle, int colorTopLeft, int colorTopRight, int colorBottonLeft, int colorBottomRight)
	{
		//POSTIION
		posArray[posIndex++]=x;
		posArray[posIndex++]=y;
		posArray[posIndex++]=x;
		posArray[posIndex++]=y;
		posArray[posIndex++]=x;
		posArray[posIndex++]=y;
		posArray[posIndex++]=x;
		posArray[posIndex++]=y;
		//TEX COORS
		texArray[texIndex++]=u1;
		texArray[texIndex++]=v1;
		texArray[texIndex++]=u1;
		texArray[texIndex++]=v2;
		texArray[texIndex++]=u2;
		texArray[texIndex++]=v2;
		texArray[texIndex++]=u2;
		texArray[texIndex++]=v1;
		//SIZE
		sizeArray[sizeIndex++]=width;
		sizeArray[sizeIndex++]=height;
		sizeArray[sizeIndex++]=width;
		sizeArray[sizeIndex++]=height;
		sizeArray[sizeIndex++]=width;
		sizeArray[sizeIndex++]=height;
		sizeArray[sizeIndex++]=width;
		sizeArray[sizeIndex++]=height;
		//ROTATION
		rotationArray[rotationIndex++]=angle;
		rotationArray[rotationIndex++]=angle;
		rotationArray[rotationIndex++]=angle;
		rotationArray[rotationIndex++]=angle;
		//COLOR
		colorArray[colorIndex++]= Color.red(colorTopLeft);//TOP LEFT
		colorArray[colorIndex++]= Color.green(colorTopLeft);
		colorArray[colorIndex++]= Color.blue(colorTopLeft);
		colorArray[colorIndex++]= Color.alpha(colorTopLeft);
		colorArray[colorIndex++]= Color.red(colorBottonLeft);//BOTTOM LEFT
		colorArray[colorIndex++]= Color.green(colorBottonLeft);
		colorArray[colorIndex++]= Color.blue(colorBottonLeft);
		colorArray[colorIndex++]= Color.alpha(colorBottonLeft);
		colorArray[colorIndex++]= Color.red(colorBottomRight);//BOTTOM RIGHT
		colorArray[colorIndex++]= Color.green(colorBottomRight);
		colorArray[colorIndex++]= Color.blue(colorBottomRight);
		colorArray[colorIndex++]= Color.alpha(colorBottomRight);
		colorArray[colorIndex++]= Color.red(colorTopRight);//TOP RIGHT
		colorArray[colorIndex++]= Color.green(colorTopRight);
		colorArray[colorIndex++]= Color.blue(colorTopRight);
		colorArray[colorIndex++]= Color.alpha(colorTopRight);

		numSprites++;
	}

	public void draw()
	{
		if(numSprites == 0)
			return;
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIdentifier);
		//Min & Mag filtering
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, sceneLayer.getMinFilter());
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, sceneLayer.getMagFilter());
		GLES20.glUniform1i(sceneLayer.loc_uniform_sampler_2d,0);
		prepareBuffers();
		drawBuffers();
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
		//specialDraw();
		//Reset the used sprites in this bucket
		resetArrayIndexes();
	}

	private void prepareBuffers()
	{
		//CLEAR BUFFERS
		posBuffer.clear();
		texBuffer.clear();
		sizeBuffer.clear();
		rotationBuffer.clear();
		colorBuffer.clear();
		//FILL BUFFERS
		posBuffer.put(posArray);
		texBuffer.put(texArray);
		sizeBuffer.put(sizeArray);
		rotationBuffer.put(rotationArray);
		colorBuffer.put(colorArray);
		//POSITION BUFFERS
		_VERTEX_BUFFER.position(0);
		posBuffer.position(0);
		texBuffer.position(0);
		sizeBuffer.position(0);
		rotationBuffer.position(0);
		colorBuffer.position(0);
	}

	private void drawBuffers()
	{
		int numVertex = numSprites *_SPRITE_VERTEX;
		GLES20.glVertexAttribPointer(sceneLayer.loc_attr_vertex_pos,2, 	GLES20.GL_FLOAT, false, 2*_BYTES_PER_FLOAT, _VERTEX_BUFFER);
		GLES20.glEnableVertexAttribArray(sceneLayer.loc_attr_vertex_pos);
		GLES20.glVertexAttribPointer(sceneLayer.loc_attr_pos, 		2, 	GLES20.GL_FLOAT, false, 2*_BYTES_PER_FLOAT, posBuffer);
		GLES20.glEnableVertexAttribArray(sceneLayer.loc_attr_pos);
		GLES20.glVertexAttribPointer(sceneLayer.loc_attr_tex_coor, 	2, 	GLES20.GL_FLOAT, false, 2*_BYTES_PER_FLOAT, texBuffer);
		GLES20.glEnableVertexAttribArray(sceneLayer.loc_attr_tex_coor);
		GLES20.glVertexAttribPointer(sceneLayer.loc_attr_size, 		2, 	GLES20.GL_FLOAT, false, 2*_BYTES_PER_FLOAT, sizeBuffer);
		GLES20.glEnableVertexAttribArray(sceneLayer.loc_attr_size);
		GLES20.glVertexAttribPointer(sceneLayer.loc_attr_rotation, 	1, 	GLES20.GL_FLOAT, false, 1*_BYTES_PER_FLOAT, rotationBuffer);
		GLES20.glEnableVertexAttribArray(sceneLayer.loc_attr_rotation);
		GLES20.glVertexAttribPointer(sceneLayer.loc_attr_color, 	4, 	GLES20.GL_FLOAT, false, 4*_BYTES_PER_FLOAT, colorBuffer);
		GLES20.glEnableVertexAttribArray(sceneLayer.loc_attr_color);
		/*GLES20.glVertexAttribPointer(sceneLayer.loc_attr_vertex_pos,2, 	GLES20.GL_FLOAT, false, 0, _VERTEX_BUFFER);
		GLES20.glEnableVertexAttribArray(sceneLayer.loc_attr_vertex_pos);
		GLES20.glVertexAttribPointer(sceneLayer.loc_attr_pos, 		2, 	GLES20.GL_FLOAT, false, 0, posBuffer);
		GLES20.glEnableVertexAttribArray(sceneLayer.loc_attr_pos);
		GLES20.glVertexAttribPointer(sceneLayer.loc_attr_tex_coor, 	2, 	GLES20.GL_FLOAT, false, 0, texBuffer);
		GLES20.glEnableVertexAttribArray(sceneLayer.loc_attr_tex_coor);
		GLES20.glVertexAttribPointer(sceneLayer.loc_attr_size, 		2, 	GLES20.GL_FLOAT, false, 0, sizeBuffer);
		GLES20.glEnableVertexAttribArray(sceneLayer.loc_attr_size);
		GLES20.glVertexAttribPointer(sceneLayer.loc_attr_rotation, 	1, 	GLES20.GL_FLOAT, false, 0, rotationBuffer);
		GLES20.glEnableVertexAttribArray(sceneLayer.loc_attr_rotation);
		GLES20.glVertexAttribPointer(sceneLayer.loc_attr_color, 	1, 	GLES20.GL_FLOAT, false, 0, colorBuffer);
		GLES20.glEnableVertexAttribArray(sceneLayer.loc_attr_color);*/
		_INDICES_BUFFER.position(0);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, numSprites * 6, GLES20.GL_UNSIGNED_SHORT, _INDICES_BUFFER);
	}

	private void resetArrayIndexes()
	{
		posIndex = 0;
		texIndex = 0;
		sizeIndex = 0;
		rotationIndex = 0;
		colorIndex = 0;
		numSprites = 0;
	}

	private void specialDraw()
	{
		float[] triangle1VerticesData = {
			// X, Y, Z,
			// R, G, B, A
			-0.5f, -0.25f, 0.0f,
			1.0f, 0.0f, 0.0f, 1.0f,

			0.5f, -0.25f, 0.0f,
			0.0f, 0.0f, 1.0f, 1.0f,

			0.0f, 0.559016994f, 0.0f,
			0.0f, 1.0f, 0.0f, 1.0f};
		int mBytesPerFloat = 4;
		FloatBuffer aTriangleBuffer = ByteBuffer.allocateDirect(triangle1VerticesData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
		aTriangleBuffer.put(triangle1VerticesData).position(0);
/** How many elements per vertex. */
		int mStrideBytes = 7 * mBytesPerFloat;

/** Offset of the position data. */
		 int mPositionOffset = 0;

/** Size of the position data in elements. */
		 int mPositionDataSize = 3;

/** Offset of the color data. */
		 int mColorOffset = 3;

/** Size of the color data in elements. */
		int mColorDataSize = 4;
		// Pass in the position information
		aTriangleBuffer.position(mPositionOffset);
		GLES20.glVertexAttribPointer(sceneLayer.loc_attr_pos, mPositionDataSize, GLES20.GL_FLOAT, false,
				mStrideBytes, aTriangleBuffer);

		GLES20.glEnableVertexAttribArray(sceneLayer.loc_attr_pos);

		// Pass in the color information
		aTriangleBuffer.position(mColorOffset);
		GLES20.glVertexAttribPointer(sceneLayer.loc_attr_color, mColorDataSize, GLES20.GL_FLOAT, false,
				mStrideBytes, aTriangleBuffer);

		GLES20.glEnableVertexAttribArray(sceneLayer.loc_attr_color);


		GLES20.glUniformMatrix4fv(sceneLayer.loc_uniform_mvp, 1, false, Camera2D._MVP_MATRIX, 0);
		//GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
		_INDICES_BUFFER.position(0);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, 3, GLES20.GL_UNSIGNED_SHORT, _INDICES_BUFFER);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// STATIC
	//
	////////////////////////////////////////////////////////////////////////////////////////////////

	private static FloatBuffer getInitialVertexBuffer()
	{
		float d = 0.5f;
		int stride = _SPRITE_VERTEX * _VERTEX_POSITIONS;
		float[] vertexArray = new float[_SPRITES_MAX * stride];
		for(int i = 0; i < _SPRITES_MAX; i ++)
		{
			vertexArray[i*stride+0] = -d;
			vertexArray[i*stride+1] =  d;
			vertexArray[i*stride+2] = -d;
			vertexArray[i*stride+3] = -d;
			vertexArray[i*stride+4] =  d;
			vertexArray[i*stride+5] = -d;
			vertexArray[i*stride+6] =  d;
			vertexArray[i*stride+7] =  d;
		};
		FloatBuffer buffer = ByteBuffer.allocateDirect(vertexArray.length * _BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
		buffer.put(vertexArray).position(0);
		return buffer;
	}

	private static ShortBuffer getInitialIndicesBuffer()
	{
		short[] indicesArray = new short[_SPRITES_MAX * _SPRITE_INDICES];
		for(int i = 0; i < _SPRITES_MAX; i ++)
		{
			indicesArray[i*_SPRITE_INDICES+0] = (short) (i*_SPRITE_VERTEX+0);
			indicesArray[i*_SPRITE_INDICES+1] = (short) (i*_SPRITE_VERTEX+1);
			indicesArray[i*_SPRITE_INDICES+2] = (short) (i*_SPRITE_VERTEX+2);
			indicesArray[i*_SPRITE_INDICES+3] = (short) (i*_SPRITE_VERTEX+0);
			indicesArray[i*_SPRITE_INDICES+4] = (short) (i*_SPRITE_VERTEX+2);
			indicesArray[i*_SPRITE_INDICES+5] = (short) (i*_SPRITE_VERTEX+3);
		}
		String mString = "[";
		for(int i = 0; i < indicesArray.length; i++)
			mString += " " + indicesArray[i] + " ";
		mString += "]";
		if(CONSTANTS._DEBUG_SHADER)Log.d(CONSTANTS._TAG, mString);

		ShortBuffer buffer = ByteBuffer.allocateDirect(indicesArray.length * _BYTES_PER_SHORT).order(ByteOrder.nativeOrder()).asShortBuffer();
		buffer.put(indicesArray).position(0);
		return buffer;
	}

}
