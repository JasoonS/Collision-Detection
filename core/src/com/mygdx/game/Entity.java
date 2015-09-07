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
	
	public Entity(CollisionDetection game, float x, float y, float speed, Texture texture) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.texture = texture;
		this.width = texture.getWidth();
		this.height = texture.getHeight();
		
		if (!this.texture.getTextureData().isPrepared()) {
		    this.texture.getTextureData().prepare();
		}
		// creates the new bitMask for the collision detection.
		this.bitMask = new BitMask(this.texture.getTextureData().consumePixmap());

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
		
		// this shifts the correct objects bounding box by the correct amount. (ie deals with the x-axis)
		if (xDiff > 0) e2shift = xDiff;
		else e1shift = -xDiff;
		
		// this gets correct range in hight to be testsed. (ie deals with the y-axis)
		if (yDiff > 0) {
			numChecks = this.height - yDiff;
			e2start = yDiff;
		} else {
			numChecks = e2.height + yDiff;
			e1start = - yDiff;
		}

		// runs through bitMasks to detect collision.
		for (int i = 0; i < numChecks; i++){
			// does the bit shifting to the relevant rows of the bitMasks and relevant shifts to check for collision.
			if( ((this.bitMask.bits[e1start] >> e1shift) & (e2.bitMask.bits[e2start] >> e2shift)) != 0) {
				return true;
			}
			
			// increments the indexes of the two bitmasks.
			e1start++;
			e2start++;
		}
		
		// Return false if no collision.
		return false;
	}
}
