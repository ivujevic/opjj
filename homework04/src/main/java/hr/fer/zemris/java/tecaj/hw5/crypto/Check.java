/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.crypto;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Metoda u kojoj se provejrava SHA1 potpis
 * 
 */

public class Check {

	private byte[] hash;

	private MessageDigest sha;
	private String fileName;

	/**
	 * @throws NoSuchAlgorithmException
	 * 
	 */
	public Check(String fileName) throws NoSuchAlgorithmException {
		this.fileName = fileName;
		sha = MessageDigest.getInstance("SHA-1");
	}

	/**
	 * Metoda koja pronalazi hash vrijednost iz zadane datoteke. Čita u buffer veličine 4096.
	 * 
	 * @throws IOException
	 */
	public void findHash() throws IOException {

		InputStream input = new FileInputStream(fileName);

		byte[] buffer = new byte[4096];
		int numberOfCharacter = 0;

		while (numberOfCharacter != -1) {

			numberOfCharacter = input.read(buffer);

			if (numberOfCharacter < 0)
				break;
			sha.update(buffer, 0, numberOfCharacter);
		}

		input.close();
		this.hash = sha.digest();
	}

	/**
	 * Metoda u kojoj se uspoređuju dobivenii potpisi
	 * 
	 * @param signature
	 * @return
	 */
	public boolean compareSignature(String signature) {

		return byteToHex(hash).equals(signature);
	}

	/**
	 * Metoad koja vraća hash vrijednost
	 * 
	 * @return
	 */
	public String getHash() {
		return byteToHex(hash);
	}

	/**
	 * Metoda koja pretvara byte u hex zapis.
	 * 
	 * @param array
	 *            Niz byte-ova koji se pretvaraju
	 * @return String hex znakova
	 */
	private String byteToHex(byte[] array) {

		StringBuilder sb = new StringBuilder();
		for (byte b : array)
			sb.append(String.format("%02x", b & 0xff));
		return sb.toString();
	}
}
