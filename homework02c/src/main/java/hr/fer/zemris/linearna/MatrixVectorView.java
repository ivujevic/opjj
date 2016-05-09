/**
 * Paket u kojem se nalaze svi razredi potrebni za rad s matricama i vektorima
 */
package hr.fer.zemris.linearna;

/**
 * Razred koji služi za prikazivanje vektora kao matrica u <code>živom</code> pogledu.
 * 
 */
public class MatrixVectorView extends AbstractMatrix {

    /**
     * Varijabla koja pokazuje je li se dani vektor prikazuje kao redčana ili stupčana matrica ako je
     * vrijednost <code>true</code> tada je kao redčana, inače kao stupčana.
     */
    private boolean asRowMatrix;

    /**
     * Referenca na vektor koji se prikazuje kao matrica.
     */
    private IVector vektor;

    /**
     * Konstruktor koji prima referencu na vektor te dani vektor pretvara u redčana ili u stupčanu matricu u
     * ovisnosti o tome što je zapisani u varijabli <code>asRowMatrix</code>.
     * 
     * @param original
     *            Referenca na originalni vektor
     * @param asRowMatrix
     *            Varijabla koja je <code>true</code> ako se vektor prikazuje kao redčana matrica, inače je
     *            <code>false</code>
     */
    public MatrixVectorView(final IVector original, final boolean asRowMatrix) {
	vektor = original;
	this.asRowMatrix = asRowMatrix;
    }

    @Override
    public final int getRowsCount() {
	if (asRowMatrix) {
	    return 1;
	} else {
	    return vektor.getDimension();
	}
    }

    @Override
    public final int getColsCount() {
	if (asRowMatrix) {
	    return vektor.getDimension();
	} else {
	    return 1;
	}
    }

    @Override
    public final double get(final int row, final int col) {
	if (asRowMatrix) {
	    return vektor.get(col);
	} else {
	    return vektor.get(row);
	}
    }

    @Override
    public final IMatrix set(final int row, final int col, final double value) {
	if (asRowMatrix) {
	    return vektor.set(col, value).toRowMatrix(true);
	} else {
	    return vektor.set(row, value).toColumnMatrix(true);
	}
    }

    @Override
    public final IMatrix copy() {
	double[][] elementiMatrice = this.toArray();
	return new Matrix(this.getRowsCount(), this.getColsCount(), elementiMatrice, false);
    }

    @Override
    public final IMatrix newInstance(final int rows, final int col) {
	return new Matrix(rows, col);
    }

}
