package com.etk2000.openGL;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class HelloWorld {

	int width, height;
	String title;
	private Texture floor_wood;

	public HelloWorld(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;

		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create();
		}
		catch (LWJGLException e) {// Display
			e.printStackTrace();
		}

		floor_wood = loadTexture("floor_wood");

		// OpenGL Init
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);

		while (!Display.isCloseRequested()) {

			// OpenGL Renderer Code
			glClear(GL_COLOR_BUFFER_BIT);

			floor_wood.bind();

			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 0);// Texture: Upper-Left
				glVertex2i(200, 200);// Upper-Left
				glTexCoord2f(1, 0);// Texture: Upper-Right
				glVertex2i(450, 200);// Upper-Right
				glTexCoord2f(1, 1);// Texture: Lower-Right
				glVertex2i(450, 450);// Lower-Right
				glTexCoord2f(0, 1);// Texture: Lower-Left
				glVertex2i(200, 450);// Lower-Left
			}
			glEnd();

			glBegin(GL_LINES);
			{
				glVertex2i(100, 100);// Line starting point
				glVertex2i(200, 200);// Line ending point
			}
			glEnd();

			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				Display.destroy();
				System.exit(0);
			}
			Display.setTitle(title + "; X: " + Mouse.getX() + ", Y: " + getMouseY() + "; dx: " + Mouse.getDX()
					+ ", dy: " + Mouse.getDY());

			Display.update();
			Display.sync(60);
		}

		Display.destroy();
	}

	public static void main(String[] args) {
		new HelloWorld(640, 480, "Hello From LWJGL");
	}

	/** Image Must Be PNG **/
	private Texture loadTexture(String imageName) {
		try {
			return TextureLoader.getTexture("PNG", new FileInputStream(new File("res/" + imageName + ".png")));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getMouseY() {
		return height - Mouse.getY() - 1;
	}
}
