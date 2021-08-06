package hadences.reforgedmha.GUI;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private Inventory Menu;

    public void createInventory(){
        Menu = Bukkit.createInventory(null,18, ChatColor.BLACK + "[" + ChatColor.YELLOW + "ProjectMHA" + ChatColor.BLACK + "]" + ChatColor.WHITE + " Menu");

        ItemStack item_close = new ItemStack(Material.BARRIER);
        ItemStack item_play = new ItemStack(Material.LIME_DYE);
        ItemStack item_class = new ItemStack(Material.GRAY_DYE);

        List<String> lore = new ArrayList<>();

        //item_close data
        ItemMeta meta = item_close.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "[Close Menu]");
        lore.clear();
        lore.add(ChatColor.WHITE + "* " + ChatColor.RED + "Click to Exit!");
        meta.setLore(lore);
        item_close.setItemMeta(meta);
        Menu.setItem(17,item_close);

        //item_play data
        meta = item_play.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "[Play]");
        lore.clear();
        lore.add(ChatColor.WHITE + "* " + ChatColor.GREEN + "Click to Open Play Menu");
        meta.setLore(lore);
        item_play.setItemMeta(meta);
        Menu.setItem(3,item_play);

        //item_class data
        meta = item_class.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "[Classes]");
        lore.clear();
        lore.add(ChatColor.WHITE + "* " + ChatColor.GREEN + "Click to View Class List");
        meta.setLore(lore);
        item_class.setItemMeta(meta);
        Menu.setItem(4,item_class);


    }

    public Inventory getMenu(){
        return Menu;
    }


}

