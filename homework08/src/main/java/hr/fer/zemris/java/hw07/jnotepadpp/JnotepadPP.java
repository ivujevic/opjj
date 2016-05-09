/**
 * 
 */
package hr.fer.zemris.java.hw07.jnotepadpp;

import hr.fer.zemris.java.hw07.jnotepadpp.elements.Function;
import hr.fer.zemris.java.hw07.jnotepadpp.elements.TabDesctiptor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Razred u kojem se definira izgled samog Notepadda te se pokreće prilikom pokretanja programa
 * 
 * @author Ivan
 * 
 */
public class JnotepadPP extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Služi se pohranu stvorenih tabova.
	 */
	public JTabbedPane tabbedPane = new JTabbedPane();

	/**
	 * Referenca na textAera koja se nalazi u tab koju je trenutno otvoren.
	 */
	public JTextArea textArea = new JTextArea();

	/**
	 * Lista u koju se sprema opis svakog otvorenog taba.
	 */
	public List<TabDesctiptor> list = new ArrayList<>();

	/**
	 * Listener koji prati događaje u textArea.
	 */
	public DocumentListener listener;

	/**
	 * Referenca na novi razred u kojem se nalaze funkcije koje se mogu koristit u programu.
	 */
	Function function = new Function(this);

	/**
	 * Konstruktor Notepada
	 */
	public JnotepadPP() {

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(100, 0);
		setSize(1000, 500);
		String imgLocation = "images/Notes.jpg";
		this.setIconImage(new ImageIcon(imgLocation).getImage());

		/*
		 * Prati promjene koje se dešavaju u tabbedPane-u (aktivira se prilikom promjene selektiranog taba)
		 */
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				setTitle(tabbedPane.getComponent(tabbedPane.getSelectedIndex()).getName() + " - JNotepad++");
				if (list.size() == tabbedPane.getComponentCount()) {
					function.setTextArea(list.get(tabbedPane.getSelectedIndex()).getTextArea());
					function.setName(list.get(tabbedPane.getSelectedIndex()).getFileName());
					function.setTabbedPane(list.get(tabbedPane.getSelectedIndex()).getTabbedPane());
					textArea = list.get(tabbedPane.getSelectedIndex()).getTextArea();
					textArea.requestFocus();
					listener = makeListener();
				}
				// Prati promjene u textArea
				textAreaChange(textArea);
			}
		});

		/*
		 * Prati promjene koje se dešavaju prilikom promjene sadržaja tabbedPane-a. Konkretno koristi se
		 * prilikom kreiranja prvog taba kako korisnik nebi trebao kliknuti New te se koristi kada se datoteka
		 * spremi pod drugim imenom da osvježi novo ime datoteke
		 */
		tabbedPane.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {

				setTitle(tabbedPane.getComponent(tabbedPane.getSelectedIndex()).getName() + " - JNotepad++");
				if (list.size() == tabbedPane.getComponentCount()) {
					function.setTextArea(list.get(tabbedPane.getSelectedIndex()).getTextArea());
					function.setName(list.get(tabbedPane.getSelectedIndex()).getFileName());
					function.setTabbedPane(list.get(tabbedPane.getSelectedIndex()).getTabbedPane());
					textArea = list.get(tabbedPane.getSelectedIndex()).getTextArea();
					textArea.requestFocus();
				}
				listener = makeListener();
				// Prati promjene u textArea
				textAreaChange(textArea);
			}
		});

		/**
		 * Prati događaje koji se događaju u prozoru.
		 */
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				// Ako je prozor zatvoren prođi kroz tabove i provjeri je li koji od njih mijenjan
				for (TabDesctiptor tab : list) {
					if (tab.isDirty()) {
						int answer = JOptionPane.showConfirmDialog(tabbedPane.getParent(),
								"Želite li spremiti promjene natale u datoteci: \n" + tab.getFileName(),
								"Save", JOptionPane.YES_NO_CANCEL_OPTION);

						if (answer == JOptionPane.YES_OPTION) {
							function.setName(tab.getFileName());
							function.setTabbedPane(tab.getTabbedPane());
							function.setTextArea(tab.getTextArea());
							function.mapaAkcija.get("Save").actionPerformed(
									new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
						}
						else if (JOptionPane.NO_OPTION == answer) {
							continue;
						}
						else {
							return;
						}
					}
				}
				dispose();
			}
		});

		initGUI();
	}

	/**
	 * Metoda koja stvara novi DocumentListener.
	 * 
	 * @return DocumentListener
	 */
	protected DocumentListener makeListener() {
		return new DocumentListener() {

			boolean dirty = list.get(tabbedPane.getSelectedIndex()).isDirty();

			boolean file = false;

			@Override
			public void removeUpdate(DocumentEvent e) {

				if (file == false) {
					if (!list.get(tabbedPane.getSelectedIndex()).isDirty() && dirty) {
						file = true;
					}
				}
				if (dirty == false || file) {
					list.get(tabbedPane.getSelectedIndex()).setDirty(true);
					dirty = true;
					file = false;

				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {

				if (file == false) {
					if (!list.get(tabbedPane.getSelectedIndex()).isDirty() && dirty) {
						file = true;
					}
				}
				if (dirty == false || file) {
					list.get(tabbedPane.getSelectedIndex()).setDirty(true);
					dirty = true;
					file = false;

				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (file == false) {
					if (!list.get(tabbedPane.getSelectedIndex()).isDirty() && dirty) {
						file = true;
					}
				}
				if (dirty == false || file) {
					list.get(tabbedPane.getSelectedIndex()).setDirty(true);
					dirty = true;
					file = false;

				}
			}
		};
	}

	/**
	 * Dodaje listener u textArea.
	 * 
	 * @param textArea
	 *            textArea u koju se dodaje listener
	 */
	protected void textAreaChange(JTextArea textArea) {
		textArea.getDocument().addDocumentListener(listener);
		return;
	}

	/**
	 * Inicijalizira izgled ekrana.
	 */
	private void initGUI() {

		this.getContentPane().setLayout(new BorderLayout());

		// Pozovi akciju koja stvara novi tab
		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		function.mapaAkcija.get("New").actionPerformed(
				new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setLayout(new GridLayout(1, 3));

		JPanel panel = new JPanel();

		JButton button = new JButton(function.get("New"));

		button.setFocusPainted(false);
		button.setFocusable(false);
		panel.add(button);
		button = new JButton(function.get("Open"));
		button.setFocusPainted(false);
		button.setFocusable(false);
		panel.add(button);

		toolBar.add(panel);

		button = new JButton(function.get("Save"));
		button.setFocusPainted(false);
		button.setFocusable(false);
		panel.add(button);

		button = new JButton(function.get("SaveAs"));
		button.setFocusPainted(false);
		button.setFocusable(false);
		panel.add(button);
		toolBar.add(panel);

		panel = new JPanel();

		button = new JButton(function.get("Cut"));
		button.setFocusPainted(false);
		button.setFocusable(false);
		panel.add(button);

		button = new JButton(function.get("Copy"));
		button.setFocusPainted(false);
		button.setFocusable(false);
		panel.add(button);

		button = new JButton(function.get("Paste"));
		button.setFocusPainted(false);
		button.setFocusable(false);
		panel.add(button);

		button = new JButton(function.get("Statistical"));
		button.setFocusPainted(false);
		button.setFocusable(false);
		panel.add(button);

		toolBar.add(panel);

		button = new JButton(function.get("Exit"));
		button.setFocusPainted(false);
		button.setFocusable(false);

		toolBar.add(button);

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu file = new JMenu("File");
		menuBar.add(file);

		tabbedPane.setFocusable(false);

		JMenuItem itemNew = new JMenuItem(function.get("New"));
		file.add(itemNew);

		JMenuItem itemOpen = new JMenuItem(function.get("Open"));
		file.add(itemOpen);

		JMenuItem itemSave = new JMenuItem(function.get("Save"));
		file.add(itemSave);

		JMenuItem itemSaveAs = new JMenuItem(function.get("SaveAs"));
		file.add(itemSaveAs);

		JMenuItem itemExit = new JMenuItem(function.get("Exit"));
		file.add(itemExit);

		JMenu edit = new JMenu("Edit");
		menuBar.add(edit);

		JMenuItem itemCut = new JMenuItem(function.get("Cut"));
		edit.add(itemCut);

		JMenuItem itemCopy = new JMenuItem(function.get("Copy"));
		edit.add(itemCopy);

		JMenuItem itemPaste = new JMenuItem(function.get("Paste"));
		edit.add(itemPaste);

		JMenuItem itemStatistical = new JMenuItem(function.get("Statistical"));
		edit.add(itemStatistical);
		pack();
	}

	/**
	 * Metoda koja se pokreće prilikom pokretanja programa.
	 * 
	 * @param args
	 *            Ne prima ništa
	 */
	public static void main(String[] args) {

		// Omogući da se izgled prilagodi operacijskom sustavu na kojem se izvodi.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {

		} catch (InstantiationException e) {

		} catch (IllegalAccessException e) {

		} catch (UnsupportedLookAndFeelException e) {

		}

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				startGUIApp();

			}
		});

	}

	/**
	 * Kreira novi prozor.
	 */
	protected static void startGUIApp() {
		new JnotepadPP().setVisible(true);
	}

}
