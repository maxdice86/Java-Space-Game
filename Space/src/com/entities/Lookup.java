package com.entities;


// TODO: Auto-generated Javadoc
/**
 * The Class Lookup.
 */
public class Lookup
{
	
	/** The Constant cos. */
	public static final double[] cos = generateCos();
	
	/** The Constant sin. */
	public static final double[] sin = generateSin();
	
	public static final double[] ScaleUp = generateScaleUp();

	public static final double[] ScaleDn = generateScaleDn();
	
	
	
	
	private static double[] generateScaleUp()
	{
		double[] scale = new double[4];
		
		int j = 0;
		
		for(double i = 1.25; i < scale.length; i += 0.25) {
			
			
			scale[j] = i;
			
			j++;
			
			if (j == 4) break;
		}
		
		return scale;
	}
	
	
	
	private static double[] generateScaleDn()
	{
		double[] scale = new double[3];
		
		int j = 0;
		
		for(double i = 0.25; i < scale.length; i += 0.25) {
			
			
			scale[j] = i;
			
			j++;
			
			if (j == 3) break;
		}
		
		return scale;
	}
	
	
	
	
	private static double[] generateCos()
	{
		double[] cos = new double[360];
		
		for(int i = 0; i < cos.length; i++)
			
			//cos[i] = Math.cos(i * Math.PI / 180);
		    cos[i] = Math.cos(Math.toRadians(i));
		
		
		
		return cos;
	}
	

	private static double[] generateSin()
	{
		double[] sin = new double[360];
		
		for(int i = 0; i < sin.length; i++)
			
			//sin[i] = Math.sin(i * Math.PI / 180);
			sin[i] = Math.sin(Math.toRadians(i));
		
		return sin;
	}
	
}