package net.tropixmc.tropisk;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ColorUtils {
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    // Predefined colors
    public static final String PRIMARY = "<#3498db>";
    public static final String SECONDARY = "<#2ecc71>";
    public static final String ACCENT = "<#e74c3c>";
    public static final String WARNING = "<#f1c40f>";
    public static final String ERROR = "<#e74c3c>";
    public static final String SUCCESS = "<#2ecc71>";
    public static final String INFO = "<#95a5a6>";

    // Message templates
    public static final String SUCCESS_TEMPLATE = "<green>[+] {message}</green>";
    public static final String ERROR_TEMPLATE = "<red>[-] {message}</red>";
    public static final String WARNING_TEMPLATE = "<yellow>[!] {message}</yellow>";
    public static final String INFO_TEMPLATE = "<gray>[*] {message}</gray>";

    public static Component parse(String message) {
        return miniMessage.deserialize(message);
    }

    public static String format(String template, String message) {
        return template.replace("{message}", message);
    }
}