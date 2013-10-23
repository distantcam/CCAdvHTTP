package distantcam.ccadvhttp.tile;

import java.io.*;
import java.net.*;
import java.util.List;

import dan200.computer.api.IComputerAccess;
import dan200.computer.api.ILuaContext;
import dan200.computer.api.IPeripheral;
import distantcam.ccadvhttp.HTTPRequestException;
import distantcam.ccadvhttp.ResponseObject;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityAdvHTTP extends TileEntity implements IPeripheral {

	private World world;
	
	public TileEntityAdvHTTP(World world) {
		this.world = world;
	}
	
	@Override
	public String getType() {
		return "AdvHTTP_peripheral";
	}

	@Override
	public String[] getMethodNames() {
		return new String[] { "request" };
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context,
			int method, Object[] arguments) throws Exception {
		if (arguments.length < 1) {
            throw new Exception("URL expected, got nil");
        }

        if (!(arguments[0] instanceof String)) {
            throw new Exception("String expected for argument 0");
        }
        
        for (int i = 2; i < arguments.length; i++) {
        	if (!(arguments[i] instanceof String)) {
                throw new Exception("String expected for argument " + i);
            }
        }
        
        if (arguments.length > 2 && arguments.length % 2 != 0) {
        	throw new Exception("Request properties must be in pairs");
        }

        final String urlString = arguments[0].toString();
        String postString = null;
        if (arguments.length > 1 && arguments[1] instanceof String) {
            postString = arguments[1].toString();
        }
        
        int requestPropertiesLength = arguments.length > 2 ? arguments.length - 2 : 0;
        String[] requestProperties = new String[requestPropertiesLength];
        for (int i = 2; i < arguments.length; i++) {
        	requestProperties[i - 2] = arguments[i].toString();
        }
        
        return DoHTTPRequest(urlString, postString, requestProperties);
	}

	@Override
	public boolean canAttachToSide(int side) {
		return true;
	}

	@Override
	public void attach(IComputerAccess computer) {
	}

	@Override
	public void detach(IComputerAccess computer) {
	}
	
	private Object[] DoHTTPRequest(String urlString, String postString,
			String[] requestProperties) throws HTTPRequestException {
		URL url;
		try {
			url = new URL(urlString);
			final String protocol = url.getProtocol().toLowerCase();
			if (!protocol.equals("http") && !protocol.equals("https")) {
                throw new HTTPRequestException("Not an HTTP URL");
			}
		}
        catch (MalformedURLException e) {
            throw new HTTPRequestException("Invalid URL");
        }
		
		try {
			final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	        if (postString == null) {
	            connection.setRequestMethod("GET");
	        }
	        else {
	            connection.setRequestMethod("POST");
	            connection.setDoOutput(true);
	            final OutputStream os = connection.getOutputStream();
	            final OutputStreamWriter osr = new OutputStreamWriter(os);
	            final BufferedWriter writer = new BufferedWriter(osr);
	            writer.write(postString, 0, postString.length());
	            writer.close();
	        }
	        for (int i = 0; i < requestProperties.length; i+=2) {
	        	connection.setRequestProperty(requestProperties[i], requestProperties[i+1]);
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
            int responseCode = connection.getResponseCode();
            connection.disconnect();
            
            return new Object[] { new ResponseObject(result.toString(), responseCode) };
		}
        catch (IOException e) {
        	throw new HTTPRequestException("Error reading http connection");
        }
	}

}
