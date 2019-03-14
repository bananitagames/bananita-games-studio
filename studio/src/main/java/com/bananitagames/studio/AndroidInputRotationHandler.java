package com.bananitagames.studio;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

final class AndroidInputRotationHandler implements SensorEventListener
{
	private float[] rotation = new float[3];
	private float azimut, pitch, roll;
	private float[] mGravity = new float[10];
	private float[] mGeomagnetic = new float[10];

    public AndroidInputRotationHandler(Context context)
    {
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) 
        {
            Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_GAME);
        }
        if (manager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).size() != 0)
        {
        	Sensor magnetometer = manager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).get(0);
        	manager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing to do here
    }

    @Override
    public void onSensorChanged(SensorEvent event) 
    {
    	if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
	        mGravity = event.values;
	    if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
	        mGeomagnetic = event.values;
	    if (mGravity != null && mGeomagnetic != null) {
	       float R[] = new float[9];
	       float I[] = new float[9];
	       boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
	       if (success) {
	         SensorManager.getOrientation(R, rotation);
	         azimut = rotation[0]; // orientation contains: azimut, pitch and roll
	         pitch = rotation[1];
	         roll = rotation[2];
	       }
	    }
    }

    public float getAzimut() {
    	return azimut;
    }

    public float getPitch() {
        return pitch;
    }

    public float getRoll() {
        return roll;
    }

	public float[] getRotation()
	{
		return rotation;
	}
}

