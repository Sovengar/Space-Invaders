package FIGURAS;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import JUEGO.JuegoSpaceInvaders;

/**
 * Clase abstracta que hereda de figura, con el objetivo de generar un comportamiento determinado para los aliens y hacer polimorfismo
 * de una manera mas concreta
 * @author Jonathan Garcia Ruiz
 *
 */
public abstract class Alien extends Figura{

	
	//ATRIBUTOS
	protected JuegoSpaceInvaders juego;
	protected URL url;
	protected Image imagenAlien;
	
	protected int hits; 	
	protected int points;
	
	
	/**
	 * Constructor de la clase Alien necesario para que los hijos incorporen las caracteristicas del padre
	 * @param juego
	 * @param ref
	 * @param x
	 * @param y
	 */
	public Alien(JuegoSpaceInvaders juego, String ref, int x, int y) {
		
		super(ref, x, y);
		
		this.juego=juego;
		
		url = this.getClass().getClassLoader().getResource(ref);
		try {
			imagenAlien = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	/**
	 * Metodo destinado a pintar el alien en el canvas
	 */
	@Override
	public void DrawImage(Graphics g) {

		g.drawImage(imagenAlien, getX(), getY(), null);	
		
	}
	
	/**
	 * Metodo destinado a mover el alien en el canvas
	 */
	@Override
	public void move(long velocidad) {

		super.move(velocidad);
		
		if(x>=JuegoSpaceInvaders.WIDTH) {
			juego.setLogicNeeded(true);
		}
		
		if(x<=0) {
			juego.setLogicNeeded(true);
		}
		
	}

	/**
	 * Metodo encargado de cambiar el comportamiento del alien
	 */
	@Override
	public void doLogic() {
		
		Dx=-Dx;
		y+=30;

	}
	
	
	/**
	 * Metodo encargado de hacer la logica cuando 2 figuras colisionan
	 */
	@Override
	public void collidedWith(Figura otro) {
		
		if (otro instanceof DisparoNave) {
			
			hits--;
			juego.removeFigura(otro);
			
			if(hits==0) {
				
				juego.removeFigura(this);
				juego.setScore(juego.getScore()+points);
				
			}
			

		}
	}
	

	
	
}
