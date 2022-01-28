package FIGURAS;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import JUEGO.JuegoSpaceInvaders;


/**
 * Clase que hereda de figura, con el objetivo de generar un comportamiento determinado para la nave 
 * @author Jonathan Garcia Ruiz
 *
 */
public class Nave extends Figura{


	// CONSTANTES
	private static final int HITSS=3;
	
	
	//ATRIBUTOS
	private JuegoSpaceInvaders juego;
	private URL url;
	private Image imagenNave;
	
	private int hits;
	
	
	
	/**
	 * Constructor de la clase Nave necesario para instanciar objetos de esta clase
	 * 
	 * @param juego
	 * @param ref
	 * @param x
	 * @param y
	 */
	public Nave(JuegoSpaceInvaders juego, String ref, int x, int y) {
		
		super(ref, x, y);
		
		this.juego=juego;

		hits = HITSS;
		
		
		url = this.getClass().getClassLoader().getResource(ref);
		try {
			imagenNave = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
	


	/**
	 * Metodo destinado a pintar la nave en el canvas
	 */
	@Override
	public void DrawImage(Graphics g) {

		g.drawImage(imagenNave, getX(), getY(), null);
		
	}

	
	/**
	 * Metodo encargado de hacer la logica cuando 2 figuras colisionan
	 */
	@Override
	public void collidedWith(Figura otro) {

		if (otro instanceof Alien) {

			hits--;
			juego.removeFigura(otro);

		}

	}

	
	//GETTERS Y SETTERS
	public int getHits() {
		return hits;
	}

	
	public void setHits(int hits) {
		this.hits = hits;
	}
	
	public static int getHitss() {
		return HITSS;
	}
	
	
}
