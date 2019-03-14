package com.bananitagames.studio;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

final class AndroidInputAccelerometerHandler implements SensorEventListener {
    float[] acceleration = new float[3];

    public AndroidInputAccelerometerHandler(Context context) {
        SensorManager manager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor accelerometer = manager.getSensorList(
                    Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing to do here
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        for(int i = 0; i < 3; i++)
            acceleration[i] = event.values[i];
    }

    public float getAccelX() {
        return acceleration[0];
    }

    public float getAccelY() {
        return acceleration[1];
    }

    public float getAccelZ() {
        return acceleration[2];
    }

	public float[] getAcceleration()
	{
		return acceleration;
	}
}
