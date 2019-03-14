package com.bananitagames.studio;

import android.graphics.Color;

import com.bananitagames.studio.interfaces.Texture;

public class Font 
{
	
    public final Texture texture;
    public final float glyphFactor;
    public final TextureRegion[] glyphs = new TextureRegion[96];
    
    public Font(Texture texture, int offsetX, int offsetY, int glyphsPerRow, int glyphWidth, int glyphHeight)
    {        
        this.texture = texture;
        this.glyphFactor = (float)glyphHeight/(float)glyphWidth;
        int x = offsetX;
        int y = offsetY;
        for(int i = 0; i < 96; i++) {
            glyphs[i] = new TextureRegion(texture, x, y, glyphWidth, glyphHeight);
            x += glyphWidth;
            if(x == offsetX + glyphsPerRow * glyphWidth) {
                x = offsetX;
                y += glyphHeight;
            }
        }        
    }

}
