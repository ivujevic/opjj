/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * Sučelje naredbe. Naredba prima 3 argumenta. Prvi je odakle se čita, drugi je gdje se piše, treći argument
 * su argumetni naredbe.
 */
public interface ShellCommand {

	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments);
}
