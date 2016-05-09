/**
 * Paket u kojem se nalaze svi razredi potrebni za rad s matricama i vektorima.
 */
package hr.fer.zemris.linearna;

/**
 * Razred koji nasljeđuje razred AbstractMatrix. U njemu se implementiraju metode koje su abstraktne u razredu
 * AbstractMatrix.
 * 
 */
public class Matrix extends AbstractMatrix {

    /**
     * Polje u koje se spremaju elementi matrice.
     */
    private double[][] elements;

    /**
     * Broj redaka matrice.
     */
    private int rows;

    /**
     * Broj stupaca matrice.
     */
    private int cols;

    /**
     * Oznaka na koji se način odvajaju redci matrice prilikom korištenja. <code>parseSimple</code> metode
     */
    private static final String ODVAJANJEREDAKA = "\\| ";

    /**
     * Konstruktor koji stvara matricu zadanih dimenzija.
     * 
     * @param rows
     *            Broj redaka matrice
     * @param cols
     *            Broj stupaca matrice
     */
    public Matrix(final int rows, final int cols) {
	if (rows < 1 || cols < 1) {
	    throw new IllegalArgumentException("Nije moguće kreirati takvu matricu");
	}
	this.rows = rows;
	this.cols = cols;
	elements = new double[rows][cols];
    }

    /**
     * Konstruktor koji stvara matricu zadanih dimenzija, prima polje elemenata od kojih se stvara matrica te
     * varijablu koja označava može li se koristiti predano polje ili treba stvarati novo polje koje ne dijeli
     * ništa s početnim poljem.
     * 
     * @param rows
     *            Broj redaka matrice
     * @param cols
     *            Broj stupaca matrice
     * @param array
     *            Polje koje sadrži elemente matrice
     * @param useGiven
     *            ako je <code>true</code> tada se dano polje koristi kao polje matrice, inače se kreira novo
     *            polje
     */
    public Matrix(final int rows, final int cols, final double[][] array, final boolean useGiven) {
	if (rows < 1 || cols < 1) {
	    throw new IllegalArgumentException("Nije moguće kreirati takvu matricu");
	}
	this.rows = rows;
	this.cols = cols;
	if (useGiven) {
	    elements = array.clone();
	} else {
	    elements = new double[rows][cols];
	    for (int i = 0; i < rows; i++) {
		for (int j = 0; j < cols; j++) {
		    elements[i][j] = array[i][j];
		}
	    }
	}
    }

    @Override
    public final int getRowsCount() {
	return this.rows;
    }

    @Override
    public final int getColsCount() {
	return this.cols;
    }

    @Override
    public final double get(final int row, final int col) {
	if (row > this.getRowsCount() - 1 || col > this.getColsCount() - 1 || row < 0 || col < 0) {
	    throw new IllegalArgumentException("U matrici ne postoji element s tim indeksom");
	}
	return elements[row][col];
    }

    @Override
    public final IMatrix set(final int row, final int col, final double value) {
	if (row > this.getRowsCount() - 1 || col > this.getColsCount() - 1 || row < 0 || col < 0) {
	    throw new IllegalArgumentException("U matrici ne postoji element s tim indeksom");
	}
	elements[row][col] = value;
	return this;
    }

    @Override
    public final IMatrix copy() {
	IMatrix novaMatrica = new Matrix(this.getRowsCount(), this.getColsCount(), this.elements, false);
	return novaMatrica;
    }

    @Override
    public final IMatrix newInstance(final int rows, final int col) {
	if (rows < 1 || col < 1) {
	    throw new IllegalArgumentException("Nije moguće kreirati takvu matricu");
	}
	IMatrix novaMatrica = new Matrix(rows, col);
	return novaMatrica;
    }

    /**
     * Parsira ulaz tako što prvo zamijeni sve praznine jednom prazninom Nakon toga u polje stringova razdvoji
     * matricu po redcima gdje je znak | te kasnije ukloni sve praznine te tako svaki broj postane string.
     * 
     * @param ulaz
     *            String na temelju kojeg se stvara matrica
     * @return Stvorenu matricu
     */
    public static Matrix parseSimple(String ulaz) {
	String niz = ulaz;
	// Gdje je više praznine pretvori u jednu
	niz = niz.replaceAll("\\|", "\\| ");
	niz = niz.replaceAll(" +", " ");
	// Podijeli niz gdje je znak koji je u ODVAJANJEREDAKA tako da se dobiju
	// redci matrice
	String[] redciMatrice = niz.split(ODVAJANJEREDAKA);
	// U redku polja je zapisan svaki redak, a u stupcima su pojedini
	// elementi matrice
	String[][] elementiMatrice = new String[redciMatrice.length][redciMatrice[0].length()];

	for (int i = elementiMatrice.length - 1; i >= 0; i--) {
	    elementiMatrice[i] = redciMatrice[i].split(" ");
	}

	double[][] polje = new double[elementiMatrice.length][elementiMatrice[0].length];
	for (int i = elementiMatrice.length - 1; i >= 0; i--) {
	    for (int j = elementiMatrice[i].length - 1; j >= 0; j--) {
		if (!elementiMatrice[i][j].isEmpty()) {
		    try {
			polje[i][j] = Double.parseDouble(elementiMatrice[i][j]);
		    } catch (NumberFormatException e) {
			System.out.println("Ulazni znakovi " + ulaz
				+ " ne odgovaraju ispravnom formatu inicijalizacije!");
			System.exit(0);
		    }
		}
	    }
	}
	return new Matrix(polje.length, polje[0].length, polje, true);
    }
}
