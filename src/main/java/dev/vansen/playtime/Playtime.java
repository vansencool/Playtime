package dev.vansen.playtime;

import dev.vansen.playtime.cmds.PlaytimeCmd;
import org.bukkit.plugin.java.JavaPlugin;

public class Playtime extends JavaPlugin {
    
    @Override
    public void onEnable() {
        getCommand("playtime").setExecutor(new PlaytimeCmd());
    }
}
