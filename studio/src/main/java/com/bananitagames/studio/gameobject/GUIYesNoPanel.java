package com.bananitagames.studio.gameobject;

import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.Scene;
import com.bananitagames.studio.interfaces.GUIButtonListener;

/**
 * Created by luis on 05/10/2017.
 */

public class GUIYesNoPanel extends GameObject implements GUIButtonListener
{
	float padding;
	float backgroundSize;
	float buttonSize;
	GUIButton buttonYes, buttonNo;
	Runnable yesRunnable, noRunnable;
	public String text1 = "", text2 = "";

	public GUIYesNoPanel()
	{
		super();
		float basicSize = Math.min(scene.cam.frustumHeight, scene.cam.frustumWidth);
		this.padding = basicSize / 12.0f;
		this.backgroundSize = basicSize * 5.0f / 6.0f;
		this.buttonSize = basicSize / 6.0f;
		this.transform.position.set(scene.cam.position);
		this.filterColor = 0x44000000;
		buttonYes = createButton();
		buttonNo = createButton();
		this.buttonYes.filterColor = 0xff00ff00;
		this.buttonNo.filterColor = 0xffff0000;
	}

	private final GUIButton createButton()
	{
		GUIButton button = new GUIButton()
				.setMaskCircle()
				.setTextureRegions(Scene.regionButtonCircle,Scene.regionButtonCircle,Scene.regionButtonCircle,Scene.regionButtonCircle)
				.setSize(buttonSize, buttonSize)
				.setRadius(buttonSize/2f)
				.setGUIButtonListener(this);
		return button;
	}

	private final void updateButtonYesTransformPosition()
	{
		buttonYes.transform.position.set(transform.position.x + backgroundSize/2f - buttonSize/2f - padding, transform.position.y - backgroundSize/2f + buttonSize/2f + padding);
	}

	private final void updateButtonNoTransformPosition()
	{
		buttonNo.transform.position.set(transform.position.x - backgroundSize/2f + buttonSize/2f + padding, transform.position.y - backgroundSize/2f + buttonSize/2f + padding);
	}


	public final GUIYesNoPanel setYesRunnable(Runnable runnable)
	{
		yesRunnable = runnable;
		return this;
	}

	public final GUIYesNoPanel setNoRunnable(Runnable runnable)
	{
		noRunnable = runnable;
		return this;
	}

	@Override
	public void update()
	{
		super.update();
		updateButtonYesTransformPosition();
		updateButtonNoTransformPosition();
		buttonYes.update();
		buttonNo.update();
	}

	@Override
	public void present()
	{
		super.present();
		drawSprite(Scene.regionSquare, transform.position.x, transform.position.y, backgroundSize, backgroundSize);
		drawTextCentered(Scene.font, text1, buttonSize/4f, transform.position.x, transform.position.y + padding + buttonSize/4f, 0xffffffff);
		drawTextCentered(Scene.font, text2, buttonSize/4f, transform.position.x, transform.position.y, 0xffffffff);
		buttonYes.present();
		drawSprite(Scene.regionTick, buttonYes.transform.position.x, buttonYes.transform.position.y, buttonSize*0.6f,buttonSize*0.6f, 0xffffffff);
		buttonNo.present();
		drawSprite(Scene.regionCross, buttonNo.transform.position.x, buttonNo.transform.position.y, buttonSize*0.6f,buttonSize*0.6f, 0xffffffff);
	}

	@Override
	public void onGainedFocus(GUIButton button)
	{

	}

	@Override
	public void onLostFocus(GUIButton button)
	{

	}

	@Override
	public void onPressed(GUIButton button)
	{

	}

	@Override
	public void onClick(GUIButton button)
	{
		if(button == buttonYes && yesRunnable != null)
			yesRunnable.run();
		if(button == buttonNo && noRunnable != null)
			noRunnable.run();
	}
}
