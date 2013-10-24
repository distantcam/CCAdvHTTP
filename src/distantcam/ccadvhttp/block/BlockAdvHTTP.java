package distantcam.ccadvhttp.block;

import distantcam.ccadvhttp.CCAdvHTTP;
import distantcam.ccadvhttp.tile.TileEntityAdvHTTP;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAdvHTTP extends BlockContainer {

	public BlockAdvHTTP() {
		super(CCAdvHTTP.blockAdvHTTPID, Material.ground);
		setHardness(0.5F);
        setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override
    public void registerIcons(IconRegister register) {
            blockIcon = register.registerIcon(CCAdvHTTP.ID.toLowerCase() + ":advhttp");
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityAdvHTTP(world);
	}

}
