package FIGURAS;

import JUEGO.JuegoSpaceInvaders;


/**
 * Clase que hereda de Disparo, con el objetivo de generar un comportamiento de disparo determinado y poder instanciarlo
 * @author Jonathan Garcia Ruiz
 *
 */
public class DisparoAlien extends Disparo {


	/**
	 * Constructor de la clase DisparoAlien necesario para instanciar objetos de esta clase
	 * 
	 * @param juego
	 * @param ref
	 * @param x
	 * @param y
	 */
	public DisparoAlien(JuegoSpaceInvaders juego, String ref, int x, int y) {

		super(juego, ref, x, y);
		
		Dx= 0;
		Dy = 1000;

	}
	
	
	/**
	 * Metodo destinado a mover el disparo en el canvas
	 */
	@Override
	public void move(long velocidad) {

		super.move(velocidad);
		
		if (y < -1000) {
			juego.removeFigura(this);
		}
		
	}
	
	
}
