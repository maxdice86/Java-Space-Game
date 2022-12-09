package com.entities;

import java.awt.*;

import com.engine.Camera3D;

public class Line
{
	//------------------------------------------------------------------------//
	
	double x1;
	double y1;
	double z;
	
	int sx,sy,sw,sh,per_x,per_y,sx2,sy2, per_x2,per_y2;
	
	double w,h;
	
	double x2;
	double y2;
		
	double Nx;
	double Ny;
	
	double c;
	
	
	public Line(double x1, double y1,double x2, double y2, double z)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		this.z = z;
				
		computeNormal();
		
		c = x1 * Nx  + y1 * Ny;		
	}
	
	
	
	public void calDepth()
	{
		 int d = 512;
		 
		 sx = (int) (d * (x1 - Camera3D.x) / (z - Camera3D.z));
		 sy = (int) (d * (y1 - Camera3D.y) / (z- Camera3D.z));
		 
		 sx2 = (int) (d * (x2 - Camera3D.x) / (z - Camera3D.z));
		 sy2 = (int) (d * (y2 - Camera3D.y) / (z- Camera3D.z));

		 sw = (int) (d * w / (z - Camera3D.z));
		 sh = (int) (d * h / (z- Camera3D.z));
		 
		 per_x = (int) (sx + Camera3D.origin_x);
		 per_y = (int) (sy + Camera3D.origin_y);
		 
		 per_x2 = (int) (sx2 + Camera3D.origin_x);
		 per_y2 = (int) (sy2 + Camera3D.origin_y);
		 
		// DepthNormal();

	}
	
	public void drawDepth(Graphics2D g) 
	{
		calDepth();
		
		g.drawLine((int)(per_x + Camera3D.x ), (int)(per_y + Camera3D.y ), (int)(per_x2 + Camera3D.x ),(int) (per_y2 + Camera3D.y ));
	}
	

	//------------------------------------------------------------------------//
	
	public Line(double x1, double y1, double x2, double y2)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		this.z = 512;
				
		computeNormal();
		
		c = x1 * Nx  + y1 * Ny;		
	}
	
	//------------------------------------------------------------------------//

	public void computeNormal()
	{
		double vx = x2 - x1;                     //     v
		double vy = y2 - y1;
		
		double mag_v = Math.sqrt(vx*vx + vy*vy); //    |v|
		
		
		double ux = vx / mag_v;                  //      v
		double uy = vy / mag_v;                  // u = ---
		                                         //     |v|		
		
		Nx =   -uy;                                
		Ny =    ux;       
		
	}
	
	//------------------------------------------------------------------------//

	
	public double distanceTo(double x, double y)
	{
		
		return Nx * ((x - sx ) - Camera3D.x * 2) + Ny * ((y - sy ) - Camera3D.y * 2);
		
		//return Nx * (x - x1) + Ny * (y - y1);
		
	
		//return  x * Nx  +  y * Ny - c;
	}
	
	//------------------------------------------------------------------------//
	
	public void draw(Graphics g)
	{
		g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	}
	
	//------------------------------------------------------------------------//
	//------------------------------------------------------------------------//
	//------------------------------------------------------------------------//

	int held_at = 0;
	
	public boolean isHeld()
	{
		return held_at != 0;
	}
		
	//------------------------------------------------------------------------//
	
	public void grabbedAt(int mx, int my)
	{
		double dx;
		double dy;
		
		dx = x1 - mx;
		dy = y1 - my;
		
		if(dx*dx + dy*dy < 49)  held_at = 1;
		
		dx = x2 - mx;
		dy = y2 - my;
		
		if(dx*dx + dy*dy < 49)  held_at = 2;
	}
	
	//------------------------------------------------------------------------//

	public void draggedBy(int dx, int dy)
	{
		if(held_at == 1)
		{
			x1 += dx;
			y1 += dy;
		}
		
		if(held_at == 2)
		{
			x2 += dx;
			y2 += dy;
		}
		
		computeNormal();
	}
	
	//------------------------------------------------------------------------//

	public void released()
	{
		held_at = 0;
	}
	
	//------------------------------------------------------------------------//
	
}