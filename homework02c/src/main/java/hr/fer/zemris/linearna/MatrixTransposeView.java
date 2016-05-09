/**
 * Paket u kojem se nalaze svi razredi potrebni za rad s matricama i vektorima
 */
package hr.fer.zemris.linearna;

/**
 * Razred koji radi transponiranje matrice u <code>živom</code> pogledu.
 * 
 */
public class MatrixTransposeView extends AbstractMatrix {

    /**
     * Referenca na matricu koja se prima kao original.
     */
    private IMatrix novaMatrica;

    /**
     * Konstruktor koji prima referencu na originalnu matricu.
     * 
     * @param matrica
     *            Referenca na originalnu matricu
     */
    public MatrixTransposeView(final IMatrix matrica) {
	novaMatrica = matrica;
    }

    @Override
    public final int getRowsCount() {
	return this.novaMatrica.getColsCount();
    }

    @Override
    public final int getColsCount() {
	return this.novaMatrica.getRowsCount();
    }

    @Override
    public final double get(final int row, final int col) {
	return this.novaMatrica.get(col, row);
    }

    @Override
    public final IMatrix set(final int row, final int col, final double value) {
	novaMatrica.set(col, row, value);
	return this;
    }

    @Override
    public final IMatrix copy() {
	IMatrix matrica = new Matrix(novaMatrica.getRowsCount(), novaMatrica.getColsCount(), novaMatrica.toArray(),
		false);
	return matrica;
    }

    @Override
    public final IMatrix newInstance(final int rows, final int col) {
	IMatrix novaMatrica = new Matrix(rows, col);
	return novaMatrica;
    }

    @Override
    public final double[][] toArray() {
	double[][] array = new double[this.getRowsCount()][this.getColsCount()];
	for (int i = this.getRowsCount() - 1; i >= 0; i--) {
	    for (int j = this.getColsCount() - 1; j >= 0; j--) {
		array[i][j] = this.get(i, j);
	    }
	}
	return array;
    }
}
