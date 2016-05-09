/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Razred u kojem se implementira naredba <code>hexdump</code>. Naredba čita iz datoteke te na ispisuje
 * znakove pretovrene u hex zapis.
 * 
 */
public class HexDumpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {

		if (arguments.length != 1) {
			System.err.println("Unijeli ste krive parametre");
			return ShellStatus.CONTINUE;
		}

		Path path = Paths.get(arguments[0]);
		int count = 0;
		try {

			byte[] data = Files.readAllBytes(path);

			StringBuilder builderHex = new StringBuilder();
			StringBuilder builderStr = new StringBuilder();
			builderStr.append("|");

			/*
			 * Zastavica u koja označava je li zadnji pročitan redak koji treba ispisati pun ili ne.
			 */
			int f = 0;

			for (byte p : data) {

				/*
				 * Kada je pročitan zadnji znak u redku izvodi se sljedeće.
				 */
				if (count % 16 == 0) {

					if (count != 0) {
						builderHex.insert(34, '|');
						System.out.println(String.format("%-58s%-16s", builderHex.toString(),
								builderStr.toString()));
						f = 0;
					}

					builderHex.delete(0, builderHex.length());
					builderStr.delete(0, builderStr.length());
					builderHex.append(String.format("%08x: ", count));
					builderStr.append("|");
					f = 1;
				}

				/*
				 * Pretvara u hex format velikih slova.
				 */
				builderHex.append(String.format("%02x ", p).toUpperCase());
				if (p >= 32 && p <= 127) {
					builderStr.append((char) p);
				}
				else {
					builderStr.append(".");
				}
				count++;
			}

			if (f == 1) {
				/*
				 * Kako builder nije potpuno pomoću novoog stringa stvori builder koji odgovara i postavi |
				 */
				String a = String.format("%-58s", builderHex.toString());
				builderHex.delete(0, builderHex.length());
				builderHex.append(a);
				if (builderHex.indexOf("|") == -1) {
					builderHex.insert(34, '|');
				}
				System.out.println(String.format("%-58s%-16s", builderHex.toString(), builderStr.toString()));
			}
		} catch (IOException e) {
			System.err.println("Pogreška u čitanju datoteke!");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

}
