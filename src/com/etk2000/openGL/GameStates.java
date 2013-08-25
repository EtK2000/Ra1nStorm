package com.etk2000.openGL;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

public class GameStates {

	/** State **/
	private static enum State {
		INTRO, MAIN_MENU, GAME;
	}
	
	private State state = State.INTRO;
	boolean paused;
	
	/** Time **/
	private static long lastFrame;
	
	private static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	private static long getDelta() {
		long currentTime = getTime();
		long delta = currentTime - lastFrame;
		lastFrame = getTime();
		return delta;
	}
	
	//
	int width, height;
	String title;

	public GameStates(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		
		lastFrame = getTime();

		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create();
		}
		catch (LWJGLException e) {// Display
			e.printStackTrace();
		}

		// OpenGL Init
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);

		while (!Display.isCloseRequested()) {
			checkInput();
			
			// OpenGL Renderer Code
			glClear(GL_COLOR_BUFFER_BIT);
			
			render();

			Display.update();
			Display.sync(60);
			
			Display.setTitle(getDelta() + "; " + title);
		}

		Display.destroy();
	}

	private void render() {
		switch (state) {
			case INTRO:
				glColor3f(1.0f, 0f, 0f);
				glRectf(0, 0, width, height);
				break;
			case MAIN_MENU:
				glColor3f(0f, 0f, 1.0f);
				glRectf(0, 0, width, height);
				break;
			case GAME:
				glColor3f(0f, 1.0f, 0f);
				glRectf(0, 0, width, height);
				break;
		}
	}

	private void checkInput() {
		switch (state) {
			case INTRO:
				if (Keyboard.isKeyDown(Keyboard.KEY_S))
					state = State.MAIN_MENU;
				else if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					Display.destroy();
					System.exit(0);
				}
				break;
			case MAIN_MENU:
				if (Keyboard.isKeyDown(Keyboard.KEY_RETURN))
					state = State.GAME;
				else if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
					state = State.INTRO;
				break;
			case GAME:
				if (Keyboard.isKeyDown(Keyboard.KEY_BACK))
					state = State.MAIN_MENU;
				else if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
					paused = true;
				break;
		}
	}

	public static void main(String[] args) {
		new GameStates(640, 480, "GameStates");
	}
}
