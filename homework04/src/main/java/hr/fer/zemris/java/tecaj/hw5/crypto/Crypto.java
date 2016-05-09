/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.crypto;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

/**
 * Razred u kojem se implementira postupak kriptiranja i dekriptiranja koristeći AES algoritam.
 * 
 * @author Ivan
 * 
 */
public class Crypto {

	/**
	 * @param args
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws ShortBufferException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, ShortBufferException {
		if (args.length == 0) {
			throw new IllegalArgumentException();
		}
		if (args[0].equals("checksha")) {
			checkSha(args[1]);
		}
		else if (args[0].equals("crypt") || args[0].equals("decrypt")) {
			cryptFile(args[0], args[1], args[2]);
		}
	}

	private static void cryptFile(String mode, String inputFile, String outputFile)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, ShortBufferException, IOException {

		Scanner sc = new Scanner(System.in, "UTF-8");

		System.out.println("Please provide password as hex-encoded text:");
		String password = sc.nextLine();

		System.out.println("Please provide initialization vector as hex-encoded text:");
		String vector = sc.nextLine();

		sc.close();

		int cMode = 0;
		if (mode.equals("crypt")) {
			cMode = Cipher.ENCRYPT_MODE;
		}
		else if (mode.equals("decrypt")) {
			cMode = Cipher.DECRYPT_MODE;
		}

		Encrypt encrypt = new Encrypt(inputFile, outputFile, password, vector, cMode);
		encrypt.execute();
	}

	/**
	 * Metoda u kojoj se vrši kriptiranje i dekriptiranje.
	 * 
	 * @param string
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	private static void checkSha(String fileName) throws NoSuchAlgorithmException, IOException {

		Check check = new Check(fileName);
		Scanner sc = new Scanner(System.in, "UTF-8");

		System.out.println("Please provide expected sha signature for " + fileName + ":");
		String expectedSignature = sc.nextLine();

		check.findHash();
		if (check.compareSignature(expectedSignature)) {
			System.out.println("Digesting commpleted. Digest of " + fileName + " matches expected digest");
		}
		else {
			System.out.println("Digesting completed. Digest of " + fileName
					+ " does not match the expected digest. Digest was: " + check.getHash());
		}
		sc.close();
	}

}
