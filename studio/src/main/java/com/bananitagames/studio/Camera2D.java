package com.bananitagames.studio;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;


import com.bananitagames.studio.math.Vector2;


public final class Camera2D {
	public static float[] _MVP_MATRIX = new float[16];
    public final Vector2 position;
    public float zoom;
    public final float frustumWidth;
    public final float frustumHeight;
    private GLSurfaceView glView;
    
    public Camera2D(float frustumWidth, float frustumHeight) {
        ActivityGame a = ActivityGameManager.getInstance().getActivity();
		this.glView = a.getGlView();
        this.frustumWidth = frustumWidth;
        this.frustumHeight = frustumHeight;
        this.position = new Vector2(frustumWidth / 2, frustumHeight / 2);
        this.zoom = 1.0f;
    }
    
    public void setViewportAndMatrices() {
		Matrix.orthoM(_MVP_MATRIX,0,
				position.x - frustumWidth * zoom / 2.0f,
				position.x + frustumWidth * zoom/ 2.0f,
				position.y - frustumHeight * zoom / 2.0f,
				position.y + frustumHeight * zoom/ 2.0f,
				-10.0f, 10.0f);
    }
    
    public void touchToWorld(Vector2 touch) {
        touch.x = (touch.x / (float) glView.getWidth()) * frustumWidth * zoom;
        touch.y = (1.0f - touch.y / (float) glView.getHeight()) * frustumHeight * zoom;
        touch.add(position).sub(frustumWidth * zoom / 2.0f, frustumHeight * zoom / 2.0f);
    }
}
