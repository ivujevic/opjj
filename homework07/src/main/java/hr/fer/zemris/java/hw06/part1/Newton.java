/**
 * 
 */
package hr.fer.zemris.java.hw06.part1;

import hr.fer.zemris.java.hw06.part1.fraktali.NewtonRaphson;
import hr.fer.zemris.java.tecaj_06.fractals.FractalViewer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalProducer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalResultObserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Program koji prikazuje Newton-Rapshon fraktale.
 * 
 * @author Ivan
 * 
 */
public class Newton {

	/**
	 * Polinom prikazan preko korijena koje korisnik unese.
	 */
	private static ComplexRootedPolynomial rootedPolynom;

	/**
	 * Metoda koja se pokreće prilikom pokretanja programa.
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner sc = new Scanner(System.in, "UTF-8");

		int numberOfRoots = 1;
		List<Complex> list = new LinkedList<>();

		while (true) {
			System.out.print("Root " + numberOfRoots + ">");
			numberOfRoots++;
			String line = sc.nextLine();
			if (line.equals("done")) {
				break;
			}
			list.add(parse(line));
		}
		sc.close();
		int i = 0;
		Complex[] array = new Complex[list.size()];
		for (Complex elem : list) {
			array[i] = elem;
			i++;
		}
		rootedPolynom = new ComplexRootedPolynomial(array);
		FractalViewer.show(new Generiranje());
	}

	/**
	 * Metoda koja prima string i pretvara ga u kompleksni broj
	 * 
	 * @param line
	 *            String koji je potrebno pretvoriti
	 * @return Kompleksni broj
	 */
	private static Complex parse(final String line) {

		Pattern pattern = Pattern.compile("([+-]?\\d*\\.*\\d+)([+-]?\\d*\\.*\\d+)i");
		StringBuilder builder = new StringBuilder();
		builder.append(line);
		// Ako je unesen samo imaginarni dio broja, realni postavi na 0
		if (builder.indexOf("i") == 0) {
			builder.insert(0, "0+");
		}
		else if (builder.indexOf("i") == 1) {
			builder.insert(0, "0");
		}
		// Ako je unesena samo imaginarna jedinica tretiraj kao da piše 1i
		if (builder.indexOf("i") == builder.length() - 1) {
			builder.append("1");
		}
		// Zamijeni poziciju imaginarnom dijelu i imaginarnoj jedinici
		if (builder.indexOf("i") != -1) {
			builder = builder.deleteCharAt(builder.indexOf("i"));
			builder.append("i");
		}
		else {
			builder.append("+0i");
		}

		Matcher matcher = pattern.matcher(builder.toString().replace(" ", ""));
		matcher.matches();
		double re = Double.parseDouble(matcher.group(1));
		double im = Double.parseDouble(matcher.group(2));
		return new Complex(re, im);
	}

	/**
	 * Razred u kojem se nalaze metoda koje prikazuju fraktale.
	 */
	static class Generiranje implements IFractalProducer {

		/**
		 * Stvori jedan Executor
		 */
		private static final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors(), new ThreadFactory() {

			@Override
			public Thread newThread(final Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
		});

		@Override
		public void produce(final double reMin, final double reMax, final double imMin, final double imMax, final int width, final int height,
				final long requestNo, final IFractalResultObserver observer) {

			short[] data = new short[height * width];

			List<ThreadJob> jobs = new ArrayList<>();
			int heightTape = 8 / Runtime.getRuntime().availableProcessors();
			int ymin = 0;

			while (true) {
				int ymax = Math.min(ymin + heightTape - 1, height - 1);
				jobs.add(new ThreadJob(reMin, reMax, imMin, imMax, width, height, ymin, ymax, data));
				ymin = ymax + 1;
				if (ymin >= height) {
					break;
				}

			}
			List<Future<Void>> results = null;

			try {
				results = service.invokeAll(jobs);
			} catch (InterruptedException e) {
			}

			for (Future<Void> result : results) {
				try {
					result.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}

			observer.acceptResult(data, (short) (rootedPolynom.toComplexPolynom().order() + 1), requestNo);
		}

	}

	static class DaemonThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(final Runnable r) {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;
		}

	}

	/**
	 * Razred u kojem se poziva metoda koja će računati fraktale za predane parametre
	 * 
	 * @author Ivan
	 * 
	 */
	static class ThreadJob implements Callable<Void> {
		private double reMin;
		private double reMax;
		private double imMin;
		private double imMax;
		private int width;
		private int height;
		private int ymin;
		private int ymax;
		private short[] data;

		public ThreadJob(final double reMin, final double reMax, final double imMin, final double imMax, final int width, final int height,
				final int ymin, final int ymax, final short[] data) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.ymin = ymin;
			this.ymax = ymax;
			this.data = data;
		}

		@Override
		public Void call() throws Exception {
			NewtonRaphson.racunaj(reMin, reMax, imMin, imMax, width, height, ymin, ymax, data, rootedPolynom);
			return null;
		}
	}
}
