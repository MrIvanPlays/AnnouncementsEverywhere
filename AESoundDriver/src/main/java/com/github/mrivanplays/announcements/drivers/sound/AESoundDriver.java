/*
 * Copyright 2019 Ivan Pekov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.mrivanplays.announcements.drivers.sound;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.File;

public class AESoundDriver extends JavaPlugin implements PluginMessageListener {

  @Override
  public void onEnable() {
    checkIfBungee();
    if (!getServer().getPluginManager().isPluginEnabled(this)) return;
    getServer().getMessenger().registerIncomingPluginChannel(this, "ae:announcements", this);
    getLogger().info("AnnouncementsEverywhere Sound Driver enabled successfully. The driver is ready to receive plugin messages!");
  }

  @Override
  public void onDisable() {
    getLogger().info("AnnouncementsEverywhere Sound Driver disabled.");
  }

  @Override
  public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
    if (!channel.equalsIgnoreCase("ae:announcements")) {
      return;
    }
    ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
    String subChannel = in.readUTF();
    if (subChannel.equalsIgnoreCase("PlaySound")) {
      String sound = in.readUTF();
      SoundUtils.playSound(player, sound);
    }
  }

  private void checkIfBungee() {
    if (!getServer().getVersion().contains("Spigot") || !getServer().getVersion().contains("Paper")) {
      getLogger().severe("You probably run CraftBukkit... Please update atleast to spigot for this to work...");
      getLogger().severe("Plugin disabled!");
      getServer().getPluginManager().disablePlugin(this);
      return;
    }
    File file = new File(getDataFolder().getParentFile().getParent(), "spigot.yml");
    FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
    if (!configuration.getBoolean("settings.bungeecord")) {
      getLogger().severe("This server is not BungeeCord.");
      getLogger().severe("If the server is already hooked to BungeeCord, please enable it into your spigot.yml aswell.");
      getLogger().severe("Plugin disabled!");
      getServer().getPluginManager().disablePlugin(this);
    }
  }
}