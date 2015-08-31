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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Nathan Crause <nathan at crause.name>
 */
public class ToolBar extends JToolBar {
	
	private final Window window;

	public ToolBar(Window window) {
		super(JToolBar.VERTICAL);
		
		this.window = window;
		
		addComponents();
		
		// listen for orientation changes, and update the slider to match
		addPropertyChangeListener("orientation", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				opacitySlider.setOrientation(getOrientation() == VERTICAL
						? JSlider.VERTICAL
						: JSlider.HORIZONTAL);
			}
		});
	}
	
	private void addComponents() {
		add(createOpenFile());
		addSeparator();
		add(createOpacitySlider());
		addSeparator();
		add(createMinimizeButton());
		add(createStopButton());
	}
	
	private JButton createOpenFile() {
		JButton button = new JButton(new ImageIcon(getClass().getResource("icons/open-file.png")));
		
		button.setToolTipText("Load image");
		
		button.addActionListener((ActionEvent e) -> {
			JFileChooser chooser = new JFileChooser(window.prefs.get("directory", System.getProperty("user.home")));
			
			chooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif"));
			chooser.setMultiSelectionEnabled(false);
			
			int returnVal = chooser.showOpenDialog(window);
			
			if (returnVal == JFileChooser.CANCEL_OPTION) {
				return;
			}
			
			window.prefs.put("directory", chooser.getSelectedFile().getParentFile().getAbsolutePath());
			
			window.setImage(chooser.getSelectedFile());
		});
		
		return button;
	}
	
	private JSlider opacitySlider;
	
	private JSlider createOpacitySlider() {
		opacitySlider = new JSlider(JSlider.VERTICAL, 25, 100, 100);
		
		opacitySlider.setMajorTickSpacing(10);
		opacitySlider.setMinorTickSpacing(5);
		opacitySlider.setPaintTicks(true);
		opacitySlider.setMaximumSize(new Dimension(20, 20));
		opacitySlider.setSnapToTicks(true);
		opacitySlider.setToolTipText("Adjust opacity");
		
		opacitySlider.addChangeListener((ChangeEvent e) -> {
			window.setOpacity((float)opacitySlider.getValue() / 100.0f);
		});
		
		return opacitySlider;
	}
	
	private JButton createMinimizeButton() {
		JButton button = new JButton(new ImageIcon(getClass().getResource("icons/minimize.png")));
		
		button.setToolTipText("Minimize the window");
		
		button.addActionListener((ActionEvent e) -> {
			window.setExtendedState(JFrame.ICONIFIED);
		});
		
		return button;
	}

	private JButton createStopButton() {
		JButton button = new JButton(new ImageIcon(getClass().getResource("icons/cancel.png")));
		
		button.setToolTipText("Close the application");
		
		button.addActionListener((ActionEvent e) -> {
			window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		});
		
		return button;
	}
	
}
