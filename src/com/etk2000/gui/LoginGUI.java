package com.etk2000.gui;

import javax.swing.*;

import com.etk2000.gamebase.Constants;
import com.etk2000.gamebase.StartGame;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class LoginGUI implements ActionListener {
	/** the info used in the gui, then sent to the login **/
	private static String[] privateUserinfo = new String[2];

	// Definition of global values and items that are part of the GUI.
	int onlinePlayersCount = 0;
	int blueScoreAmount = 0;

	static JFrame frame;
	JButton deployButton, offlineButton;
	JPanel buttonPanel, logPanel, scorePanel, titlePanel;
	JTextField userField, passField;
	JTextArea log;

	public JPanel createContentPane() {

		// We create a bottom JPanel to place everything on.
		JPanel totalGUI = new JPanel();
		totalGUI.setLayout(null);

		// Creation of a Panel to contain the title labels
		userField = new JTextField();
		userField.setLayout(null);
		userField.setLocation(10, 5);
		userField.setSize(150, 30);
		if (StartGame.username != null && StartGame.username.length() <= Constants.maxPWLength) {
			Constants.logger.info("passing on \"username\" argument to GUI (" + StartGame.username + ')');
			userField.setText(StartGame.username);
		}
		totalGUI.add(userField);
		userField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (userField.getText().length() >= Constants.maxPWLength && c != KeyEvent.VK_BACK_SPACE) {
					e.consume();// ignore the event
				}
				else if (c == KeyEvent.VK_ENTER) {
					// TODO: move to password box
				}
			}
		});

		passField = new JTextField();
		passField.setLayout(null);
		passField.setLocation(10, 40);
		passField.setSize(150, 30);
		totalGUI.add(passField);
		passField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (passField.getText().length() < Constants.maxPWLength) {
					if (c == KeyEvent.VK_BACK_SPACE && privateUserinfo[1].length() > 0) {
						privateUserinfo[1] = privateUserinfo[1].substring(0, privateUserinfo[1].length() - 1);
					}
					else if (c == KeyEvent.VK_ENTER) {
						tryToLogin();
					}
					else if (c != KeyEvent.VK_BACK_SPACE) {
						privateUserinfo[1] += c;
					}
					String passText = new String();
					for (int i = 0; i < privateUserinfo[1].length(); i++) {
						passText += '•';
					}
					passField.setText(passText);
				}
				e.consume();// ignore the event
			}
		});

		deployButton = new JButton("login");
		deployButton.setLayout(null);
		deployButton.setLocation(160, 42);
		deployButton.setSize(100, 25);
		deployButton.addActionListener(this);
		totalGUI.add(deployButton);

		// redLabel.setForeground(Color.red);

		// Creation of a Panel to contain the logger.
		logPanel = new JPanel();
		logPanel.setLayout(null);
		logPanel.setLocation(10, 150);
		logPanel.setSize(500, 500);
		totalGUI.add(logPanel);

		log = new JTextArea();
		log.setLayout(null);
		log.setLocation(10, 0);
		log.setSize(200, 30);
		log.setForeground(Color.red);
		log.setText("");
		// blueScore.setHorizontalAlignment(0);// center the text
		log.setFocusable(false);
		logPanel.add(log);

		offlineButton = new JButton("play offline");
		offlineButton.setLayout(null);
		offlineButton.setLocation(220, 0);
		offlineButton.setSize(100, 25);
		offlineButton.addActionListener(this);
		offlineButton.setVisible(false);
		logPanel.add(offlineButton);

		totalGUI.setOpaque(true);
		return totalGUI;
	}

	// This catches any events with an ActionListener attached.
	// Using an if statement, we can determine which button was pressed
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == deployButton) {
			tryToLogin();
		}
		else if(e.getSource() == offlineButton) {
			tryToLogin(true);
		}
	}

	public static void createAndShowGUI() {
		/** change the look of the GUI **/
		privateUserinfo[1] = new String();
		// JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("Ra1nServer");

		// Create and set up the content pane.
		LoginGUI demo = new LoginGUI();
		frame.setContentPane(demo.createContentPane());

		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// DISPOSE_ON_CLOSE will terminate your application when the last JFrame is closed.
		// EXIT_ON_CLOSE will terminate your application as soon as that JFrame is closed.
		// HIDE_ON_CLOSE (default) will not terminate your application even if all JFrames are
		// hidden.
		frame.setSize(500, 400);// 280, 190
		frame.setVisible(true);
	}

	private void tryToLogin(boolean... sentFromOffline) {
		/** the "try - catch" statement is JIC **/
		boolean offline = false;
		if (sentFromOffline.length > 0) {
			offline = sentFromOffline[0];
		}
		try {
			privateUserinfo[0] = userField.getText();
			if (privateUserinfo[0] == null || privateUserinfo[0].length() == 0) {
				log.setText("please enter your username!");
			}
			else if (privateUserinfo[1] == null || privateUserinfo[1].length() == 0) {
				log.setText("please enter your password!");
			}
			else if (privateUserinfo[0].length() < Constants.minUNLength) {
				log.setText("username too short!");
			}
			else if (privateUserinfo[1].length() < Constants.minPWLength) {
				log.setText("password too short!");
			}
			else {
				String salt = StartGame.authenticate(privateUserinfo[0], privateUserinfo[1], false);
				if (salt.contains(":")) {
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					StartGame.username = privateUserinfo[0];
					StartGame.password = privateUserinfo[1];
				}
				else if (salt.equals("offline")) {
					if (offline) {
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						StartGame.username = privateUserinfo[0];
						StartGame.password = privateUserinfo[1];
					}
					else {
						log.setText("Would You Like To Play Offline?");
						offlineButton.setVisible(true);
					}
				}
				else if (salt.equals("incorrect_info")) {
					log.setText("Invalid Info!");
				}
			}
		}
		catch (Exception e) {
			// "please enter your username and/or password!"
			log.setText("error!" + e.getMessage());
			e.printStackTrace();
		}
	}
}