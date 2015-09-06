package com.mygdx.game;

import com.badlogic.gdx.graphics.Pixmap;

public class BitMask {
	int[] bits;
	
	BitMask(Pixmap pixelMap){
		bits = new int[pixelMap.getHeight()];
		for (int i=0; i<20; i++) {
			bits[i] = 0;
		}
		
		for (int i = 0; i < pixelMap.getHeight(); i++) {
			for(int j = 0; j < pixelMap.getWidth(); j++) {
				int val = pixelMap.getPixel(j, i);
				bits[i] = bits[i] << 1;
				if(val != 0 && val != -256){
					bits[i] = bits[i] | 1;
				}
			}
			System.out.println();
			System.out.printf("%40s", Integer.toBinaryString(bits[i]));
		}
		
		System.out.println();System.out.println();
	}
	
	public int[] getSection(int sizeY, int fromY, int shift){
		int[] section = new int[sizeY];
		
		System.out.println("Getting a section:");
		for (int i = 0; i < sizeY; i++){
			System.out.println("to:" + i + " from:" + fromY);
			section[i] = bits[fromY] >> shift;
			fromY++;
		}
		
		return section;
	}
}
