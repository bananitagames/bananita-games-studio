package com.bananitagames.studio;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public abstract class ActivityGame extends Activity implements Renderer {

	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	////
	////	ACTIVITY
	////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////

	@Override
	protected final void onCreate(Bundle b) {
		super.onCreate(b);
		if (CONSTANTS._DEBUG) Log.d(CONSTANTS._TAG, "ActivityGame.onCreate()");
		// Window features
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// Save activity context
		ActivityGameManager.getInstance().init(this);
		// ContentView
		glView = new GLSurfaceView(this);
		glView.setEGLContextClientVersion(2);
		glView.setRenderer(this);
		fileIO = new AndroidFileIO(getAssets());
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, glView, 1, 1);
		resources = new AndroidResources();
		viewGroup = new RelativeLayout(this);
		viewGroup.addView(glView);
		setContentView(viewGroup);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (CONSTANTS._DEBUG) Log.d(CONSTANTS._TAG, "ActivityGame.onResume()");
		if (glView != null) {
			glView.onResume();
		}
		/*// Music resume
		Music m = Assets.music;
		if (m != null && !m.isPlaying()) {
			m.play();
		}*/
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (CONSTANTS._DEBUG) Log.d(CONSTANTS._TAG, "ActivityGame.onPause()");
		if (glView != null) {
			glView.onPause();
		}
		SHADERS.reset();
		/*// Music pause
		Music m = Assets.music;
		if (m != null && m.isPlaying()) {
			m.pause();
		}*/
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (CONSTANTS._DEBUG) Log.d(CONSTANTS._TAG, "ActivityGame.onStop()");
		/*// Music stop
		Music m = Assets.music;
		if (m != null && m.isPlaying()) {
			m.stop();
		}*/
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (CONSTANTS._DEBUG) Log.d(CONSTANTS._TAG, "ActivityGame.onDestroy()");
		/*// Music stop
		Music m = Assets.music;
		if (m != null) {
			m.dispose();
		}*/
	}

	@Override
	public void onBackPressed() {
		scene.safeOnBackPressed();
	}

	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	////
	////		METHODS
	////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////

	public abstract Scene getStartScene();

	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	////
	////		GAME
	////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////

	AndroidInput getInput() {
		return input;
	}

	AndroidFileIO getFileIO() {
		return fileIO;
	}

	AndroidAudio getAudio() {
		return audio;
	}

	AndroidResources getAndroidResources()
	{
		return resources;
	}

	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	////
	////		RENDERER
	////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////

	@Override
	public void onDrawFrame(GL10 unused) {
		float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
		TIME.deltaTime = deltaTime;
		input.loadGetters();
		startTime = System.nanoTime();
		scene.update();
		updateTime = System.nanoTime();
		TIME.deltaTimeUpdate = (updateTime - startTime) / 1000000000.0f;
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glDepthFunc(GLES20.GL_LEQUAL);
		GLES20.glEnable(GLES20.GL_TEXTURE_2D);
		GLES20.glEnable(GLES20.GL_BLEND);
		scene.present();
		presentTime = System.nanoTime();
		TIME.deltaTimePresent = (presentTime - updateTime) / 1000000000.0f;
	}

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		if (CONSTANTS._DEBUG) Log.d(CONSTANTS._TAG, "onSurfaceChanged");
		GLES20.glViewport(0, 0, width, height);
	}

	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		if (CONSTANTS._DEBUG) Log.d(CONSTANTS._TAG, "onSurfaceCreated");
		GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);
		if(scene == null)
			setScene(getStartScene());
		else
			reloadScene();
		startTime = System.nanoTime();
	}

	public void setScene(Scene scene)
	{
		if(CONSTANTS._DEBUG) Log.d(CONSTANTS._TAG, "SetScene " + scene.getClass().toString());
		this.scene = scene;
		loadingResources = true;
		resources.markAllResourcesUnused();
		this.scene.basicLoad();
		this.scene.loadResources();
		resources.removeAllResourcesUnused();
		loadingResources = false;
		this.scene.setup();
	}

	public void reloadScene()
	{
		if(CONSTANTS._DEBUG || CONSTANTS._DEBUG_RESOURCES) Log.d(CONSTANTS._TAG, "Reloading scene, and its assets");
		loadingResources = true;
		resources.markAllResourcesUnused();
		resources.markAllResourcesDeprecated();
		scene.basicLoad();
		scene.loadResources();
		resources.removeAllResourcesUnused();
		loadingResources = false;
	}

	public Scene getScene()
	{
		return scene;
	}

	public ViewGroup getViewGroup() {return viewGroup; }

	public GLSurfaceView getGlView()
	{
		return glView;
	}

	public boolean isLoadingResources()
	{
		return loadingResources;
	}

	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	////
	////	VARIABLES
	////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////

	private ViewGroup viewGroup;
	private GLSurfaceView glView;
	private Scene scene;

    private AndroidAudio audio;
    private AndroidInput input;
    private AndroidFileIO fileIO;
	private AndroidResources resources;

    private long startTime = System.nanoTime();
    private long updateTime = System.nanoTime();
    private long presentTime = System.nanoTime();
    private boolean loadingResources = false;

}
