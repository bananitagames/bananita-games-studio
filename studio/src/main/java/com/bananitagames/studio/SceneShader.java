package com.bananitagames.studio;

/**
 * Created by luis on 22/01/2018.
 */

public final class SceneShader
{

	private static final int _DEFAULT_SHADER_PROGRAM = -1;

	private int programId;
	private int vertexShaderResourceId;
	private int fragmentShaderResourceId;

	public SceneShader(int programId, int vertexShaderResourceId, int fragmentShaderResourceId)
	{
		this.programId = programId;
		this.vertexShaderResourceId = vertexShaderResourceId;
		this.fragmentShaderResourceId = fragmentShaderResourceId;
	}

	public void reset()
	{
		programId = _DEFAULT_SHADER_PROGRAM;
	}

	public int getProgram()
	{
		if(programId == _DEFAULT_SHADER_PROGRAM)
			programId = SHADERS.createShaderProgram(vertexShaderResourceId, fragmentShaderResourceId);
		return programId;
	}

	public boolean isLoaded()
	{
		return !(programId == _DEFAULT_SHADER_PROGRAM);
	}

	public int getVertexShaderResourceId()
	{
		return vertexShaderResourceId;
	}

	public int getFragmentShaderResourceId()
	{
		return  fragmentShaderResourceId;
	}
}
