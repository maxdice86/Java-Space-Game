package com.entities;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.engine.Engine;

// TODO: Auto-generated Javadoc
/**
 * The Class PowerUps.
 */
public class PowerUps extends Sprite {
	
	
	 public PowerUps(double x, double y, double z, double w, double h, int angle, ID id) {
		
		 super(x, y, z, w, h, angle, id);
		
		loadAnimation("power", "/Effects/p_Sprite", 8, 3);
		loadAnimation("health","/Effects/galaxy",17,4);
	}
	 
	 public void remove() {
			
			
			if ( x <  -1980 || x >  1980 )
			{
				
				Engine.obj_manager.allobjects.remove(this);
				Engine.obj_manager.powerups -= 1;
			} 
		
			
			if ( y > 1980 || y < -1980)
			{
				
				Engine.obj_manager.allobjects.remove(this);
				Engine.obj_manager.powerups -= 1;
			} 
				
			
		}
	 
	@Override
	public void update()
	{
		if (enabled)
		{
			collisionMap.updatePostions(px, py, pz, collisionMap.r ,A);
			zClip();
			findPlayer();
		}
		
		remove();
	}
	
	public void zClip()
	{
 		pz -= 10;
 		
		if (pz <= 512) pz = 512;
	}
	
	public void findPlayer() 
	{
		for(int i = 0; i < Engine.obj_manager.allobjects.size();i++)
		{
			Sprite player = Engine.obj_manager.allobjects.get(i);
			
			if(player.getId() == ID.Player && player.alive )
				
			{ // Turn to player direction //
				
				//turnToward(player);
				
				goBackward(1);
								
				break;
			}	
	}

}

	@Override
	public void render(Graphics2D g) {

        cal3D();
		
        currentTrans = g.getTransform();
    	
        AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(A), px + sw/2, y + sh/2);
        g.setTransform(rotate);
        

		if (this.id == ID.PowerUP)
		{
			g.drawImage(animator.get("power").getCurrentImage(),x, y,sw,sh,null);
		}
		else  { 
			
			g.drawImage(animator.get("health").getCurrentImage(),x, y,sw,sh,null);
			
		       }
		
		g.setTransform(currentTrans);
	}

}
