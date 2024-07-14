package dev.vansen.playtime.cmds;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaytimeCmd implements CommandExecutor {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0 && sender instanceof Player player) {
            long playtime = getPlaytime(player);
            String message = formatPlaytimeMessage("Your", playtime);
            player.sendActionBar(miniMessage.deserialize(message));
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                long playtime = getPlaytime(target);
                String message = formatPlaytimeMessage(target.getName(), playtime);
                sender.sendMessage(miniMessage.deserialize(message));
            } else {
                sender.sendMessage(miniMessage.deserialize("<red>Player not found or offline.</red>"));
            }
        } else {
            sender.sendMessage(miniMessage.deserialize("<red>Usage: /playtime [player]</red>"));
        }
        return true;
    }

    private long getPlaytime(@NotNull Player player) {
        long playtimeTicks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
        return playtimeTicks / 20 / 60;
    }

    private String formatPlaytimeMessage(@NotNull String name, long playtimeMinutes) {
        long days = playtimeMinutes / (24 * 60);
        long hours = (playtimeMinutes % (24 * 60)) / 60;
        long minutes = playtimeMinutes % 60;

        String dayUnit = days <= 2 ? "day" : "days";
        String hourUnit = hours <= 2 ? "hour" : "hours";
        String minuteUnit = minutes <= 2 ? "minute" : "minutes";

        return String.format("<green>%s playtime is %d %s, %d %s, and %d %s</green>", name, days, dayUnit, hours, hourUnit, minutes, minuteUnit);
    }
}
