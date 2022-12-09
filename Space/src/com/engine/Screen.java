package com.engine;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Screen extends Canvas
{
	public JFrame frame; 
	public BufferStrategy render;
	public int WIDTH = 1280, HEIGHT = 720;

	public Screen (Engine game) {
		
	 GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	 GraphicsDevice device = ge.getDefaultScreenDevice();
	 GraphicsConfiguration gc = device.getDefaultConfiguration();
		 
	    frame = new JFrame(gc);
	    
	    frame.setTitle(("Space Shooter"));
		 
		frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		frame.setMaximumSize(new Dimension(WIDTH,HEIGHT));
		frame.setMinimumSize(new Dimension(WIDTH,HEIGHT));
		frame.getContentPane().add(game);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(this);
		frame.pack();
		frame.setVisible(true);
		
		setBuffer(frame);
		
	}
	
	
	public BufferStrategy setBuffer(JFrame frame)
	{
	          
		frame.createBufferStrategy(2);
	  
		return render = frame.getBufferStrategy();
	
	}
}
