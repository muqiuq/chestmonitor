package ch.uisa.minecraft.chestmonitor;

import ch.uisa.minecraft.chestmonitor.commands.InfoCommand;
import ch.uisa.minecraft.chestmonitor.events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private Logger logger;

    @Override
    public void onEnable() {
        Global.logger = Bukkit.getLogger();
        Global.main = this;

        Bukkit.getLogger().info(ChatColor.GREEN + "Enabled " + this.getName());
        logger = Bukkit.getLogger();

        try {
            Config.Load();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Cannot properly start without Config File");
            return;
        }

        Global.chestDB = new ChestDB("plugins/chestmonitor.json");

        try {
            Global.chestDB.open();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not load chestmonitor data file");
            return;
        }


        this.getCommand("chestmonitorinfo").setExecutor(new InfoCommand(logger));

        getServer().getPluginManager().registerEvents(new OnInventoryOpenEvent(logger), this);
        getServer().getPluginManager().registerEvents(new OnPlayerEggThrowEvent(logger), this);

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.RED + "Disabled " + this.getName());
        try{
            Global.chestDB.write();
        }catch(IOException ex) {
            logger.severe("Could not save chestmonitor");
        }
        try{
            Global.executor.shutdown();
        }catch(Exception e) {
            logger.log(Level.FINER, "", e);
        }
    }

}
