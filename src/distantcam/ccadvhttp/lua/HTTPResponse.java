package distantcam.ccadvhttp.lua;

import java.io.*;
import java.net.*;

import dan200.computer.api.ILuaContext;
import dan200.computer.api.ILuaObject;

public class HTTPResponse implements ILuaObject {

	private HttpURLConnection connection;
	
	private Object lock;
	private boolean complete;
	private boolean success;
	private String result;
	
	private Thread thread;
	
	public HTTPResponse(final HttpURLConnection connection, final String outputText) {
		this.connection = connection;
		
		lock = new Object();
		
		complete = false;
		success = false;
		
		thread = new Thread(new Runnable() {
			public void run() {
				try {
					if (outputText != null && outputText != "") {
						HTTPResponse.this.connection.setDoOutput(true);
	                    final OutputStream os = HTTPResponse.this.connection.getOutputStream();
	                    final OutputStreamWriter osr = new OutputStreamWriter(os);
	                    final BufferedWriter writer = new BufferedWriter(osr);
	                    writer.write(outputText, 0, outputText.length());
	                    writer.close();
					}
					
					final InputStream is = connection.getInputStream();
                    final InputStreamReader isr = new InputStreamReader(is);
                    final BufferedReader reader = new BufferedReader(isr);
                    final StringBuilder result = new StringBuilder();
                    
                    while (true) {
                    	final String line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        result.append(line);
                        result.append('\n');
                    }
                    reader.close();
                    
                    final Object localLock;
                    synchronized (localLock = HTTPResponse.this.lock) {
                    	HTTPResponse.this.complete = true;
    					HTTPResponse.this.success = true;
    					HTTPResponse.this.result = result.toString();
                    }
                    connection.disconnect();
				}
				catch (IOException e) {
					HTTPResponse.this.complete = true;
					HTTPResponse.this.success = false;
					HTTPResponse.this.result = null;
					return;
				}
			}
		});
		thread.start();
	}

	@Override
	public String[] getMethodNames() {
		return new String[] { "isComplete", "wasSuccessful", "responseCode", "result" };
	}

	@Override
	public Object[] callMethod(ILuaContext context, int method,
			Object[] arguments) throws Exception {
		final Object lock;
		
		switch (method) {
			case 0: {
				synchronized (lock = this.lock) {
					return new Object[] { complete };
				}
			}
			
			case 1: {
				synchronized (lock = this.lock) {
					return new Object[] { success };
				}
			}
			
			case 2: {
				assertComplete();
				return new Object[] { connection.getResponseCode() };
			}
			
			case 3: {
				assertComplete();
				return new Object[] { result };
			}
		}
		
		return null;
	}
	
	private void assertComplete() throws Exception {
		final Object lock;
		synchronized (lock = this.lock) {
			if (!complete) {
				throw new Exception("Method cannot be called until the response completes");
			}
		}
	}
}
