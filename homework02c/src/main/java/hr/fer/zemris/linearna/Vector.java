/**
 * Paket koji sadrži sve razrede potrebne za rad s vektorima i matricama
 */
package hr.fer.zemris.linearna;

/**
 * Razred koji nasljeđuje AbstractVector. Implementira metode koje su abstraktne u razredu AbstractVector.
 * 
 */
public class Vector extends AbstractVector {

    /**
     * Polje koje predstavlja komponente vektora.
     */
    private double[] elements;

    /**
     * Varijabla koja predstavlja veličinu vektora.
     */
    private int dimension;

    /**
     * Zastavica koja pokazuje je li se vektora koristi samo za čitanje ili se mogu raditi i ostale operacije
     * Ako je vrijednost <code>true</code> tada može samo čitanje, inače može sve.
     */
    private boolean redOnly = false;

    /**
     * Konstrkutor koji prima proizvoljan broj <code>double</code> elemenata od koji se stvara vektor.
     * 
     * @param elems
     *            Elementi vektora
     */
    public Vector(final double... elems) {
	this.dimension = elems.length;
	elements = new double[dimension];
	System.arraycopy(elems, 0, elements, 0, dimension);
    }

    /**
     * Konstrktor koji prima polje elemenata te preko varijable <code>useGiven</code> određuje treba li
     * stvarati novo polje ili može koristiti predano polje. Na temelju tog polja stvara novi vektor koji se u
     * ovisnosti o varijabli <code>readOnly</code> može mijenjati ili ne.
     * 
     * @param readOnly
     *            Ako je <code>true</code> tada se sadržaj vektora ne smije mijenjati
     * @param useGiven
     *            ako je <code>true</code> predano polje se koristi kao polje koje sprema elemente vektora
     *            inače se stvara novo polje
     * @param elems
     *            Elementi vektora
     */
    public Vector(final boolean readOnly, final boolean useGiven, final double... elems) {
	this.dimension = elems.length;
	this.redOnly = readOnly;
	elements = new double[this.dimension];
	if (useGiven) {
	    this.elements = elems.clone();
	} else {
	    System.arraycopy(elems, 0, elements, 0, elems.length);
	}

    }

    @Override
    public final double get(final int index) {
	if (index >= 0 && index < this.dimension) {
	    return elements[index];
	} else {
	    throw new IllegalArgumentException("Unesen je krivi index polja");
	}
    }

    @Override
    public final IVector set(final int index, final double value) {
	if (this.redOnly) {
	    throw new IllegalAccessError("Zabranjeno je mijenjanje vektora");
	}
	if (index >= 0 && index < this.dimension) {
	    elements[index] = value;
	} else {
	    throw new IllegalArgumentException("Unesen je krivi index polja");
	}
	return this;
    }

    @Override
    public final int getDimension() {
	return this.dimension;
    }

    @Override
    public final IVector copy() {
	return new Vector(this.elements);
    }

    @Override
    public final IVector newInstance(final int dimension) {
	if (dimension < 1) {
	    throw new IllegalArgumentException("Takav vektor se ne može stvoriti");
	}

	double[] polje = new double[dimension];
	return new Vector(polje);
    }

    /**
     * Parsira <code>nizKomponenti</code> u kojem se pohranjeni elementi vektora. Elementi vektora su odvojeni
     * s jednim ili više razmaka.
     * 
     * @param nizKomponenti
     *            Niz koji se parsira
     * @return Stvoreni vektor
     */
    public static Vector parseSimple(final String nizKomponenti) {
	String pomNizKomponenti = nizKomponenti;
	pomNizKomponenti = pomNizKomponenti.replaceAll(" +", " ");
	final String[] komponente = pomNizKomponenti.split(" ");
	double[] komponenteVektora = new double[komponente.length];
	for (int i = komponente.length - 1; i >= 0; i--) {
	    if (!komponente[i].isEmpty()) {
		try {
		    komponenteVektora[i] = Double.parseDouble(komponente[i]);
		} catch (NumberFormatException e) {
		    System.out.println("Ulazni znakovi " + nizKomponenti
			    + " ne odgovaraju ispravnom formatu inicijalizacije!");
		    System.exit(0);
		}
	    }
	}
	return new Vector(komponenteVektora);
    }
}
