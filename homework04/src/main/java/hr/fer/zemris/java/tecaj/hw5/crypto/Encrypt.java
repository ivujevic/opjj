/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Encrypt {

	/**
	 * Odakle se čita.
	 */
	private String inputName;

	/**
	 * Gdje se piše.
	 */
	private String outputName;

	/**
	 * Šifra
	 */
	private Cipher cipher;

	/**
	 * @param inputFile
	 * @param outputFile
	 * @param password
	 * @param vector
	 * @param cMode
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 */
	public Encrypt(String inputFile, String outputFile, String password, String vector, int cMode)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException {
		this.inputName = inputFile;
		this.outputName = outputFile;

		SecretKeySpec keySpec = new SecretKeySpec(hexToByte(password), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hexToByte(vector));

		this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		this.cipher.init(cMode, keySpec, paramSpec);
	}

	/**
	 * 
	 * @throws IOException
	 * @throws ShortBufferException
	 */
	public void execute() throws IOException, ShortBufferException {

		InputStream input = new FileInputStream(this.inputName);
		OutputStream output = new FileOutputStream(this.outputName);

		byte[] Ibuffer = new byte[4096];
		byte[] Obuffer = new byte[4096];
		int numberOfCharacter = 0;
		while (numberOfCharacter != -1) {

			numberOfCharacter = input.read(Ibuffer);

			if (numberOfCharacter < 0)
				break;

			numberOfCharacter = cipher.update(Ibuffer, 0, numberOfCharacter, Obuffer);
			output.write(Obuffer, 0, numberOfCharacter);
		}
		input.close();
		output.close();
	}

	/**
	 * Metoda u kojoj se pretvara hex zapis u polje byte-ova.
	 * 
	 * @param hexString
	 *            String koji se sastoji od hex znakova
	 * @return Polje u koje su pretvoreni hex znakovi.
	 */
	private byte[] hexToByte(String hexString) {

		return DatatypeConverter.parseHexBinary(hexString);
	}
}
