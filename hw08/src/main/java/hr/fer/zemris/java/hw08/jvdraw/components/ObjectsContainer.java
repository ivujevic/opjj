/**
 * 
 */
package hr.fer.zemris.java.hw08.jvdraw.components;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji implementira DrawingModel te služi za pohranjivanje nacrtanih objekata.
 * 
 * @author Ivan
 * 
 */
public class ObjectsContainer implements DrawingModel {

	List<GeometricalObject> container = new ArrayList<>();
	List<DrawingModelListener> listener = new ArrayList<>();

	int index;

	@Override
	public int getSize() {
		return container.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		if (index > -1 && index < container.size()) {
			return container.get(index);
		}
		else
			return null;
	}

	@Override
	public void add(GeometricalObject object) {
		container.add(object);
		index = container.indexOf(object);
		update(index);
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listener.add(l);

	}

	public void change(int index) {
		for (DrawingModelListener l : listener) {
			l.objectsChanged(this, index, index);
		}
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listener.remove(l);

	}

	/**
	 * Obavještava listenere
	 */
	private void update(int index) {
		for (DrawingModelListener l : listener) {
			l.objectsAdded(this, index, index);
		}

	}

}
