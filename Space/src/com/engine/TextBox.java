package com.engine;

import java.awt.*;
import java.awt.geom.Rectangle2D;
 
public class TextBox  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String str;
	private String[] display;
	public double x, y, z,w,h,sx,sy;
	public int sw , sh, per_x, per_y;
	public Rectangle2D box;
	private double d = 512;
	
	public TextBox (String s, double x, double y, double z)
	{
		this.str = s;
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.display = str.split("\n");
	}
	
	private void addDepth() {
		
		 sx = (int) (d * x  / z );
		 sy = (int) (d * y  / z );

		 sw = (int) (d * w / z );
		 sh = (int) (d * h / z );
		 
		 per_x = (int) (sx - sw/2 + Camera3D.origin_x);
		 per_y = (int) (sy - sh + Camera3D.origin_y);
		
	};
	
	public void draw2D(Graphics2D g) 
	{
		Font old = 	g.getFont();

		Font myFont = new Font ("Impact New", 1, 18);
		
		g.setFont(myFont);
				
		for (int i = 0; i < display.length;i++)
			
		{
			box = g.getFontMetrics().getStringBounds(display[i], g);
			
			x = box.getX();
			y = box.getY();
			w = box.getWidth();
		    h = box.getHeight();
			 
			 addDepth();
			 
			g.drawString(display[i],per_x, per_y + i * 30);
			
		}
			g.setFont(old);
	}

	
	public void cal3DCamera() 
	{

		 int d = 512;
		 
		 int sx = (int) (d * (x - Camera3D.x) / (z - Camera3D.z));
		 int sy = (int) (d * (y - Camera3D.y) / (z- Camera3D.z));

		 sw = (int) (d * w / (z - Camera3D.z));
		 sh = (int) (d * h / (z- Camera3D.z));
		 
		 per_x = (int) (sx - sw/2 + Camera3D.origin_x);
		 per_y = (int) (sy - sh + Camera3D.origin_y);
	}
	
	public void update() {}
	
	public void setString(String s) 
	{
		this.str = s;
	}
	
	public void moveBox(double dx, double dy) 
	{
		
		if (y > 32) 
		{
			x += dx;
			y += dy;
		}
		
		else dy = 0;
	}
	
	public void render(Graphics2D g) 
	{
		Font old = 	g.getFont();

		Font myFont = new Font ("Impact New", 1, 18);
		g.setFont(myFont);
				
		for (int i = 0; i < display.length;i++)
		{
			box = g.getFontMetrics().getStringBounds(display[i], g);
			
			 //x = box.getX();
			 //y = box.getY();
			 w = box.getWidth();
			 h = box.getHeight();
		
			cal3DCamera();
			g.setColor(Color.white);
			g.drawString(display[i],per_x, per_y + i * 50);
						
		

		}  g.setFont(old);
	}

}