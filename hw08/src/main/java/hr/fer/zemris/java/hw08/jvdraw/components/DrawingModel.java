/**
 * 
 */
package hr.fer.zemris.java.hw08.jvdraw.components;

/**
 * @author Ivan
 * 
 */
public interface DrawingModel {
	int getSize();

	public GeometricalObject getObject(int index);

	public void add(GeometricalObject object);

	public void addDrawingModelListener(DrawingModelListener l);

	public void removeDrawingModelListener(DrawingModelListener l);
}
