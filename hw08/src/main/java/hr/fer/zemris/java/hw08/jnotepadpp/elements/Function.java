/**
 * 
 */
package hr.fer.zemris.java.hw08.jnotepadpp.elements;

import hr.fer.zemris.java.hw08.jnotepadpp.JnotepadPP;
import hr.fer.zemris.java.hw08.jnotepadpp.translator.LocalizableAction;
import hr.fer.zemris.java.hw08.jnotepadpp.translator.LocalizationProvider;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Razred u kojem se nalaze implementirane funkcije koje program može koristiti.
 * 
 * @author Ivan
 * 
 */
public class Function {

	/**
	 * Mapa u koju se dodaju akcije za pojedinu funkciju.
	 */
	public Map<String, LocalizableAction> mapaAkcija = new HashMap<>();

	/**
	 * Referenca na textArea koja se stvara za tab.
	 */
	protected JTextArea textArea;

	/**
	 * Metoda koja postavlja referencu na trenutno aktivnu textArea-u koja.
	 * 
	 * @param textArea
	 *            aktivna textArea
	 */
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	/**
	 * Metoda koja postavlja ime trenutno otvorenog taba.
	 * 
	 * @param name
	 *            ime taba
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Referenca na tabbedPane.
	 */
	protected JTabbedPane tabbedPane;

	/**
	 * Referenca na otvoreni prozor.
	 */
	protected JnotepadPP window;

	/**
	 * Pamti ime trenutnog taba.
	 */
	protected String filesName = "New 1";

	/**
	 * Služi kao pomoćna varijabla za ime kako bi se moglo kreirati New1,New2....
	 */
	protected String tempName = "New 1";

	/**
	 * Ime otvorenog taba.
	 */
	protected String name;

	/**
	 * Metoda koja vraća referencu na tabbedPane.
	 * 
	 * @return referenca na tabbedPane.
	 */
	public synchronized JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	/**
	 * Metoda koja postavlja referencu na tabbedPane.
	 * 
	 * @param tabbedPane
	 *            referenca na tabbedPane.
	 */
	public synchronized void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	/**
	 * Referenca na razred u kojem se opisuje pojedini tab.
	 */
	TabDesctiptor tabDescriptor;

	public Function(final JnotepadPP window) {

		this.window = window;
		this.textArea = window.textArea;
		this.tabbedPane = window.tabbedPane;

		String imgLocation = "images/New24.gif";

		// Akcija za naredbu New
		LocalizableAction action = new LocalizableAction("New", window.flp, new ImageIcon(imgLocation),
				"NewDescription") {

			@Override
			public void actionPerformed(ActionEvent e) {

				tabDescriptor = new TabDesctiptor();
				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout());

				panel.setName(filesName);
				String title;
				if (filesName.contains("\\")) {
					title = filesName.substring(filesName.lastIndexOf("\\") + 1);
				}
				else {
					title = filesName;
				}
				tabbedPane.add(window.flp.getString(title.split(" ")[0]) + title.split(" ")[1], panel);
				/*
				 * Ako su imena različita znači da se kreira tab s imenom datoteke inače će se kreirati čisti
				 * novi tab
				 */
				name = filesName;
				if (!filesName.equals(tempName)) {
					filesName = tempName;
				}
				else {
					filesName = "New " + String.valueOf(Integer.parseInt(filesName.split(" ")[1]) + 1);
					tempName = filesName;
				}

				JTextArea textArea = new JTextArea();
				textArea.setFont(new Font("Verdana", Font.PLAIN, 12));
				textArea.setName(title);
				JScrollPane scrollPane = new JScrollPane(textArea);
				panel.add(scrollPane);
				panel.setPreferredSize(new Dimension(1000, 500));
				tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);

				tabDescriptor.setDirty(false);
				tabDescriptor.setFileName(name);
				tabDescriptor.setTabbedPane(tabbedPane);
				tabDescriptor.setTextArea(textArea);
				Function.this.window.list.add(tabDescriptor);
				Function.this.textArea = textArea;

				/*
				 * Napravi kako bi mogao dobiti akciju u kojoj se selektira trenutno stvoreni tab
				 */
				if (tabbedPane.getComponentCount() > 1) {
					tabbedPane
							.setSelectedComponent(tabbedPane.getComponent(tabbedPane.getSelectedIndex() - 1));
					tabbedPane
							.setSelectedComponent(tabbedPane.getComponent(tabbedPane.getSelectedIndex() + 1));
				}
			}
		};
		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK, true));

		mapaAkcija.put("New", action);

		// Akcija za naredbu Open
		imgLocation = "images/Open24.gif";
		action = new LocalizableAction("Open", window.flp, new ImageIcon(imgLocation), "OpenDescription") {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(Function.this.tabbedPane.getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					File file = fc.getSelectedFile();

					if (Function.this.window.list.contains(new TabDesctiptor(textArea, file.getPath()
							.toString(), tabbedPane))) {
						return;
					}
					try {
						BufferedReader br = new BufferedReader(new InputStreamReader(
								new FileInputStream(file), StandardCharsets.UTF_8));
						StringBuilder builder = new StringBuilder();
						while (true) {
							String line = br.readLine();
							if (line == null) {
								br.close();
								break;
							}
							builder.append(line + "\n");
						}
						br.close();
						filesName = file.getPath();
						mapaAkcija.get("New").actionPerformed(
								new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

						Function.this.textArea.setText(builder.toString());
						tabDescriptor.setDirty(false);
					} catch (IOException a) {
						JOptionPane.showMessageDialog(Function.this.tabbedPane.getParent(),
								window.flp.getString("ReadFileError"), window.flp.getString("Error"),
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		};

		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));

		mapaAkcija.put("Open", action);

		// Akcija za naredbu Save
		imgLocation = "images/Save24.gif";

		action = new LocalizableAction("Save", window.flp, new ImageIcon(imgLocation), "SaveDescription") {

			@Override
			public void actionPerformed(ActionEvent e) {
				File file = new File(name);

				if (!file.exists()) {
					mapaAkcija.get("SaveAs").actionPerformed(
							new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
					return;
				}
				Function.this.window.list.get(tabbedPane.getSelectedIndex()).setDirty(false);
				Function.this.window.list.get(tabbedPane.getSelectedIndex()).setFileName(file.getName());
				writeToFile(file);
			}
		};

		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

		mapaAkcija.put("Save", action);

		// Akcija za naredbu Save As
		imgLocation = "images/SaveAs24.gif";
		action = new LocalizableAction("SaveAs", window.flp, new ImageIcon(imgLocation), "SaveAsDescription") {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new FileNameExtensionFilter("Text file", "txt", "rtf"));
				int returnVal = fc.showSaveDialog(Function.this.window);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					int repyly = JOptionPane.YES_OPTION;
					if (file.exists()) {
						repyly = JOptionPane.showConfirmDialog(Function.this.tabbedPane.getParent(),
								window.flp.getString("AskForChangeFile"), window.flp.getString("FileExist")
										+ "!", JOptionPane.YES_NO_OPTION);
					}
					if (repyly == JOptionPane.YES_OPTION) {
						writeToFile(file);
					}
					else {
						return;
					}
					name = file.toString();

					String title = file.getName();
					tabbedPane.getComponentAt(tabbedPane.getSelectedIndex()).setName(name);
					tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), title);
					tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex());
					Function.this.window.list.get(tabbedPane.getSelectedIndex()).setDirty(false);
					Function.this.window.list.get(tabbedPane.getSelectedIndex()).setFileName(file.getName());
				}

			}
		};

		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

		mapaAkcija.put("SaveAs", action);

		// Akcija za naredbu Cut
		imgLocation = "images/Cut24.gif";
		action = new LocalizableAction("Cut", window.flp, new ImageIcon(imgLocation)) {

			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuilder builder = new StringBuilder();

				textArea.requestFocus();

				if (textArea.getSelectedText() == null) {
					return;
				}
				builder.append(textArea.getText());
				builder.delete(textArea.getSelectionStart(), textArea.getSelectionEnd());

				StringSelection clipboard = new StringSelection(textArea.getSelectedText());
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipboard, null);
				textArea.setText(builder.toString());
			}
		};

		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));

		action.putValue(Action.SHORT_DESCRIPTION, window.flp.getString("CutDescription"));

		mapaAkcija.put("Cut", action);

		// Akcija za naredbu Copy
		imgLocation = "images/Copy24.gif";

		action = new LocalizableAction("Copy", window.flp, new ImageIcon(imgLocation), "CopyDescription") {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.requestFocus();
				if (textArea.getSelectedText() == null) {
					return;
				}
				StringSelection clipboard = new StringSelection(textArea.getSelectedText());
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipboard, null);

			}
		};

		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));

		mapaAkcija.put("Copy", action);

		// akcija za naredbu Paste
		imgLocation = "images/Paste24.gif";
		action = new LocalizableAction("Paste", window.flp, new ImageIcon(imgLocation), "PasteDescription") {

			@Override
			public void actionPerformed(ActionEvent e) {
				Transferable clipData = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
				String s = null;
				try {
					s = (String) clipData.getTransferData(DataFlavor.stringFlavor);
					StringBuilder builder = new StringBuilder();
					builder.append(textArea.getText());
					if (textArea.getSelectedText() != null) {
						builder.delete(textArea.getSelectionStart(), textArea.getSelectionEnd());
					}
					builder.insert(textArea.getSelectionStart(), s);
					textArea.setText(builder.toString());
				} catch (UnsupportedFlavorException | IOException e1) {
				}
			}
		};

		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));

		mapaAkcija.put("Paste", action);

		// Akcija za naredbu Exit
		imgLocation = "images/Stop24.gif";

		action = new LocalizableAction("Exit", window.flp, new ImageIcon(imgLocation), "ExitDescription") {

			@Override
			public void actionPerformed(ActionEvent e) {
				WindowEvent wev = new WindowEvent(Function.this.window, WindowEvent.WINDOW_CLOSING);
				Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
			}
		};

		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

		mapaAkcija.put("Exit", action);

		// Akcija za naredbu Statistical
		imgLocation = "images/Information24.gif";

		action = new LocalizableAction("Statistical", window.flp, new ImageIcon(imgLocation), "Statistical") {

			@Override
			public void actionPerformed(ActionEvent e) {

				char[] text = textArea.getText().toCharArray();
				int numberOfCharacters = 0;
				int numberOfNonBlankCharacter = 0;
				int numberOfLines = 1;

				for (char c : text) {
					if (c == '\n') {
						numberOfLines++;
					}
					else if (c == 32 || c == 9 || c == 13 || c == '\n') {
						numberOfNonBlankCharacter++;
						numberOfCharacters++;
					}
					else {
						numberOfCharacters++;
					}
				}

				JOptionPane.showMessageDialog(tabbedPane.getParent(), String.format(
						window.flp.getString("statsFormat"), numberOfCharacters, numberOfNonBlankCharacter,
						numberOfLines), window.flp.getString("Statistical"), JOptionPane.INFORMATION_MESSAGE);
			}
		};

		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));

		mapaAkcija.put("Statistical", action);

		// Akcija za naredbu Engleski jezik

		action = new LocalizableAction("English", window.flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		};

		mapaAkcija.put("English", action);

		// Akcija za naredbu Hrvatski jezik

		action = new LocalizableAction("Croatian", window.flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		};

		mapaAkcija.put("Croatian", action);

	}

	/**
	 * Metoda u kojoj se piše u predanu datoteku.
	 * 
	 * @param file
	 *            Datoteka u koju se piše
	 */
	protected void writeToFile(File file) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),
					StandardCharsets.UTF_8));
			String text = textArea.getText();
			BufferedReader a = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(
					text.getBytes("UTF-8")), "UTF-8"));
			while (true) {
				String line = a.readLine();
				if (line == null) {
					bw.close();
					a.close();
					break;
				}
				bw.write(line + "\n");
			}
			bw.close();
			textArea.requestFocus();
		} catch (IOException a) {
			JOptionPane.showMessageDialog(Function.this.tabbedPane.getParent(),
					window.flp.getString("ReadFileError"), window.flp.getString("Error"),
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Metoda koja vraća akciju pojedine funkcije.
	 * 
	 * @param key
	 *            Ključ za mapu, ime funkcije
	 * @return Akcija za funkciju.
	 */

	public LocalizableAction get(String key) {
		return mapaAkcija.get(key);
	}

}
