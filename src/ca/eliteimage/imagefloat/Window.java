/*
 * Copyright (C) 2015 Nathan Crause <nathan at crause.name>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.eliteimage.imagefloat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The primary window
 *
 * @author Nathan Crause <nathan at crause.name>
 */
public class Window extends JFrame {
	
	public static String PREFS_NODE = "ca.eliteimage.imagefloat".intern();
	
	public static int DEFAULT_WIDTH = 640;
	
	public static int DEFAULT_HEIGHT = 480;
	
	public static int DEFAULT_X = 0;
	
	public static int DEFAULT_Y = 0;
	
	public static Insets ZERO_INSETS = new Insets(0, 0, 0, 0);
	
	public static Dimension PIXEL_SIZE = new Dimension(16, 16);
	
	Preferences prefs;

	public Window() throws HeadlessException, BackingStoreException {
		super("Image Float");
		
		prefs = Preferences.userRoot().node(PREFS_NODE);
		
		// initialize the basics
		initBase();
//		// initialize the content
		initToolbar();
		initBody();
	}

	private void initBase() {
		setUndecorated(true);
		setLayout(new BorderLayout());
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// check to see if we have stuff stored in preferences
		setLocationAndSize();
		
		addWindowListener(new WindowListener() {
			@Override public void windowOpened(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Window closing ...");
				prefs.putInt("width", getSize().width);
				prefs.putInt("height", getSize().height);
				prefs.putInt("x", getLocation().x);
				prefs.putInt("y", getLocation().y);
			}

			@Override public void windowClosed(WindowEvent e) {}
			@Override public void windowIconified(WindowEvent e) {}
			@Override public void windowDeiconified(WindowEvent e) {}
			@Override public void windowActivated(WindowEvent e) {}
			@Override public void windowDeactivated(WindowEvent e) {}
		});
	}
	
	private void setLocationAndSize() {
		int width = prefs.getInt("width", DEFAULT_WIDTH);
		int height = prefs.getInt("height", DEFAULT_HEIGHT);
		int x = prefs.getInt("x", DEFAULT_X);
		int y = prefs.getInt("y", DEFAULT_Y);

		setSize(width, height);
		setLocation(x, y);
	}
	
	private ToolBar toolbar;

	private void initToolbar() {
		toolbar = new ToolBar(this);
		
		add(toolbar, BorderLayout.WEST);
	}
	
	private Body body;

	private void initBody() {
		body = new Body(this);
		
		add(body, BorderLayout.CENTER);
	}
	
	public void setImage(File file) {
		try {
			body.setImage(file);
		} 
		catch (IOException ex) {
			Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this, ex);
		}
	}
	
}
