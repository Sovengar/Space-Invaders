package FIGURAS;

import JUEGO.JuegoSpaceInvaders;


/**
 * Clase que hereda de Alien, con el objetivo de generar un comportamiento de alien determinado y poder instanciarlo
 * @author Jonathan Garcia Ruiz
 *
 */
public class Alien2 extends Alien {
	
	
	/**
	 * Constructor de la clase Alien2 necesario para instanciar objetos de esta clase
	 * 
	 * @param juego
	 * @param ref
	 * @param x
	 * @param y
	 */
	public Alien2(JuegoSpaceInvaders juego, String ref, int x, int y) {

		super(juego, ref, x, y);

		hits=3;
		points=75;
		Dy = 0;
		Dx = 500;

	}

}
