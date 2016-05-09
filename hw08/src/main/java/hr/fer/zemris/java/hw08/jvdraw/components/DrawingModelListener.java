/**
 * 
 */
package hr.fer.zemris.java.hw08.jvdraw.components;

/**
 * @author Ivan
 * 
 */
public interface DrawingModelListener {

	public void objectsAdded(DrawingModel source, int index0, int index1);

	public void objectsRemoved(DrawingModel source, int index0, int index1);

	public void objectsChanged(DrawingModel source, int index0, int index1);
}
