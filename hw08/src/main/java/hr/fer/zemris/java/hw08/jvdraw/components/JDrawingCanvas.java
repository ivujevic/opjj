/**
 * 
 */
package hr.fer.zemris.java.hw08.jvdraw.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

/**
 * Razred u kojem se implementira komponenta koja služi za crtanje objekata.
 * 
 * @author Ivan
 * 
 */
public class JDrawingCanvas extends JComponent {

	Point firstPoint;
	Point secondPoint;
	JColorArea foregroundArea;
	JColorArea backgroundArea;
	public boolean dirty = false;
	final ObjectsContainer container;

	public Class<?> selectedObject = Line.class;

	public JDrawingCanvas(JColorArea foregroundArea, JColorArea backgroundArea, ObjectsContainer container) {

		this.container = container;
		this.foregroundArea = foregroundArea;
		this.backgroundArea = backgroundArea;

		this.container.addDrawingModelListener(new DrawingModelListener() {

			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				repaint();

			}

			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				repaint();

			}
		});

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (firstPoint == null) {
					firstPoint = new Point(e.getX(), e.getY());
					secondPoint = new Point(e.getX(), e.getY());
				}
				else {
					secondPoint.x = e.getX();
					secondPoint.y = e.getY();

					Class[] param = new Class[2];
					param[0] = Point.class;
					param[1] = Point.class;

					Constructor constructor;
					try {
						constructor = selectedObject.getDeclaredConstructor(param);
						GeometricalObject o = (GeometricalObject) constructor.newInstance(firstPoint,
								secondPoint);
						JDrawingCanvas.this.container.add(o);
						dirty = true;
					} catch (NoSuchMethodException | SecurityException | InstantiationException
							| IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					firstPoint = null;
					secondPoint = null;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (firstPoint == null) {
					return;
				}
				secondPoint.x = e.getX();
				secondPoint.y = e.getY();
				repaint();

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	protected synchronized JColorArea getForegroundArea() {
		return foregroundArea;
	}

	protected synchronized JColorArea getBackgroundArea() {
		return backgroundArea;
	}

	@Override
	protected void paintComponent(Graphics g) {
		for (int i = 0; i < this.container.getSize(); i++) {
			GeometricalObject l = this.container.getObject(i);
			Class<?> c = l.getClass();
			try {
				Class[] param = new Class[3];
				param[0] = Graphics.class;

				param[1] = param[2] = Point.class;

				Method method = c.getDeclaredMethod("draw", param);
				GeometricalObject o = (GeometricalObject) c.newInstance();

				if (l.getBackgroundColor() == null) {
					l.setBackgroundColor(backgroundArea.getColor());
				}

				if (l.getForegroundColor() == null) {
					l.setForegroundColor(foregroundArea.getColor());
				}

				method.invoke(l, g, l.getFirstPoint(), l.getSecondPoint());
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (firstPoint != null) {
			g.setColor(Color.GREEN);
			Class[] param = new Class[3];
			param[0] = Graphics.class;
			param[1] = Point.class;
			param[2] = Point.class;
			try {
				Method draw = selectedObject.getDeclaredMethod("draw", param);
				Object o = selectedObject.newInstance();
				draw.invoke(o, g, firstPoint, secondPoint);
			} catch (NoSuchMethodException | SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
