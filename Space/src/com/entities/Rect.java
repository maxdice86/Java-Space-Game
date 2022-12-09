package com.entities;
import java.awt.*;

public class Rect
{
   //------------------------------------------------------------------------//

	int x;
	int y;
	
	int w;
	int h;
	
	Color color;
	
	int vx = 0;
	int vy = 0;
	
	int ay = 0;
	
	public static final int GRAVITY = 1;
	
   //------------------------------------------------------------------------//

	public Rect(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
   //------------------------------------------------------------------------//

	public boolean overlaps(Rect r)
	{
		return (r.x + r.w >=   x)  &&
				 (  x +   w >= r.x)  &&
				 (r.y + r.h >=   y)  &&
				 (  y +   h >= r.y);
	}
	
   //------------------------------------------------------------------------//

	public boolean contains(int mx, int my)
	{
		return (mx >= x  ) &&
				 (mx <= x+w) &&
				 (my >= y  ) &&
				 (my <= y+h);
	}
		
   //------------------------------------------------------------------------//

	public void moveUp(int dy)
	{
		vy = -dy;
	}
	
   //------------------------------------------------------------------------//

	public void moveDown(int dy)
	{
		vy = dy;
	}
	
   //------------------------------------------------------------------------//

	public void moveLeft(int dx)
	{
		vx = -dx;
	}
	
   //------------------------------------------------------------------------//

	public void moveRight(int dx)
	{
		vx = dx;
	}
	
   //------------------------------------------------------------------------//

	public void update()
	{
		vy += ay;
		
	   x += vx;
	   y += vy;
	}
	
	//------------------------------------------------------------------------//

	public void stops()
	{
		 vx = 0;
		 vy = 0;
		 ay = 0;
	}
	
	//------------------------------------------------------------------------//

   public void jump(int dy)
   {
   	vy = -dy;
   }
	
	//------------------------------------------------------------------------//

   public void applyGravity()
   {
   	ay = GRAVITY;
   }
   
	//------------------------------------------------------------------------//

	public void draw(Graphics g)
	{		
		g.setColor(color);

	//	g.drawRect(x - Camera.x + Camera.x_origin, y - Camera.y + Camera.y_origin, w, h);
	}

   //------------------------------------------------------------------------//

	public void fill(Graphics g)
	{	
		g.setColor(color);
		
		g.fillRect(x, y, w, h);
	}

   //------------------------------------------------------------------------//

}
