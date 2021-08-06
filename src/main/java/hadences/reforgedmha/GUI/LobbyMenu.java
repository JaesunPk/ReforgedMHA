package hadences.reforgedmha.GUI;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LobbyMenu {
    private Inventory lobbyMenu;
    public void createInventory() {
        lobbyMenu = Bukkit.createInventory(null, 18, ChatColor.BLACK + "[" + ChatColor.YELLOW + "ProjectMHA" + ChatColor.BLACK + "]" + ChatColor.WHITE + " Lobby Menu");

        ItemStack item_close = new ItemStack(Material.BARRIER);
        ItemStack item_joinredteam = new ItemStack(Material.RED_WOOL);
        ItemStack item_joinblueteam = new ItemStack(Material.BLUE_WOOL);
        ItemStack item_readyup = new ItemStack(Material.GRAY_DYE);
        ItemStack item_classselection = new ItemStack(Material.GOLD_BLOCK);
        ItemStack item_gamemode = new ItemStack(Material.REDSTONE_TORCH);

        List<String> lore = new ArrayList<>();

        //item_close data
        ItemMeta meta = item_close.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "[Close Menu]");
        lore.clear();
        lore.add(ChatColor.WHITE + "* " + ChatColor.RED + "Click to Exit!");
        meta.setLore(lore);
        item_close.setItemMeta(meta);
        lobbyMenu.setItem(17, item_close);

        //item_joinredteam
        meta = item_joinredteam.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "[Alpha]");
        lore.clear();
        lore.add(ChatColor.GOLD + "* " + ChatColor.RED + "Click to join Alpha!");
        meta.setLore(lore);
        item_joinredteam.setItemMeta(meta);
        lobbyMenu.setItem(12,item_joinredteam);

        //item_joinblueteam
        meta = item_joinblueteam.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "[Beta]");
        lore.clear();
        lore.add(ChatColor.GOLD + "* " + ChatColor.BLUE + "Click to join Beta!");
        meta.setLore(lore);
        item_joinblueteam.setItemMeta(meta);
        lobbyMenu.setItem(14,item_joinblueteam);

        //item_readyup
        meta = item_readyup.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "[READY UP]");
        lore.clear();
        lore.add(ChatColor.WHITE + "* " + ChatColor.GREEN + "Click to Ready up!");
        lore.add(ChatColor.RED + "WARNING " + ChatColor.WHITE + "Once you ready up you cannot open this menu again!");
        meta.setLore(lore);
        item_readyup.setItemMeta(meta);
        lobbyMenu.setItem(13,item_readyup);

        //item_classselection
        meta = item_classselection.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "[Quirk Selection]");
        lore.clear();
        lore.add(ChatColor.WHITE + "* " + ChatColor.GREEN + "Click to select a quirk!");
        meta.setLore(lore);
        item_classselection.setItemMeta(meta);
        lobbyMenu.setItem(4,item_classselection);

        //item_classselection
        meta = item_gamemode.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "[Gamemode Selection]");
        lore.clear();
        lore.add(ChatColor.WHITE + "* " + ChatColor.GREEN + "Click to select a gamemode!");
        meta.setLore(lore);
        item_gamemode.setItemMeta(meta);
        lobbyMenu.setItem(8, item_gamemode);


    }

    public Inventory getLobbyMenu() {
        return lobbyMenu;
    }
}
