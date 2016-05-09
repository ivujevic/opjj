/**
 * 
 */
package hr.fer.zemris.java.hw08.jnotepadpp.elements;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

/**
 * Razred koji opisuje svaki otvoreni tab.
 * 
 * @author Ivan
 * 
 */
public class TabDesctiptor {

	/**
	 * Referenca na textArea koju tab sadrži.
	 */
	private JTextArea textArea;

	/**
	 * Ime daoteke koja ja otvorena u tabu.
	 */
	private String fileName;

	/**
	 * Referenca na tabbedPane u kojem je tab
	 */
	private JTabbedPane tabbedPane;

	/**
	 * Zastavica koja označava je li datoteka u tabu mijenjana.
	 */
	private boolean isDirty;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TabDesctiptor other = (TabDesctiptor) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		}
		else if (!fileName.equals(other.fileName))
			return false;
		return true;
	}

	/**
	 * Vraća true ako je datoteka promijenjena inače vraća false
	 * 
	 * @return Vrijednost zastavice
	 */
	public synchronized boolean isDirty() {
		return isDirty;
	}

	/**
	 * Postavlja zastavicu na zadanu vrijednost.
	 * 
	 * @param isDirty
	 *            Vrijednost na koju se postavlja zastavica
	 */
	public synchronized void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	/**
	 * Prazni konstruktor.
	 */
	public TabDesctiptor() {

	}

	/**
	 * Konstruktor za opsnik tabova.
	 * 
	 * @param textArea
	 *            referenca na textArea koja ja u tabu.
	 * @param fileName
	 *            ime datoteka koja je otvorena u tabu.
	 * @param tabbedPane
	 *            referenca na tabbedPane u kojem je tab
	 */
	public TabDesctiptor(JTextArea textArea, String fileName, JTabbedPane tabbedPane) {
		super();
		this.textArea = textArea;
		this.fileName = fileName;
		this.tabbedPane = tabbedPane;
	}

	/**
	 * Vraća referencu na textArea.
	 * 
	 * @return referenca na textArea
	 */
	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * Postavlja referenca za textArea.
	 * 
	 * @param textArea
	 *            referenca na textArea
	 */
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	/**
	 * Metoda u kojoj se vraća ime datoteka koja je otvorena u tabu.
	 * 
	 * @return ime datoteke
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Metoda koja postavlja ime datoteke.
	 * 
	 * @param fileName
	 *            ima na koje se ime datoteke postavlja
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Metoda koja vraća referencu na tabbedPane
	 * 
	 * @return referenca na tabbedPane.
	 */
	public synchronized JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	/**
	 * Metoda koja postavlja referenca na tabbedPane.
	 * 
	 * @param tabbedPane
	 *            referenca na tabbedPane
	 */
	public synchronized void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

}
