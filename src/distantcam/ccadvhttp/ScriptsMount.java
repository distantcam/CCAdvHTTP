package distantcam.ccadvhttp;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import dan200.computer.api.IMount;

public class ScriptsMount implements IMount {
	
	private static String localLuaFolder;
	private static String localLuaListing;
	
	static {
		File baseDirectory = null;

        if (FMLLaunchHandler.side().isClient()) {
                baseDirectory = Minecraft.getMinecraft().mcDataDir;
        } else {
                baseDirectory = new File(".");
        }

        File modFolder = new File(baseDirectory, "mods");
        File ccadvhttpFolder = new File(modFolder, "ccadvhttp");
        File luaFolder = new File(ccadvhttpFolder, "lua");

        localLuaListing = ccadvhttpFolder.getAbsolutePath() + "/scripts.txt";
        localLuaFolder = luaFolder.getAbsolutePath();
        
        String externalAssets = "https://raw.github.com/distantcam/CCAdvHTTP/master/assets/ccadvhttp/";
        String externalLuaListing = String.format("%s%s", externalAssets, "scripts.txt");
        String externalLuaFolder = String.format("%s%s", externalAssets, "lua/");
        
        refreshLatestFiles(externalLuaListing, externalLuaFolder);        
	}
	
	private static void refreshLatestFiles(String externalLuaListing, String externalLuaFolder) {

        downloadFileIfOlderThan(externalLuaListing, localLuaListing, 7);

        try {
        	BufferedReader reader = new BufferedReader(new FileReader(localLuaListing));
        	String line;
            while ((line = reader.readLine()) != null) {
            	downloadFileIfOlderThan(String.format("%s%s", externalLuaFolder, line), String.format("%s/%s", localLuaFolder, line), 7);
            }
            reader.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
	}
	
	private static void downloadFileIfOlderThan(String external, String local, int days) {

        File file = new File(local);

        file.getParentFile().mkdirs();

        if (!file.exists() || (file.lastModified() < System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000))) {

                BufferedInputStream in = null;
                FileOutputStream fout = null;
                try {
                        in = new BufferedInputStream(new URL(external).openStream());
                        fout = new FileOutputStream(local);

                        byte data[] = new byte[1024];
                        int count;
                        while ((count = in.read(data, 0, 1024)) != -1) {
                                fout.write(data, 0, count);
                        }
                        fout.close();
                } catch (Exception e) {}
                try {
                        if (in != null) in.close();
                        if (fout != null) fout.close();
                } catch (IOException e) {}
        }

	}

	@Override
    public boolean exists(String path) throws IOException {
            File file = new File(new File(localLuaFolder), path);
            return file.exists();
    }

    @Override
    public boolean isDirectory(String path) throws IOException {
            File file = new File(new File(localLuaFolder), path);
            return file.isDirectory();
    }

    @Override
    public void list(String path, List<String> contents) throws IOException {
            File directory = new File(new File(localLuaFolder), path);
            for (File file : directory.listFiles()) {
                    contents.add(file.getName());
            }
    }

    @Override
    public long getSize(String path) throws IOException {
            File file = new File(new File(localLuaFolder), path);
            return file.length();
    }

    @Override
    public InputStream openForRead(String path) throws IOException {
            File file = new File(new File(localLuaFolder), path);
            return new FileInputStream(file);
    }

}
