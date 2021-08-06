package hadences.reforgedmha.GUI;

import hadences.reforgedmha.Quirk.Quirk;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class GamemodeMenu {

    private Inventory gamemodeMenu;

    public void createInventory(){
        gamemodeMenu = Bukkit.createInventory(null,18, ChatColor.BLACK + "[" + ChatColor.YELLOW + "ProjectMHA" + ChatColor.BLACK + "]" + ChatColor.WHITE + " Quirk Selection");

        ItemStack item_close = new ItemStack(Material.BARRIER);

        ItemStack item_game = new ItemStack(Material.LIME_DYE);

        List<String> lore = new ArrayList<>();

        //item_close data
        ItemMeta meta = item_close.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "[Close Menu]");
        lore.clear();
        lore.add(ChatColor.WHITE + "* " + ChatColor.RED + "Click to Exit!");
        meta.setLore(lore);
        item_close.setItemMeta(meta);
        gamemodeMenu.setItem(17,item_close);

            //item_quirk data
            meta = item_game.getItemMeta();
            meta.setDisplayName(WHITE + "" + BOLD + "[" + GREEN + "" + BOLD + "Gamemode" + WHITE + "" + BOLD + "] " + ChatColor.RED + "Team" + ChatColor.GOLD + " vs " + ChatColor.BLUE + "Team");
            lore.clear();
            lore.add("Classic Team Deathmatch with one life");
            meta.setLore(lore);
            item_game.setItemMeta(meta);
            gamemodeMenu.setItem(0, item_game);


    }

    public Inventory getQuirkMenu() {
        return gamemodeMenu;
    }
}
