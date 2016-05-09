/**
 * paket u kojem se nalaze svi razredi potrebni za rad s vektorima i matricama.
 */
package hr.fer.zemris.linearna;

/**
 * Razred koji služi za prikazivanje matrica kao vektora u <code>živom</code> pogledu.
 * 
 */
public class VectorMatrixView extends AbstractVector {

    /**
     * Ako je <code>true</code> tada je predana matrica redčana inače je stupčana.
     */
    private boolean rowMatrix;

    /**
     * Referenca na danu matricu.
     */
    private IMatrix novaMatrica;

    /**
     * Konstrkutor koji prima referencu na matricu koja se želi prikazati kao vektor.
     * 
     * @param original
     *            Referenca na matricu
     */
    public VectorMatrixView(final IMatrix original) {
	if (original.getRowsCount() == 1) {
	    rowMatrix = true;
	} else if (original.getColsCount() == 1) {
	    rowMatrix = false;
	} else {
	    throw new IllegalAccessError("Dana matrica se ne može pretvoriti u vektor!");
	}
	novaMatrica = original;
    }

    @Override
    public final double get(final int index) {
	if (rowMatrix) {
	    return novaMatrica.get(0, index);
	} else {
	    return novaMatrica.get(index, 0);
	}
    }

    @Override
    public final IVector set(final int index, final double value) {
	if (rowMatrix) {
	    return novaMatrica.set(0, index, value).toVector(true);
	} else {
	    return novaMatrica.set(index, 0, value).toVector(true);
	}
    }

    @Override
    public final int getDimension() {
	if (rowMatrix) {
	    return novaMatrica.getColsCount();
	} else {
	    return novaMatrica.getRowsCount();
	}
    }

    @Override
    public final IVector copy() {
	double[] elementiMatrice = novaMatrica.toVector(true).toArray();
	return new Vector(elementiMatrice);
    }

    @Override
    public final IVector newInstance(final int dimension) {
	double[] elementiMatrice = novaMatrica.toVector(true).toArray();
	return new Vector(elementiMatrice);
    }

}
