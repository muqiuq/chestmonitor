package ch.uisa.minecraft.chestmonitor.events;

import ch.uisa.minecraft.chestmonitor.ChestMonitor;
import ch.uisa.minecraft.chestmonitor.Global;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;

import java.io.IOException;
import java.util.logging.Logger;

public class OnPlayerEggThrowEvent implements Listener {

    private Logger logger;

    public OnPlayerEggThrowEvent(Logger logger) {
        this.logger = logger;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerEggThrowEvent(PlayerEggThrowEvent playerEggThrowEvent) throws IOException {
        logger.info(String.format("EGG-THROW BY %s", playerEggThrowEvent.getPlayer().getName()));
    }


}
