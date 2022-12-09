package com.entities;

public class BadCircle2 extends Circle
{
	public BadCircle2(double x, double y, int r, int A)
	{
		super(x, y, r, A);
		
	}
	
	@Override
	public void turnToward(Circle c)
	{		
		if(toLeftOf(c))  turnLeft(2);
		
		else             turnRight(2);		
	}
	
	
	public void chase(Circle c)
	{
		turnToward(c);
		
		goForward(3);
	}

	}
