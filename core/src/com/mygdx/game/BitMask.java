package com.mygdx.game;

import com.badlogic.gdx.graphics.Pixmap;

public class BitMask {
	int[] bits;
	
	BitMask(Pixmap pixelMap){
		
		bits = new int[pixelMap.getHeight()];
		
		// initialise the entire bitMask to 0 (all 0s)
		for (int i=0; i<20; i++) {
			bits[i] = 0;
		}
		
		// create the bitmask by going through the pixelMap pixel by pixel
		for (int i = 0; i < pixelMap.getHeight(); i++) {
			for(int j = 0; j < pixelMap.getWidth(); j++) {
				int val = pixelMap.getPixel(j, i);
				bits[i] = bits[i] << 1;
				if(val != 0 && val != -256){
					bits[i] = bits[i] | 1;
				}
			}
//			System.out.println();
//			System.out.printf("%40s", Integer.toBinaryString(bits[i]));
			
		}
		
	}
}
