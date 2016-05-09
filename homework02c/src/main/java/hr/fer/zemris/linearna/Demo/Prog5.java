package hr.fer.zemris.linearna.Demo;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

import java.util.Scanner;

/**
 * Razred u kojem je rješenje petog programa iz domaće zadaće. Zadatak se odnosi na vektore.
 */
public class Prog5 {

    /**
     * Metoda koja se poziva prilikom pokretanja programa. Unose se elementi dvaju vektora i ispisuje se
     * reflektirani vektor koji je nastao refleksijom prvog vektora u odnosu na drugi vektor
     */
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in, "UTF-8");

	System.out.println("Unesite elemente prvog vektora odvojene razmakom");
	String ulaz = sc.nextLine();
	IVector n = Vector.parseSimple(ulaz);
	System.out.println("Unesite elemente drugog vektora odvojene razmakom");
	ulaz = sc.nextLine();
	IVector m = Vector.parseSimple(ulaz);

	sc.close();

	IVector r = n.normalize().scalarMultiply((m.scalarProduct(n)) / n.norm() * 2).nSub(m);

	System.out.println(r);
    }

}
