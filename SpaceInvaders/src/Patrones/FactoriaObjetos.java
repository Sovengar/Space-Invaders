package Patrones;

import FIGURAS.Alien;
import FIGURAS.Alien1;
import FIGURAS.Alien2;
import FIGURAS.Alien3;
import FIGURAS.Bloque;
import FIGURAS.Disparo;
import FIGURAS.DisparoAlien;
import FIGURAS.DisparoNave;
import FIGURAS.Figura;
import FIGURAS.Nave;
import JUEGO.JuegoSpaceInvaders;

/**
 * Clase abstracta en la cual hemos delegado la potestad para que se encargue mediante sus metodos de crear los objetos de las clases,
 * bajo estas caracteristicas podemos decir que sigue el patron factoria, es por esto que se encuentra en el paquete Patrones
 * @author Jonathan Garcia Ruiz
 *
 */
public abstract class FactoriaObjetos {

	
	//CONSTANTES
	private static final String refNave="res/ship.gif";
	private static final String refDisparoNave="res/shot.gif";
	private static final String refDisparoAlien="res/shotBad.gif";
	
	private static final String refAlien="res/alien1.gif";
	private static final String refAlien2="res/alien2.gif";
	private static final String refAlien3="res/alien3.gif";
	
	private static final String refBloque="res/block.gif";
	
	
	/**
	 * Metodo que crea un objeto de la clase nave
	 * @param juego
	 * @param x
	 * @param y
	 * @return
	 */
	public static Figura buildNave(JuegoSpaceInvaders juego, int x, int y) {
		
		return new Nave(juego,refNave,x,y);
	
	}
	
	
	/**
	 * Metodo que crea un objeto de la clase Alien(Alien1,Alien2,..)
	 * @param juego
	 * @param x
	 * @param y
	 * @return
	 */
	public static Alien buildAlien(JuegoSpaceInvaders juego, int x, int y, int tipo) {

		Alien alien = null;
		// Alien alien=new Alien2(juego,refAlien2,x,y);

		if (tipo==1) {

			alien = new Alien1(juego, refAlien, x, y);

		} else if (tipo==2) {

			alien = new Alien2(juego, refAlien2, x, y);

		} else if (tipo==3) {

			alien = new Alien3(juego, refAlien3, x, y);
		}

		return alien;

	}
	
	
	/**
	 * Metodo que crea un objeto de la clase Disparo(DisparoNave, DisparoAlien..)
	 * @param juego
	 * @param x
	 * @param y
	 * @return
	 */
	public static Disparo buildDisparo(JuegoSpaceInvaders juego, int x, int y, Figura  f) {
		
		Disparo disparo;
		
		if(f instanceof Nave) {
			
			disparo = new DisparoNave(juego, refDisparoNave, x, y);
			
		} else
			
		disparo = new DisparoAlien(juego, refDisparoAlien, x, y);

		return disparo;
	}
	
	
	/**
	 * Metodo que crea un objeto de la clase bloque
	 * @param juego
	 * @param x
	 * @param y
	 * @return
	 */
	public static Bloque buildBloque(JuegoSpaceInvaders juego, int x, int y) {
		
		Bloque bloque;
		
		bloque = new Bloque(juego, refBloque, x, y);

		return bloque;
	}
	
	
}
