package com.bananitagames.studio;

public final class GAME
{
	private GAME(){}

	public static void end()
	{
		ActivityGameManager.getInstance().getActivity().finish();
	}

	public static void setScene(Scene scene)
	{
		ActivityGameManager.getInstance().getActivity().setScene(scene);
	}

	public static Scene getScene()
	{
		return ActivityGameManager.getInstance().getActivity().getScene();
	}
}
