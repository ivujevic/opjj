/**
 * 
 */
package hr.fer.zemris.java.hw08.jvdraw;

import hr.fer.zemris.java.hw08.jvdraw.components.Circle;
import hr.fer.zemris.java.hw08.jvdraw.components.ColorWriter;
import hr.fer.zemris.java.hw08.jvdraw.components.DrawingObjectListModel;
import hr.fer.zemris.java.hw08.jvdraw.components.FilledCircle;
import hr.fer.zemris.java.hw08.jvdraw.components.GeometricalObject;
import hr.fer.zemris.java.hw08.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw08.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw08.jvdraw.components.Line;
import hr.fer.zemris.java.hw08.jvdraw.components.ObjectsContainer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Razred u kojem je definiran izgled JVDraw te se pokreće prilikom pokretanja.
 * 
 * @author Ivan
 * 
 */
public class JVDraw extends JFrame {

	/**
	 * Mapa u koju se spremaju akcije.
	 */
	private Map<String, Action> mapaAkcija = new HashMap();

	/**
	 * Referenca na objekt u kojem se crta.
	 */
	JDrawingCanvas canvas;

	/**
	 * Datoteka koja je otvorena.
	 */
	File file;

	/**
	 * Container u kojem su spremljeni svi nacrtani likovi.
	 */
	ObjectsContainer container;

	/**
	 * Zastavica koja označava je li datoteka uređivana nakon spremanja ili ne.
	 */
	private boolean dirty = false;

	/**
	 * Konstruktor
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(1000, 500);
		setLocation(100, 0);
		initGUI();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (canvas.dirty) {
					int answer = JOptionPane.showConfirmDialog(null, "Želite li spremiti nastale promjene?",
							"Save", JOptionPane.YES_NO_CANCEL_OPTION);
					if (answer == JOptionPane.NO_OPTION) {
						dispose();
					}
					else if (answer == JOptionPane.YES_OPTION) {
						mapaAkcija.get("Save").actionPerformed(
								new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
					}
					else {
						return;
					}
				}
				dispose();
			}
		});
	}

	/**
	 * Metoda u kojoj se kreiraju komponente korisničkog sučelja.
	 */
	private void initGUI() {

		initAction(this);
		getContentPane().setLayout(new BorderLayout());
		JToolBar toolbar = new JToolBar();
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.getContentPane().add(toolbar, BorderLayout.PAGE_START);

		JColorArea foregroundArea = new JColorArea(Color.red);
		toolbar.add(foregroundArea);

		JColorArea backgroundArea = new JColorArea(Color.blue);
		container = new ObjectsContainer();

		AbstractListModel<GeometricalObject> model = new DrawingObjectListModel(container);

		final JList<GeometricalObject> list = new JList<>(model);

		list.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					list.getSelectedValue().showDialog();
					container.change(list.getSelectedIndex());
				}
			}
		});

		this.getContentPane().add(new JScrollPane(list), BorderLayout.EAST);
		canvas = new JDrawingCanvas(foregroundArea, backgroundArea, container);

		backgroundArea.setLocation(foregroundArea.getX() + 100, foregroundArea.getY());
		toolbar.add(backgroundArea);

		JToggleButton line = new JToggleButton(mapaAkcija.get("Line"));
		JToggleButton circle = new JToggleButton(mapaAkcija.get("Circle"));
		JToggleButton filledCircle = new JToggleButton(mapaAkcija.get("FilledCircle"));

		ButtonGroup group = new ButtonGroup();
		group.add(line);
		group.add(circle);
		group.add(filledCircle);

		toolbar.add(line);
		toolbar.add(circle);
		toolbar.add(filledCircle);

		this.getContentPane().add(canvas, BorderLayout.CENTER);

		ColorWriter label = new ColorWriter(foregroundArea, backgroundArea);
		this.getContentPane().add(label, BorderLayout.SOUTH);

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu file = new JMenu("File");
		menuBar.add(file);

		JMenuItem open = new JMenuItem(mapaAkcija.get("Open"));
		file.add(open);

		JMenuItem save = new JMenuItem(mapaAkcija.get("Save"));
		file.add(save);

		JMenuItem saveAs = new JMenuItem(mapaAkcija.get("SaveAs"));
		file.add(saveAs);

		JMenuItem export = new JMenuItem(mapaAkcija.get("Export"));
		file.add(export);

		JMenuItem exit = new JMenuItem(mapaAkcija.get("Exit"));
		file.add(exit);
	}

	/**
	 * Metoda u kojoj se inicijaliziraju akcije
	 * 
	 * @param jvDraw
	 *            Referenca na prozor u kojem se crta.
	 * 
	 */
	private void initAction(final JVDraw jvDraw) {

		// akcija ako se klikne na button za liniju
		Action action = new AbstractAction("Line") {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.selectedObject = Line.class;
			}
		};
		mapaAkcija.put("Line", action);

		// akcija ako se klikne na button za krug
		action = new AbstractAction("Circle") {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.selectedObject = Circle.class;

			}
		};
		mapaAkcija.put("Circle", action);

		// akcija ako se klikne na button za ispunjeni krug
		action = new AbstractAction("Filled Circle") {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.selectedObject = FilledCircle.class;
			}
		};
		mapaAkcija.put("FilledCircle", action);

		// akcija za otvaranje datoteke
		action = new AbstractAction("Open") {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JVDraw files", "jvd");
				fc.removeChoosableFileFilter(fc.getChoosableFileFilters()[0]);
				fc.addChoosableFileFilter(filter);
				int n = fc.showDialog(null, "Open");
				if (n == JFileChooser.APPROVE_OPTION) {
					if (file != null && file.equals(fc.getSelectedFile())) {
						return;
					}
					file = fc.getSelectedFile();
					readFile(file);
					dirty = false;
				}
				else {
					return;
				}
			}
		};
		mapaAkcija.put("Open", action);

		// akcija za spremanje datoteke
		action = new AbstractAction("Save") {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (file == null) {
					mapaAkcija.get("SaveAs").actionPerformed(
							new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
					System.out.println(dirty);
					return;
				}
				System.out.println(dirty);
				saveFile(file);
				dirty = false;
			}
		};
		mapaAkcija.put("Save", action);

		action = new AbstractAction("Save As") {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JVDraw files", "jvd");
				fc.removeChoosableFileFilter(fc.getChoosableFileFilters()[0]);
				fc.addChoosableFileFilter(filter);
				int n = fc.showDialog(null, "Save");
				if (n == fc.APPROVE_OPTION) {
					file = fc.getSelectedFile();
					String extension = ((FileNameExtensionFilter) fc.getFileFilter()).getExtensions()[0];
					if (file.toString().indexOf('.') == -1) {
						file = new File(file.toString() + "." + extension);
					}
					saveFile(file);
				}
				else {
					return;
				}
			}
		};
		mapaAkcija.put("SaveAs", action);

		action = new AbstractAction("Export") {

			@Override
			public void actionPerformed(ActionEvent e) {

				int minX = Integer.MAX_VALUE;
				int maxX = Integer.MIN_VALUE;

				int minY = minX;
				int maxY = maxX;

				int size = container.getSize();

				for (int i = 0; i < size; i++) {

					GeometricalObject o = container.getObject(i);
					minX = Math.min(minX, Math.min(o.getFirstPoint().x, o.getSecondPoint().x));
					maxX = Math.max(maxX, Math.max(o.getFirstPoint().x, o.getSecondPoint().x));

					minY = Math.min(minY, Math.min(o.getFirstPoint().y, o.getSecondPoint().y));
					maxY = Math.max(maxY, Math.max(o.getFirstPoint().y, o.getSecondPoint().y));
				}

				for (int i = 0; i < size; i++) {

					GeometricalObject o = container.getObject(i);
					if (o instanceof Circle) {
						Circle circle = (Circle) o;
						if (circle.getFirstPoint().x - (int) circle.getRadius() < minX) {

							minX = circle.getFirstPoint().x - (int) circle.getRadius();
						}
						if (circle.getFirstPoint().y - (int) circle.getRadius() < minY) {
							minY = circle.getFirstPoint().y - (int) circle.getRadius();
						}

						if (circle.getFirstPoint().x + (int) circle.getRadius() > maxX) {
							maxX = circle.getFirstPoint().x + (int) circle.getRadius();
						}
						if (circle.getFirstPoint().y + (int) circle.getRadius() > maxY) {
							maxY = circle.getFirstPoint().y + (int) circle.getRadius();
						}
					}
				}

				int width = maxX - minX;
				int height = maxY - minY;

				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);

				int shiftX = -minX;
				int shiftY = -minY;

				for (int i = 0; i < size; i++) {
					GeometricalObject o = container.getObject(i);
					o.setFirstPoint(container.getObject(i).firstPoint);
					o.setSecondPoint(container.getObject(i).secondPoint);

					Point first = o.getFirstPoint();
					Point second = o.getSecondPoint();

					o.setFirstPoint(new Point(first.x + shiftX, first.y + shiftY));
					o.setSecondPoint(new Point(second.x + shiftX, second.y + shiftY));

					o.draw(g);
					canvas.repaint();
				}
				g.dispose();

				JFileChooser fc = new JFileChooser();
				fc.removeChoosableFileFilter(fc.getChoosableFileFilters()[0]);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Jpg files", "jpg");
				fc.addChoosableFileFilter(filter);
				filter = new FileNameExtensionFilter("Gif files", "gif");
				fc.addChoosableFileFilter(filter);
				filter = new FileNameExtensionFilter("Png files", "png");
				fc.addChoosableFileFilter(filter);
				int n = fc.showDialog(null, "Save");
				if (n == fc.APPROVE_OPTION) {
					file = fc.getSelectedFile();
					String extension = ((FileNameExtensionFilter) fc.getFileFilter()).getExtensions()[0];
					if (file.toString().indexOf('.') == -1) {
						file = new File(file.toString() + "." + extension);
					}
					try {
						ImageIO.write(image, extension, file);
						JOptionPane.showMessageDialog(null, "Image exported", "Export",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					return;
				}
			}
		};
		mapaAkcija.put("Export", action);

		action = new AbstractAction("Exit") {

			@Override
			public void actionPerformed(ActionEvent e) {
				WindowEvent wev = new WindowEvent(jvDraw, WindowEvent.WINDOW_CLOSING);
				Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
			}
		};
		mapaAkcija.put("Exit", action);
	}

	/**
	 * Metoda u kojoj se sprema nacrtana slika u datoteku kao niz naredbi
	 * 
	 * @param file
	 *            Datoteka u koju se sprema.
	 */
	protected void saveFile(File file) {

		try {
			BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),
					StandardCharsets.UTF_8));
			for (int i = 0; i < container.getSize(); i++) {
				GeometricalObject object = container.getObject(i);
				StringBuilder builder = new StringBuilder();
				if (object.toString().startsWith("L")) {
					builder.append("LINE ");
				}
				else if (object.toString().startsWith("C")) {
					builder.append("CIRCLE ");
				}
				else {
					builder.append("FCIRCLE ");
				}
				builder.append(object.firstPoint.x + " " + object.firstPoint.y + " ");
				if (object.toString().substring(0, 4).equals("Line")) {
					builder.append(object.getSecondPoint().x + " " + object.getSecondPoint().y + " ");
				}
				else {
					if (((Circle) object).radius == 0) {
						builder.append(String.valueOf(((FilledCircle) object).radius).substring(0,
								String.valueOf(((FilledCircle) object).radius).indexOf('.') + 3)
								+ " ");
					}
					else {
						builder.append(String.valueOf(((Circle) object).radius).substring(0,
								String.valueOf(((Circle) object).radius).indexOf('.') + 3)
								+ " ");
					}
				}
				Color fColor = object.foregroundColor;
				builder.append(fColor.getRed() + " " + fColor.getGreen() + " " + fColor.getBlue());
				if (object.toString().length() > 12
						&& object.toString().substring(0, 12).equals("FilledCircle")) {
					Color bColor = object.backgroundColor;
					builder.append(" " + bColor.getRed() + " " + bColor.getGreen() + " " + bColor.getBlue());
				}
				br.write(builder.toString() + "\n");
			}
			br.close();
			canvas.dirty = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metoda u kojoj se čita iz datoteke.
	 * 
	 * @param file
	 *            Datoteka iz koje se čita
	 */
	protected void readFile(File file) {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),
					StandardCharsets.UTF_8));

			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				String[] param = line.split(" ");
				if (param[0].equals("LINE")) {
					createLine(param);
				}
				else if (param[0].equals("CIRCLE")) {
					createCircle(param);
				}
				else if (param[0].equals("FCIRCLE")) {
					createFCircle(param);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metoda u kojoj se kreira ispunjeni krug sa zadanim parametrima pročitanim iz datoteke.
	 * 
	 * @param param
	 *            parametri za kreiranje ispunjenog kruga
	 */
	private void createFCircle(String[] param) {
		Point firstPoint = new Point(Integer.parseInt(param[1]), Integer.parseInt(param[2]));
		double radius = Double.parseDouble(param[3]);
		Color foregroundColor = new Color(Integer.parseInt(param[4]), Integer.parseInt(param[5]),
				Integer.parseInt(param[6]));
		Color backGroundColor = new Color(Integer.parseInt(param[7]), Integer.parseInt(param[8]),
				Integer.parseInt(param[9]));
		container.add(new FilledCircle(firstPoint, radius, foregroundColor, backGroundColor));

	}

	/**
	 * Metoda koja kreira krug sa zadanim parametrima pročitanim iz datoteke.
	 * 
	 * @param param
	 *            parametri za kreiranje kruga
	 */
	private void createCircle(String[] param) {
		Point firstPoint = new Point(Integer.parseInt(param[1]), Integer.parseInt(param[2]));
		double radius = Double.parseDouble(param[3]);
		Color foregroundColor = new Color(Integer.parseInt(param[4]), Integer.parseInt(param[5]),
				Integer.parseInt(param[6]));
		container.add(new Circle(firstPoint, radius, foregroundColor));

	}

	/**
	 * Metoda koja kreira liniju sa zadanim parametrika iz datoteke.
	 * 
	 * @param param
	 *            parametri za kreiranja linije
	 */
	private void createLine(String[] param) {
		Point firstPoint = new Point(Integer.parseInt(param[1]), Integer.parseInt(param[2]));
		Point secondPoint = new Point(Integer.parseInt(param[3]), Integer.parseInt(param[4]));
		Color foregroundColor = new Color(Integer.parseInt(param[5]), Integer.parseInt(param[6]),
				Integer.parseInt(param[7]));
		container.add(new Line(firstPoint, secondPoint, foregroundColor));

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new JVDraw().setVisible(true);

			}
		});
	}
}
