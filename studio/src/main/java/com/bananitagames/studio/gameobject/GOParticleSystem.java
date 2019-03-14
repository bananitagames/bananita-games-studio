package com.bananitagames.studio.gameobject;

import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.Scene;
import com.bananitagames.studio.TIME;
import com.bananitagames.studio.TextureRegion;
import com.bananitagames.studio.math.ColorRandomBetweenTwoConstants;
import com.bananitagames.studio.math.FloatConstant;
import com.bananitagames.studio.math.FloatParameter;
import com.bananitagames.studio.math.FloatRandomBetweenTwoConstants;
import com.bananitagames.studio.math.IntParameter;
import com.bananitagames.studio.math.Interpolator;

import java.util.Random;

public class GOParticleSystem extends GameObject
{

	private Random random = new Random();
	private float storedEmission = 0.0f;

	// GLOBAL PARTICLE SYSTEM VARIABLES
	private final int size;
	private TextureRegion region;
	private int index;
	private FloatParameter _x;
	private FloatParameter _y;
	private FloatParameter _vx;
	private FloatParameter _vy;
	private FloatParameter _ax;
	private FloatParameter _ay;
	private FloatParameter _wStart;
	private FloatParameter _wEnd;
	private FloatParameter _hStart;
	private FloatParameter _hEnd;
	private FloatParameter _lifeTime;
	private FloatParameter _rot;
	private FloatParameter _vRot;

	private FloatParameter _aRot;
	private IntParameter _colorStart, _colorMiddle, _colorEnd;

	public float particlesPerSecond;
	public boolean inheritParticleSystemTransform;
	public boolean squareParticles;


	// PARTICLES VARIABLES
	private float[] x,y,vx,vy,ax,ay,wStart,wEnd,hStart,hEnd,lifeTime,storedTime,rot,vRot,aRot;
	private int[] colorStart, colorMiddle, colorEnd;
	private boolean[] enabled;

	/**
	 * Builds a particle system object with a specified size. This size will determine the max amount
	 * of particles presented in realTime. High values will consume more memory
	 * @param size
	 */
	public GOParticleSystem(int size)
	{
		super();
		this.size = size;
		init();
	}

	private void init()
	{
		//Global variables
		initGlobalVariables();
		//Particle variables
		x 			= new float[size];
		y 			= new float[size];
		vx 			= new float[size];
		vy 			= new float[size];
		ax 			= new float[size];
		ay 			= new float[size];
		wStart 		= new float[size];
		wEnd		= new float[size];
		hStart		= new float[size];
		hEnd 		= new float[size];
		lifeTime 	= new float[size];
		storedTime 	= new float[size];
		rot 		= new float[size];
		vRot 		= new float[size];
		aRot 		= new float[size];

		colorStart 	= new int[size];
		colorMiddle	= new int[size];
		colorEnd 	= new int[size];

		enabled		= new boolean[size];
	}

	private void initGlobalVariables()
	{
		region = Scene.regionCircleGradient;
		index = 0;
		particlesPerSecond = 80.0f;
		inheritParticleSystemTransform = true;
		squareParticles = true;
		_x 			= new FloatConstant(0.0f);
		_y 			= new FloatConstant(0.0f);
		_vx 		= new FloatRandomBetweenTwoConstants(-scene.cam.frustumWidth/4.0f, scene.cam.frustumWidth/4.0f);
		_vy 		= new FloatRandomBetweenTwoConstants(-scene.cam.frustumWidth/4.0f, scene.cam.frustumWidth/4.0f);
		_ax 		= new FloatConstant(0.0f);
		_ay 		= new FloatConstant(0.0f);
		_wStart 	= new FloatRandomBetweenTwoConstants(scene.cam.frustumWidth/10.0f,scene.cam.frustumWidth/20.0f);
		_wEnd		= new FloatRandomBetweenTwoConstants(scene.cam.frustumWidth/50.0f,scene.cam.frustumWidth/100.0f);
		_hStart		= new FloatRandomBetweenTwoConstants(scene.cam.frustumWidth/10.0f,scene.cam.frustumWidth/20.0f);
		_hEnd 		= new FloatRandomBetweenTwoConstants(scene.cam.frustumWidth/50.0f,scene.cam.frustumWidth/100.0f);
		_lifeTime 	= new FloatRandomBetweenTwoConstants(1.0f,5.0f);
		_rot 		= new FloatConstant(0.0f);
		_vRot 		= new FloatConstant(0.0f);
		_aRot 		= new FloatConstant(0.0f);
		_colorStart	= new ColorRandomBetweenTwoConstants(0x00ff0000, 0x004444ff);
		_colorMiddle= new ColorRandomBetweenTwoConstants(0xffff0000, 0xff4444ff);
		_colorEnd	= new ColorRandomBetweenTwoConstants(0x00ff0000, 0x004444ff);
	}

	public void reset()
	{
		//Global
		initGlobalVariables();
		//Particles
		for(int i = 0; i < size; i++)
		{
			enabled[i] = false;
			storedTime[i] = 0.0f;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//	UPDATING
	//
	////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void update()
	{
		super.update();
		float deltaTime = TIME.deltaTime;
		updateEmission(deltaTime);
		for(int i = 0; i < size; i++)
			if(enabled[i])
			{
				//TIME
				storedTime[i] += deltaTime;
				if(storedTime[i] > lifeTime[i])
					enabled[i] = false;
				//VELOCITY
				vx[i] += ax[i] * deltaTime;
				vy[i] += ay[i] * deltaTime;
				vRot[i] += aRot[i] * deltaTime;
				//POSITION
				x[i] += vx[i] * deltaTime;
				y[i] += vy[i] * deltaTime;
				rot[i] += vRot[i] * deltaTime;
			}
	}

	private void updateEmission(float deltaTime)
	{
		float frameEmission = deltaTime * particlesPerSecond;
		storedEmission += frameEmission;
		int amount = 0;
		while(storedEmission >= 1.0f)
		{
			amount++;
			storedEmission -= 1.0f;
		}
		emit(amount);
	}

	@Override
	public void present()
	{
		super.present();
		float w, h, livedPercent;
		int color;
		for(int i = 0; i < size; i++)
			if(enabled[i])
			{
				livedPercent = Interpolator.progress(storedTime[i], 0.0f, lifeTime[i]);
				w = Interpolator.easyInOut(livedPercent, wStart[i], wEnd[i]);
				h = Interpolator.easyInOut(livedPercent, hStart[i], hEnd[i]);
				color = livedPercent < 0.5f ?
						Interpolator.color(livedPercent * 2.0f, colorStart[i], colorMiddle[i]) :
						Interpolator.color((livedPercent - 0.5f) * 2.0f, colorMiddle[i], colorEnd[i]) ;
				drawSprite(region,x[i],y[i],w,h,rot[i],color);
			}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//	SET DEFAULTS
	//
	////////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//	NEW PARTICLE
	//
	////////////////////////////////////////////////////////////////////////////////////////////////

	public final void emit(int amount)
	{
		int emitted = 0;
		int searched = 0;
		while(emitted < amount && searched < size)
		{
			if(!enabled[index])
			{
				instantiateParticle(index);
				emitted++;
			}
			searched++;
			index++;
			if(index >= size)
				index = 0;
		}
	}

	private final void instantiateParticle(int index)
	{
		enabled[index] 		= true;
		storedTime[index] 	= 0.0f;

		x[index] 			= this.transform.position.x + _x.getValue();
		y[index] 			= this.transform.position.y + _y.getValue();
		vx[index] 			= _vx.getValue();
		vy[index] 			= _vy.getValue();
		ax[index] 			= _ax.getValue();
		ay[index] 			= _ay.getValue();
		wStart[index] 		= _wStart.getValue();
		wEnd[index]			= _wEnd.getValue();
		hStart[index]		= squareParticles ? wStart[index] : _hStart.getValue();
		hEnd[index] 		= squareParticles ? wEnd[index]	  : _hEnd.getValue();
		lifeTime[index] 	= _lifeTime.getValue();
		rot[index] 			= _rot.getValue();
		vRot[index] 		= _vRot.getValue();
		aRot[index] 		= _aRot.getValue();

		colorStart[index] 	= _colorStart.getValue();
		colorMiddle[index] 	= _colorMiddle.getValue();
		colorEnd[index] 	= _colorEnd.getValue();
	}

	//region setters

	public GOParticleSystem set_x(FloatParameter _x)
	{
		this._x = _x;
		return this;
	}

	public GOParticleSystem set_y(FloatParameter _y)
	{
		this._y = _y;
		return this;
	}

	public GOParticleSystem set_vx(FloatParameter _vx)
	{
		this._vx = _vx;
		return this;
	}

	public GOParticleSystem set_vy(FloatParameter _vy)
	{
		this._vy = _vy;
		return this;
	}

	public GOParticleSystem set_ax(FloatParameter _ax)
	{
		this._ax = _ax;
		return this;
	}

	public GOParticleSystem set_ay(FloatParameter _ay)
	{
		this._ay = _ay;
		return this;
	}

	public GOParticleSystem set_wStart(FloatParameter _wStart)
	{
		this._wStart = _wStart;
		return this;
	}

	public GOParticleSystem set_wEnd(FloatParameter _wEnd)
	{
		this._wEnd = _wEnd;
		return this;
	}

	public GOParticleSystem set_hStart(FloatParameter _hStart)
	{
		this._hStart = _hStart;
		return this;
	}

	public GOParticleSystem set_hEnd(FloatParameter _hEnd)
	{
		this._hEnd = _hEnd;
		return this;
	}

	public GOParticleSystem set_lifeTime(FloatParameter _lifeTime)
	{
		this._lifeTime = _lifeTime;
		return this;
	}

	public GOParticleSystem set_rot(FloatParameter _rot)
	{
		this._rot = _rot;
		return this;
	}

	public GOParticleSystem set_vRot(FloatParameter _vRot)
	{
		this._vRot = _vRot;
		return this;
	}

	public GOParticleSystem set_aRot(FloatParameter _aRot)
	{
		this._aRot = _aRot;
		return this;
	}

	public GOParticleSystem set_colorStart(IntParameter _colorStart)
	{
		this._colorStart = _colorStart;
		return this;
	}

	public GOParticleSystem set_colorMiddle(IntParameter _colorMiddle)
	{
		this._colorMiddle = _colorMiddle;
		return this;
	}

	public GOParticleSystem set_colorEnd(IntParameter _colorEnd)
	{
		this._colorEnd = _colorEnd;
		return this;
	}

	public GOParticleSystem setParticlesPerSecond(float particlesPerSecond)
	{
		this.particlesPerSecond = particlesPerSecond;
		return this;
	}

	public GOParticleSystem setInheritParticleSystemTransform(boolean inheritParticleSystemTransform)
	{
		this.inheritParticleSystemTransform = inheritParticleSystemTransform;
		return this;
	}

	public GOParticleSystem setSquareParticles(boolean squareParticles)
	{
		this.squareParticles = squareParticles;
		return this;
	}

	//endregion
}
