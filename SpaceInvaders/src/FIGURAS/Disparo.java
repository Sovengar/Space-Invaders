package FIGURAS;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import JUEGO.JuegoSpaceInvaders;


/**
 * Clase abstracta que hereda de figura, con el objetivo de generar un comportamiento determinado para los disparos y hacer polimorfismo
 * de una manera mas concreta
 * @author Jonathan Garcia Ruiz
 *
 */
public abstract class Disparo extends Figura{

	
	//ATRIBUTOS
	protected JuegoSpaceInvaders juego;
	protected URL url;
	protected Image imagenDisparo;
	

	/**
	 * Constructor de la clase Disparo necesario para que los hijos incorporen las caracteristicas del padre
	 * @param juego
	 * @param ref
	 * @param x
	 * @param y
	 */
	public Disparo(JuegoSpaceInvaders juego, String ref, int x, int y) {
		
		super(ref, x, y);
		
		this.juego=juego;
		
		
		url = this.getClass().getClassLoader().getResource(ref);
		try {
			imagenDisparo = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	/**
	 * Metodo destinado a pintar el disparo en el canvas
	 */
	@Override
	public void DrawImage(Graphics g) {
		
		g.drawImage(imagenDisparo, getX(), getY(), null);
		
	}
	

	/**
	 * Metodo encargado de hacer la logica cuando 2 figuras colisionan
	 */
	@Override
	public void collidedWith(Figura otro) {
		
	}
	
	
	
}
