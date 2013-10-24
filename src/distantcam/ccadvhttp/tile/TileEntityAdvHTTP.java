package distantcam.ccadvhttp.tile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.ILuaContext;
import dan200.computer.api.IPeripheral;
import distantcam.ccadvhttp.lua.HTTPRequest;

public class TileEntityAdvHTTP extends TileEntity implements IPeripheral {

	private World world;
	
	public TileEntityAdvHTTP(World world) {
		this.world = world;
	}
	
	@Override
	public String getType() {
		return "CCAdvHTTP";
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
            throw new Exception("String expected for url");
        }
        
        final String urlString = arguments[0].toString();
        
        return new Object[] { new HTTPRequest(urlString) };
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
}
