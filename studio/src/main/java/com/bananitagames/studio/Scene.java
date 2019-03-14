package com.bananitagames.studio;

import android.opengl.GLSurfaceView;
import android.util.Log;

import com.bananitagames.studio.interfaces.LAYER;
import com.bananitagames.studio.interfaces.Music;
import com.bananitagames.studio.interfaces.Sound;
import com.bananitagames.studio.interfaces.Texture;

import java.util.ArrayList;


public abstract class Scene
{

	private static final int _DEFAULT_LAYER_ID = 0;
	private static final int _MAX_LAYERS = 128;
	////////////////////////////////////////////////////////////////////////////////////////////////
	// INTERNAL HANDLING VARIABLES
	private boolean isBackPressed = false;

	////////////////////////////////////////////////////////////////////////////////////////////////
	// LAYER VARIABLES
	private SceneLayer layers[] = new SceneLayer[_MAX_LAYERS];

	////////////////////////////////////////////////////////////////////////////////////////////////
	// CAM
    public Camera2D cam;

	////////////////////////////////////////////////////////////////////////////////////////////////
	// PREDEFINED REGIONS AND TEXTURE

	public static Texture mainTexture;
	public static TextureRegion regionButtonRectangle;
	public static TextureRegion regionButtonRectangleHover;
	public static TextureRegion regionButtonSquare;
	public static TextureRegion regionButtonSquareHover;
	public static TextureRegion regionButtonCircle;
	public static TextureRegion regionButtonCircleHover;
	public static TextureRegion regionIconGame;
	public static TextureRegion regionIconLike;
	public static TextureRegion regionIconShare;

	public static TextureRegion regionIconTrash;
	public static TextureRegion regionIconBack;
	public static TextureRegion regionIconReload;
	public static TextureRegion regionIconMusic;
	public static TextureRegion regionIconSound;

	public static TextureRegion regionStar3;
	public static TextureRegion regionStar4;
	public static TextureRegion regionStar5;
	public static TextureRegion regionStar6;
	public static TextureRegion regionSquareRounded;
	public static TextureRegion regionFinger;
	public static TextureRegion regionCircleGradient;
	public static TextureRegion regionSquareGradient;

	public static TextureRegion regionCallEliptic;
	public static TextureRegion regionCallCloud;
	public static TextureRegion regionArrow1;
	public static TextureRegion regionArrow2;
	public static TextureRegion regionArrow3;
	public static TextureRegion regionArrow4;
	public static TextureRegion regionTriangle;
	public static TextureRegion regionPentagon;
	public static TextureRegion regionHexagon;

	public static TextureRegion regionSquare;
	public static TextureRegion regionCircle;
	public static TextureRegion regionBolt;
	public static TextureRegion regionTick;
	public static TextureRegion regionCross;
	public static TextureRegion regionOption;
	public static TextureRegion regionLike;
	public static TextureRegion regionCallRectangle;
	public static TextureRegion regionCallRectangleEliptic;

	public static Font font;


	////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// CONSTRUCTOR
	//
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Scene()
	{
		//Declare
		float w, h, max;
		ActivityGame a;
		GLSurfaceView view;
		a =  ActivityGameManager.getInstance().getActivity();
		view = a.getGlView();
        w = view.getWidth();
        h = view.getHeight();
		if(CONSTANTS._DEBUG) Log.d(CONSTANTS._TAG, "WIDTH REAL = " + w + " . HEIGHT REAL = " + h);
		max = w > h ? w : h;
		w /= max;
		h /= max;
        if(CONSTANTS._DEBUG) Log.d(CONSTANTS._TAG, "FRUSTUM WIDTH = " + w + " . FRUSTUM HEIGHT = " + h);
        this.cam = new Camera2D(w, h);
	}

	final void basicLoad()
	{
		float w = 64f, h = 96f, cell = 32f, offset = 7f*cell, size = 6f*cell;
		mainTexture = loadTexture(R.drawable.basic_sheet);
		regionButtonRectangle = new TextureRegion(mainTexture,1f,6f*h,7f*w,2f*h);
		regionButtonRectangleHover = new TextureRegion(mainTexture,7f*w,6f*h,7f*w,2f*h);
		regionButtonSquare = new TextureRegion(mainTexture,1f,8f*h,3f*w,2f*h);
		regionButtonSquareHover = new TextureRegion(mainTexture,3f*w,8f*h,3f*w,2f*h);
		regionButtonCircle = new TextureRegion(mainTexture,6f*w,8f*h,3f*w,2f*h);
		regionButtonCircleHover = new TextureRegion(mainTexture,9f*w,8f*h,3f*w,2f*h);
		regionIconGame = new TextureRegion(mainTexture,1,10f*h,2f*w,1f*h);
		regionIconLike = new TextureRegion(mainTexture,2f*w,10f*h,2f*w,1f*h);
		regionIconShare = new TextureRegion(mainTexture,4f*w,10f*h,2f*w,1f*h);
		font = new Font(mainTexture,1,0,16,64,96);


		regionIconTrash  = new TextureRegion(mainTexture, 0f*5f*cell + cell, 38f*cell, 4f*cell, 4f*cell);
		regionIconBack   = new TextureRegion(mainTexture, 1f*5f*cell + cell, 38f*cell, 4f*cell, 4f*cell);
		regionIconReload = new TextureRegion(mainTexture, 2f*5f*cell + cell, 38f*cell, 4f*cell, 4f*cell);
		regionIconMusic  = new TextureRegion(mainTexture, 3f*5f*cell + cell, 38f*cell, 4f*cell, 4f*cell);
		regionIconSound  = new TextureRegion(mainTexture, 4f*5f*cell + cell, 38f*cell, 4f*cell, 4f*cell);

		regionStar3 			= new TextureRegion(mainTexture,0 * offset + cell, 2048f - 3 * offset, size, size);
		regionStar4 			= new TextureRegion(mainTexture,1 * offset + cell, 2048f - 3 * offset, size, size);
		regionStar5 			= new TextureRegion(mainTexture,2 * offset + cell, 2048f - 3 * offset, size, size);
		regionStar6			 	= new TextureRegion(mainTexture,3 * offset + cell, 2048f - 3 * offset, size, size);
		regionSquareRounded 	= new TextureRegion(mainTexture,4 * offset + cell, 2048f - 3 * offset, size, size);
		regionFinger 			= new TextureRegion(mainTexture,5 * offset + cell, 2048f - 3 * offset, size, size);
		regionCircleGradient 	= new TextureRegion(mainTexture,6 * offset + cell, 2048f - 3 * offset, size, size);
		regionSquareGradient 	= new TextureRegion(mainTexture,7 * offset + cell, 2048f - 3 * offset, size, size);

		regionCallEliptic = new TextureRegion(mainTexture,0 * offset + cell, 2048f - 2 * offset, size, size);
		regionCallCloud = new TextureRegion(mainTexture,1 * offset + cell, 2048f - 2 * offset, size, size);
		regionArrow1 = new TextureRegion(mainTexture,2 * offset + cell, 2048f - 2 * offset, size, size);
		regionArrow2 = new TextureRegion(mainTexture,3 * offset + cell, 2048f - 2 * offset, size, size);
		regionArrow3 = new TextureRegion(mainTexture,4 * offset + cell, 2048f - 2 * offset, size, size);
		regionArrow4 = new TextureRegion(mainTexture,5 * offset + cell, 2048f - 2 * offset, size, size);
		regionTriangle = new TextureRegion(mainTexture,6 * offset + cell, 2048f - 2 * offset, size, size);
		regionPentagon = new TextureRegion(mainTexture,7 * offset + cell, 2048f - 2 * offset, size, size);
		regionHexagon = new TextureRegion(mainTexture,8 * offset + cell, 2048f - 2 * offset, size, size);

		regionSquare = new TextureRegion(mainTexture,0 * offset + cell, 2048f - 1 * offset, size, size);
		regionCircle = new TextureRegion(mainTexture,1 * offset + cell, 2048f - 1 * offset, size, size);
		regionBolt = new TextureRegion(mainTexture,2 * offset + cell, 2048f - 1 * offset, size, size);
		regionTick = new TextureRegion(mainTexture,3 * offset + cell, 2048f - 1 * offset, size, size);
		regionCross = new TextureRegion(mainTexture,4 * offset + cell, 2048f - 1 * offset, size, size);
		regionOption = new TextureRegion(mainTexture,5 * offset + cell, 2048f - 1 * offset, size, size);
		regionLike = new TextureRegion(mainTexture,6 * offset + cell, 2048f - 1 * offset, size, size);
		regionCallRectangle = new TextureRegion(mainTexture,7 * offset + cell, 2048f - 1 * offset, size, size);
		regionCallRectangleEliptic = new TextureRegion(mainTexture,8 * offset + cell, 2048f - 1 * offset, size, size);

	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// ABSTRACT
	//
	////////////////////////////////////////////////////////////////////////////////////////////////

	public abstract void loadResources();

	public abstract void setup();

	////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// PUBLIC
	//
	////////////////////////////////////////////////////////////////////////////////////////////////

	public final Texture loadTexture(String fileName)
	{
		return ActivityGameManager.getInstance().getActivity().getAndroidResources().addTexture(fileName);
	}

	public final Texture loadTexture(int resourceId)
	{
		return ActivityGameManager.getInstance().getActivity().getAndroidResources().addTexture(resourceId);
	}

	public final Sound loadSound(String fileName)
	{
		return ActivityGameManager.getInstance().getActivity().getAndroidResources().addSound(fileName);
	}

	public final Music loadMusic(String fileName)
	{
		return ActivityGameManager.getInstance().getActivity().getAndroidResources().addMusic(fileName);
	}

	public final boolean addGameObject(GameObject gameObject)
	{
		return addGameObject(gameObject,_DEFAULT_LAYER_ID);
	}
	
	public final boolean addGameObject(GameObject gameObject, int layerId)
	{
		securityLayerCheck(layerId);
		createLayerIfNeeded(layerId);
		layers[layerId].addGameObject(gameObject);
		return true;
	}

	public LAYER getLayer(int layerId)
	{
		securityLayerCheck(layerId);
		createLayerIfNeeded(layerId);
		return layers[layerId];
	}

	public final ArrayList<GameObject> getGameObjects(int layerId)
	{
		securityLayerCheck(layerId);
		createLayerIfNeeded(layerId);
		return layers[layerId].getGameObjects();
	}

	public final int getGameObjectsAmount()
	{
		int amount = 0;
		for(SceneLayer layer : layers)
			if(layer != null)
				amount += layer.getGameObjectsAmount();
		return amount;
	}
	
	public final int getGameObjectsAmount(int layerId)
	{
		securityLayerCheck(layerId);
		createLayerIfNeeded(layerId);
		return layers[layerId].getGameObjectsAmount();
	}
	
	public void update()
	{
		if(isBackPressed)
		{
			onBackPressed();
			isBackPressed = false;
		}
		for(SceneLayer layer : layers)
			if(layer != null)
				layer.update();
	}
	
	public final void present()
	{
		for(SceneLayer layer : layers)
			if(layer != null)
				layer.present();
		cam.setViewportAndMatrices();
		for(SceneLayer layer : layers)
			if(layer != null)
				layer.draw();
	}


	public void onBackPressed()
	{
		GAME.end();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// PRIVATE
	//
	////////////////////////////////////////////////////////////////////////////////////////////////

	private void securityLayerCheck(int layerId)
	{
		if(layerId < 0 || layerId >= _MAX_LAYERS)
			throw new RuntimeException("Scene layer: " + layerId + "doesn't exist. Minimum layer = 0. Maximum layer = " + (_MAX_LAYERS-1));
	}

	private void createLayerIfNeeded(int layerId)
	{
		if(layers[layerId] == null)
			layers[layerId] = new SceneLayer(layerId);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// PACKAGE
	//
	////////////////////////////////////////////////////////////////////////////////////////////////

	final void safeOnBackPressed()
	{
		isBackPressed = true;
	}

	final void drawTextureRegion(int layerId, Texture texture, float u1, float u2, float v1, float v2, float x, float y, float width, float height, float angle, int colorTopLeft, int colorTopRight, int colorBottonLeft, int colorBottomRight)
	{
		securityLayerCheck(layerId);
		createLayerIfNeeded(layerId);
		layers[layerId].drawTextureRegion(texture.getTextureId(), u1, u2, v1, v2, x, y, width, height, angle, colorTopLeft, colorTopRight, colorBottonLeft, colorBottomRight);
	}
}