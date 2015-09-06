package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.CollisionDetection.Direction;

public class Entity {
	public CollisionDetection game;
	public float x;
	public float y;
	public float dx;
	public float dy;
	public int width;
	public int height;  
	public float speed;
	public Texture texture;
	public BitMask bitMask;
	
	public Entity(CollisionDetection game, float x, float y, int width, int height, float speed, Texture texture) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.texture = texture;
		
		if (!this.texture.getTextureData().isPrepared()) {
		    this.texture.getTextureData().prepare();
		}
		this.bitMask = new BitMask(this.texture.getTextureData().consumePixmap()); //pixmap to use with pixel level testing

	}

	public void update(float delta) {

	}
	
	public void move(float newX, float newY) {
		x = newX;
		y = newY;		
	}
	
	public void render() {
		
	}

	public void tileCollision(int tile, int tileX, int tileY, float newX, float newY, Direction direction) {
		System.out.println("tile collision at: " + tileX + " " + tileY);
		
		if(direction == Direction.U) {
			y = tileY * game.tileSize + game.tileSize;
		}
		else if(direction == Direction.D) {
			y = tileY * game.tileSize - height;
		}
		else if(direction == Direction.L) {
			x = tileX * game.tileSize + game.tileSize;
		}
		else if(direction == Direction.D) {
			x = tileX * game.tileSize - width;
		}		
	}

	public boolean entityCollision(Entity e2, float newX, float newY, Direction direction) {
		
		int e1start = 0;
		int e2start = 0;
		int e1shift = 0;
		int e2shift = 0;
		int numChecks;
		int xDiff = Math.round(e2.x) - Math.round(newX);
		int yDiff = Math.round(e2.y) - Math.round(newY);
		
		// this shifts the correct objects bounding box by the correct amount.
		if (xDiff > 0) e2shift = xDiff;
		else e1shift = -xDiff;
		
//		System.out.println("e1 - x:" + newX + " y:" + newY);
//		System.out.println("e2 - x:" + e2.x + " y:" + e2.y);
		
		// this gets the two sections of bitmasks to be tested against each other.
		if (yDiff > 0) {
//			System.out.println("mask 1 - yHeight: " + (e2.height - yDiff) + " ; yStart: " + 0 + " ; xShift: " + e1shift);
//			System.out.println("mask 2 - yHeight: " + (e2.height - yDiff)  + " ; yStart: " + (e2.height - yDiff) + " ; xShift: " + e2shift);
//			e1mask = this.bitMask.getSection(this.height - yDiff, 0, e1shift);
//			e2mask = e2.bitMask.getSection(e2.height - yDiff, yDiff, e2shift);
			numChecks = this.height - yDiff;
			e2start = yDiff;
		} else {
//			System.out.println("mask 1 - yHeight: " + (e2.height + yDiff)  + " ; yStart: " + (e2.height + yDiff) + " ; xShift: " + e1shift);
//			System.out.println("mask 2 - yHeight: " + (e2.height + yDiff) + " ; yStart: " + 0 + " ; xShift: " + e2shift);
//			e1mask = this.bitMask.getSection(this.height + yDiff, -yDiff, e1shift);
//			e2mask = e2.bitMask.getSection(e2.height + yDiff, 0, e2shift);
			numChecks = e2.height + yDiff;
			e1start = - yDiff;
		}
		
//		System.out.println("Entity One check:");
//		for (int i = 0; i < e1mask.length; i++) {
//			System.out.printf("%32s", Integer.toBinaryString(e1mask[i]) + "\n");
//		}
//		
//		System.out.println("Entity two check:");
//		for (int i = 0; i < e2mask.length; i++) {
//			System.out.printf("%32s", Integer.toBinaryString(e2mask[i]) + "\n");
//		}
		
		
		
//		for (int i = 0; i < e1mask.length; i++) {
//			if( (e1mask[i] & e2mask[i]) != 0) {
//				return false;
//			}
//		}
		
		for (int i = 0; i < numChecks; i++){
			if( ((this.bitMask.bits[e1start] >> e1shift) & (e2.bitMask.bits[e2start] >> e2shift)) != 0) {
				return true;
			}
			
			e1start++;
			e2start++;
		}
		
		return false;
		
//		move(newX, newY);
		// could also resolve entity collisions in the same we do tile collision resolution
	}
}
