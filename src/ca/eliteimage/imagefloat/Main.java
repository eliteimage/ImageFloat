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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Desktop app entry point.
 *
 * @author Nathan Crause <nathan at crause.name>
 */
public class Main {
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			
			Window window = new Window();
			
			window.addWindowListener(new WindowListener() {
				@Override public void windowOpened(WindowEvent e) {}
				@Override public void windowClosing(WindowEvent e) {}

				@Override
				public void windowClosed(WindowEvent e) {
					System.out.println("Window closed!");
					System.exit(0);
				}

				@Override public void windowIconified(WindowEvent e) {}
				@Override public void windowDeiconified(WindowEvent e) {}
				@Override public void windowActivated(WindowEvent e) {}
				@Override public void windowDeactivated(WindowEvent e) {}
			});
			
			window.setVisible(true);
		}
		catch (Throwable thrown) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, thrown);
			
			thrown.printStackTrace();
			System.exit(1);
		}
	}
	
}

