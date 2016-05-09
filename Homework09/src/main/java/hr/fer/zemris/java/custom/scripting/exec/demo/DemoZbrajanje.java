/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan
 * 
 */
public class DemoZbrajanje {

	public static void main(final String[] args) throws IOException, SmartScriptParserException {
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<>();

		parameters.put("a", "4");
		parameters.put("b", "2");

		if (args.length != 1) {
			System.err.println("Potreban je jedan parametar!");
			System.exit(-1);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]),
				StandardCharsets.UTF_8));
		StringBuilder builder = new StringBuilder();
		while (true) {
			String line = br.readLine();
			if (line == null) {
				break;
			}
			builder.append(line).append("\n");
		}
		new SmartScriptEngine(new SmartScriptParser(builder.toString()).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}
}
