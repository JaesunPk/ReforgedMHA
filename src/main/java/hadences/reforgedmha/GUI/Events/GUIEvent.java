package hadences.reforgedmha.GUI.Events;

import hadences.reforgedmha.GUI.PlayMenu;
import hadences.reforgedmha.GameManager;
import hadences.reforgedmha.Quirk.Quirk;
import hadences.reforgedmha.ReforgedMHA;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static hadences.reforgedmha.PlayerManager.playerdata;
import static hadences.reforgedmha.Quirk.Quirk.quirklist;
import static org.bukkit.ChatColor.*;

public class GUIEvent implements Listener {
    public static GameManager console;
    ReforgedMHA plugin = ReforgedMHA.getPlugin(ReforgedMHA.class);
    public static HashMap<String, Listener> QuirkEvents = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent e) {
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getItemMeta() == null) return;
            if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if(e.getInventory().equals(plugin.playMenu.getMenu())) {
                //Play Menu{
                if (e.getSlot() != 53) {
                    if (checkPlayersInGame()) {
                        p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] " + ChatColor.RED + "Game is in Progress!");
                        p.closeInventory();
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 1.0F);
                        return;
                    }
                    //Button_Play
                    String MapID = PlayMenu.getMaps().get(e.getSlot()).getName();
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                    //p.sendMessage("Map Joined : " + MapID);
                    p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] " + ChatColor.WHITE + "Game Created for Map : " + ChatColor.GREEN + MapID);
                    createGame(MapID);
                    p.closeInventory();
                } else if (e.getSlot() == 53) {
                    //Button_Close
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                    p.closeInventory();
                }
                //}
            }

        if(e.getInventory().equals(plugin.gamemodeMenu.getQuirkMenu())) {
            //gamemode Menu{
            if (e.getSlot() == 0) {
                //Button TeamvTeam
                sendTitleToAll(ChatColor.RED + "Team" + ChatColor.AQUA + " vs " + ChatColor.BLUE + "Team");
                console.setGamemode("TvT");
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                p.getWorld().playSound(console.getArena().getLobbySpawn(),Sound.ENTITY_ENDER_DRAGON_GROWL,2,1);
                p.closeInventory();
            }
            if (e.getSlot() == 1) {
                //Button TeamvTeam
                sendTitleToAll(ChatColor.AQUA + "Infiltrate" + ChatColor.DARK_AQUA + " -/- " + ChatColor.AQUA + "Defend");
                console.setGamemode("ID");
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                p.getWorld().playSound(console.getArena().getLobbySpawn(),Sound.ENTITY_ENDER_DRAGON_GROWL,2,1);
                p.closeInventory();
            }
            if (e.getSlot() == 2) {
                //Button FFA
                sendTitleToAll(ChatColor.LIGHT_PURPLE + "Free For All");
                console.setGamemode("FFA");
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                p.getWorld().playSound(console.getArena().getLobbySpawn(),Sound.ENTITY_ENDER_DRAGON_GROWL,2,1);
                p.closeInventory();
            }
            //}
        }

            if(e.getInventory().equals(plugin.lobbyMenu.getLobbyMenu())) {
                //Lobby Menu
                if(e.getSlot() == 12){
                    //item_joinredteam
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                    joinTeam("Alpha",p);
                    p.sendMessage(ChatColor.WHITE + "You have joined Team " + ChatColor.RED + "Alpha" );
                    p.sendMessage(ChatColor.WHITE + "Your Quirk has been set to " + ChatColor.GREEN + "Quirkless");
                    playerdata.get(p.getUniqueId()).setQuirk(Quirk.getQuirk("Quirkless"));
                    p.closeInventory();
                }else if(e.getSlot() == 8){
                    //gamemode selection
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                    p.closeInventory();
                    p.openInventory(plugin.gamemodeMenu.getQuirkMenu());
                }else if(e.getSlot() == 14){
                    //item_joinblueteam
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                    joinTeam("Beta",p);
                    p.sendMessage(ChatColor.WHITE + "You have joined Team " + ChatColor.BLUE + "Beta" );
                    p.sendMessage(ChatColor.WHITE + "Your Quirk has been set to " + ChatColor.GREEN + "Quirkless");
                    playerdata.get(p.getUniqueId()).setQuirk(Quirk.getQuirk("Quirkless"));
                    p.closeInventory();
                }else if(e.getSlot() == 13){
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);

                    if(console.getGamemode().equalsIgnoreCase("None")){
                       p.sendMessage("Gamemaster must choose a gamemode before players can lock in!");
                       p.closeInventory();
                       return;
                    }
                    //ready up
                    if(playerdata.get(p.getUniqueId()).getTeam().equals("NONE")){
                        p.sendMessage(ChatColor.RED + "You must choose a team before pressing ready!");
                        p.closeInventory();
                        return;
                    }else if(playerdata.get(p.getUniqueId()).getQuirk().getName().equalsIgnoreCase("Quirkless")){
                        p.sendMessage(ChatColor.RED + "You must choose a quirk before pressing ready!");
                        p.closeInventory();
                        return;
                    }
                    playerdata.get(p.getUniqueId()).setReady(true);
                    p.sendMessage(ChatColor.GREEN + "You Ready up!" );

                    Bukkit.broadcastMessage(ChatColor.GREEN +p.getName() + " is ready!");

                    p.closeInventory();
                }else if(e.getSlot() == 4){
                    //class selection
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                    p.closeInventory();
                    p.openInventory(plugin.quirkMenu.getQuirkMenu());
                }else if(e.getSlot() == 17){
                    //Button_Close
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                    p.closeInventory();
                }
            }

            if(e.getInventory().equals(plugin.menu.getMenu())) {
                //Menu
                if(e.getSlot() == 3){
                    //Button_Play
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                    p.closeInventory();
                    p.openInventory(plugin.playMenu.getMenu());
                }else if(e.getSlot() == 4){
                    //Button_Class
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                    p.closeInventory();
                }else if(e.getSlot() == 17){
                    //Button_Close
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                    p.closeInventory();
                }
            }

            if(e.getInventory().equals(plugin.quirkMenu.getQuirkMenu())){
                p.setWalkSpeed(0.2f);
                p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 0.5F, 1.2F);
                String quirk = e.getCurrentItem().getItemMeta().getLocalizedName();
                if(e.getCurrentItem().getItemMeta().getLocalizedName().contains("random")){
                    randomQuirk(p);
                    return;
                }

                if(getQuirk(quirk)){
                    if(!isQuirkFree(p,quirk)){
                        sendTitle_QuirkTaken(p);
                        return;
                    }
                    //p.sendMessage(Quirk.getQuirk(quirk).getName());
                    playerdata.get(p.getUniqueId()).setQuirk(Quirk.getQuirk(quirk));
                    p.sendTitle(ChatColor.WHITE + "[" + playerdata.get(p.getUniqueId()).getQuirk().getDisplayName() + ChatColor.WHITE + "]",ChatColor.GOLD + "Selected Quirk");
                    p.closeInventory();
                }
                if(e.getCurrentItem().getItemMeta().getLocalizedName().contains("close")){
                    p.getInventory().setItem(7,createClassInfo(p));
                    p.closeInventory();
                }
                if(!e.getCurrentItem().getItemMeta().getLocalizedName().contains("close")){
                    p.getInventory().setItem(7,createClassInfo(p));
                    p.closeInventory();
                }
            }

    }

    public void randomQuirk(Player p){
        Quirk q = quirklist.get((int) (Math.random()*quirklist.size()+1));
        if(getQuirk(q.getName())){
            if(!isQuirkFree(p,q.getName())){
                randomQuirk(p);
                return;
            }
            playerdata.get(p.getUniqueId()).setQuirk(Quirk.getQuirk(q.getName()));
            p.sendTitle(ChatColor.WHITE + "[" + playerdata.get(p.getUniqueId()).getQuirk().getDisplayName() + ChatColor.WHITE + "]",ChatColor.GOLD + "Selected Quirk");
            p.getInventory().setItem(7,createClassInfo(p));
            p.closeInventory();
        }
    }

    public boolean getQuirk(String quirk){
        for(Quirk q : quirklist){
            if(q.getName().equalsIgnoreCase(quirk));
            return true;
        }
        return false;
    }

    public void createGame(String Arena){
        console = new GameManager(Arena, (Integer) plugin.getConfig().get("Game.Settings.Minutes"),(Integer) plugin.getConfig().get("Game.Settings.Seconds"), (Integer) plugin.getConfig().get("Game.Settings.StartingHealth"), (Integer) plugin.getConfig().get("Game.Settings.StartingStamina"));
        console.addPlayersToGameList(Bukkit.getOnlinePlayers());
        console.LobbyInitialize(Arena);
        console.initiate();
    }

    public boolean checkPlayersInGame(){
        for(Player p : Bukkit.getOnlinePlayers()){
            if(playerdata.get(p.getUniqueId()).isInGame() || playerdata.get(p.getUniqueId()).isInLobby()){
                return true;
            }
        }
        return false;
    }

    public void joinTeam(String team, Player p){
        plugin.board.getBoard().getTeam(team).addPlayer(p);
        playerdata.get(p.getUniqueId()).setTeam(team);
    }

    public void sendTitle_QuirkTaken(Player p){ p.sendTitle(ChatColor.WHITE + "[" + RED + "Quirk Taken" + ChatColor.WHITE + "]",ChatColor.GOLD + "Someone chose this quirk!"); }

    public boolean isQuirkFree(Player p, String Quirk){
        String Team = playerdata.get(p.getUniqueId()).getTeam();

        for(Player player : console.getPlayerList()){
            if(player != p){
                if(playerdata.get(player.getUniqueId()).getTeam().equalsIgnoreCase(Team))
                    if(playerdata.get(player.getUniqueId()).getQuirk().getName().equalsIgnoreCase(Quirk)){
                        return false;
                    }
            }
        }

        return true;
    }

    public static ItemStack createClassInfo(Player p){
        ItemStack item_info = new ItemStack(Material.GLOBE_BANNER_PATTERN);
        ItemMeta meta = item_info.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.setDisplayName(org.bukkit.ChatColor.WHITE + "" + org.bukkit.ChatColor.BOLD + "[" + org.bukkit.ChatColor.GOLD + "" + org.bukkit.ChatColor.BOLD + "QUIRK INFO" + org.bukkit.ChatColor.WHITE + "" + org.bukkit.ChatColor.BOLD + "]");
        lore.add(org.bukkit.ChatColor.WHITE + "" + org.bukkit.ChatColor.BOLD + "[" + org.bukkit.ChatColor.GOLD + "" + org.bukkit.ChatColor.BOLD + "Ability 1" + org.bukkit.ChatColor.WHITE + "" + org.bukkit.ChatColor.BOLD + "] "  + hearts(playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg()/2) + " / " + stamina(playerdata.get(p.getUniqueId()).getQuirk().getAbility1Stamina()) + " / " +  cooldown(playerdata.get(p.getUniqueId()).getQuirk().getAbility1CD())+ " / " + comment(p,1));
        lore.add(org.bukkit.ChatColor.GREEN + playerdata.get(p.getUniqueId()).getQuirk().getAbility1Info() + " ");
        lore.add("");
        lore.add(org.bukkit.ChatColor.WHITE + "" + org.bukkit.ChatColor.BOLD + "[" + org.bukkit.ChatColor.GOLD + "" + org.bukkit.ChatColor.BOLD + "Ability 2" + org.bukkit.ChatColor.WHITE + "" + org.bukkit.ChatColor.BOLD + "] "  + hearts(playerdata.get(p.getUniqueId()).getQuirk().getAbility2Dmg()/2) + " / " + stamina(playerdata.get(p.getUniqueId()).getQuirk().getAbility2Stamina()) + " / " +  cooldown(playerdata.get(p.getUniqueId()).getQuirk().getAbility2CD())+ " / " + comment(p,2));
        lore.add(org.bukkit.ChatColor.GREEN + playerdata.get(p.getUniqueId()).getQuirk().getAbility2Info() + " ");
        lore.add("");
        lore.add(org.bukkit.ChatColor.WHITE + "" + org.bukkit.ChatColor.BOLD + "[" + org.bukkit.ChatColor.GOLD + "" + org.bukkit.ChatColor.BOLD + "Ability 3" + org.bukkit.ChatColor.WHITE + "" + org.bukkit.ChatColor.BOLD + "] "  + hearts(playerdata.get(p.getUniqueId()).getQuirk().getAbility3Dmg()/2) + " / " + stamina(playerdata.get(p.getUniqueId()).getQuirk().getAbility3Stamina()) + " / " +  cooldown(playerdata.get(p.getUniqueId()).getQuirk().getAbility3CD())+ " / " + comment(p,3));
        lore.add(org.bukkit.ChatColor.GREEN + playerdata.get(p.getUniqueId()).getQuirk().getAbility3Info() + " ");
        lore.add("");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item_info.setItemMeta(meta);
        return item_info;
    }

    public static String hearts(double hearts){
        return ChatColor.RED + "❤ " + (double) hearts;
    }

    public static String stamina(double stamina){
        return ChatColor.GOLD + "♦ " + (int) stamina;
    }

    public static String cooldown(int time){
        return ChatColor.BLUE + "CD ♠ " + time;
    }

    public static String comment(Player p,int ability){
        if(ability == 1){
            return ChatColor.GREEN + "+ " + playerdata.get(p.getUniqueId()).getQuirk().getAbility1Comment();
        }else if(ability == 2){
            return ChatColor.GREEN + "+ " + playerdata.get(p.getUniqueId()).getQuirk().getAbility2Comment();
        }else if(ability ==3){
            return ChatColor.GREEN + "+ " + playerdata.get(p.getUniqueId()).getQuirk().getAbility3Comment();

        }
        return "";
    }

    public void sendTitleToAll(String string){
        for(Player p : console.getPlayerList()){
            p.sendTitle(ChatColor.WHITE + "[" + ChatColor.GREEN + string + ChatColor.WHITE +"]",ChatColor.GOLD + "Gamemode Selected");
        }
    }

    @EventHandler
    public void PlayerLeaveEvent(PlayerQuitEvent e){
        Player p = e.getPlayer();
        ReforgedMHA.getPlugin(ReforgedMHA.class).updateConfig();
        if(!playerdata.get(p.getUniqueId()).isInGame()) {
            plugin.board.updatePlayerDataBoard(plugin.board.getBoard()); }
        else{
            if(plugin.board.getBoard().getTeam("Alpha").hasPlayer(p))
                plugin.board.getBoard().getTeam("Alpha").removePlayer(p);
            else plugin.board.getBoard().getTeam("Beta").removePlayer(p);
            console.getPlayerList().remove(p); }
        playerdata.get(p.getUniqueId()).setInGame(false);
        playerdata.get(p.getUniqueId()).setInLobby(false);
        playerdata.get(p.getUniqueId()).setReady(false);
    }

}
