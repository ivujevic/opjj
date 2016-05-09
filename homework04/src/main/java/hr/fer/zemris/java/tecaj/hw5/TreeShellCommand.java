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

/**
 * Metoda u kojoj se ispisuje sadržaj direktorija u obliku stabla.
 * 
 */
public class TreeShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {

		if (arguments.length != 1) {
			System.err.println("Naredba tree koristi samo jedan parametar");
			return ShellStatus.CONTINUE;
		}

		Path dir = Paths.get(arguments[0]);
		if (!Files.exists(dir)) {
			System.err.println("Ne postoji navedena staza!");
			return ShellStatus.CONTINUE;
		}
		try {
			Files.walkFileTree(dir, new Walker());
		} catch (IOException e) {
			System.err.println("Pogreska u citanju!");
		}
		return ShellStatus.CONTINUE;
	}

	static class Walker implements FileVisitor<Path> {

		int indentation = 0;

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

			String print = null;
			if (indentation != 0) {
				print = String.format("%" + indentation + "s%s", "", dir.getFileName().toString());
			}
			else {
				print = dir.getFileName().toString();
			}
			System.out.println(print);
			indentation += 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			System.out.println(String.format("%" + indentation + "s%s", "", file.getFileName().toString()));

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			indentation -= 2;
			return FileVisitResult.CONTINUE;
		}

	}
}
