package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Entity;

public class Player extends Entity {
	
	public Player(CollisionDetection game, float x, float y, float speed, Texture texture) {
		super(game, x, y, speed, texture);
	}

	@Override
	public void update(float delta) {
		
		dx = 0;
		dy = 0;

		// move
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			dy = 1;//speed * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			dy = -1;//-speed * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			dx = -1;//-speed * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			dx = 1;//speed * delta;
		}
	}
	
	// accurately tests for a wall collision
	public boolean wallCollision(int x, int y, float newX, float newY){
		
		int wall = 1048575; // in binary is 20 consecutive 1s (2^20 -1)
		int start = 0;
		int numChecks;
		int xDiff = (x * 20) - Math.round(newX);
		int yDiff = (y * 20) - Math.round(newY);
		
		// this shifts the correct objects bounding box by the correct amount. (ie deals with the x-axis)
		if (xDiff > 0){
			wall = wall >> xDiff;
		} else {
			wall = wall << (-xDiff);
		}
		
		// this gets correct range in hight to be testsed. (ie deals with the y-axis)
		if (yDiff > 0) {
			numChecks = this.height - yDiff;
		} else {
			numChecks = 20 + yDiff;
			start = - yDiff;
		}

		// runs through bitMasks to detect collision.
		for (int i = 0; i < numChecks; i++){
			// compare row of 
			if( ((this.bitMask.bits[start]) & wall) != 0) {
				return true;
			}
			
			// increments the indexes of the bitmasks.
			start++;
		}
		
		// Return false if no collision.
		return false;
	}
}
