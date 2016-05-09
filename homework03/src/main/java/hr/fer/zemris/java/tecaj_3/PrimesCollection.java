package hr.fer.zemris.java.tecaj_3;

import java.util.Iterator;

/**
 * Razred koji implementira kolekciju prostih brojeva od 2 do <code>numberOfPrimes</code>
 * 
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * Broj prostih brojeva koji se žele ispisati
	 */
	int numbersOfPrimes;

	public PrimesCollection(int numbersOfPrimes) {
		this.numbersOfPrimes = numbersOfPrimes;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new MyIterator();
	}

	/**
	 * Razred koji implementira iterator pomoću kojeg će se ispisavati prosti brojevi.
	 * 
	 */
	private class MyIterator implements Iterator<Integer> {

		/**
		 * Broj preostalih prostih brojeva koje treba pronaći
		 */
		private int remainingNumbers;

		/**
		 * Zadnji pronađeni prosti broj.
		 */
		private int lastPrimeNumber = 1;

		public MyIterator() {
			this.remainingNumbers = numbersOfPrimes;
		}

		@Override
		public boolean hasNext() {
			return remainingNumbers > 0;
		}

		@Override
		public Integer next() {
			boolean flag = true;
			int potentialPrimeNumber = lastPrimeNumber + 1;

			while (true) {
				for (int i = 2; i < potentialPrimeNumber; i++) {
					if (potentialPrimeNumber % i == 0) {
						flag = false;
						break;
					}
				}

				if (flag) {
					lastPrimeNumber = potentialPrimeNumber;
					remainingNumbers--;
					return potentialPrimeNumber;
				}

				flag = true;
				potentialPrimeNumber++;
			}
		}

		@Override
		public void remove() {
			System.out.println("Ne može se brisati!");

		}

	}

}
