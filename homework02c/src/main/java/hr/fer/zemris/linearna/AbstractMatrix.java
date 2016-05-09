/**
 * Paket u kojem se nalaze svi razredi potrebni za rad s matricama i vektorima
 */
package hr.fer.zemris.linearna;

import java.text.DecimalFormat;

/**
 * Razred koji implementira sučelje <code>IMatrix</code> Sadrži implementaciju metoda koje omogućuju rad s
 * matricama. To su operacije zbrajanja, oduzimanja, množenje skalarom, matrično množenje, računanje
 * determinante, stvaranje minore, transponiranje, inverz matrice, pretvaranje matrice u polje, pretvaranje u
 * vektor.
 * 
 */

public abstract class AbstractMatrix implements IMatrix {

    /**
     * Zaokruživanje na željeni broj decimala.
     */
    private static final int PRECIZNOSTZAOK = 3;

    /**
     * Metoda koja vraća broj redaka u matrici.
     * 
     * @return broj redaka matrice
     */
    public abstract int getRowsCount();

    /**
     * Metoda koja vraća broj stupaca u matrici.
     * 
     * @return broj stupaca matrice.
     */
    public abstract int getColsCount();

    /**
     * Metoda koja vraća element matrice na poziciji (row,col). Ako je zadana nedozvoljena pozicija baca
     * iznimku
     * 
     * @param row
     *            Redak u kojem se element nalazi
     * @param col
     *            Stupac u kojem se element nalazi
     * @return element na poziciji (row,col)
     */
    public abstract double get(int row, int col);

    /**
     * Metoda koja postavlja element matrice (row,col) na vrijednost value.
     * 
     * @param row
     *            Redak u kojem se nalazi element kojem se postavlja vrijednost
     * @param col
     *            Stupac u kojem se nalazi element kojem se postavlja vrijednost
     * @param value
     *            Vrijednost na koju se postavlja
     * @return Referenca na matricu
     */
    public abstract IMatrix set(int row, int col, double value);

    /**
     * Kopira matricu stvarajući novu matricu koja ne dijeli polje elemenata sa početnom.
     * 
     * @return Referenca na kopiranu matricu
     */
    public abstract IMatrix copy();

    /**
     * Kreira novu matricu i popunjava je nulama. Ako se dogodi da je parametar nevažeći baca iznimku
     * 
     * @param rows
     *            Broj redaka željene matrice
     * @param col
     *            Broj stupaca željene matrice
     * @return Referenca na stvorenu matricu
     */
    public abstract IMatrix newInstance(int rows, int col);

    @Override
    public final IMatrix nTranspose(final boolean liveView) {
	if (liveView) {
	    IMatrix matrica = new MatrixTransposeView(this);
	    return matrica;
	} else {
	    IMatrix matrica = newInstance(this.getRowsCount(), this.getColsCount());
	    int brojRedaka = this.getColsCount();
	    int brojStupaca = this.getRowsCount();
	    for (int i = 0; i < brojRedaka; i++) {
		for (int j = 0; j < brojStupaca; j++) {
		    matrica.set(i, j, this.get(j, i));
		}
	    }
	    return matrica;
	}
    }

    @Override
    public final IMatrix add(final IMatrix other) {
	for (int i = this.getRowsCount() - 1; i >= 0; i--) {
	    for (int j = this.getColsCount() - 1; j >= 0; j--) {
		this.set(i, j, this.get(i, j) + other.get(i, j));
	    }
	}
	return this;
    }

    @Override
    public final IMatrix nAdd(final IMatrix other) {
	return this.copy().add(other);
    }

    @Override
    public final IMatrix sub(final IMatrix other) {
	for (int i = this.getRowsCount() - 1; i >= 0; i--) {
	    for (int j = this.getColsCount() - 1; j >= 0; j--) {
		this.set(i, j, this.get(i, j) - other.get(i, j));
	    }
	}
	return this;
    }

    @Override
    public final IMatrix nSub(final IMatrix other) {
	return this.copy().sub(other);
    }

    @Override
    public final IMatrix nMultiply(final IMatrix other) {
	IMatrix rezMatrica = newInstance(this.getRowsCount(), other.getColsCount());
	double zbroj = 0;
	for (int i = this.getRowsCount() - 1; i >= 0; i--) {
	    for (int j = other.getColsCount() - 1; j >= 0; j--) {
		for (int k = this.getColsCount() - 1; k >= 0; k--) {
		    zbroj += this.get(i, k) * other.get(k, j);
		}
		rezMatrica.set(i, j, zbroj);
		zbroj = 0;
	    }
	}
	return rezMatrica;
    }

    @Override
    public final double determinant() {
	double racunanje = 0;
	int predznak = 1;
	int velicina;
	if (this.getColsCount() != this.getRowsCount()) {
	    throw new IncompatibleOperandException();
	}
	if (this.getColsCount() == 1) {
	    return this.get(0, 0);
	}
	if (this.getColsCount() == 2) {
	    return this.get(0, 0) * this.get(1, 1) - this.get(1, 0) * this.get(0, 1);
	}
	velicina = this.getColsCount();
	for (int j = 0; j < velicina; j++) {
	    racunanje += predznak * (this.get(0, j)) * ((this.subMatrix(0, j, true).determinant()));
	    predznak *= -1;
	}
	return racunanje;
    }

    @Override
    public IMatrix subMatrix(final int row, final int col, final boolean liveView) {
	if (liveView) {
	    IMatrix matrica = new MatrixSubMatrixView(this, row, col);
	    return matrica;
	} else {
	    double[][] polje = new double[this.getRowsCount() - 1][this.getColsCount() - 1];
	    int brojRedaka = this.getRowsCount();
	    int brojStupaca = this.getColsCount();
	    for (int i = 0, fi = 0; i < brojRedaka; i++) {
		for (int j = 0, fj = 0; j < brojStupaca; j++) {
		    if (i == row) {
			fi = 1;
			break;
		    }
		    if (j == col) {
			fj = 1;
			continue;
		    }
		    if (fi == 0 && fj == 0) {
			polje[i][j] = this.get(i, j);
		    } else if (fi == 0 && fj == 1) {
			polje[i][j - 1] = this.get(i, j);
		    } else if (fi == 1 && fj == 0) {
			polje[i - 1][j] = this.get(i, j);
		    } else {
			polje[i - 1][j - 1] = this.get(i, j);
		    }
		}
	    }

	    IMatrix matrica = new Matrix(this.getRowsCount() - 1, this.getColsCount() - 1, polje, false);
	    return matrica;
	}
    }

    @Override
    public final IMatrix nInvert() {
	double[][] poljeKofaktora = new double[this.getRowsCount()][this.getColsCount()];
	int predznak = 1;
	int brojRedaka = this.getRowsCount();
	int brojStupaca = this.getColsCount();
	if (this.determinant() == 0) {
	    System.out.println("Matrica je singularna pa prema tome njen inverz ne postoji!");
	    return null;
	}
	for (int i = 0, odrediPredznak = 1; i < brojRedaka; i++, odrediPredznak *= -1) {
	    predznak = odrediPredznak;
	    for (int j = 0; j < brojStupaca; j++) {
		poljeKofaktora[i][j] = predznak * (this.subMatrix(i, j, true).determinant());
		predznak *= -1;
	    }
	}
	IMatrix matricaKofaktora = new Matrix(this.getRowsCount(), this.getColsCount(), poljeKofaktora, false);
	return matricaKofaktora.nTranspose(false).nScalarMultiply(1 / this.determinant());
    }

    @Override
    public double[][] toArray() {
	double[][] novoPolje = new double[this.getRowsCount()][this.getColsCount()];
	for (int i = this.getRowsCount() - 1; i >= 0; i--) {
	    for (int j = this.getColsCount() - 1; j >= 0; j--) {
		novoPolje[i][j] = this.get(i, j);
	    }
	}
	return novoPolje;
    }

    @Override
    public final String toString() {
	return toString(PRECIZNOSTZAOK);
    }

    @Override
    public final IVector toVector(final boolean liveView) {
	if (liveView) {
	    return new VectorMatrixView(this);
	}
	if (this.getRowsCount() == 1) {
	    double[][] poljeMatrica = this.toArray();
	    double[] poljeVektor = new double[this.getColsCount()];
	    for (int i = poljeMatrica[0].length - 1; i >= 0; i--) {
		poljeVektor[i] = this.get(0, i);
	    }
	    return new Vector(false, true, poljeVektor);
	} else if (this.getColsCount() == 1) {
	    double[][] poljeMatrica = this.toArray();
	    double[] poljeVektor = new double[this.getRowsCount()];
	    for (int i = poljeMatrica.length - 1; i >= 0; i--) {
		poljeVektor[i] = this.get(i, 0);
	    }
	    return new Vector(false, true, poljeVektor);
	}
	return null;
    }

    @Override
    public final IMatrix nScalarMultiply(final double value) {
	IMatrix novaMatrica = new Matrix(this.getRowsCount(), this.getColsCount());
	for (int i = this.getRowsCount() - 1; i >= 0; i--) {
	    for (int j = this.getColsCount() - 1; j >= 0; j--) {
		novaMatrica.set(i, j, this.get(i, j) * value);
	    }
	}
	return novaMatrica;
    }

    @Override
    public final IMatrix scalarMultiply(final double value) {
	for (int i = this.getRowsCount() - 1; i >= 0; i--) {
	    for (int j = this.getColsCount() - 1; j >= 0; j--) {
		this.set(i, j, this.get(i, j) * value);
	    }
	}
	return this;
    }

    /**
     * Metoda koja određuje preciznost na koju se zaokružuje određeni decimalni broj.
     * 
     * @param precision
     *            Preciznost na koju se zaokružuje
     * @return String koji predstavlja elemente matrice zaokružene na zadanu preciznost
     */
    public final String toString(final int precision) {
	StringBuilder spremnik = new StringBuilder();
	int brojRedaka = this.getRowsCount();
	int brojStupaca = this.getColsCount();
	spremnik.append("0.");
	for (int i = 0; i < precision; i++) {
	    spremnik.append("0");
	}
	DecimalFormat df = new DecimalFormat(spremnik.toString());
	spremnik.delete(0, spremnik.length());
	for (int i = 0; i < brojRedaka; i++) {
	    spremnik.append("[");
	    for (int j = 0; j < brojStupaca; j++) {
		String pom = df.format(this.get(i, j));
		pom = pom.replace(",", ".");
		pom = pom.replace(",", ".");
		spremnik.append(pom);
		if (j < brojStupaca - 1) {
		    spremnik.append(", ");
		}
	    }
	    spremnik.append("]\n");
	}
	return spremnik.toString();
    }

    @Override
    public final IMatrix makeIdentity() {
	if (this.getColsCount() != this.getRowsCount()) {
	    throw new IllegalAccessError("Dana matrica nije jedinična");
	}
	for (int i = this.getRowsCount() - 1; i >= 0; i--) {
	    for (int j = this.getColsCount() - 1; j >= 0; j--) {
		if (i == j) {
		    this.set(i, j, 1);
		} else {
		    this.set(i, j, 0);
		}
	    }
	}
	return this;
    }
}
