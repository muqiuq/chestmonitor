package ch.uisa.minecraft.chestmonitor.commands;

import ch.uisa.minecraft.chestmonitor.ChestMonitor;
import ch.uisa.minecraft.chestmonitor.Global;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Logger;

public class InfoCommand implements CommandExecutor {

    private final Logger logger;

    public InfoCommand(Logger logger) {
        this.logger = logger;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if(strings.length != 1) {
            player.sendMessage("Invalid syntax");
            return true;
        }

        player.sendMessage("Last accessed chests for " + strings[0]);
        List<ChestMonitor> allChestsForPlayer = Global.chestDB.get20LatestChestsForPlayer(strings[0]);
        for(ChestMonitor m: allChestsForPlayer) {
            player.sendMessage(m.toString());
        }

        return true;
    }
}
