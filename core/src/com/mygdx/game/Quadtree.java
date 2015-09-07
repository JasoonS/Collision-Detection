package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

public class Quadtree {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private int level;
	private List<Entity> entities;
	private Quadtree[] nodes;
	 
	// creates a node in the Quadtree
	public Quadtree(int pLevel, int x, int y, int width, int height) {
		level = pLevel;
		entities = new ArrayList<Entity>();
		nodes = new Quadtree[4];
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	private int getIndex(Entity entity) {
		// Get midpoints of this node of the Quadtree
		int verMidpoint = this.y + (this.height / 2);
		int horMidpoint = this.x + (this.width / 2);
	
		// Test whether the entity can fit completely within the top, bottom, left, or right Quadrant.
		boolean top = (entity.y > verMidpoint);
		boolean bottom = ((entity.y + entity.height) < verMidpoint);
		boolean right = (entity.x > horMidpoint);
		boolean left = ((entity.x + entity.width) < horMidpoint);
	
		// Return the correct index
		if(left && bottom) return 0;
		if(right && bottom) return 1;
		if(left && top) return 2;
		if(right && top) return 3;

		// If the entity overlaps any boundary
		return -1;
	}

	private void split() {
		int subWidth = this.width / 2;
		int subHeight = this.height / 2;
	
		// add quatdree nodes with the correct quadrants as subnodes.
		nodes[0] = new Quadtree(level+1, x, y, subWidth, subHeight);
		nodes[1] = new Quadtree(level+1, x + subWidth, y, subWidth, subHeight);
		nodes[2] = new Quadtree(level+1, x, y + subHeight, subWidth, subHeight);
		nodes[3] = new Quadtree(level+1, x + subWidth, y + subHeight, subWidth, subHeight);
	}

	private void insert(Entity entity) {
		
		// get index to be used and insert into that index recursively.
		int index = getIndex(entity);
	
		// My Quad tree has max 3 levels.
		if (index != -1 && level < 4) {
			
			if (nodes[index] == null) split();
				
			nodes[index].insert(entity);
		
			return;
		}
	
//		System.out.println("Entity at (" + entity.x + "," + entity.y + ") added to level of the: " + this.level);
		
		entities.add(entity);
	}
	
	public void insert(List<Entity> entities) {
//		System.out.println("ADDING ENTITIES!!!!!!!");
		for(Entity e: entities){
			System.out.println(e.x);
			insert(e);
		}
	}

	public List<Entity> retrieve(List<Entity> returnEntities, Entity entity) {
		int index = getIndex(entity);
		
		if(nodes[0] != null) {
		
			if (index != -1) {
				nodes[index].retrieve(returnEntities, entity);
			} else {
				retrieveAllSub(returnEntities);
			}
		
		}
		
		returnEntities.addAll(entities);
		
		return returnEntities;
	}

	private void retrieveAllSub(List<Entity> returnEntities) {
		if(nodes[0] != null) {
			
			for(Quadtree node: nodes){
				returnEntities.addAll(node.entities);
				node.retrieveAllSub(returnEntities);
			}
		
		}
	}
}
