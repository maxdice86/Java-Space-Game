package com.entities;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Hashtable;

import com.engine.Animation;

/**
 * The Class Sprite.
 */
public abstract class Sprite  extends Circle
	{

	protected ID id;
		
	protected boolean alive, enabled;
	
	protected  Circle collisionMap;
	
	protected  AffineTransform currentTrans;

	protected  Hashtable<String, Animation> animator;
	
	
	public Sprite(double x, double y ,double z, double w,double h, int angle, ID id) 
	{
		
		super(x, y, z, w, h, angle);
		
		this.id = id;
		this.alive = true;
		this.enabled = true;
						
		this.collisionMap = new Circle(x, y, z, w * 0.70, h * 0.70, angle);
		
		this.animator = new Hashtable<String, Animation>();
		
	}
	
	public abstract void update();
	
	public abstract void render(Graphics2D g);
	
	
	public boolean isAlive() 
	{
		return alive;
	}
	
	public boolean isEnabled() 
	{
		return enabled;
	}

	public void setEnabled(boolean e) 
	{
		enabled = e; 
	}
	
	public void setAlive(boolean a) 
	{
		alive = a;
	}

	public void loadAnimation(String name , String filename, int count, int duration) 
	{
		
		animator.put(name, new Animation(filename, count, duration));
	}
	
	public ID getId()
	{
		
		return id;
	}
	
	
	public double getDistance(Sprite c)
	{
	double dx = x - c.x;
	double dy = y - c.y;
	
	double d2 = dx*dx + dy*dy;
	
	//double ri = w + c.w;
	
	return d2 ;//<= ri*ri;
	}
	
	public void updateSprite(double x, double y ,double z, double w,double h, int angle) 
	{
		
		this.A = angle;
	}
}


