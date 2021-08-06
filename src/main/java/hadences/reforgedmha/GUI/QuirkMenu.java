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

import static hadences.reforgedmha.Quirk.Quirk.quirklist;
import static org.bukkit.ChatColor.*;

public class QuirkMenu {

    private Inventory quirkMenu;

    public void createInventory(){
        quirkMenu = Bukkit.createInventory(null,54, ChatColor.BLACK + "[" + ChatColor.YELLOW + "ProjectMHA" + ChatColor.BLACK + "]" + ChatColor.WHITE + " Quirk Selection");

        ItemStack item_close = new ItemStack(Material.BARRIER);

        ItemStack item_quirk = new ItemStack(Material.LIME_DYE);

        ItemStack randomizer = new ItemStack(Material.BEACON);


        List<String> lore = new ArrayList<>();

        //item_close data
        ItemMeta meta = item_close.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "[Close Menu]");
        lore.clear();
        lore.add(ChatColor.WHITE + "* " + ChatColor.RED + "Click to Exit!");
        meta.setLocalizedName("close");
        meta.setLore(lore);
        item_close.setItemMeta(meta);
        quirkMenu.setItem(53,item_close);

        meta = randomizer.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "[Random Quirk]");
        lore.clear();
        lore.add(ChatColor.WHITE + "* " + ChatColor.RED + "Click to get a random quirk!");
        meta.setLocalizedName("random");
        meta.setLore(lore);
        randomizer.setItemMeta(meta);
        quirkMenu.setItem(52,randomizer);

        for(int i = 1; i < quirklist.size() && i < 53; i++) {
            //item_quirk data
            meta = item_quirk.getItemMeta();
            meta.setDisplayName(WHITE + "" + BOLD + "[" + GREEN + "" + BOLD + "Quirk" + WHITE + "" + BOLD + "] " + ChatColor.WHITE + quirklist.get(i).getDisplayName());
            lore.clear();
            lore.add(ChatColor.WHITE + "Type : " + ChatColor.GRAY + quirklist.get(i).getType());
            lore.add(ChatColor.WHITE + "Role : " + quirklist.get(i).getRole());
            meta.setLore(lore);
            meta.setLocalizedName(quirklist.get(i).getName());
            item_quirk.setItemMeta(meta);
            quirkMenu.setItem(i-1, item_quirk);

        }

    }

    public Inventory getQuirkMenu() {
        return quirkMenu;
    }
}
