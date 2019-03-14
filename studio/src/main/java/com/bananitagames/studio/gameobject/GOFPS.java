package com.bananitagames.studio.gameobject;

import com.bananitagames.studio.CONSTANTS;
import com.bananitagames.studio.GameObject;
import com.bananitagames.studio.Scene;
import com.bananitagames.studio.TIME;

public class GOFPS extends GameObject
{

    private boolean showAdvancedFPS = false;

    private static final int MAX_FPS = 60;
    private int color, colorUpdate, colorPresent;
    private float   x1,x2,x3,xInstaUpdate,xInstaPresent,xAverageUpdate,xAveragePresent,
                    y1,y2,
                    w1,w2,h, wInstaUpdate,wInstaPresent,wAverageUpdate,wAveragePresent,
                    maxWidth,frustumWidth,
                    instantFPS,averageFPS,
					instantUpdateFraction, averageUpdateFraction,
					instantPresentFraction, averagePresentFraction;

    public GOFPS()
    {
        super();
        resetValues();
    }

    private void resetValues()
    {
        instantFPS = 0f;
        averageFPS = 0f;
        instantUpdateFraction = 0.0f;
        averageUpdateFraction = 0.0f;
        instantPresentFraction = 0.0f;
        averagePresentFraction = 0.0f;
        color = 0xFF00FF00;
        colorUpdate = 0xFFFF0000;
        colorPresent = 0xFF0000FF;
        frustumWidth = scene.cam.frustumWidth;
        maxWidth = frustumWidth/4f;
        h = maxWidth/16f;
        x1 = 0;
        x2 = 0;
        x3 = 0;
        xInstaUpdate = 0;
        xInstaPresent = 0;
        xAverageUpdate = 0;
        xAveragePresent = 0;
        y1 = 0;
        y2 = 0;
        w1 = 0;
        w2 = 0;
        wInstaUpdate = 0;
        wInstaPresent = 0;
        wAverageUpdate = 0;
        wAveragePresent = 0;
    }

    public GOFPS setAlpha(int alpha)
    {
		color = 0x0000FF00 | (alpha << 24);
		colorUpdate = 0x00FF0000 | (alpha << 24);
		colorPresent = 0x000000FF | (alpha << 24);
		return this;
    }

    public GOFPS showAdvanced(boolean advancedMode)
	{
		this.showAdvancedFPS = advancedMode;
		return this;
	}

    @Override
    public void update()
    {
        super.update();
        float deltaTime = TIME.deltaTime;
        float deltaTimeUpdate = TIME.deltaTimeUpdate;
        float deltaTimePresent = TIME.deltaTimePresent;
        if(scene.cam.frustumWidth != frustumWidth)
            resetValues();
        transform.position
                .set(scene.cam.position)
                .sub(scene.cam.frustumWidth/2f,-scene.cam.frustumHeight/2f);
        // DELTA TIME
		deltaTime = deltaTime > 1f ? 1f : deltaTime;
        instantFPS = 1f/deltaTime;
        averageFPS = averageFPS*(1f-deltaTime) + 1f;
        instantFPS = instantFPS > 60f ? 60f : instantFPS;
        // UPDATE TIME
		deltaTimeUpdate = deltaTimeUpdate > 1f ? 1f : deltaTimeUpdate;
		instantUpdateFraction = deltaTimeUpdate / deltaTime;
		averageUpdateFraction = averageUpdateFraction *(1f-deltaTime) + deltaTimeUpdate;
		instantUpdateFraction = instantUpdateFraction > 1f ? 1f : instantUpdateFraction;
		// PRESENT TIME
		deltaTimePresent = deltaTimePresent > 1f ? 1f : deltaTimePresent;
		instantPresentFraction = deltaTimePresent / deltaTime;
		averagePresentFraction = averagePresentFraction *(1f-deltaTime) + deltaTimePresent;
		instantPresentFraction = instantPresentFraction > 1f ? 1f : instantPresentFraction;
		// SIZES
        w1 = instantFPS/60f*maxWidth;
        w2 = averageFPS/60f*maxWidth;
        x1 = transform.position.x + h + (w1/2f);
        x2 = transform.position.x + h + (w2/2f);
        y1 = transform.position.y - (3f/2f * h);
        y2 = y1 - 2f*h;
        x3 = transform.position.x + 2f*h + maxWidth;
		wInstaUpdate = instantUpdateFraction * maxWidth;
		wInstaPresent = instantPresentFraction *maxWidth;
		wAverageUpdate = averageUpdateFraction * maxWidth;
		wAveragePresent = averagePresentFraction * maxWidth;
		xInstaUpdate = transform.position.x + h + (wInstaUpdate/2f);
		xInstaPresent = transform.position.x + h + wInstaUpdate + (wInstaPresent/2f);
		xAverageUpdate = transform.position.x + h + (wAverageUpdate/2f);
		xAveragePresent = transform.position.x + h + wAverageUpdate + (wAveragePresent/2f);
    }

    @Override
    public void present()
    {
        super.present();
        if(CONSTANTS._DEBUG)
        {
            // Draw instant FPS bar
            drawSprite(Scene.regionSquare, x1, y1, w1, h, color);
            // Draw instant FPS number
            drawText(Scene.font, "Dir.FPS="+(int)instantFPS, h , x3, y1, color);
            // Draw average FPS bar
            drawSprite(Scene.regionSquare, x2, y2, w2, h, color);
            // Draw average FPS number
            drawText(Scene.font, "Avg.FPS="+(int)averageFPS, h , x3, y2, color);
            if(showAdvancedFPS)
			{
				//Draw instant Update FPS
				drawSprite(Scene.regionSquare, xInstaUpdate, y1, wInstaUpdate, h, colorUpdate);
				//Draw instant Present FPS
				drawSprite(Scene.regionSquare, xInstaPresent, y1, wInstaPresent, h, colorPresent);
				//Draw average Update FPS
				drawSprite(Scene.regionSquare, xAverageUpdate, y2, wAverageUpdate, h, colorUpdate);
				//Draw average Present FPS
				drawSprite(Scene.regionSquare, xAveragePresent, y2, wAveragePresent, h, colorPresent);
			}
        }
    }
}
