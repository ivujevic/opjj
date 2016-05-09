package hr.fer.zemris.linearna.Demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;

/**
 * Razred u kojem je rješenje trećeg zadatka iz domaće zadaće.
 * 
 * @author Ivan
 * 
 */
public class Prog3 {

    /**
     * Metoda koja se poziva prilikom pokretanja programa. Rješava sustav dviju jednadžbi s dvije nepoznanice.
     */
    public static void main(String[] args) {
	IMatrix a = Matrix.parseSimple("3 5 | 2 10");
	IMatrix r = Matrix.parseSimple("2 | 8");
	IMatrix v = a.nInvert().nMultiply(r);
	System.out.println("Rjesenje sustava je: ");
	System.out.println(v);

    }

}
