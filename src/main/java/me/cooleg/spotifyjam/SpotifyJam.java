/*
MIT License

Copyright (c) 2024 Cooleg

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

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
