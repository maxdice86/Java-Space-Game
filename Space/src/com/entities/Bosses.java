package com.entities;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.engine.Camera3D;
import com.engine.Engine;
// TODO: Auto-generated Javadoc
/**
 * The Class Bosses.
 */
public class Bosses extends Enemy {
		
	private int firerate ;
	
	private Effects dfx;	
	
	public Bosses (double x, double y, double z, double w, double h, int angle, ID id)
	{
		
		super(x, y, z, w, h, angle, id, "bos");
		
		firerate = 50;
		
		loadAnimation("Boss", "/Enemies/mothership", 1, 2);
		loadAnimation("BossDeath","/Effects/expl_09",32,3 );
		
		dfx = new Effects(x, y, z, 2, 2, angle, id, "dfx");
	}
	

	
	@Override
	public void setHp(double h)
	{
		Engine.gui.setBossLife(h);
		
		if (Engine.gui.bossLife <= 0)
		{
			alive = false;
		}
	}
	
	
	
	public void checkCollisionBoss() 
	{
			for(int i = 0; i < Engine.obj_manager.allobjects.size();i++)
			{
				Sprite player = Engine.obj_manager.allobjects.get(i);
				
				if(player.getId() == ID.Player && player.alive )
					
				{
					turnToward(player);
					
					if (getDis(player) < -10 || getDis(player) > 10 )
							
						 goBackward(2);	
				}
			}
	}

		
	@Override
	public void fire() 
	{
			firerate--;
		
			if (firerate == 0)
			{
				
				Bullets b = new Bullets(px , py, pz, 32 , 32, A, 
						
		    			   3 * - cosA, 3 * - sinA, ID.EBullet, "bos");
				
				Bullets b2 = new Bullets(px , py, pz, 24, 24, A, 
						
		    			   6 *  cosA, 6 * sinA, ID.EBullet, "en2");
				
				Bullets b3 = new Bullets(px , py , pz, 16, 16, A, 
					
		    			   8 * -cosA, 8 * - sinA, ID.EBullet, "en1");
				Bullets b4 = new Bullets(px , py , pz, 16, 16, A, 
						
		    			   10 * cosA, 10 * sinA, ID.EBullet, "bos");
	
		    	   Engine.audiomg.sfx.get("enemyshot").play();
		    	   
		    	   firerate = 30;
		    	   
		    	   Engine.obj_manager.allobjects.add(b);
		    	   Engine.obj_manager.allobjects.add(b2);
		    	   Engine.obj_manager.allobjects.add(b3); 
		    	   Engine.obj_manager.allobjects.add(b4); 
	 	   }
		}
	
//	Bullets b = new Bullets(px , py, 512, 16, 16, A, 
//			
//			   4 * -cosA, 4 * -sinA, ID.EBullet, type);
	
	
	public void zClipBoss() 
	{
		pz -= 20;
 		
		if (pz <= 512) pz = 512;
	}
	
	
	@Override
	public synchronized void update()
	{	
		if (alive && enabled)
		{
			collisionMap.updatePostions(px, py, pz, collisionMap.r ,A);
			dfx.updatePostions(px, py, pz, w , A);
			
			zClipBoss();
			if(pz < 2048)
			{
			
				
			checkCollision();
			fire();
			
			}
				
		}
	}
	
    @Override
	public void checkBounds(){}
     
	
	@Override
	public void render(Graphics2D g)
	{
		
		super.render(g);
    	
    	currentTrans = g.getTransform();

        AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(collisionMap.A), x + sw/2, y + sh/2);
        g.setTransform(rotate);

        if(pz - Camera3D.z > 10) 
         
        {
      
        	if (alive) 
        	{
        		 g.drawImage(animator.get("Boss").getCurrentImage(),x,y,sw,sh,null);
            }
        	else if(!animator.get("BossDeath").hasPlayed())
        	{
        		g.drawImage(animator.get("BossDeath").getCurrentImage(),x,y,sw,sh,null);
        	}
		    else 
		    {
		    
		    Engine.obj_manager.bossinGame = false;
		    Engine.audiomg.sfx.get("incoming").stop();
			Engine.audiomg.sfx.get("bgm1").setLoop();
			Engine.obj_manager.allobjects.remove(this);
			Engine.obj_manager.enemycount -= 1;
			}
        	
        	if (Engine.gui.bossLife < 320 && alive) dfx.render(g);

       }
        g.setTransform(currentTrans);
}}
