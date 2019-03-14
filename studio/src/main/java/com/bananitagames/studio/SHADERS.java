package com.bananitagames.studio;

import android.opengl.GLES20;
import android.util.Log;

import com.bananitagames.studio.shaders.BaseShaderDirect;

import java.util.ArrayList;

/**
 * This class provides static methods that grant you identifiers that can be used at runtime, to
 * setup {@link SceneLayer} parameters. Specifically, with these identifiers you can set which shader
 * is going to use the layer, by passing them as parameters in the setShader method
 */
public final class SHADERS
{

	private static final String _TAG_SHADER = "SHADER";

	private static ArrayList<SceneShader> loadedShaders = new ArrayList<>();

	private SHADERS(){}

	public static void reset()
	{
		for(SceneShader shader : loadedShaders)
			shader.reset();
	}

	public static SceneShader getShader(int vertexSourceId, int fragmentSourceId)
	{
		boolean shaderExists = false;
		SceneShader resultShader = null;
		for(SceneShader shader : loadedShaders)
		{
			//If shader is founded
			if (shader.getVertexShaderResourceId() == vertexSourceId && shader.getFragmentShaderResourceId() == fragmentSourceId)
			{
				resultShader = shader;
				shaderExists = true;
				break;
			}
		}
		if(!shaderExists)
		{
			int programId = createShaderProgram(vertexSourceId, fragmentSourceId);
			resultShader = new SceneShader(programId, vertexSourceId, fragmentSourceId);
			loadedShaders.add(resultShader);
		}
		return resultShader;
	}

	/**
	 * Dont' call this method directly
	 * @param vertexShaderResourceId
	 * @param fragmentShaderResourceId
	 * @return
	 */
	public static int createShaderProgram(int vertexShaderResourceId, int fragmentShaderResourceId)
	{
		return createShader(DATA.readTextFileFromResource(vertexShaderResourceId), DATA.readTextFileFromResource(fragmentShaderResourceId));
	}

	/**
	 * Call this function to create a shader and get its identifier value. its CPU expensive, so try
	 * to call only once this function per different shader in your application. The identifer that
	 * this function creates, should be passed to a scenelayer through its setShader function
	 * @param vertexShaderCode
	 * @param fragmentShaderCode
	 * @return the identifier value you should use in your scene layer properties to use it. Its a program identifier
	 */
	public static int createShader(String vertexShaderCode, String fragmentShaderCode)
	{
		int mProgram, vertexShader, fragmentShader;
		vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
		fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
		// create empty OpenGL ES Program
		mProgram = GLES20.glCreateProgram();

		if(mProgram != 0)
		{
			// add the vertex shader to program
			GLES20.glAttachShader(mProgram, vertexShader);

			// add the fragment shader to program
			GLES20.glAttachShader(mProgram, fragmentShader);

			// creates OpenGL ES program executables
			GLES20.glLinkProgram(mProgram);

			// get Link status
			final int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);

			// if the link failed, delete the program
			if (linkStatus[0] == 0)
			{
				GLES20.glDeleteProgram(mProgram);
				mProgram = 0;
			}
		}
		if(mProgram == 0)
		{
			throw new RuntimeException("Error creating program");
		}
		if(CONSTANTS._DEBUG_SHADER)
		{
			Log.d(CONSTANTS._TAG, "SUCCESFULLY create ShaderProgram ["+mProgram+"]");
		}
		return mProgram;
	}

	/**
	 * Call this function to get the identifier of the default framework shader. By default, all
	 * scene layers has this shader assigned
	 * @return the shader identifier
	 */
	public static SceneShader defaultShader()
	{
		return getShader(R.raw.base_v_shader, R.raw.base_f_shader);
	}

	/**
	 * Call this function to get the identifier of the default framework shader zero alpha
	 * @return the shader identifier
	 */
	public static SceneShader defaultShaderZeroAlpha()
	{
		return getShader(R.raw.base_v_shader, R.raw.base_f_shader_zero_alpha);
	}

	/**
	 * Call this function to get the identifier of the default framework shader low alpha
	 * @return the shader identifier
	 */
	public static SceneShader defaultShaderLowAlpha()
	{
		return getShader(R.raw.base_v_shader, R.raw.base_f_shader_low_alpha);
	}

	/**
	 * Call this function to get the identifier of the default framework shader square. This shader
	 * wont use texture information, and it will draw everything as a perfect analitic square.
	 * Whenever you use this
	 * @return the shader identifier
	 */
	public static SceneShader defaultShaderSquare()
	{
		return getShader(R.raw.base_v_shader, R.raw.base_f_shader_square);
	}

	/**
	 * Call this function to get the identifier of the default framework shader circle gradient. This shader
	 * wont use texture information, and it will draw everything as a square.
	 * @return the shader identifier
	 */
	public static SceneShader defaultShaderCircleGradient()
	{
		return getShader(R.raw.base_v_shader, R.raw.base_f_shader_circle_gradient);
	}

	/**
	 * Call this function to get the identifier of the default framework shader circle. This shader
	 * wont use texture information, and it will draw everything as a circle.
	 * @return the shader identifier
	 */
	public static SceneShader defaultShaderCircle()
	{
		return getShader(R.raw.base_v_shader, R.raw.base_f_shader_circle);
	}

	private static int loadShader(int type, String shaderCode)
	{
		// create a vertex shader type (GLES20.GL_VERTEX_SHADER)
		// or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
		int shader = GLES20.glCreateShader(type);

		if(shader != 0)
		{
			// add the source code to the shader and compile it
			GLES20.glShaderSource(shader, shaderCode);
			GLES20.glCompileShader(shader);
			// Get the compilation status.
			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

			// If the compilation failed, delete the shader.
			if (compileStatus[0] == 0)
			{
				GLES20.glDeleteShader(shader);
				shader = 0;
			}
		}
		if (shader == 0)
		{
			throw new RuntimeException("Error creating shader.");
		}
		if(CONSTANTS._DEBUG_SHADER)
		{
			Log.d(CONSTANTS._TAG, "SUCCESFULLY Load Shader ["+shader+"]");
		}
		return shader;
	}
}
