/**
 * 
 */
package hr.fer.zemris.java.hw08.jvdraw.components;

import javax.swing.AbstractListModel;

/**
 * Spremljeni podatci
 * 
 * @author Ivan
 * 
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> {

	ObjectsContainer container;

	public DrawingObjectListModel(final ObjectsContainer container) {

		/**
		 * Referena na kontejner u kojem su spremljeni objekti
		 */
		this.container = container;

		container.addDrawingModelListener(new DrawingModelListener() {

			@Override
			public void objectsRemoved(final DrawingModel source, final int index0, final int index1) {
				fireIntervalRemoved(source, index0, index1);

			}

			@Override
			public void objectsChanged(final DrawingModel source, final int index0, final int index1) {
				fireContentsChanged(source, index0, index1);
			}

			@Override
			public void objectsAdded(final DrawingModel source, final int index0, final int index1) {
				fireIntervalAdded(source, index0, index1);
			}
		});
	}

	@Override
	public final int getSize() {
		return container.getSize();
	}

	@Override
	public final GeometricalObject getElementAt(final int index) {
		return container.getObject(index);
	}

}
