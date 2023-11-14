package ch.uisa.minecraft.chestmonitor.events;

import ch.uisa.minecraft.chestmonitor.ChestMonitor;
import ch.uisa.minecraft.chestmonitor.Global;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.logging.Logger;

public class OnInventoryOpenEvent implements Listener
{
    private Logger logger;

    public OnInventoryOpenEvent(Logger logger) {
        this.logger = logger;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerOpenInventory(InventoryCloseEvent inventoryCloseEvent) throws IOException {
        Inventory inventory = inventoryCloseEvent.getInventory();
        if(inventory.getType() != InventoryType.CHEST
                && inventory.getType() != InventoryType.BARREL
                && inventory.getType() != InventoryType.ENDER_CHEST
                && inventory.getType() != InventoryType.SHULKER_BOX) return;
        Global.chestDB.logAndUpdate(inventoryCloseEvent.getPlayer(), inventoryCloseEvent.getInventory());
        logger.info(String.format("%s opened chest at %s",
                inventoryCloseEvent.getPlayer().getName(),
                ChestMonitor.createId(inventoryCloseEvent.getInventory(), inventoryCloseEvent.getPlayer())));
    }

}
