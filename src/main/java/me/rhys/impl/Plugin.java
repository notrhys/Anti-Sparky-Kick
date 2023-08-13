package me.rhys.impl;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Plugin extends JavaPlugin implements Listener {

    private final List<Integer> reasons = new ArrayList<>();

    @Override
    public void onEnable() {
        for (int i = 0; i < 10; i++) {
            reasons.add(i);
        }

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onKick(PlayerKickEvent event) {
        this.reasons.forEach(integer -> {
            if (event.getReason().equalsIgnoreCase(String.format("Disconnected [%s]", integer))
                    || event.getReason().equalsIgnoreCase(String.format("Internal Exception: java.io.IOException: Error while write(...): Broken pipe [%s]", integer))
                    || event.getReason().equalsIgnoreCase(String.format("Internal Exception [%s]", integer))) {
                event.setCancelled(true);
                getLogger().info("Prevented kick ~ " + event.getPlayer().getName());
            }
        });
    }
}
