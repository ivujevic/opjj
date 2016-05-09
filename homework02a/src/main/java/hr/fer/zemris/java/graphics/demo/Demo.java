/**
 * Paket koji sadrži razred koji u sebi ima metode <code>main</code>
 */
package hr.fer.zemris.java.graphics.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;
import hr.fer.zemris.java.graphics.shapes.GeometricShape;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.shapes.Square;
import hr.fer.zemris.java.graphics.shapes.Triangle;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

public class Demo {
	/**
	 * @param args
	 */

	/**
	 * Varijabla koja predstavlja raster tipa <code>BWRasterMem</code>
	 */
	private static BWRasterMem raster;

	/**
	 * Varijabla koja prestavlja iscrtavanje tipa <code>SimpleRasterView</code>
	 */
	private static SimpleRasterView view;

	/**
	 * Polje koje pamti ulazne likove
	 */
	private static GeometricShape[] likoviUlaz;

	/**
	 * Glavna metoda koja se pokreće prilikom pokretanja programa
	 * 
	 * @param args
	 *            Visina i širina rastera
	 */
	public static void main(String[] args) {
		if (args.length == 2) {
			raster = new BWRasterMem(Integer.parseInt(args[0]),
					Integer.parseInt(args[1]));
		} else if (args.length == 1) {
			raster = new BWRasterMem(Integer.parseInt(args[0]),
					Integer.parseInt(args[0]));
		} else {
			throw new IllegalArgumentException(
					"Unijeli ste krive parametre za veličinu rastera");
		}
		Scanner sc = new Scanner(System.in,"UTF-8");
		int brojRedaka = sc.nextInt();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		int preostalo = brojRedaka;
		String[] redak = new String[brojRedaka];
		likoviUlaz = new GeometricShape[brojRedaka];

		while (preostalo > 0) {
			try {
				redak[brojRedaka - preostalo] = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			preostalo--;
		}

		view = new SimpleRasterView();
		for (int i = 0; i < redak.length; i++) {
			izvrsiNaredbu(redak[i], i);
		}
		for (int i = 0; i < brojRedaka; i++) {
			if (likoviUlaz[i] != null) {
				likoviUlaz[i].draw(raster);
			} else {
				raster.stanjeFlipMode = !raster.stanjeFlipMode;
			}
		}
		view.produce(raster);
	}

	/**
	 * Metoda koja prepoznaje o kojoj se naredbi radi te zadani lik sprema u
	 * polje
	 * 
	 * @param redak
	 *            Učitani redak s naredbom i parametrima
	 * @param brojac
	 *            Broj do sada učitanih naredbi
	 */
	private static void izvrsiNaredbu(String redak, int brojac) {
		String[] naredba = new String[7];
		naredba = redak.split(" ");
		switch (naredba[0].toUpperCase()) {
		case "FLIP":
			likoviUlaz[brojac] = null;
			break;
		case "RECTANGLE":
			try {
				likoviUlaz[brojac] = new Rectangle(
						Integer.parseInt(naredba[1]),
						Integer.parseInt(naredba[2]),
						Integer.parseInt(naredba[3]),
						Integer.parseInt(naredba[4]));
			} catch (Throwable e) {
				System.out.println("Pogreška u parametrima");
				System.exit(1);
			}
			break;
		case "SQUARE":
			try {
				likoviUlaz[brojac] = new Square(Integer.parseInt(naredba[1]),
						Integer.parseInt(naredba[2]),
						Integer.parseInt(naredba[3]));
			} catch (Throwable e) {
				System.out.println("Pogreška u parametrima");
				System.exit(1);
			}
			break;
		case "CIRCLE":
			try {
				likoviUlaz[brojac] = new Circle(Integer.parseInt(naredba[1]),
						Integer.parseInt(naredba[2]),
						Integer.parseInt(naredba[3]));
			} catch (Throwable e) {
				System.out.println("Pogreška u parametrima");
				System.exit(1);
			}
			break;
		case "ELLIPSE":
			try {
				likoviUlaz[brojac] = new Ellipse(Integer.parseInt(naredba[1]),
						Integer.parseInt(naredba[2]),
						Integer.parseInt(naredba[3]),
						Integer.parseInt(naredba[4]));
			} catch (Throwable e) {
				System.out.println("Pogreška u parametrima");
				System.exit(1);
			}
			break;
		case "TRIANGLE":
			try {
				likoviUlaz[brojac] = new Triangle(Integer.parseInt(naredba[1]),
						Integer.parseInt(naredba[2]),
						Integer.parseInt(naredba[3]),
						Integer.parseInt(naredba[4]),
						Integer.parseInt(naredba[5]),
						Integer.parseInt(naredba[6]));
			} catch (Throwable e) {
				System.out.println("Pogreška u parametrima");
				System.exit(1);
			}
			break;
		default:
			System.out.println("Unijeli ste krivu naredbu");
			System.exit(1);

		}
	}
}