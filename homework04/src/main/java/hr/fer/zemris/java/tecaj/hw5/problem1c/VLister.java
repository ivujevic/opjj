/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.problem1c;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Razred u kojem se nalaze funkcije koje čitaju zadani direktorij te određuju broj direktorija,broj
 * datoteka,ukupnu veličinu,prosječnu veličinu datoteka s istom ekstenzijom
 * 
 */
public class VLister {

	/**
	 * Sortiranje po ekstenziji.
	 */
	private final static int SORTBYEXTENSION = 0;

	/**
	 * Sortiranje po veličini datoteke.
	 */
	private final static int SORTBYSIZE = 1;

	/**
	 * Sortiranje po broju datoteka.
	 */
	private final static int SORTBYNUMBEROFFILES = 2;

	/**
	 * Sortiranje po prosjeku.
	 */
	private final static int SORTBYAVERAGE = 3;

	/**
	 * Pamti osnovno informacije.
	 */
	protected static Info generalInformation;

	/**
	 * Mapa u koju se pohranjuju podaci o ekstenzijama.
	 */
	protected static Map<String, ExtensionInfo> mapExtensions;

	Comparator<ExtensionInfo> comparator = new Comparator<ExtensionInfo>() {

		@Override
		public int compare(ExtensionInfo o1, ExtensionInfo o2) {
			return o1.extensionName.compareTo(o2.extensionName);
		};
	};

	/**
	 * Metoda koja se pokreće prilikom poziva programa, preko komandne linije prima jedan parametar koji
	 * predstavalja direktorij čiji se sadržaj mora pročitati.
	 * 
	 * @param args
	 *            Direktorij čiji se sadržaj čita.
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Krivi poziv");
			return;
		}

		Path dir = Paths.get(args[0]);

		generalInformation = new Info();

		mapExtensions = new LinkedHashMap<>();

		try {
			Files.walkFileTree(dir, new Walker());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scanner sc = new Scanner(System.in, "UTF-8");
		while (true) {

			String input = sc.nextLine();
			switch (input) {
				case "G":
					writeGeneralInformation();
					break;
				case "E":
					writeExtenesion(SORTBYEXTENSION, 0);
					break;
				case "!E":
					writeExtenesion(SORTBYEXTENSION, 1);
					break;
				case "S":
					writeExtenesion(SORTBYSIZE, 0);
					break;
				case "!S":
					writeExtenesion(SORTBYSIZE, 1);
					break;
				case "N":
					writeExtenesion(SORTBYNUMBEROFFILES, 0);
					break;
				case "!N":
					writeExtenesion(SORTBYNUMBEROFFILES, 1);
					break;
				case "A":
					writeExtenesion(SORTBYAVERAGE, 0);
					break;
				case "!A":
					writeExtenesion(SORTBYAVERAGE, 1);
					break;
				case "Q":
					sc.close();
					System.exit(1);
				default:
					System.out.println("Unijeli ste krivu vrijednost, pokušajte ponovno");
			}
		}
	}

	/**
	 * Metoda u kojoj se ispisuje podaci o ekstenzijama sortirani po određenom parametru.
	 * 
	 * @param sortbyextension
	 *            Parametar po kojem se sortira.
	 * @param i
	 *            Ako je way =0 sortiranje je uzlazno, inače ako je way =0 sortiranje je silazno
	 */
	private static void writeExtenesion(int sortbyextension, int way) {

		List<ExtensionInfo> list = new LinkedList<>();
		Comparator<ExtensionInfo> comp;
		for (java.util.Map.Entry<String, ExtensionInfo> p : mapExtensions.entrySet()) {
			list.add(p.getValue());
		}

		if (sortbyextension == SORTBYEXTENSION) {
			comp = new Comparator<ExtensionInfo>() {
				@Override
				public int compare(ExtensionInfo o1, ExtensionInfo o2) {
					return o1.extensionName.compareTo(o2.extensionName);
				};
			};
		}
		else if (sortbyextension == SORTBYNUMBEROFFILES) {
			comp = new Comparator<ExtensionInfo>() {
				@Override
				public int compare(ExtensionInfo o1, ExtensionInfo o2) {
					return Integer.valueOf(o1.numberOfFiles).compareTo(Integer.valueOf(o2.numberOfFiles));
				};
			};
		}
		else if (sortbyextension == SORTBYAVERAGE) {
			comp = new Comparator<ExtensionInfo>() {
				@Override
				public int compare(ExtensionInfo o1, ExtensionInfo o2) {
					return Double.valueOf(o1.average()).compareTo(Double.valueOf(o2.average()));
				};
			};
		}
		else {
			comp = new Comparator<ExtensionInfo>() {
				@Override
				public int compare(ExtensionInfo o1, ExtensionInfo o2) {
					return Double.valueOf(o1.size).compareTo(Double.valueOf(o2.size));
				};
			};
		}
		Collections.sort(list, comp);
		if (way == 1) {
			Collections.reverse(list);
		}
		System.out.println("Extension\t\tNumber of files\t\tTotal size of all files\t\tAverage");
		System.out
				.println("=============================================================================================");
		for (ExtensionInfo p : list) {
			String ispis = String.format("%-20s\t\t %-10s\t\t %-10s\t\t %-10.2f", p.extensionName,
					p.numberOfFiles, p.size, p.average());
			System.out.println(ispis);
		}

	}

	/**
	 * Metoda u kojoj se ispisuju osnovne informacije.
	 */
	private static void writeGeneralInformation() {

		System.out.println("number of files: " + generalInformation.numberOfAllFiles);
		System.out.println("total size of all files: " + generalInformation.sizeOfAllFiles);
		System.out.println("average size of a file: " + generalInformation.average());
		System.out.println("number of directories:" + generalInformation.numberOfDirectories);
	}

	/**
	 * Razred koji implementira FileVisitor, prolazi kroz direktorije i čita ih.
	 * 
	 */
	static class Walker implements FileVisitor<Path> {

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

			generalInformation.numberOfDirectories++;

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

			generalInformation.numberOfAllFiles++;
			generalInformation.sizeOfAllFiles += attrs.size();

			processingFile(file, attrs);

			return FileVisitResult.CONTINUE;
		}

		/**
		 * Određuju tip datoteke, veličinu.
		 * 
		 * @param file
		 * @param attrs
		 */
		private void processingFile(Path file, BasicFileAttributes attrs) {

			ExtensionInfo extension = new ExtensionInfo();
			extension.extensionName = file.getFileName().toString()
					.substring(file.getFileName().toString().lastIndexOf('.')).toUpperCase();

			if (mapExtensions.containsKey(extension.extensionName)) {
				extension = mapExtensions.get(extension.extensionName);
			}
			extension.numberOfFiles++;
			extension.size += attrs.size();
			mapExtensions.put(extension.extensionName, extension);
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

	}

	/**
	 * Razred koji nam služi za pohranu općenitih informacija koje je potrebno ispisati kada korisnik to
	 * zatraži
	 * 
	 * @author Ivan
	 * 
	 */
	static class Info {
		private int numberOfAllFiles = 0;
		private int sizeOfAllFiles = 0;
		private int numberOfDirectories = 0;

		double average() {
			if (numberOfAllFiles == 0) {
				return 0;
			}
			return (double) sizeOfAllFiles / numberOfAllFiles;
		}
	}

	/**
	 * Razred koji opisuje skup datoteke iste ekstenzije.
	 * 
	 * @author Ivan
	 * 
	 */
	static class ExtensionInfo {
		private String extensionName;
		private int size = 0;
		private int numberOfFiles = 0;

		double average() {
			if (numberOfFiles == 0) {
				return 0;
			}
			return (double) size / numberOfFiles;
		}
	}
}
