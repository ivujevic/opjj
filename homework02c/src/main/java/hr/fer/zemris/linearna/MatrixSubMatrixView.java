/**
 * Paket u kojem se nalaze svi razredi potrebni za rad s matricama i vektorima.
 */
package hr.fer.zemris.linearna;

/**
 * Razred koji se koristi prilikom izrade minora matrice u <code>živom</code> pogledu.
 */
public class MatrixSubMatrixView extends AbstractMatrix {

    /**
     * Indeksi redaka početne matrice.
     */
    private int[] rowIndexes;

    /**
     * Indeksi stupaca početne matrice.
     */
    private int[] colIndexes;

    /**
     * Referenca na matricu.
     */
    private IMatrix matrica;

    /**
     * Konstruktor koji prima referencu na originalnu matricu koja ima određen broj stupaca i redaka.
     * 
     * @param original
     *            Matrica kojoj se traži minora
     * @param row
     *            Redak u kojem se nalazi element čija se minora traži
     * @param col
     *            Stupac u kojem se nalazi element čija se minora traži
     */
    public MatrixSubMatrixView(final IMatrix original, final int row, final int col) {
	matrica = original;
	rowIndexes = new int[matrica.getRowsCount() - 1];
	colIndexes = new int[matrica.getColsCount() - 1];
	int brojRedaka = original.getRowsCount();
	int brojStupaca = original.getColsCount();
	for (int i = 0, f = 0; i < brojRedaka; i++) {
	    if (i == row) {
		f = 1;
	    } else if (f == 1) {
		rowIndexes[i - 1] = i;
	    } else {
		rowIndexes[i] = i;
	    }
	}
	for (int i = 0, f = 0; i < brojStupaca; i++) {
	    if (i == col) {
		f = 1;
	    } else if (f == 1) {
		colIndexes[i - 1] = i;
	    } else {
		colIndexes[i] = i;
	    }
	}
    }

    @Override
    public final int getRowsCount() {
	return rowIndexes.length;
    }

    @Override
    public final int getColsCount() {
	return colIndexes.length;
    }

    @Override
    public final double get(final int row, final int col) {
	return matrica.get(rowIndexes[row], colIndexes[col]);
    }

    @Override
    public final IMatrix set(final int row, final int col, final double value) {
	return matrica.set(rowIndexes[row], colIndexes[col], value);
    }

    @Override
    public final IMatrix copy() {
	return new Matrix(this.getRowsCount(), this.getColsCount(), matrica.toArray(), false);
    }

    @Override
    public final IMatrix newInstance(final int rows, final int col) {
	IMatrix novaMatrica = new Matrix(rows, col);
	return novaMatrica;
    }

    @Override
    public final IMatrix subMatrix(final int row, final int col, final boolean liveView) {
	if (liveView) {
	    return new MatrixSubMatrixView(this, row, col);
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
	    return new Matrix(this.getRowsCount() - 1, this.getColsCount() - 1, polje, false);
	}
    }
}