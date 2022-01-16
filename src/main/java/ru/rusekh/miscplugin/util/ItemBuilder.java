package ru.rusekh.miscplugin.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {

  private final ItemStack is;
  private final ItemMeta im;

  public ItemBuilder(final Material m, final int amount) {
    this.is = new ItemStack(m, amount);
    this.im = this.is.getItemMeta();
  }

  public ItemBuilder(final Material m, final int amount, final short durability) {
    this.is = new ItemStack(m, amount, durability);
    this.im = this.is.getItemMeta();
  }

  public ItemBuilder setName(final String name) {
    this.im.setDisplayName(ChatUtil.color(name));
    this.is.setItemMeta(this.im);
    return this;
  }

  public ItemBuilder setLore(final String lore) {
    this.im.setLore(ChatUtil.color(Collections.singletonList(lore)));
    this.is.setItemMeta(this.im);
    return this;
  }

  public ItemBuilder addLore(final String lore) {
    if (lore == null) {
      return this;
    }

    if (this.im.getLore() == null) {
      this.im.setLore(new ArrayList<>());
    }

    this.im.getLore().add(ChatUtil.color(lore));
    this.is.setItemMeta(this.im);
    return this;
  }

  public ItemBuilder setLore(final List<String> lore) {
    this.im.setLore(ChatUtil.color(lore));
    this.is.setItemMeta(this.im);
    return this;
  }

  public ItemBuilder setLore(final String... lore) {
    this.im.setLore(ChatUtil.color(Arrays.asList(lore)));
    this.is.setItemMeta(this.im);
    return this;
  }

  public ItemBuilder setSkullOwner(final String owner) {
    ((SkullMeta) this.im).setOwner(owner);
    this.is.setItemMeta(this.im);
    return this;
  }

  public ItemBuilder setSkullBase64(final String base64) {
    try {
      Field gameProfileField = this.im.getClass().getDeclaredField("profile");
      gameProfileField.setAccessible(true);
      GameProfile profile = (GameProfile) gameProfileField.get(this.im);
      if(profile == null)
      {
        profile = new GameProfile(UUID.randomUUID(), "Memexurer nie umie w jave");
        gameProfileField.set(this.im, profile);
      }

      profile.getProperties().put("textures", new Property("textures", base64));

      this.is.setItemMeta(this.im);
    } catch (ReflectiveOperationException ex) {
      throw new RuntimeException(ex);
    }
    return this;
  }

  private static final String SKULL_URL_FORMAT = "{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/%s\"}}}";

  public ItemBuilder setSkullUrl(final String url) {
    return setSkullBase64(Base64.getEncoder().encodeToString(String.format(SKULL_URL_FORMAT, url).getBytes(
        StandardCharsets.UTF_8)));
  }

  public ItemBuilder addEnchant(final Enchantment enchantment, final int level) {
    this.is.addUnsafeEnchantment(enchantment, level);
    return this;
  }

  public ItemBuilder addEnchant(final Map<Enchantment, Integer> map) {
    this.is.addUnsafeEnchantments(map);
    return this;
  }

  public ItemBuilder addFlag(final ItemFlag... flag) {
    this.im.addItemFlags(flag);
    this.is.setItemMeta(this.im);
    return this;
  }

  public ItemBuilder setColor(final Color color) {
    ((LeatherArmorMeta) this.im).setColor(color);
    this.is.setItemMeta(this.im);
    return this;
  }

  public ItemStack build() {
    return this.is;
  }
}
