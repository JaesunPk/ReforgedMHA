package hadences.reforgedmha.GUI;

import hadences.reforgedmha.Arena;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static hadences.reforgedmha.Arena.ArenaList;

public class PlayMenu {

    private Inventory PlayMenu;

    public void createInventory(){
        PlayMenu = Bukkit.createInventory(null,54, ChatColor.BLACK + "[" + ChatColor.YELLOW + "ProjectMHA" + ChatColor.BLACK + "]" + ChatColor.GREEN + " PLAY");

        ItemStack item_close = new ItemStack(Material.BARRIER);
        ItemStack item_map = new ItemStack(Material.LIME_WOOL);

        List<String> lore = new ArrayList<>();

        //item_close data
        ItemMeta meta = item_close.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "[Close Menu]");
        lore.clear();
        lore.add(ChatColor.WHITE + "* " + ChatColor.RED + "Click to Exit!");
        meta.setLore(lore);
        item_close.setItemMeta(meta);
        PlayMenu.setItem(53,item_close);

        if(!ArenaList.isEmpty())
        for(int i = 0; i < getMaps().size(); i++){
            //item_map data
            meta = item_map.getItemMeta();
            meta.setDisplayName(ChatColor.WHITE + getMaps().get(i).getName());
            lore.clear();
            lore.add(ChatColor.WHITE + "* " + ChatColor.GREEN + "Click to Start!");
            meta.setLore(lore);
            item_map.setItemMeta(meta);
            PlayMenu.setItem(i,item_map);
        }


    }

    public static int getTotalMaps(){
        int totalmaps = ArenaList.size();
        if(ArenaList.isEmpty()){
            return 0;
        }

        return totalmaps;
    }

    public static ArrayList<Arena> getMaps(){
        ArrayList<Arena> arenas = new ArrayList<>();
        if(ArenaList.keySet().isEmpty())
            return null;
        for(String key : ArenaList.keySet()){
            arenas.add(ArenaList.get(key));
        }

        return arenas;

    }

    public Inventory getMenu(){
        return PlayMenu;
    }

}
