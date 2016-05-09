package hr.fer.zemris.linearna.Demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;

/**
 * Razred u kojem je rješenje četvrtog zadatka iz domaće zadaće.
 */
public class Prog4 {

    /**
     * Metoda koja se poziva prilikom pokretanja programa. Rješava se sustav tri jednadžbe s tri nepoznanice.
     */
    public static void main(String[] args) {

	IMatrix m1 = Matrix.parseSimple("1 5 3 | 0 0 8 | 1 1 1");
	IMatrix m2 = Matrix.parseSimple("3 | 4 | 1");
	IMatrix r = m1.nInvert().nMultiply(m2);
	System.out.println(r);

    }

}
