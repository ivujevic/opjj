/**
 * Paket u kojem se nalaze svi razredi potrebni za rad s matricama i vektorima
 */
package hr.fer.zemris.linearna;

import java.text.DecimalFormat;

/**
 * Abstraktni razred koji implementira funkcije iz IVector za rad s vektorima.
 * 
 */
public abstract class AbstractVector implements IVector {

    /**
     * prvi element vektora ili matrice.
     */
    private static final int PRVIELEMENT = 0;

    /**
     * drugi element vektora ili matrice.
     */
    private static final int DRUGIELEMENT = 1;

    /**
     * treći element vektora ili matrice.
     */
    private static final int TRECIELEMENT = 2;

    /**
     * Broj redaka u matrici.
     */
    private static final int BROJREDAKA = 1;

    /**
     * Broj stupaca u matrici.
     */
    private static final int BROJSTUPACA = 1;

    /**
     * Zaokruživanje na željeni broj decimala.
     */
    private static final int PRECIZNOSTZAOK = 3;

    /**
     * Dimenzije vektora za skalarni produkt.
     */
    private static final int DIM = 3;

    /**
     * Vraća element vektora na poziciji <code>index</code>.
     * 
     * @param index
     *            Pozicija elemente u vektoru
     * @return Element koji je na traženoj poziciji
     */
    public abstract double get(int index);

    /**
     * Postavalja element vektora na poziciji <code>index</code> na vrijednost <code>value</code>.
     * 
     * @param index
     *            Pozicija na koju se postavlja vrijednost
     * @param value
     *            Vrijednost na koju se postavalja
     * @return Referenca na vektor.
     */
    public abstract IVector set(int index, double value);

    /**
     * Metoda koja određuje od koliko se elemenata zadani vektor sastoji.
     * 
     * @return Dimenzije vektora
     */
    public abstract int getDimension();

    /**
     * Stvara novu kopiju danog vektora. Kopija ne dijeli ništa s originalnim vektorom.
     * 
     * @return Referenca na kopirani vektor
     */
    public abstract IVector copy();

    /**
     * Stvara novi vektor zadanih dimenzija čiji su svi elementi 0.
     * 
     * @param dimension
     *            vektora
     * @return Referenca na novi vektor
     */
    public abstract IVector newInstance(int dimension);

    @Override
    public final IVector add(final IVector other) {
	if (this.getDimension() != other.getDimension()) {
	    throw new IncompatibleOperandException("Navedeni vektori se ne mogu zbrajati");
	}
	for (int i = this.getDimension() - 1; i >= 0; i--) {
	    this.set(i, this.get(i) + other.get(i));
	}
	return this;
    }

    @Override
    public final IVector nAdd(final IVector other) {
	if (this.getDimension() != other.getDimension()) {
	    throw new IncompatibleOperandException("Navedeni vektori se ne mogu zbrajati");
	}
	return this.copy().add(other);
    }

    @Override
    public final IVector sub(final IVector other) {

	if (this.getDimension() != other.getDimension()) {
	    throw new IncompatibleOperandException("Navedeni vektori se ne mogu oduzimati");
	}
	for (int i = this.getDimension() - 1; i >= 0; i--) {
	    this.set(i, this.get(i) - other.get(i));
	}
	return this;
    }

    @Override
    public final IVector nSub(final IVector other) {
	if (this.getDimension() != other.getDimension()) {
	    throw new IncompatibleOperandException("Navedeni vektori se ne mogu oduzimati");
	}
	return this.copy().sub(other);
    }

    @Override
    public final IVector scalarMultiply(final double byValue) {
	for (int i = this.getDimension() - 1; i >= 0; i--) {
	    this.set(i, this.get(i) * byValue);
	}
	return this;
    }

    @Override
    public final IVector nScalarMultiply(final double byValue) {
	return this.copy().scalarMultiply(byValue);
    }

    @Override
    public final double norm() {
	double zbrojKvadrata = 0;
	double vrijednost;
	for (int i = this.getDimension() - 1; i >= 0; i--) {
	    vrijednost = this.get(i);
	    zbrojKvadrata += vrijednost * vrijednost;
	}
	return Math.sqrt(zbrojKvadrata);
    }

    @Override
    public final IVector normalize() {
	double norma = this.norm();
	if (norma == 0) {
	    return this;
	}
	for (int i = this.getDimension() - 1; i >= 0; i--) {
	    this.set(i, this.get(i) / norma);
	}
	return this;
    }

    @Override
    public final IVector nNormalize() {
	return this.copy().normalize();
    }

    @Override
    public final double cosine(final IVector other) {
	if (this.getDimension() != other.getDimension()) {
	    throw new IncompatibleOperandException();
	}
	return scalarProduct(other) / (this.norm() * other.norm());
    }

    @Override
    public final double scalarProduct(final IVector other) {
	double zbrojUmnozaka = 0;
	if (this.getDimension() != other.getDimension()) {
	    throw new IncompatibleOperandException("Vektori trebaju biti istih dimenzija");
	}
	for (int i = this.getDimension() - 1; i >= 0; i--) {
	    zbrojUmnozaka += this.get(i) * other.get(i);
	}
	return zbrojUmnozaka;
    }

    @Override
    public final IVector nVectorProduct(final IVector other) {

	if (this.getDimension() != DIM || other.getDimension() != DIM) {
	    throw new IncompatibleOperandException("Vektor treba biti u 3 dimenzije");
	}

	double[] polje = new double[DIM];
	polje[PRVIELEMENT] = this.get(PRVIELEMENT) * other.get(TRECIELEMENT) - other.get(PRVIELEMENT)
		* this.get(TRECIELEMENT);
	polje[DRUGIELEMENT] = -(this.get(0) * other.get(TRECIELEMENT) - other.get(PRVIELEMENT) * this.get(TRECIELEMENT));
	polje[DRUGIELEMENT] = this.get(PRVIELEMENT) * other.get(DRUGIELEMENT) - other.get(PRVIELEMENT)
		* this.get(DRUGIELEMENT);
	return new Vector(polje);
    }

    @Override
    public final IVector nFromHomogeneus() {
	double zadnjaKomponenta = this.get(this.getDimension() - 1);
	if (zadnjaKomponenta == 0) {
	    return this;
	}
	IVector noviVektor = newInstance(this.getDimension() - 1);
	for (int i = noviVektor.getDimension() - 1; i >= 0; i--) {
	    noviVektor.set(i, this.get(i) / zadnjaKomponenta);
	}
	return noviVektor;
    }

    @Override
    public final double[] toArray() {
	double[] polje = new double[this.getDimension()];
	for (int i = this.getDimension() - 1; i >= 0; i--) {
	    polje[i] = this.get(i);
	}
	return polje;
    }

    @Override
    public final IVector copyPart(final int n) {

	if (n < 0) {
	    throw new IllegalArgumentException("Ne može se kopirati manje od 0 elemenata");
	}

	IVector noviVektor = newInstance(n);
	int velicinaPocetnogVektora = this.getDimension();

	for (int i = n - 1; i >= 0; i--) {
	    if (i > (velicinaPocetnogVektora - 1)) {
		noviVektor.set(i, PRVIELEMENT);
	    } else {
		noviVektor.set(i, this.get(i));
	    }
	}
	return noviVektor;
    }

    @Override
    public final IMatrix toRowMatrix(final boolean liveView) {
	if (liveView) {
	    return new MatrixVectorView(this, true);
	}
	double[][] polje = new double[BROJREDAKA][this.getDimension()];
	for (int i = this.getDimension() - 1; i >= 0; i--) {
	    polje[PRVIELEMENT][i] = this.get(i);
	}
	return new Matrix(BROJREDAKA, this.getDimension(), polje, true);
    }

    @Override
    public final IMatrix toColumnMatrix(final boolean liveView) {
	if (liveView) {
	    return new MatrixVectorView(this, false);
	}
	double[][] polje = new double[this.getDimension()][1];
	for (int i = this.getDimension() - 1; i >= 0; i--) {
	    polje[i][PRVIELEMENT] = this.get(i);
	}
	return new Matrix(this.getDimension(), BROJSTUPACA, polje, true);
    }

    @Override
    public final String toString() {
	return this.toString(PRECIZNOSTZAOK);
    }

    /**
     * Metoda koja zaokružuje broj na zadani broj decimala.
     * 
     * @param precision
     *            Broj decimala na koji se zaokružuje
     * @return Rezultat <code>String</code> tipa
     */
    public final String toString(final int precision) {
	StringBuilder builder = new StringBuilder();
	builder.append("0.");
	for (int i = 0; i < precision; i++) {
	    builder.append("0");
	}
	DecimalFormat df = new DecimalFormat(builder.toString());
	builder.delete(0, builder.length());
	builder.append("( ");
	int dimenzija = this.getDimension();
	for (int i = 0; i < dimenzija; i++) {
	    String pom = df.format(this.get(i));
	    pom = pom.replace(",", ".");
	    pom = pom.replace(",", ".");
	    builder.append(pom);
	    if (i < dimenzija - 1) {
		builder.append(", ");
	    }
	}
	builder.append(")");
	return builder.toString();
    }
}
