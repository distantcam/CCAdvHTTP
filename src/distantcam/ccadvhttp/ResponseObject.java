package distantcam.ccadvhttp;

import dan200.computer.api.ILuaContext;
import dan200.computer.api.ILuaObject;

public class ResponseObject implements ILuaObject {

	private String[] lines;
	private int responseCode;
	private int currentLine;
	
	public ResponseObject(String result, int responseCode) {
		lines = result.split("\n");
		
		this.responseCode = responseCode;
	}

	@Override
	public String[] getMethodNames() {
		return new String[] { "readLine", "readAll", "getResponseCode" };
	}

	@Override
	public Object[] callMethod(ILuaContext context, int method,
			Object[] arguments) throws Exception {
		switch (method) {
			case 0: {
				return new Object[] { lines[currentLine++] };
			}
			case 1: {
                final StringBuilder result = new StringBuilder("");
                while (currentLine < lines.length) {
                	result.append(lines[currentLine++]);
                	result.append('\n');
                }                    	
                return new Object[] { result.toString() };
			}
			case 2: {
				return new Object[] { responseCode };
			}
		}
		return null;
	}

}
