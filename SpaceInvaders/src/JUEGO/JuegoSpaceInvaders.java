package JUEGO;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import FIGURAS.DisparoNave;
import FIGURAS.Figura;
import FIGURAS.Nave;
import Patrones.FactoriaObjetos;

@SuppressWarnings("serial")
/**
 * Clase que hereda de Canvas la cual nos permite generar una ventana con el juego y su correspondiente logica y funcionamiento
 * @author Jonathan Garcia Ruiz
 *
 */
public class JuegoSpaceInvaders extends Canvas {

	
	/**
	 * Metodo main donde iniciaremos nuestra aplicacion mediante instanciacion y llamada de metodos
	 * @param args
	 */
	public static void main(String[] args) {

		JuegoSpaceInvaders juego = new JuegoSpaceInvaders();

		juego.gameLoop();

	}

	
	// CONSTANTES
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	private static final int ALIEN_INIT_X = 150;
	private static final int ALIEN_INIT_Y = 5;

	private static final int WIDTH_SPACE_ALIENS = 40;
	private static final int HEIGHT_SPACE_ALIENS = 30;

	private static final int START_X = WIDTH / 2;
	private static final int START_Y = HEIGHT - 50;

	private static final int FIRING_INTERVAL = 500;
	private static final int RESPAWN_INTERVAL = 4000;
	
	private static final int LIVESS = 3;

	
	// ATRIBUTOS
	private JFrame container;
	private JPanel panel;
	private BufferStrategy strategy;
	private Graphics2D g;
	private Nave nave;
	private DisparoNave dn;

	private ArrayList<Figura> figuras = new ArrayList<Figura>();
	private ArrayList<Figura> removeList = new ArrayList<Figura>();

	private long lastShot;
	private long lastRespawn;
	private int score;
	private int alienCount;
	private int lives;

	private boolean leftPressed;
	private boolean rightPressed;
	private boolean firePressed;
	private boolean isLogicNeeded;
	private boolean waitingForKeyPressed;

	
	
	/**
	 * Constructor sin parametros que nos sirve para dar un estado inicial al crear un objeto de esta clase
	 */
	public JuegoSpaceInvaders() {

		lastShot = 0;
		lastRespawn = 0;
		lives = LIVESS;
		leftPressed = false;
		rightPressed = false;
		firePressed = false;
		isLogicNeeded = false;
		waitingForKeyPressed = false;

		initWindow();

		generateFiguras();

	}

	
	/**
	 * Creamos una ventana, creamos un panel obteniendo el panel de la ventana.
	 * Ajustamos los valores y añadimos el canvas con panel.add Ajustamos otros
	 * valores como permitir el repintado, prohibir el redimensionado, hacerlo
	 * visible y centrarlo. Añadimos un detector de eventos, en concreto el evento
	 * de cerrar la ventana para terminar la consola Obtenemos el foco y generamos
	 * una estrategia la cual nos genera 2 paneles que se van intercambiando para
	 * mejorar el rendimiento
	 */
	public void initWindow() {

		container = new JFrame("Poo 2017-2018. Space Invaders");

		/*
		 * Image background;
		 * 
		 * if (background != null) {
		 * 
		 * url = this.getClass().getClassLoader().getResource("res/fondo.jpg"); try {
		 * background = ImageIO.read(url);
		 * 
		 * g.drawImage(background, 0, 0, getWidth(), getHeight(), this); } catch
		 * (IOException e) { e.printStackTrace(); } }
		 */

		panel = (JPanel) container.getContentPane();

		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setLayout(null);

		setBounds(0, 0, WIDTH, HEIGHT);
		panel.add(this);

		setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		container.setLocationRelativeTo(null);

		container.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		addKeyListener(new KeyInputHandler());

		requestFocus();

		createBufferStrategy(2);
		strategy = getBufferStrategy();

	}

	
	/**
	 * Metodo que cumple la funcion de motor de juego, donde los metodos del juego se utilizan para crear el juego
	 */
	public void gameLoop() {


		while (waitingForKeyPressed==false) {
		
			g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			drawFiguras();
			
			drawMarcador(g);
			g.drawString("PRESS LEFT OR RIGHT ARROW TO START", (WIDTH/2)-300, 300);
			
			keyboard();

			g.dispose();
			strategy.show();

			fpsOptimizer();
			
		}
		
		
		while (isLooser() == false && isWinner() == false) {

			g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			drawMarcador(g);

			game();
			
			
			if(lives==2) {
				
				figuras.removeAll(figuras);
				
				generateFiguras();
				
				g.clearRect(0, 0, WIDTH, HEIGHT);
				
				while(lives==2) {
					
					g = (Graphics2D) strategy.getDrawGraphics();
					g.setColor(Color.black);
					g.fillRect(0, 0, WIDTH, HEIGHT);

					respawn();

					drawMarcador(g);
					
					game();
					
				}	
					
			}
			
			
			if (lives == 1) {

				figuras.removeAll(figuras);

				generateFiguras();

				g.clearRect(0, 0, WIDTH, HEIGHT);

				while (lives == 1) {

					g = (Graphics2D) strategy.getDrawGraphics();
					g.setColor(Color.black);
					g.fillRect(0, 0, WIDTH, HEIGHT);

					respawn();

					drawMarcador(g);

					game();

				}

			}
			
			
		}

		while (isLooser() == true) {

			g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			drawMarcador(g);
			g.drawString("GAME OVER", (WIDTH/2)-100, 300);
			
			g.dispose();
			strategy.show();
			fpsOptimizer();

		} 
		
		while (isWinner() == true) {

			g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			drawMarcador(g);
			g.drawString("YOU WIN!", (WIDTH/2)-100, 300);
		
			g.dispose();
			strategy.show();
			fpsOptimizer();

		}

	}

	
	/**
	 * Metodo que contiene la logica del movimiento de la nave
	 */
	public void keyboard() {

		if (leftPressed == true) {

			waitingForKeyPressed=true;
			
			if ((nave.getX() <= 0) == false) {
				nave.setX(nave.getX() - 5);
			}

		} else if (rightPressed == true) {

			waitingForKeyPressed=true;
			
			if ((nave.getX() >= WIDTH) == false) {
				nave.setX(nave.getX() + 5);
			}

		}
		if (firePressed == true) {
			
			waitingForKeyPressed=true;
			
			trytofire();
		}

	}

	
	/**
	 * Metodo que se encarga de intentar crear un disparo si se puede
	 */
	public void trytofire() {

		if (((System.currentTimeMillis() - lastShot) < FIRING_INTERVAL) == false) {

			lastShot = System.currentTimeMillis();
			dn = (DisparoNave) FactoriaObjetos.buildDisparo(this, nave.getX() + 10, nave.getY() - 25, nave);
			figuras.add(dn);

		}

	}

	
	/**
	 * Metodo que reune varios metodos comunes a un ciclo de partida para mejorar la visibilidad del metodo gameLoop
	 */
	public void game() {
		
		keyboard();

		moveFiguras();

		drawFiguras();

		iscolliding();

		logic();

		figuras.removeAll(removeList);
		removeList.clear();

		g.dispose();
		strategy.show();

		fpsOptimizer();
		
	}
	
	
	/**
	 * Metodo que instancia objetos de la clase nave y las clases alien, y los
	 * almacena en el arraylist figuras
	 */
	public void generateFiguras() {

		nave = (Nave) FactoriaObjetos.buildNave(this, START_X, START_Y);
		figuras.add(nave);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 3; j++) {
				figuras.add(FactoriaObjetos.buildAlien(this, ALIEN_INIT_X + WIDTH_SPACE_ALIENS * i,
						ALIEN_INIT_Y + HEIGHT_SPACE_ALIENS * j, 3));

				figuras.add(FactoriaObjetos.buildAlien(this, ALIEN_INIT_X + WIDTH_SPACE_ALIENS * i,
						ALIEN_INIT_Y * 20 + HEIGHT_SPACE_ALIENS * j, 2));

				figuras.add(FactoriaObjetos.buildAlien(this, ALIEN_INIT_X + WIDTH_SPACE_ALIENS * i,
						ALIEN_INIT_Y * 40 + HEIGHT_SPACE_ALIENS * j, 1));

				alienCount++;

			}
		}

		for (int i = 0; i < 8; i++) {

			figuras.add(FactoriaObjetos.buildBloque(this, -500 * i, 150));

		}

	}

	
	/**
	 * Metodo que se encarga de crear cada x periodo de tiempo una fila de aliens de tipo aleatorio
	 */
	public void respawn() {

		int interval = 3;
		int number = (int) (Math.random() * interval) + 1;

		if (((System.currentTimeMillis() - lastRespawn) < RESPAWN_INTERVAL) == false) {

			lastRespawn = System.currentTimeMillis();

			for (int i = 0; i < 8; i++) {

				figuras.add(FactoriaObjetos.buildAlien(this, ALIEN_INIT_X + WIDTH_SPACE_ALIENS * i, ALIEN_INIT_Y + HEIGHT_SPACE_ALIENS, number));

			}

		}
 
	}

	
	/**
	 * Metodo que recorre el arraylist de figuras llamando el metodo move que implementan(si no tiene la de su padre)
	 */
	public void moveFiguras() {

		for (int i = 0; i < figuras.size(); i++) {

			figuras.get(i).move(20);

		}

	}

	/**
	 * Metodo que recorre el arraylist de figuras llamando el metodo drawimage que implementan(si no tiene la de su padre)
	 */
	public void drawFiguras() {

		for (int i = 0; i < figuras.size(); i++) {

			figuras.get(i).DrawImage(g);

		}

	}

	
	/**
	 * Metodo que comprueba la colision de cada figura con todas las figuras y si han colisionado llama al meotodo collidedWith
	 */
	public void iscolliding() {

		for (int i = 0; i < figuras.size(); i++) {
			for (int j = i + 1; j < figuras.size(); j++) {

				if (figuras.get(i).collidesWith(figuras.get(j))) {

					figuras.get(i).collidedWith(figuras.get(j));
					figuras.get(j).collidedWith(figuras.get(i));

				}
			}
		}

	}

	
	/**
	 * Metodo que pinta en el canvas un marcador para saber cuantos puntos tenemos
	 * @param g
	 */
	public void drawMarcador(Graphics g) {

		g.setColor(Color.red);
		Font small = new Font("Helvetica", Font.BOLD, 24);
		g.setFont(small);
		g.drawString("00000000" + score, (-g.getFontMetrics().stringWidth("hola")) / 2, +20);

	}

	
	/**
	 * Metodo que añade la figura pasada por parametro al array removeList
	 * @param figuraa
	 */
	public void removeFigura(Figura figuraa) {
		removeList.add(figuraa);
	}

	
	/**
	 * Metodo que comprueba si hay ocurrido cierto evento que haga que la variable booleana isLogicNeeded sea true, en tal caso,
	 * llamara al metodo doLogic de las figuras que hay en el arraylist figuras
	 */
	public void logic() {

		if (isLogicNeeded == true) {

			for (int i = 0; i < figuras.size(); i++) {

				figuras.get(i).doLogic();
			}

			isLogicNeeded = false;

		}

	}

	
	/**
	 * Metodo booleano que comprueba si es perdedor, restando vidas hasta tener 0 vidas, poniendo a true el valor que devuelve
	 * @return booleano
	 */
	public boolean isLooser() {

		boolean resultado;

		if (nave.getHits() <= 0) {
			
			lives--;
			nave.setHits(3);
			resultado = false;
			
			if(lives<=0) {
				resultado = true;
			}
			

		} else resultado = false;

		return resultado;
	}

	
	/**
	 * Metodo booleano que comprueba si es ganador, comprobando el numero de aliens en pantalla
	 * @return booleano
	 */
	public boolean isWinner() {

		boolean resultado;

		if (alienCount == 0) {
			resultado = true;
		} else
			resultado = false;

		return resultado;
	}

	/**
	 * Explicacion en ingles ya que esto nos lo paso el profesor
	 * 
	 * finally pause for a bit. Note: this should run us at about 100 fps but on
	 * windows this might vary each loop due to a bad implementation of timer
	 */
	public void fpsOptimizer() {
		try {
			Thread.sleep(10);
		} catch (Exception e) {
		}
	}

	
	// CLASE INTERNA
	private class KeyInputHandler extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				firePressed = true;
			}
		}

		/**
		 * Notification from AWT that a key has been released.
		 *
		 * @param e
		 *            The details of the key that was released
		 */
		@Override
		public void keyReleased(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				firePressed = false;
			}
		}

		/**
		 * Notification from AWT that a key has been typed. Note that typing a key means
		 * to both press and then release it.
		 *
		 * @param e
		 *            The details of the key that was typed.
		 */
		@Override
		public void keyTyped(KeyEvent e) {

			// if we hit escape, then quit the game
			if (e.getKeyChar() == 27) {
				System.exit(0);
			}
		}
	}


	
	
	// GETTERS Y SETTERS

	public void setContainer(JFrame container) {
		this.container = container;
	}


	public JPanel getPanel() {
		return panel;
	}


	public void setPanel(JPanel panel) {
		this.panel = panel;
	}


	public BufferStrategy getStrategy() {
		return strategy;
	}


	public void setStrategy(BufferStrategy strategy) {
		this.strategy = strategy;
	}


	public Graphics2D getG() {
		return g;
	}


	public void setG(Graphics2D g) {
		this.g = g;
	}


	public Nave getNave() {
		return nave;
	}


	public void setNave(Nave nave) {
		this.nave = nave;
	}


	public DisparoNave getDn() {
		return dn;
	}


	public void setDn(DisparoNave dn) {
		this.dn = dn;
	}


	public ArrayList<Figura> getFiguras() {
		return figuras;
	}


	public void setFiguras(ArrayList<Figura> figuras) {
		this.figuras = figuras;
	}


	public ArrayList<Figura> getRemoveList() {
		return removeList;
	}


	public void setRemoveList(ArrayList<Figura> removeList) {
		this.removeList = removeList;
	}


	public long getLastShot() {
		return lastShot;
	}


	public void setLastShot(long lastShot) {
		this.lastShot = lastShot;
	}


	public long getLastRespawn() {
		return lastRespawn;
	}


	public void setLastRespawn(long lastRespawn) {
		this.lastRespawn = lastRespawn;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public int getAlienCount() {
		return alienCount;
	}


	public void setAlienCount(int alienCount) {
		this.alienCount = alienCount;
	}


	public int getLives() {
		return lives;
	}


	public void setLives(int lives) {
		this.lives = lives;
	}


	public boolean isLeftPressed() {
		return leftPressed;
	}


	public void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
	}


	public boolean isRightPressed() {
		return rightPressed;
	}


	public void setRightPressed(boolean rightPressed) {
		this.rightPressed = rightPressed;
	}


	public boolean isFirePressed() {
		return firePressed;
	}


	public void setFirePressed(boolean firePressed) {
		this.firePressed = firePressed;
	}


	public boolean isLogicNeeded() {
		return isLogicNeeded;
	}


	public void setLogicNeeded(boolean isLogicNeeded) {
		this.isLogicNeeded = isLogicNeeded;
	}


	public boolean isWaitingForKeyPressed() {
		return waitingForKeyPressed;
	}


	public void setWaitingForKeyPressed(boolean waitingForKeyPressed) {
		this.waitingForKeyPressed = waitingForKeyPressed;
	}


	public static int getWidthh() {
		return WIDTH;
	}


	public static int getHeightt() {
		return HEIGHT;
	}


	public static int getAlienInitX() {
		return ALIEN_INIT_X;
	}


	public static int getAlienInitY() {
		return ALIEN_INIT_Y;
	}


	public static int getWidthSpaceAliens() {
		return WIDTH_SPACE_ALIENS;
	}


	public static int getHeightSpaceAliens() {
		return HEIGHT_SPACE_ALIENS;
	}


	public static int getStartX() {
		return START_X;
	}


	public static int getStartY() {
		return START_Y;
	}


	public static int getFiringInterval() {
		return FIRING_INTERVAL;
	}


	public static int getRespawnInterval() {
		return RESPAWN_INTERVAL;
	}
	
	
	
	
	
	
	
}
