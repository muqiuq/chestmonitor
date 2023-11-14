package ch.uisa.minecraft.chestmonitor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ChestMonitor {

    public String ID;

    public ArrayList<String> content = new ArrayList<String>();
    public HashMap<String, Long> accessLog = new HashMap<>();

    public String lastAccess = null;

    public String lastAccessByPlayer = null;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ChestMonitor(String id) {
        this.ID = id;
    }

    public ChestMonitor() {

    }

    @JsonIgnore
    public final String[] IntrestringItemKeyWords = {"DIAMOND", "STAR", "BEACON", "NETHERITE"};

    public static String createId(Inventory inventory, HumanEntity player) {
        if(inventory.getType() == InventoryType.BARREL || inventory.getType() == InventoryType.CHEST) {
            String ID = String.format(
                    "%s:%.2f:%.2f:%.2f",
                    inventory.getLocation().getWorld().getName(),
                    inventory.getLocation().getX(),
                    inventory.getLocation().getY(),
                    inventory.getLocation().getZ()
            );
            return ID;
        }
        else if(inventory.getType() == InventoryType.ENDER_CHEST) {
            return String.format("ENDERCHEST:%s", player.getName());
        }
        else if(inventory.getType() == InventoryType.SHULKER_BOX) {
            return String.format("SHULKERBOX:%s", player.getName());
        }
        else {
            return UUID.randomUUID().toString();
        }
    }

    public boolean containsIntrestingMaterials() {
        for(String c: content) {
            for(String i: IntrestringItemKeyWords) {
                if(c.contains(i)) return true;
            }
        }
        return false;
    }

    public void updateContent(Inventory inventory) {
        content.clear();
        ItemStack[] itemcontents = inventory.getStorageContents();
        for(ItemStack is: itemcontents) {
            if(is != null)
                content.add(is.toString());
        }
    }

    public void logPlayerAccess(HumanEntity player) {
        if(!accessLog.containsKey(player.getName())) {
            accessLog.put(player.getName(), 0l);
        }
        accessLog.put(player.getName(), accessLog.get(player.getName()) + 1l);
        lastAccessByPlayer = player.getName();
        lastAccess = LocalDateTime.now().format(dateTimeFormatter);
    }

    @Override
    public String toString() {
        String intrestringMaterialsSign = "";
        if(this.containsIntrestingMaterials()) {
            intrestringMaterialsSign = " *";
        }
        return "CM " + ID + " " + lastAccess + intrestringMaterialsSign;
    }
}
