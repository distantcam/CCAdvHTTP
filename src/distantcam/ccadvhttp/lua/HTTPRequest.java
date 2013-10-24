package distantcam.ccadvhttp.lua;

import java.io.*;
import java.net.*;

import dan200.computer.api.ILuaContext;
import dan200.computer.api.ILuaObject;

public class HTTPRequest implements ILuaObject {
	
	private URL url;
	private HttpURLConnection connection;
	
	private String outputText;	
	private HTTPResponse response;
	
	public HTTPRequest(String urlString) throws Exception {
		outputText = "";
		
		url = new URL(urlString);
		
		final String protocol = url.getProtocol().toLowerCase();
		if (!protocol.equals("http") && !protocol.equals("https")) {
			throw new Exception("Invalid URL");
		}
		
		connection = (HttpURLConnection)url.openConnection();
	}
	
	@Override
	public String[] getMethodNames() {
		return new String[] { "setMethod", "setOutput", "setProperty", "connect" };
	}

	@Override
	public Object[] callMethod(ILuaContext context, int method,
			Object[] arguments) throws Exception {
		switch (method) {
		
		// setMethod
		case 0:
			assertNotConnected();
			assertArgumentCount(arguments, 1);
			assertArgumentIsString(arguments[0], "method");
			connection.setRequestMethod(arguments[0].toString().toUpperCase());
			break;
		
		// setOutput
		case 1:
			assertNotConnected();
			assertArgumentCount(arguments, 1);
			assertArgumentIsString(arguments[0], "output");
			outputText = arguments[0].toString();
			break;
			
		// setProperty
		case 2:
			assertNotConnected();
			assertArgumentCount(arguments, 2);
			assertArgumentIsString(arguments[0], "header");
			assertArgumentIsString(arguments[0], "value");
			connection.setRequestProperty(arguments[0].toString(), arguments[1].toString());
			break;
		
		// connect
		case 3:
			if (response == null) {
				response = new HTTPResponse(connection, outputText);
			}
			return new Object[] { response };
		}
		
		return null;
	}
	
	private void assertArgumentIsString(Object arg, String name) throws Exception {
		if (!(arg instanceof String)) {
			throw new Exception("Argument " + name + " must be a string");
		}
	}
	
	private void assertArgumentCount(Object[] args, int count) throws Exception {
		if (args.length != count) {
			throw new Exception("Requires " + count + " arguments");
		}
	}
	
	private void assertNotConnected() throws Exception {
		if (response != null) {
			throw new Exception("Cannot change parameters after connection is established");
		}
	}
}
