/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Metoda u kojoj se ostvaruje MyShell.
 * 
 */
public class MyShell {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Map<String, ShellCommand> commands = new LinkedHashMap<>();
		commands.put("exit", new ExitShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("hexdump", new HexDumpShellCommand());

		System.out.println("Welcom to MyShell v 1.0");

		Scanner sc = new Scanner(System.in, "UTF-8");
		ShellStatus status = ShellStatus.CONTINUE;

		StringBuilder builder = new StringBuilder();

		while (status == ShellStatus.CONTINUE) {

			builder.delete(0, builder.length());
			System.out.flush();
			System.out.print(Symbols.PROMPTSYMBOL);
			String line = sc.nextLine().trim();
			builder.append(line);

			/*
			 * Ako je naredba u 2 retka.
			 */
			if (line.lastIndexOf(Symbols.MORELINESSYMBOL) == line.length() - 1) {
				/*
				 * Ukloni oznaku da je naredba u 2 retka.
				 */
				builder.delete(builder.length() - 1, builder.length());
				builder.append(" ");

				while (true) {
					System.out.print(Symbols.MULTILINE);
					line = sc.nextLine().trim();
					builder.append(line);

					if (line.indexOf(Symbols.MORELINESSYMBOL) != (line.length() - 1)) {
						break;
					}

					builder.delete(builder.length() - 1, builder.length());
					builder.append(" ");
				}
			}

			String commandName;
			String[] arguments = null;

			/*
			 * Dohati naredbu i argumente.
			 */
			if (builder.toString().trim().indexOf(' ') != -1) {

				commandName = builder.toString().trim().substring(0, builder.toString().trim().indexOf(' '));
				arguments = builder.toString().trim().substring(builder.toString().trim().indexOf(' '))
						.trim().split("\\s+");
			}
			else {
				commandName = builder.toString();
			}

			ShellCommand comand = commands.get(commandName);
			try {
				status = comand.executeCommand(new BufferedReader(new InputStreamReader(System.in)),
						new BufferedWriter(new OutputStreamWriter(System.out)), arguments);
			} catch (Throwable e) {
				System.err.println("Pogreška u naredbi!");
				continue;
			}
			builder.delete(0, builder.length());

		}
		sc.close();
	}
}
