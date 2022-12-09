package com.engine;

import com.entities.Lookup;

public class Camera3D
{
	
	public static double origin_x = 632;
	public static double origin_y = 340;
	
	public static double x = 0;
	public static double y = 0;
	public static double z = 0;
	
	public static double vx = 0;
	public static double vy = 0;
	public static double vz = 0;
	
	
	public static int A = 0;
	
	public static double cosA = Lookup.cos[A];
	public static double sinA = Lookup.sin[A];
	
	
	public static void setup(double x, double y, double z)
	{
		Camera3D.x = x;
		Camera3D.y = y;
		Camera3D.z = z;
	}
	
	
	public static void moveBy(double dx, double dy, double dz)
	{

	    if (z <= -32 && dz < 0) dz = 0;
	    if (z >=  32 && dz > 0) dz = 0; 
	    
	    z += dz;
		x += dx;
		y += dy;
		
	}
	
	public static void moveForward(double d)
	{
		x += d * sinA;   // Switched sin and cos
		z += d * cosA;
	}
	
	public static void moveBackward(double d)
	{
		x -= d * sinA;
		z -= d * cosA;
	}
	
	
	public static void rotateLeft(int dA)
	{
		A += dA;
		
		if(A > 359)  A -= 360;

	   cosA = Lookup.cos[A];
	   sinA = Lookup.sin[A];
	}

	public static void rotateRight(int dA)
	{
		A -= dA;
		
		if(A < 0)  A += 360;

	   cosA = Lookup.cos[A];
	   sinA = Lookup.sin[A];
	}
}
