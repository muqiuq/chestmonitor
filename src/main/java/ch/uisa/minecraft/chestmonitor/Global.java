package ch.uisa.minecraft.chestmonitor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

public class Global {


    public static Logger logger;

    public static Main main;

    public static ChestDB chestDB;

    public static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
}
