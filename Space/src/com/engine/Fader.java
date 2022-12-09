package com.engine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Fader implements ActionListener {

private Timer timer;
private float alpha;
private int fadeType;

private int delay = 50;
private int intial_delay;

public Fader(int type) 
{
	fadeType = type;
	
	this.alpha = type;
	
	if (type == 1) this.intial_delay = 3000;
	
	else this.intial_delay = 500;
	
    initTimer();
}

private void initTimer() 
{

    timer = new Timer(delay, this);
    timer.setInitialDelay(intial_delay);
    timer.start();
}

public void render(Graphics2D g)

{
            AlphaComposite acomp = AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, alpha);
    		g.setComposite(acomp);
    		g.setColor(Color.BLACK);
    		g.fillRect(0, 0, 1280, 720);
    		g.dispose();
}

private void fadeIn() 
{
    
     alpha += -0.01f;

     if (alpha <= 0) {

        alpha = 0f;
        timer.stop();
    }
}

private void fadeOut() 
{
    
     alpha += 0.01f;

     if (alpha >= 1) {

        alpha = 1f;
        timer.stop();
    }
}

@Override
public void actionPerformed(ActionEvent e)
{

	if (fadeType == 1) fadeIn();
	if (fadeType == 0) fadeOut();
}

}