/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Razred u kojem se implementiraju funkcije naredbe <code>ls</code>. Naredba ls ima jedan argument koji
 * predstavlja direktorij kojeg je potrebno nerekurzivno izlistati. Ispis je u 4 stupca: u prvom stupcu 4
 * slova označuju redom je li ispisan direktorij, je li omogućeno čitanje, pisanje, izvođenje. u drugom
 * stupucu se ispisuje veličina objekta u bajtovima. u trećem stupcu se ispisuje datum kreiranja objekta. u
 * četvrtom stupcu se ispisuje ime objekta.
 * 
 * 
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * Lista u koju se spremaju podaci o svakoj datoteci/direktoriju
	 */
	private List<Info> list = new ArrayList<>();

	/**
	 * Mapa u kojoj se pamti veličina direktorija kako bi se nerekurzivno izračunale veličine
	 */
	private Map<String, Long> map = new LinkedHashMap<>();

	/**
	 * Path početnog direktorija, potreban za usporedbu prilikom dohvaćanja roditelja
	 */
	public Path pocetak;

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {

		if (arguments.length != 1) {
			System.err.println("Nareba ls prihvaća 1 parametar");
			return ShellStatus.CONTINUE;
		}

		Path dir = Paths.get(arguments[0]);
		if (!Files.exists(dir)) {
			System.err.println("Ne postoji navedena staza!");
			return ShellStatus.CONTINUE;
		}

		try {
			pocetak = dir;
			Files.walkFileTree(dir, new Walker());
			Collections.reverse(list);
			for (Info p : list) {

				String print = String.format("%s %10s %-10s %-15s", p.properties, p.size, p.creationDate,
						p.name);
				try {
					out.write(print);
					out.newLine();
					out.flush();
				} catch (IOException e) {
					System.err.println("Dogodila se pogreška, ne mogu nastaviti s pisanjem!");
				}

			}
		} catch (IOException e) {
			System.err.println("Nije moguće nastaviti s čitanjem");
		}

		list.clear();
		return ShellStatus.CONTINUE;
	}

	public class Walker implements FileVisitor<Path> {

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

			/*
			 * Prije ulaska u direktorij postavi u mapu njegovu veličinu na 0.
			 */
			map.put(dir.toString(), Long.valueOf(0));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

			/*
			 * stvara novi objekt kojeg dodaje u listu i uvećava veličinu direktorija u kojem se nalazi.
			 */
			Info info = new Info();
			info = determineProperies(file, attrs);
			list.add(info);
			map.put(file.getParent().toString(), map.get(file.getParent().toString()) + attrs.size());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

			BasicFileAttributes attrs = Files.readAttributes(dir, BasicFileAttributes.class);
			Info info = new Info();

			info = determineProperies(dir, attrs);

			/*
			 * Ako nisi u početnom direktoriju tada možeš dohvatiti roditelja
			 */
			if (!dir.toAbsolutePath().equals(pocetak.toAbsolutePath())) {
				map.put(dir.getParent().toString(),
						map.get(dir.getParent().toString()) + map.get(dir.toString()));
			}

			info.size = map.get(dir.toString());
			list.add(info);
			return FileVisitResult.CONTINUE;
		}

	}

	/**
	 * Metoda koja određuje svojstva.
	 * 
	 * @param file
	 * @param attrs
	 */
	private Info determineProperies(Path file, BasicFileAttributes attrs) {
		Info info = new Info();

		StringBuilder properties = new StringBuilder();

		if (attrs.isDirectory()) {
			properties.append('d');
		}
		else {
			properties.append('-');
		}

		if (Files.isReadable(file)) {
			properties.append('r');
		}
		else {
			properties.append('-');
		}

		if (Files.isWritable(file)) {
			properties.insert(1, 'w');
		}
		else {
			properties.append('-');
		}

		if (Files.isExecutable(file)) {
			properties.insert(1, 'x');
		}
		else {
			properties.append('-');
		}

		FileTime fileTime = attrs.creationTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

		info.name = file.getFileName().toString();
		info.size = attrs.size();
		info.creationDate = formattedDateTime;
		info.properties = properties.toString();

		return info;
	}

	static class Info {
		private String name;
		private String creationDate;
		private long size;
		private String properties;
	}
}