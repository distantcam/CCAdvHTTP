package distantcam.ccadvhttp.block;

import distantcam.ccadvhttp.tile.TileEntityAdvHTTP;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAdvHTTP extends BlockContainer {

	public BlockAdvHTTP(int blockID, Material blockMaterial) {
		super(blockID, blockMaterial);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityAdvHTTP(world);
	}

}
