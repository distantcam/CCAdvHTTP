package distantcam.ccadvhttp.tile;

import dan200.computer.api.IComputerAccess;
import dan200.computer.api.ILuaContext;
import dan200.computer.api.IPeripheral;
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
		return new String[] {"method1", "method2", "echo"};
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context,
			int method, Object[] arguments) throws Exception {
		switch(method) {
		case 0:
			return new Object[] { "You just called method1!" };
		case 1:
			return new Object[] { "You just called method2!" };
		case 2: {
			Object[] returnObject = new Object[10];
			for (int k = 0; k < arguments.length; k++) {
				returnObject[k] = "You said: " + ((String)arguments[k]);
			}
			return returnObject;
		}
		default:
			return new Object[] { "That method does not exist!" };
		}
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
