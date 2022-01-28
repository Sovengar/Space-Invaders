package FIGURAS;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class Figura {

	//ATRIBUTOS
	protected int x;
	protected int y;
	
	protected int Dx;
	protected int Dy;
	
	protected Image image;
	protected String ref;
	
	protected Rectangle me = new Rectangle();
	protected Rectangle him = new Rectangle();

	

	/**
	 * Constructor de la clase Figura necesario para que los hijos incorporen las caracteristicas del padre
	 * @param ref
	 * @param x
	 * @param y
	 */
	public Figura(String ref, int x, int y ){
		
		this.x=x;
		this.y=y;
		this.ref=ref;
		
	}
	
	
	/**
	 * Metodo booleano que comprueba si las figuras colisionan
	 * 
	 * @param otro
	 * @return
	 */
	public boolean collidesWith(Figura otro) {

		me.setBounds(x, y, 20, 20);
		him.setBounds(otro.x, otro.y, 20, 20);
		// him.setBounds(otro.x, otro.y, otro.image.getWidth(null),otro.image.getHeight(null));
		return me.intersects(him);

	}
	
	
	/**
	 * Metodo abstracto para que todas las clases hijas tengan que implementarlo
	 * @param otro
	 */
	public abstract void collidedWith(Figura otro);
	

	/**
	 * Metodo destinado a mover la figura en el canvas
	 */
	public void move(long velocidad) {
		
		x+= (velocidad * Dx) / 10000;
		y+= (velocidad * Dy) / 10000;
		
	}
	
	
	/**
	 * Metodo destinado a pintar la figura en el canvas
	 */
	public void DrawImage(Graphics g) {

		g.drawImage(image, x, y, null);
		
	}
	
	
	/**
	 * Metodo que sobreescribiremos en las clases hijas
	 */
	public void doLogic() {
		
	}
	
	
	
	
	//GETTERS Y SETTERS
	
	public void setX(int x) {
		this.x = x;
	}

	
	public void setY(int y) {
		this.y = y;
	}

	
	public int getY() {
		return y;
	}

	
	public int getX() {
		return x;
	}
	
	
	public int getDx() {
		return Dx;
	}


	public void setDx(int dx) {
		Dx = dx;
	}


	public int getDy() {
		return Dy;
	}


	public void setDy(int dy) {
		Dy = dy;
	}


	public String getRef() {
		return ref;
	}


	public void setRef(String ref) {
		this.ref = ref;
	}
	
	
	public Image getImage() {

		return image;
	}
	
	
	public void setImage(Image image) {

		this.image = image;
	}
	
	
	
}
