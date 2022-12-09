package com.engine;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * The Class InputManager.
 */
public class InputManager extends KeyAdapter implements MouseListener , MouseMotionListener{
	
	/** The up pressed. */
	public static boolean up_pressed = false;
	
	/** The dn pressed. */
	public static boolean dn_pressed = false;
	
	/** The lt pressed. */
	public static boolean lt_pressed = false;
	
	/** The rt pressed. */
	public static boolean rt_pressed = false;
	
	/** The sp pressed. */
	public static boolean sp_pressed = false;
	
	/** The en pressed. */
	public  static boolean en_pressed = false;
	
	/** The w pressed. */
	public static boolean w_pressed = false;
	
	/** The a pressed. */
	public static boolean a_pressed = false;
	
	/** The s pressed. */
	public static boolean s_pressed = false;
	
	/** The d pressed. */
	public static boolean d_pressed = false;
	
	public static boolean m_pressed = false;
		
	public static int mx;
	public static int my;
	
	/**
	 * Key pressed.
	 *
	 * @param e the e
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()) {	
		
		case KeyEvent.VK_UP:
			up_pressed = true;
			break;
		case KeyEvent.VK_DOWN:
			dn_pressed = true;
			break;
		case KeyEvent.VK_LEFT:
			lt_pressed = true;
			break;
		case KeyEvent.VK_RIGHT:
			rt_pressed = true;
			break;
		case KeyEvent.VK_ENTER:
		    en_pressed = true;
		    break;
		case KeyEvent.VK_W:
			w_pressed = true;
			break;
		case KeyEvent.VK_S:
			s_pressed = true;
			break;
		case KeyEvent.VK_A:
			a_pressed = true;
			break;
		case KeyEvent.VK_D:
		    d_pressed = true;
		    break;
		default:
				break;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			
			sp_pressed = true;
		}
	}

	/**
	 * Key released.
	 *
	 * @param e the e
	 */
	@Override
	public void keyReleased(KeyEvent e) {
   
		switch(e.getKeyCode()) {
		
		case KeyEvent.VK_UP:
			up_pressed = false;
			break;
		case KeyEvent.VK_DOWN:
			dn_pressed = false;
			break;
		case KeyEvent.VK_LEFT:
			lt_pressed = false;
			break;
		case KeyEvent.VK_RIGHT:
			rt_pressed = false;
			break;
		case KeyEvent.VK_SPACE:
		    sp_pressed = false;
		    break;
		case KeyEvent.VK_ENTER:
		    en_pressed = false;
		    break;
		case KeyEvent.VK_W:
			w_pressed = false;
			break;
		case KeyEvent.VK_S:
			s_pressed = false;
			break;
		case KeyEvent.VK_A:
			a_pressed = false;
			break;
		case KeyEvent.VK_D:
		    d_pressed = false;
		    break;
		    
			default:
				break;
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		 m_pressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	     m_pressed = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		mx = e.getX();
		my = e.getY();
		
		//System.out.println(mx + " Input Manger" + my);
	}
	
}
