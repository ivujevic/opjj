/**
 * 
 */
package hr.fer.zemris.java.strategija;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Razred u kojem se ispisuje sadržaj datoteke koristeći oblikovni obrazac Strategija.
 * 
 * @author Ivan
 * 
 */
public class GlavniRazred {

	/**
	 * Metoda koja se poziva prilikom pokretanja.
	 * 
	 * @param args
	 *            Staze datoteke iz kojih se čita.
	 */
	public static void main(String[] args) {

		if (args.length != 3) {
			System.err.println("Ocekujem tri argumenta.");
			System.exit(0);
		}

		Map<String, Student> studentMap = ucitajStudente(args[0]);
		Map<String, Predmet> predmetMap = ucitajPredmete(args[1]);
		List<Upis> upisi = ucitajUpise(args[2], studentMap, predmetMap);

		for (Upis u : upisi) {
			System.out.println(String.format("| %-40s | %-20s | %-20s |", u.predmet.naziv, u.student.prezime,
					u.student.ime));
		}

	}

	/**
	 * Metoda koja čita studente.
	 * 
	 * @param datoteka
	 *            Ime datoteke iz koje se čita
	 * @return Mapu koja sadrži sve studente
	 */
	private static Map<String, Student> ucitajStudente(String datoteka) {

		return ObradaDatoteke.obradi(datoteka, new IObrada<Map<String, Student>>() {

			Map<String, Student> mapa = new HashMap<>();

			@Override
			public int brojStupaca() {
				return 3;
			}

			@Override
			public void obradiRedak(String[] elems) {

				mapa.put(elems[0], new Student(elems[0], elems[2], elems[1]));

			}

			@Override
			public Map<String, Student> dohvatiRezultat() {
				return mapa;
			}
		});
	}

	/**
	 * Metoda u kojoj se učitavaju predmeti.
	 * 
	 * @param datoteka
	 *            Datoteka iz koje se čitaju predmeti
	 * @return Mapa s učitanim predmetima
	 */
	private static Map<String, Predmet> ucitajPredmete(String datoteka) {
		return ObradaDatoteke.obradi(datoteka, new IObrada<Map<String, Predmet>>() {
			Map<String, Predmet> mapa = new HashMap<>();

			@Override
			public int brojStupaca() {
				return 2;
			}

			@Override
			public void obradiRedak(String[] elems) {
				mapa.put(elems[0], new Predmet(elems[0], elems[1]));

			}

			@Override
			public Map<String, Predmet> dohvatiRezultat() {
				return mapa;
			}

		});
	}

	/**
	 * Metoda u kojoj se učitava koji je student upisao koji predmet.
	 * 
	 * @param fileName
	 *            Ime datoteke iz koje se čita
	 * @param studenti
	 *            Mapa sa svim učitanim studentima
	 * @param predmeti
	 *            Mapa sa svim učitanim predmetima
	 * @return Lista koja sadrži koji je student što upisao.
	 */
	private static List<Upis> ucitajUpise(String datoteka, final Map<String, Student> studenti,
			final Map<String, Predmet> predmeti) {
		return ObradaDatoteke.obradi(datoteka, new IObrada<List<Upis>>() {
			List<Upis> lista = new ArrayList<>();

			@Override
			public int brojStupaca() {
				return 2;
			}

			@Override
			public void obradiRedak(String[] elems) {

				Student s = studenti.get(elems[0]);
				Predmet p = predmeti.get(elems[1]);
				if (s == null || p == null) {
					System.out.println("Pogresan zapis!");
					return;
				}
				lista.add(new Upis(p, s));

			}

			@Override
			public List<Upis> dohvatiRezultat() {
				return lista;
			}

		});
	}

	static class Upis {
		Predmet predmet;
		Student student;

		public Upis(Predmet predmet, Student student) {
			super();
			this.predmet = predmet;
			this.student = student;
		}
	}

	static class Predmet {
		String sifra;
		String naziv;

		public Predmet(String sifra, String naziv) {
			super();
			this.sifra = sifra;
			this.naziv = naziv;
		}
	}

	static class Student {
		String jmbag;
		String ime;
		String prezime;

		public Student(String jmbag, String ime, String prezime) {
			super();
			this.jmbag = jmbag;
			this.ime = ime;
			this.prezime = prezime;
		}
	}
}
