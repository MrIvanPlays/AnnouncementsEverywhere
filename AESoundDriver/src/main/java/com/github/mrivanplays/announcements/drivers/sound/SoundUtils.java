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

import com.github.mrivanplays.announcements.drivers.sound.exceptions.InvalidSoundException;
import com.github.mrivanplays.announcements.drivers.sound.exceptions.SoundNullException;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SoundUtils {
  private static List<String> sounds = new ArrayList<>();
  private static String nmsVersion =
      Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

  public static void playSound(Player player, String sound) {
    addAll();
    wrap(player, sound);
  }

  private static void wrap(Player player, String playedSound) {
    addAll();
    if (playedSound.equalsIgnoreCase("note pling")) {
      if (isLegacy()) {
        play(player, "BLOCK_NOTE_PLING");
      }
      if (is1_13AndUp()) {
        play(player, "BLOCK_NOTE_BLOCK_PLING");
      }
    } else if (playedSound.equalsIgnoreCase("level up")) {
      if (isLegacy()) {
        play(player, "ENTITY_PLAYER_LEVELUP");
      }
      if (is1_13AndUp()) {
        play(player, "ENTITY_PLAYER_LEVELUP");
      }
    } else if (playedSound.equalsIgnoreCase("ghast scream")) {
      if (isLegacy()) {
        play(player, "ENTITY_GHAST_SCREAM");
      }
      if (is1_13AndUp()) {
        play(player, "ENTITY_GHAST_SCREAM");
      }
    } else if (playedSound.equalsIgnoreCase("thunder")) {
      if (isLegacy()) {
        play(player, "ENTITY_LIGHTNING_THUNDER");
      }
      if (is1_13AndUp()) {
        play(player, "ENTITY_LIGHTNING_BOLT_THUNDER");
      }
    } else if (playedSound.equalsIgnoreCase("villager yes")) {
      if (isLegacy()) {
        play(player, "ENTITY_VILLAGER_YES");
      }
      if (is1_13AndUp()) {
        play(player, "ENTITY_VILLAGER_YES");
      }
    } else if (playedSound.equalsIgnoreCase("wither shoot")) {
      if (isLegacy()) {
        play(player, "ENTITY_WITHER_SHOOT");
      }
      if (is1_13AndUp()) {
        play(player, "ENTITY_WITHER_SHOOT");
      }
    } else if (playedSound.equalsIgnoreCase("explode")) {
      if (isLegacy()) {
        play(player, "ENTITY_GENERIC_EXPLODE");
      }
      if (is1_13AndUp()) {
        play(player, "ENTITY_GENERIC_EXPLODE");
      }
    }
    if (isNullOrEmpty(playedSound)) {
      throw new SoundNullException("The sound cant be empty!");
    }
    if (!sounds.contains(playedSound)) {
      throw new InvalidSoundException("Invalid sound " + playedSound);
    }
  }

  private static boolean isLegacy() {
    return nmsVersion.equalsIgnoreCase("v1_9_R1")
        || nmsVersion.equalsIgnoreCase("v1_9_R2")
        || nmsVersion.equalsIgnoreCase("v1_10_R1")
        || nmsVersion.equalsIgnoreCase("v1_11_R1")
        || nmsVersion.equalsIgnoreCase("v1_12_R1");
  }

  private static boolean is1_13AndUp() {
    return nmsVersion.equalsIgnoreCase("v1_13_R1")
        || nmsVersion.equalsIgnoreCase("v1_13_R2")
        || nmsVersion.equalsIgnoreCase("v1_14_R1");
  }

  private static boolean isNullOrEmpty(String toTest) {
    return toTest == null || toTest.length() == 0;
  }

  private static void play(Player player, String sound) {
    player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
  }

  private static void addAll() {
    sounds.add("note pling");
    sounds.add("level up");
    sounds.add("ghast scream");
    sounds.add("thunder");
    sounds.add("villager yes");
    sounds.add("wither shoot");
    sounds.add("explode");
  }
}
