package FIGURAS;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import JUEGO.JuegoSpaceInvaders;


/**
 * Clase que hereda de Figura, destinada a controlar el comportamiento e instanciacion de los bloques en el canvas
 * @author Jonathan Garcia Ruiz
 *
 */
public class Bloque extends Figura{

	
	//CONSTANTES
	private static final int HITSS=8;
	private static final int DX_MOVE_SPEED=0;
	private static final int DY_MOVE_SPEED=0;
	
	
	//ATRIBUTOS
	private JuegoSpaceInvaders juego;
	private URL url;
	private Image imagenBloque;
	
	private int hits;
	
	
	
	/**
	 * Constructor de la clase Bloque la cual nos permite instanciar un objeto de esta clase con cierto comportamiento
	 * @param juego
	 * @param ref
	 * @param x
	 * @param y
	 */
	public Bloque(JuegoSpaceInvaders juego, String ref, int x, int y) {
		
		super(ref, x, y);
		
		this.juego=juego;
		
		hits = HITSS;
		
		Dx = DX_MOVE_SPEED;
		Dy = DY_MOVE_SPEED;
		
		url = this.getClass().getClassLoader().getResource(ref);
		//try {
			//imagenBloque = ImageIO.read(url);
		//} catch (IOException e) {
			//e.printStackTrace();
		//}
		
	}
	
	
	/**
	 * Metodo destino a pintar el alien en el canvas
	 */
	@Override
	public void DrawImage(Graphics g) {

		g.drawImage(imagenBloque, getX(), getY(), null);
		
	}


	/**
	 * Metodo encargado de hacer la logica cuando 2 figuras colisionan
	 */
	@Override
	public void collidedWith(Figura otro) {

		if (otro instanceof Disparo) {
			
			hits--;
			juego.removeFigura(otro);
			
			if(hits==0) {
				
				juego.removeFigura(this);
				
			}
			

		}
	}
		
		
	

}
