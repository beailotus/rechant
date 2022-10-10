package io.beailotus.rechant;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Rechant extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("[#] Registering events");
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("[+] Plugin enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void rechantBook(PrepareAnvilEvent event){
        ItemStack first = event.getInventory().getItem(0);
        ItemStack second = event.getInventory().getItem(1);

        if(first == null || second == null) return;

        if(!second.equals(new ItemStack(Material.BOOK))) return;

        if(first.getEnchantments().isEmpty()) return;
        if(!second.getEnchantments().isEmpty()) return;

        ItemStack result = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) result.getItemMeta();

        for (Enchantment enchantment : first.getEnchantments().keySet()) {
            assert meta != null;
            meta.addStoredEnchant(enchantment, first.getEnchantments().get(enchantment), true);
        }

        result.setItemMeta(meta);

        event.setResult(result);
        this.getServer().getScheduler().runTask(this, () -> event.getInventory().setRepairCost(0));
    }
}
