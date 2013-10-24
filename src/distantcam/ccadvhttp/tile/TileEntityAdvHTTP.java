package distantcam.ccadvhttp.tile;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.logging.Level;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dan200.computer.api.*;
import distantcam.ccadvhttp.CCAdvHTTP;
import distantcam.ccadvhttp.lua.HTTPRequest;

public class TileEntityAdvHTTP extends TileEntity implements IPeripheral {

	private static HashMap<Integer, Integer> mountMap = new HashMap<Integer, Integer>();
	
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
		if (CCAdvHTTP.mount != null) {
			int id = computer.getID();

	        int mountCount = 0;
	        if (mountMap.containsKey(id)) {
	        	mountCount = mountMap.get(id);
	        }
	        if (mountCount < 1) {
	            mountCount = 0;
	            computer.mount("ahttp", CCAdvHTTP.mount);
	        }
	        mountMap.put(id, mountCount + 1);
		}
	}

	@Override
	public void detach(IComputerAccess computer) {
		if (CCAdvHTTP.mount != null) {
			int id = computer.getID();
	        int mountCount = 0;
	        if (mountMap.containsKey(id)) {
	        	mountCount = mountMap.get(id);
	        }
	        mountCount--;
	        if (mountCount < 1) {
	            mountCount = 0;
	            try {
	                computer.unmount("ahttp");
	            } catch (Exception e) {
	            }
	        }
	        mountMap.put(id, mountCount);
		}
	}
}
