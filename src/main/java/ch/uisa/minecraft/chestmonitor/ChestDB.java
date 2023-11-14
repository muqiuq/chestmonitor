package ch.uisa.minecraft.chestmonitor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ChestDB {

    public HashMap<String, ChestMonitor> chestsById = new HashMap<>();
    private String filename;

    private int saveCounter = 0;
    private final int ON_SAVE_AFTER = 20;

    public ChestDB(String filename) {
        this.filename = filename;
    }


    public List<ChestMonitor> get20LatestChestsForPlayer(String playerName) {
        List<ChestMonitor> allChestForPlayer = chestsById.values().stream()
                .filter(x -> x.lastAccessByPlayer.equals(playerName))
                .sorted(Comparator.comparing((ChestMonitor o) -> o.lastAccess).reversed())
                .limit(20)
                .toList();
        return allChestForPlayer;
    }

    public void logAndUpdate(HumanEntity player, Inventory inventory) throws IOException {
        String id = ChestMonitor.createId(inventory, player);
        if(!chestsById.containsKey(id)) {
            chestsById.put(id, new ChestMonitor(id));
        }
        ChestMonitor cm = chestsById.get(id);
        cm.updateContent(inventory);
        cm.logPlayerAccess(player);
        saveCounter++;
        if(saveCounter > ON_SAVE_AFTER) {
            this.write();
            saveCounter = 0;
        }
    }

    public void open() throws IOException {
        if((new File(filename)).exists()) {
            ObjectMapper objectMapper = new ObjectMapper();

            HashMap<String, ChestMonitor> loadedChestMonitors = objectMapper.readValue(new FileReader(filename), new TypeReference<HashMap<String, ChestMonitor>>() {});

            for(String key: loadedChestMonitors.keySet()) {
                chestsById.put(key, loadedChestMonitors.get(key));
            }
        }
    }

    public void write() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        objectMapper.writeValue(new FileWriter(filename), chestsById);
    }

}
