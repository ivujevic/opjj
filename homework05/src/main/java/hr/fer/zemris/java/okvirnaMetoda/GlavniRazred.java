/**
 * 
 */
package hr.fer.zemris.java.okvirnaMetoda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Razred u kojem se ispisuje sadržaj datoteke koristeći okvirnu metodu.
 * 
 * @author Ivan
 * 
 */
public class GlavniRazred {

	/**
	 * Metoda koja se poziva prilikom pokretanja.
	 * 
	 * @param args
	 *            Nazivi datoteka koje je potrebno ispisati.
	 */
	public static void main(String[] args) {

		if (args.length != 3) {
			System.err.println("Treba unijeti 3 argumenta");
			System.exit(0);
		}

		Map<String, Student> studentMap = ucitajStudente(args[0]);
		Map<String, Predmet> predmetMap = ucitajPredmete(args[1]);
		List<Upis> upisi = ucitajUpise(args[2], studentMap, predmetMap);

		for (Upis u : upisi) {
			System.out.println(String.format("| %-40s | %-20s | %-20s |", u.predmet.ime, u.student.prezime,
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

		ObradaDatoteke<Map<String, Student>> obrada = new ObradaDatoteke<Map<String, Student>>(datoteka) {

			Map<String, Student> mapa = new HashMap<>();

			@Override
			protected void obradiRedak(String[] elems) {
				mapa.put(elems[0], new Student(elems[0], elems[2], elems[1]));

			}

			@Override
			protected Map<String, Student> dohvatiRezultat() {
				return mapa;
			}

			@Override
			protected int brojStupaca() {
				return 3;
			}
		};
		return obrada.ucitaj(datoteka);

	}

	/**
	 * Metoda u kojoj se učitavaju predmeti.
	 * 
	 * @param datoteka
	 *            Datoteka iz koje se čitaju predmeti
	 * @return Mapa s učitanim predmetima
	 */
	private static Map<String, Predmet> ucitajPredmete(String datoteka) {

		ObradaDatoteke<Map<String, Predmet>> obrada = new ObradaDatoteke<Map<String, Predmet>>(datoteka) {

			Map<String, Predmet> mapa = new HashMap<>();

			@Override
			protected void obradiRedak(String[] elems) {
				mapa.put(elems[0], new Predmet(elems[0], elems[1]));

			}

			@Override
			protected Map<String, Predmet> dohvatiRezultat() {
				return mapa;
			}

			@Override
			protected int brojStupaca() {
				return 2;
			}
		};
		return obrada.ucitaj(datoteka);

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
	private static List<Upis> ucitajUpise(String fileName, final Map<String, Student> studenti,
			final Map<String, Predmet> predmeti) {

		ObradaDatoteke<List<Upis>> obrada = new ObradaDatoteke<List<Upis>>(fileName) {

			List<Upis> lista = new ArrayList<>();

			@Override
			protected void obradiRedak(String[] elems) {
				Student s = studenti.get(elems[0]);
				Predmet p = predmeti.get(elems[1]);
				if (s == null || p == null) {
					System.out.println("Pogresan zapis!");
					return;
				}
				lista.add(new Upis(s, p));
			}

			@Override
			protected List<Upis> dohvatiRezultat() {
				return lista;
			}

			@Override
			protected int brojStupaca() {
				return 2;
			}
		};
		return obrada.ucitaj(fileName);
	}

	/**
	 * Razred koji opisuje studenta
	 * 
	 */
	static class Student {
		String JMBAG;
		String ime;
		String prezime;

		public Student(String jMBAG, String ime, String prezime) {
			super();
			JMBAG = jMBAG;
			this.ime = ime;
			this.prezime = prezime;
		}
	}

	/**
	 * Razred koji opisuje predmet
	 * 
	 * @author Ivan
	 * 
	 */
	static class Predmet {
		String sifra;
		String ime;

		public Predmet(String sifra, String ime) {
			super();
			this.sifra = sifra;
			this.ime = ime;
		}
	}

	/**
	 * Razred koji opisuje jedan upisani predmet
	 * 
	 * @author Ivan
	 * 
	 */
	static class Upis {
		Student student;
		Predmet predmet;

		public Upis(Student student, Predmet predmet) {
			super();
			this.student = student;
			this.predmet = predmet;
		}

	}
}
