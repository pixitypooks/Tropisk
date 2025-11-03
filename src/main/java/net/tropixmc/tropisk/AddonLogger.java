package net.tropixmc.tropisk;

import org.bukkit.ChatColor;

public class AddonLogger {
    public static void info(String message) {
        log(LogLevel.INFO, message);
    }

    public static void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    public static void error(String message) {
        log(LogLevel.ERROR, message);
    }

    private static void log(LogLevel level, String message) {
        String prefix = ChatColor.GRAY + "[" + level.getColor() + level.name() + ChatColor.GRAY + "] " + ChatColor.RESET;
        net.tropixmc.tropisk.tropisk.getInstance().getLogger().info(prefix + message);
    }

    private enum LogLevel {
        INFO(ChatColor.GREEN),
        WARNING(ChatColor.YELLOW),
        ERROR(ChatColor.RED);

        private final ChatColor color;

        LogLevel(ChatColor color) {
            this.color = color;
        }

        public ChatColor getColor() {
            return color;
        }
    }
}