package distantcam.ccadvhttp;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import distantcam.ccadvhttp.block.BlockAdvHTTP;
import distantcam.ccadvhttp.tile.TileEntityAdvHTTP;

@Mod(modid="distantcam.ccadvhttp", name="CCAdvHTTP", version="@VERSION@", dependencies="required-after:ComputerCraft;after:CCTurtle")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class CCAdvHTTP {
	
	Block blockAdvHTTP = new BlockAdvHTTP(496,Material.ground);
	
	@Mod.EventHandler
    public void init(FMLInitializationEvent event) {
		GameRegistry.registerTileEntity(TileEntityAdvHTTP.class, "blockAdvHTTPTileEntity.ccadvhttp.distantcam");
        GameRegistry.registerBlock(blockAdvHTTP, "blockAdvHTTP.ccadvhttp.distantcam");
        LanguageRegistry.addName(blockAdvHTTP, "Advanced HTTP");
        blockAdvHTTP.setCreativeTab(CreativeTabs.tabMisc);
    }
	
}
