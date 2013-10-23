package distantcam.ccadvhttp.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import distantcam.ccadvhttp.CCAdvHTTP;
import distantcam.ccadvhttp.block.BlockAdvHTTP;
import distantcam.ccadvhttp.tile.TileEntityAdvHTTP;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CommonProxy {
	
	public void init() {
		CCAdvHTTP.Blocks.blockAdvHTTP = new BlockAdvHTTP(CCAdvHTTP.blockAdvHTTPID, Material.ground);
		
		final ItemStack advHTTPStack = new ItemStack(CCAdvHTTP.Blocks.blockAdvHTTP);
		GameRegistry.addRecipe(advHTTPStack, new Object[] { "SRS", "SRS", "SSS" }, 'S', Block.stone, 'R', Item.redstone);
		GameRegistry.registerBlock(CCAdvHTTP.Blocks.blockAdvHTTP, "blockAdvHTTP.ccadvhttp.distantcam");

		GameRegistry.registerTileEntity(TileEntityAdvHTTP.class, "blockAdvHTTPTileEntity.ccadvhttp.distantcam");
        
        LanguageRegistry.addName(CCAdvHTTP.Blocks.blockAdvHTTP, "Advanced HTTP");
        
        CCAdvHTTP.Blocks.blockAdvHTTP.setCreativeTab(CreativeTabs.tabMisc);		
	}

}
