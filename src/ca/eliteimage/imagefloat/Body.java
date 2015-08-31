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

import static ca.eliteimage.imagefloat.Window.ZERO_INSETS;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.net.MalformedURLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;

/**
 *
 * @author Nathan Crause <nathan at crause.name>
 */
class Body extends JPanel {
	
	private final Window window;

	public Body(Window window) {
		super();
		
		OverlayLayout layout = new OverlayLayout(this);
		setLayout(layout);
		
		this.window = window;
		
		addComponents();
	}
	
	private void addComponents() {
		addMove();
		addResize();
		addImage();
	}
	
	private JLabel image;
	
	private void addImage() {
		image = new JLabel("Select an image to load");
		image.setBackground(Color.RED);
		JScrollPane scroll = new JScrollPane(image, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		scroll.setAlignmentX(0.0f);
		scroll.setAlignmentY(0.0f);
		add(scroll);
	}
	
	private JLabel move;
	
	private JLabel resize;
	
	private Point deltaStart;
	
	private void addMove() {
		JPanel panel = new JPanel(new GridBagLayout());
		move = new JLabel(new ImageIcon(getClass().getResource("icons/move.png")));
		
		move.setToolTipText("Move the window");
		
		panel.add(move, new GridBagConstraints(1, 0, 1, 1, 1d, 1d, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, ZERO_INSETS, 0, 0));
		panel.setAlignmentX(0.0f);
		panel.setAlignmentY(0.0f);
		panel.setOpaque(false);
		
		move.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				deltaStart = e.getLocationOnScreen();
			}
		});
		
		move.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int deltaX = e.getLocationOnScreen().x - deltaStart.x;
				int deltaY = e.getLocationOnScreen().y - deltaStart.y;
				Point location = new Point(window.getLocation().x + deltaX, window.getLocation().y + deltaY);
				
				window.setLocation(location);
				
				deltaStart = e.getLocationOnScreen();
			}
		});
		
		add(panel);
		
	}
	
	private void addResize() {
		JPanel panel = new JPanel(new GridBagLayout());
		resize = new JLabel(new ImageIcon(getClass().getResource("icons/resize.png")));
		
		resize.setToolTipText("Resize the window");
		
		panel.add(resize, new GridBagConstraints(1, 1, 1, 1, 1d, 1d, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, ZERO_INSETS, 0, 0));
		panel.setAlignmentX(0.0f);
		panel.setAlignmentY(0.0f);
		panel.setOpaque(false);
		
		resize.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				deltaStart = e.getLocationOnScreen();
			}
		});
		
		resize.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int deltaX = e.getLocationOnScreen().x - deltaStart.x;
				int deltaY = e.getLocationOnScreen().y - deltaStart.y;
				Dimension size = new Dimension(window.getSize().width + deltaX, window.getSize().height + deltaY);
				
				window.setSize(size);
				
				deltaStart = e.getLocationOnScreen();
			}
		});
		
		add(panel);
	}
	
	public void setImage(File file) throws MalformedURLException {
		// wipe out the text (if applicable)
		image.setText("");
		// set the image
		image.setIcon(new ImageIcon(file.toURI().toURL()));
		
		SwingUtilities.invokeLater(() -> {
			window.invalidate();
		});
	}
	
}
