package dev.viaduct.factories.utils;

import dev.viaduct.factories.FactoriesPlugin;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat {

    public static void log(String... messages) {
        for (final String message : messages)
            log(message);
    }

    public static void log(String messages) {
        tell(Bukkit.getConsoleSender(), "[" + FactoriesPlugin.getInstance().getName() + "] " + messages);
    }

    public static void tell(CommandSender toWhom, String... messages) {
        for (final String message : messages)
            tell(toWhom, message);
    }

    public static void tell(CommandSender toWhom, List<String> messages) {
        for (final String message : messages)
            tell(toWhom, message);
    }

    public static void tell(CommandSender toWhom, String message) {
        toWhom.sendMessage(colorize(message));
    }

    public static void sendActionbar(Player toWhom, String message) {
        toWhom.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                new TextComponent(Chat.colorize(message)));
    }

    public static void tellFormatted(CommandSender toWhom, String message, Object... args) {
        tell(toWhom, String.format(message, args));
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String colorizeHex(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) {
                builder.append("&").append(c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String applyGradient(String message, String startHex, String endHex, boolean bold) {
        // Remove the '#' from the hex values if present
        startHex = startHex.replace("#", "");
        endHex = endHex.replace("#", "");

        // Calculate the length of the message and prepare a StringBuilder for the result
        int length = message.length();
        StringBuilder coloredMessage = new StringBuilder();

        // Parse the starting and ending hex colors into RGB components
        int startR = Integer.parseInt(startHex.substring(0, 2), 16);
        int startG = Integer.parseInt(startHex.substring(2, 4), 16);
        int startB = Integer.parseInt(startHex.substring(4, 6), 16);

        int endR = Integer.parseInt(endHex.substring(0, 2), 16);
        int endG = Integer.parseInt(endHex.substring(2, 4), 16);
        int endB = Integer.parseInt(endHex.substring(4, 6), 16);

        // For each character in the message, calculate the interpolated color
        for (int i = 0; i < length; i++) {
            double ratio = (double) i / (length - 1); // Ratio between 0 and 1

            // Calculate the interpolated RGB values
            int r = (int) (startR + ratio * (endR - startR));
            int g = (int) (startG + ratio * (endG - startG));
            int b = (int) (startB + ratio * (endB - startB));

            // Convert RGB back to hex format
            String hexColor = String.format("#%02x%02x%02x", r, g, b);

            // Build the color code for the current character
            String colorCode = colorizeHex(hexColor + message.charAt(i));

            // Apply the bold (&l) formatting if necessary
            if (bold) {
                colorCode = "&l" + colorCode;
            }

            coloredMessage.append(colorCode);
        }

        // Return the final result with color codes translated
        return ChatColor.translateAlternateColorCodes('&', coloredMessage.toString());
    }

    public static List<String> colorizeList(List<String> list) {
        List<String> temp = new ArrayList<>();
        for (String s : list)
            temp.add(colorize(s));
        return temp;
    }

    public static String strip(String text) {
        return ChatColor.stripColor(colorize(text));
    }

    public static List<String> strip(List<String> list) {
        List<String> temp = new ArrayList<>();
        for (String s : colorizeList(list)) {
            temp.add(ChatColor.stripColor(s));
        }
        return temp;
    }

    public static int getLength(String text, boolean ignoreColorCodes) {
        return ignoreColorCodes ? strip(text).length() : text.length();
    }

    public static String listToString(List<?> strings) {
        String d = ", ";

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < strings.size(); i++) {
            sb.append(strings.get(i));
            if (i != strings.size() - 1) {
                sb.append(d);
            }
        }
        return sb.toString();
    }

    public static String listToCommaSeperatedStringNoSpace(List<?> strings) {
        String d = ",";

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < strings.size(); i++) {
            sb.append(strings.get(i));
            if (i != strings.size() - 1) {
                sb.append(d);
            }
        }
        return sb.toString();
    }

}