/**
 * 
 */
package hr.fer.zemris.java.hw08.jvdraw.components;

import java.awt.Color;

/**
 * Listener za promjenu boje
 * 
 * @author Ivan
 * 
 */
public interface ColorChangeListener {
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
