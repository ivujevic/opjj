package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * Klasa koja sluzi za prepoznavanje zadovoljava li ulazni string ranije definiranu strukturu
 * 
 */
public class SmartScriptParser {

	/** Konstante koje sluze za odredjivanje stanja konacnog automata */

	private static final int pocetnoStanje = 0;

	/** Stanje u kojem se cita tekst */
	private static final int citaTekst = 1;

	/** Stanje u koje prelazi kada primi [ (mozda otvoren tag?) */
	private static final int otvorenTag = 2;

	/** Stanje u koje prelazi iz stanja 2 kada primi $ (pocetak taga) */
	private static final int pocetakTaga = 3;

	/** Stanje u kojem cita (FOR, END, =) */
	private static final int citajNaredbu = 5;

	/** Stanje u kojem cita argumente naredbe FOR */
	private static final int citajFor = 7;

	/** Stanje u kojem prelazi kada primi $ (mozda zatvoren tag?) */
	private static final int krajTaga = 8;

	/** Stanje u koje prelazi iz 5 ako je naredba END */
	private static final int naredbaEND = 9;

	/** Stanje u koje prelazi iz 5 ako je procitao = */
	private static final int nodeEcho = 10;

	/** Stanje u koje prelazi iz 10 ako je procitao " */
	private static final int citajString = 11;

	/** Stanje u koje prelazi iz 11 ako je procitao \" */
	private static final int citajNavodnike = 12;

	/** Stanje u koje prelazi iz 10 ako je procitao @ */
	private static final int citajFunkciju = 13;

	/** Stanje u koje prelazi iz 10 ako je procitao broj */
	private static final int citajBroj = 15;

	/** Stanje u koje prelazi iz 10 ako je procitao slovo */
	private static final int citajVarijablu = 16;

	private ObjectStack stog;
	private String documentBody;
	private DocumentNode documentNode;
	private StringBuilder gradiTekst;
	private ArrayBackedIndexedCollection echoNodeTokeni;

	/**
	 * Parsira dobiveni string
	 * 
	 * @param tekstZaProvjeru
	 *            string koji se zeli parsirati
	 * @throws SmartScriptParserException
	 *             javlja gresku ako parsiranje nije uspjelo
	 */

	public SmartScriptParser(final String tekstZaProvjeru) throws SmartScriptParserException {
		this.documentBody = tekstZaProvjeru;
		this.parsiranje();
	}

	/**
	 * 
	 * @throws SmartScriptParserException
	 *             Prolazi kroz niz znakova i koristeci automat odreduje je li ulazni niz ispravan ili ne
	 */
	public final void parsiranje() throws SmartScriptParserException {
		this.stog = new ObjectStack();
		int trenutnoStanje = pocetnoStanje;
		char[] ulaz = this.documentBody.trim().toCharArray();
		gradiTekst = new StringBuilder();
		documentNode = new DocumentNode();
		stog.push(documentNode);
		try {
			for (int i = 0; i < ulaz.length; i++) {
				switch (trenutnoStanje) {
					case pocetnoStanje:
						gradiTekst.setLength(0);
						if (ulaz[i] == '[') {
							trenutnoStanje = otvorenTag;
						}
						else {
							trenutnoStanje = citaTekst;
							gradiTekst.append(ulaz[i]);
						}
						break;
					case citaTekst:
						if (ulaz[i] == '[') {
							trenutnoStanje = otvorenTag;
							srediTekst();
							// brise sadrzaj gradiTekst buildera
							gradiTekst.setLength(0);
						}
						else if (ulaz[i] == '$') {
							srediTekst();
							trenutnoStanje = krajTaga;
							gradiTekst.setLength(0);
						}
						else {
							gradiTekst.append(ulaz[i]);
						}
						break;
					case otvorenTag:
						if (ulaz[i] == ' ') {
							break;
						}
						else if (ulaz[i] == '$') {
							trenutnoStanje = pocetakTaga;
						}
						else {
							throw new SmartScriptParserException("Nakon [ mora slijediti $");
						}
						break;
					case pocetakTaga:
						if (ulaz[i] == ' ') {
							break;
						}
						else if (Character.toUpperCase(ulaz[i]) != 'F'
								&& Character.toUpperCase(ulaz[i]) != 'E' && ulaz[i] != '=') {
							throw new SmartScriptParserException("Kriva naredba!");
						}
						else if (Character.toUpperCase(ulaz[i]) == 'F'
								|| Character.toUpperCase(ulaz[i]) == 'E') {
							gradiTekst.append(ulaz[i]);
							trenutnoStanje = citajNaredbu;
						}
						else if (ulaz[i] == '=') {
							trenutnoStanje = nodeEcho;
							gradiTekst.setLength(0);
						}
						break;
					case citajNaredbu:
						if (ulaz[i] == ' ') {
							if (gradiTekst.toString().toUpperCase().equalsIgnoreCase("FOR")) {
								trenutnoStanje = citajFor;
								gradiTekst.setLength(0);
							}
							else if (gradiTekst.toString().toUpperCase().equalsIgnoreCase("END")) {
								trenutnoStanje = naredbaEND;
								gradiTekst.setLength(0);
								continue;
							}
						}
						else if (Character.isLetter(ulaz[i])) {
							gradiTekst.append(ulaz[i]);
						}
						else if (ulaz[i] == '$') {
							if (stog.isEmpty()) {
								throw new SmartScriptParserException("Previse END");
							}
							else {
								stog.pop();
								trenutnoStanje = krajTaga;
							}
						}
						else {
							throw new SmartScriptParserException("Kriva naredba!");
						}
						break;
					case citajFor:
						if (ulaz[i] == ' ' && ulaz[i - 1] == ' ') {
							break;
						}
						else if (ulaz[i] == '$') {
							trenutnoStanje = krajTaga;
							srediFor();
							gradiTekst.setLength(0);
						}
						else if (Character.isLetter(ulaz[i]) || Character.isDigit(ulaz[i]) || ulaz[i] == ' '
								|| ulaz[i] == '.' || (ulaz[i] == '_' && ulaz[i - 1] != ' ')) {
							gradiTekst.append(ulaz[i]);
						}
						break;
					case krajTaga:
						if (ulaz[i] == ' ') {
							break;
						}
						else if (ulaz[i] == ']') {
							trenutnoStanje = pocetnoStanje;
						}
						else {
							throw new SmartScriptParserException("Krivi zavrsetak taga");
						}
						break;
					case naredbaEND:
						if (ulaz[i] == ' ') {
							continue;
						}
						else if (ulaz[i] == '$') {
							if (this.stog.isEmpty()) {
								throw new SmartScriptParserException("Previse END");
							}
							trenutnoStanje = krajTaga;
						}
						else {
							throw new SmartScriptParserException("Greska u naredbi END");
						}
						break;
					case nodeEcho:
						// ignoriraj praznine
						if (ulaz[i] == ' ' || ulaz[i] == '\n') {
							break;
						}
						else if (ulaz[i] == '$') {
							srediEcho();
							trenutnoStanje = krajTaga;
							gradiTekst.setLength(0);
						}
						// pocmi citati string
						else if (ulaz[i] == '"') {
							gradiTekst.append(ulaz[i]);
							trenutnoStanje = citajString;
						}
						// citaj funkciju
						else if (ulaz[i] == '@') {
							gradiTekst.append(ulaz[i]);
							trenutnoStanje = citajFunkciju;
						}
						else if (Character.isDigit(ulaz[i])) {
							gradiTekst.append(ulaz[i]);
							trenutnoStanje = citajBroj;
						}
						else if (Character.isLetter(ulaz[i])) {
							gradiTekst.append(ulaz[i]);
							trenutnoStanje = citajVarijablu;
						}
						// procitaj operator
						else if (ulaz[i] == '+' || ulaz[i] == '-' || ulaz[i] == '*' || ulaz[i] == '/') {
							gradiTekst.append(ulaz[i]);
							dodajToken();
							gradiTekst.setLength(0);
						}
						else {
							throw new SmartScriptParserException("Greska u ECHO NODE");
						}
						break;
					case citajString:
						if (ulaz[i] == '"' && ulaz[i - 1] != '\\') {
							gradiTekst.append(ulaz[i]);
							dodajToken();
							gradiTekst.setLength(0);
							trenutnoStanje = nodeEcho;
						}
						else if (ulaz[i] == '\\' && ulaz[i + 1] == '"') {
							trenutnoStanje = citajNavodnike;
						}
						else if (ulaz[i] == '\\' && ulaz[i + 1] == 'r') {
							gradiTekst.append('\r');
							i++;
							continue;
						}
						else if (ulaz[i] == '\\' && ulaz[i + 1] == 'n') {
							gradiTekst.append('\n');
							i++;
							continue;
						}
						else {
							gradiTekst.append(ulaz[i]);
						}
						break;
					case citajNavodnike:
						if (ulaz[i] == '"') {
							gradiTekst.append(ulaz[i]);
							trenutnoStanje = citajString;
						}
						break;
					case citajVarijablu:
						if (ulaz[i] == ' ') {
							dodajToken();
							gradiTekst.setLength(0);
							trenutnoStanje = nodeEcho;
						}
						else if (ulaz[i] == '$') {
							dodajToken();
							srediEcho();
							trenutnoStanje = krajTaga;
							gradiTekst.setLength(0);
						}
						else if (Character.isDigit(ulaz[i]) || Character.isLetter(ulaz[i]) || ulaz[i] == '_') {
							gradiTekst.append(ulaz[i]);
						}
						else {
							throw new SmartScriptParserException("Greska u imenu varijable");
						}
						break;
					case citajFunkciju:
						if (ulaz[i] == ' ' || ulaz[i] == '\n') {
							dodajToken();
							gradiTekst.setLength(0);
							trenutnoStanje = nodeEcho;
						}
						else if (ulaz[i] == '$') {
							dodajToken();
							srediEcho();
							trenutnoStanje = krajTaga;
							gradiTekst.setLength(0);
						}
						else if (Character.isDigit(ulaz[i]) && ulaz[i - 1] == '@' || ulaz[i] == '_'
								&& ulaz[i - 1] == '@') {
							throw new SmartScriptParserException("Greska u imenu funkcije");
						}
						else if (Character.isDigit(ulaz[i]) || Character.isLetter(ulaz[i])) {
							gradiTekst.append(ulaz[i]);
						}
						else {
							throw new SmartScriptParserException("Greska u imenu funkcije");
						}
						break;
					case citajBroj:
						if (ulaz[i] == ' ') {
							dodajToken();
							gradiTekst.setLength(0);
							trenutnoStanje = nodeEcho;
						}
						else if (ulaz[i] == '$') {
							dodajToken();
							srediEcho();
							trenutnoStanje = krajTaga;
							gradiTekst.setLength(0);
						}
						else if (Character.isDigit(ulaz[i]) || ulaz[i] == '.') {
							gradiTekst.append(ulaz[i]);
						}
						else {
							throw new SmartScriptParserException("Greska u broju unutar ECHO NODA");
						}
					default:
						break;
				}
			}
			if (trenutnoStanje == citaTekst) {
				srediTekst();
			}
			stog.pop();
			if (!stog.isEmpty()) {

				throw new SmartScriptParserException("Nedostaje END");
			}
		} catch (Exception e) {
			throw new SmartScriptParserException("Greska u citanju, ne mogu parsirati!");
		}
	}

	/**
	 * Metoda koja sluzi za razvrstavanje Tokena koji se se dodati u EchoNode
	 */
	private void dodajToken() throws SmartScriptParserException {
		if (echoNodeTokeni == null) {
			echoNodeTokeni = new ArrayBackedIndexedCollection();
		}

		if (Character.isLetter(gradiTekst.toString().charAt(0))) {
			echoNodeTokeni.add(new TokenVariable(gradiTekst.toString()));
		}
		else if (provjeriInteger(gradiTekst.toString())) {
			echoNodeTokeni.add(new TokenConstantInteger(Integer.parseInt(gradiTekst.toString())));
		}
		else if (provjeriDouble(gradiTekst.toString())) {
			echoNodeTokeni.add(new TokenConstantDouble(Double.parseDouble(gradiTekst.toString())));
		}
		else if (gradiTekst.toString().charAt(0) == '@') {
			echoNodeTokeni.add(new TokenFunction(gradiTekst.toString().substring(1)));
		}
		else if (gradiTekst.toString().charAt(0) == '"') {
			echoNodeTokeni.add(new TokenString(String.format(gradiTekst.toString().substring(1,
					gradiTekst.toString().length() - 1))));
		}
		else if (gradiTekst.toString().charAt(0) == '+' || gradiTekst.toString().charAt(0) == '-'
				|| gradiTekst.toString().charAt(0) == '*' || gradiTekst.toString().charAt(0) == '/') {
			echoNodeTokeni.add(new TokenOperator(gradiTekst.toString()));
		}
		else {
			throw new SmartScriptParserException("Greska u Token parametrima");
		}
	}

	/**
	 * Metoda koja dodaje tekst u Node
	 * 
	 * @throws Ako
	 *             na stogu nema nista javlja gresku
	 */
	private void srediTekst() throws SmartScriptParserException {
		if (stog.isEmpty()) {
			throw new SmartScriptParserException("Too much 'END's.");
		}
		else {
			((Node) stog.peek()).addChildNode(new TextNode(gradiTekst.toString()));
		}

	}

	/**
	 * Metoda koja dodaje For-Loop na stog
	 * 
	 * @throws Ako
	 *             su neispravni parametri javlja gresku
	 */
	private void srediFor() throws SmartScriptParserException {
		String[] tokenString = gradiTekst.toString().split(" ");
		Token[] tokens = new Token[4];
		for (int i = 0; i < tokenString.length; i++) {
			if (Character.isLetter(tokenString[i].charAt(0))) {
				tokens[i] = new TokenVariable(tokenString[i]);
			}
			else if (provjeriInteger(tokenString[i])) {
				tokens[i] = new TokenConstantInteger(Integer.parseInt(tokenString[i]));
			}
			else if (provjeriDouble(tokenString[i])) {
				tokens[i] = new TokenConstantDouble(Double.parseDouble(tokenString[i]));
			}
			else {
				throw new SmartScriptParserException("Greska u Token parametrima");
			}
		}

		ForLoopNode node = new ForLoopNode((TokenVariable) tokens[0], tokens[1], tokens[2], tokens[3]);
		((Node) stog.peek()).addChildNode(node);

		stog.push(node);
	}

	/**
	 * Metoda koja dodaje Echo u node
	 */
	private void srediEcho() {
		Token[] tokeni = new Token[echoNodeTokeni.size()];
		for (int i = 0; i < echoNodeTokeni.size(); i++) {
			tokeni[i] = (Token) echoNodeTokeni.get(i);
		}
		EchoNode node = new EchoNode(tokeni);

		((Node) stog.peek()).addChildNode(node);
		echoNodeTokeni.clear();
	}

	/**
	 * Metoda koja provjerava je li dani niz znakova mo�e biti <code>double</code>broj
	 * 
	 * @param string
	 *            Niz znakova
	 * @return Vrasa <code>true</code> ako niz moze biti <code>double</code>, inace <code>false</code>
	 */
	private boolean provjeriDouble(final String string) {
		int brojiTocke = 0;
		for (int i = 0; i < string.length(); i++) {
			if (!Character.isDigit(string.charAt(i)) && string.charAt(i) != '.') {
				return false;
			}
			else if (string.charAt(i) == '.') {
				brojiTocke++;
			}
		}
		if (brojiTocke > 1 || brojiTocke == 0) {
			return false;
		}
		return true;
	}

	/**
	 * Metoda koja provjerava je li dani niz brojeva mo�e biti <code>integer</code> broj
	 * 
	 * @param string
	 *            Niz brojeva
	 * @return Vraca <code>true</code> ako niz moze biti <code>integer</code>, inace vraca <code>false</code>
	 */
	private boolean provjeriInteger(final String string) {
		for (int i = 0; i < string.length(); i++) {
			if (!Character.isDigit(string.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public final DocumentNode getDocumentNode() {
		return documentNode;
	}
}