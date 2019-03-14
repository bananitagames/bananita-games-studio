package com.bananitagames.studio;


import android.content.Context;
import android.view.View;

import java.util.List;


final class AndroidInput{
    AndroidInputAccelerometerHandler accelHandler;
    AndroidInputRotationHandler rotHandler;
    AndroidInputKeyboardHandler keyHandler;
	AndroidInputMultiTouchHandler touchHandler;

    public AndroidInput(Context context, View view, float scaleX, float scaleY) {
        accelHandler = new AndroidInputAccelerometerHandler(context);
        rotHandler = new AndroidInputRotationHandler(context);
        keyHandler = new AndroidInputKeyboardHandler(view);
        touchHandler = new AndroidInputMultiTouchHandler(view, scaleX, scaleY);
    }

    public boolean isKeyPressed(int keyCode) {
        return keyHandler.isKeyPressed(keyCode);
    }

	public List<KeyEvent> getKeyEvents() {
		return keyHandler.getKeyEvents();
	}

    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }

    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }

    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }

	public List<TouchEvent> getTouchEvents() {
		return touchEvents;
	}

    public float getAccelX() {
        return accelHandler.getAccelX();
    }

    public float getAccelY() {
        return accelHandler.getAccelY();
    }

    public float getAccelZ() {
        return accelHandler.getAccelZ();
    }

	public float[] getAcceleration()
	{
		return accelHandler.getAcceleration();
	}

	public float getAzimut() 
	{
		return rotHandler.getAzimut();
	}

	public float getPitch() 
	{
		return rotHandler.getPitch();
	}

	public float getRoll() 
	{
		return rotHandler.getRoll();
	}

	public float[] getRotation()
	{
		return rotHandler.getRotation();
	}

	public void loadGetters()
	{
		touchEvents = touchHandler.getTouchEvents();
	}

	List<TouchEvent> touchEvents;
}
