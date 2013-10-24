package distantcam.ccadvhttp;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import distantcam.ccadvhttp.proxy.CommonProxy;

@Mod(modid=CCAdvHTTP.ID, name=CCAdvHTTP.NAME, version=CCAdvHTTP.VERSION, dependencies="required-after:ComputerCraft;after:CCTurtle", useMetadata = true)
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class CCAdvHTTP {
	
	public static final String ID = "ccadvhttp";
    public static final String NAME = "CCAdvHTTP";
    public static final String VERSION = "@VERSION@";
	
	@Instance(ID)
	public static CCAdvHTTP instance;
	
	@SidedProxy(clientSide = "distantcam.ccadvhttp.proxy.ClientProxy", serverSide = "distantcam.ccadvhttp.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Logger logger;
	
	public static int blockAdvHTTPID;
		
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		logger.setParent(FMLLog.getLogger());
		
		logger.log(Level.INFO, NAME + " v" + VERSION);

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		blockAdvHTTPID = config.getBlock("block", "AdvHTTP", 567).getInt();
	}
	
	@Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    	proxy.init();
    }
	
	public static class Blocks {
		public static Block blockAdvHTTP;
	}
}
