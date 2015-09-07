# Collision-Detection
SMYJAS002
Collision detection for 2D games in LibGDX. CSC2003.

Important changes I made:
 - added a BitMap class, contains the bitmap (uses int as 32 bit binary).
 - Does perfect pixel detection on objects. (please enlarge the window considerably to test its accuracy, ie. its very accurate) 
 - Use a quad tree to only test against entities that are local.
 - Special pixel level testing for the walls (wallCollision)

For more insight as to code opperation remove the '//' before the printing on the following lines:

- 74 Quadtree.java
- 135 CollisionDetection.java
- 143 CollisionDetection.java
- 26 & 27 BitMask.java
