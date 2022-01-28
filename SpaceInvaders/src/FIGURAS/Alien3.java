package FIGURAS;

import JUEGO.JuegoSpaceInvaders;


/**
 * Clase que hereda de Alien, con el objetivo de generar un comportamiento de alien determinado y poder instanciarlo
 * @author Jonathan Garcia Ruiz
 *
 */
public class Alien3 extends Alien {


	/**
	 * Constructor de la clase Alien3 necesario para instanciar objetos de esta clase
	 * 
	 * @param juego
	 * @param ref
	 * @param x
	 * @param y
	 */
	public Alien3(JuegoSpaceInvaders juego, String ref, int x, int y) {

		super(juego, ref, x, y);

		hits=5;
		points=150;
		Dy = 0;
		Dx = 500;

	}

}
