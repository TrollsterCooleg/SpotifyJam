package me.cooleg.spotifyjam;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Pattern;

public final class SpotifyJam extends JavaPlugin implements Listener {

    private static final TextReplacementConfig replacementConfig;

    static {
        Pattern linkPattern = Pattern.compile("https://spotify\\.link/[a-zA-Z0-9]{11}");

        replacementConfig = TextReplacementConfig.builder().match(linkPattern)
                .replacement((match, builder) -> Component.text("[Spotify Jam]")
                         .color(NamedTextColor.GREEN)
                         .clickEvent(ClickEvent.openUrl(match.group(0)))).build();
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void messageSend(AsyncChatEvent event) {
        if (event.getPlayer().hasPermission("spotifyjam.embed")) {
            event.message(convertMessage(event.message()));
        }
    }

    public Component convertMessage(Component component) {
        return component.replaceText(replacementConfig);
    }
}
