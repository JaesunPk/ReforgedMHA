package hadences.reforgedmha;

import com.destroystokyo.paper.event.player.PlayerStartSpectatingEntityEvent;
import hadences.reforgedmha.GUI.Events.GUIEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import static hadences.reforgedmha.Quirk.Cooldown.*;
import static hadences.reforgedmha.PlayerManager.playerdata;
import static hadences.reforgedmha.Quirk.Quirk.quirklist;

public class MainEvent implements Listener {
    ReforgedMHA plugin = ReforgedMHA.getPlugin(ReforgedMHA.class);

    //When Player Joins
    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();

        p.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
        p.setGameMode(GameMode.ADVENTURE);

        //Give MenuItem
        p.getInventory().clear();
        p.getInventory().setItem(8,getMenuItem());
        if(playerdata.containsKey(p.getUniqueId())){
            playerdata.remove(p.getUniqueId());
        }
        if(CheckPlayerInConfig(p)){
            if(!playerdata.containsKey(p.getUniqueId())) {
                playerdata.put(p.getUniqueId(), new PlayerManager(p, plugin.getConfig().get("Users." + p.getUniqueId() + ".RANK").toString(), Integer.parseInt(plugin.getConfig().get("Users." + p.getUniqueId() + ".CREDIT").toString()), Integer.parseInt(plugin.getConfig().get("Users." + p.getUniqueId() + ".WINS").toString())));
                init(p);
            }
        }else{
            AddPlayerToConfig(p);
            playerdata.put(p.getUniqueId(),new PlayerManager(p,"DEFAULT",0,0));
            init(p);

        }
            //p.sendMessage(quirklist.size()+ "");
            //p.sendMessage(playerdata.get(p.getUniqueId()).getQuirk().getName());
        if(!playerdata.get(p.getUniqueId()).isInGame())
            plugin.board.updatePlayerDataBoard(plugin.board.getBoard());

        p.setDisplayName( ChatColor.LIGHT_PURPLE + "" + playerdata.get(p.getUniqueId()).getWins() + " " + ChatColor.GOLD + "✪ " + ChatColor.WHITE + p.getName());

    }

    public void init(Player p){
        cooldowns.put(p.getName(), System.currentTimeMillis() + (1* 1000));
        cooldowns2.put(p.getName(), System.currentTimeMillis() + (1 * 1000));
        cooldowns3.put(p.getName(), System.currentTimeMillis() + (1 * 1000));
    }


    @EventHandler
    public void DeathEvent(PlayerDeathEvent e){
        Player p = e.getEntity();

        if(playerdata.get(p.getUniqueId()).isInGame() == true && playerdata.get(p.getUniqueId()).isAlive()){
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER,2,2);
            Bukkit.broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " is out!");
            e.setCancelled(true);
            p.setGameMode(GameMode.SPECTATOR);
            p.sendTitle(ChatColor.RED + "YOU DIED!",ChatColor.GOLD + "[-----]");
            playerdata.get(p.getUniqueId()).setAlive(false);
            playerdata.get(p.getUniqueId()).setAllowSkill(false);

        }

    }

    @EventHandler
    public void PlayerInventoryInteract(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(playerdata.get(p.getUniqueId()).isDevMode() == false){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerDropEvent(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        if(playerdata.get(p.getUniqueId()).isDevMode() == false){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void InteractClickMenu(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_AIR ||e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK ||e.getAction() == Action.LEFT_CLICK_AIR  ){

            try {
                if (e.getItem().getItemMeta().getDisplayName().equals(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.GOLD + "" + ChatColor.BOLD + "MENU" + ChatColor.BLACK + "" + ChatColor.BOLD + "]")) {
                    if (playerdata.get(p.getUniqueId()).isInGame() == true && playerdata.get(p.getUniqueId()).isInLobby() && playerdata.get(p.getUniqueId()).isReady() == false) {
                        p.openInventory(plugin.lobbyMenu.getLobbyMenu());
                        return;
                    } else if (playerdata.get(p.getUniqueId()).isInGame() == true && playerdata.get(p.getUniqueId()).isInLobby() == false) {
                        //Delete it during actal vv
                        //p.openInventory(plugin.lobbyMenu.getLobbyMenu());

                        return;
                    }

                    p.openInventory(plugin.menu.getMenu());

                }

            }catch(Exception exception){

            }


        }
    }

    @EventHandler
    public void spectaterMode(PlayerStartSpectatingEntityEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void preventSpectaterTP(PlayerTeleportEvent e){
        if(e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE)
            e.setCancelled(true);
    }

    @EventHandler
    public void takeDmg(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof IronGolem){
            if(e.getEntity().hasMetadata("defendBlue") && e.getDamager().hasMetadata("Beta")){
                e.setCancelled(true);
            }else if(e.getEntity().hasMetadata("defendRed") && e.getDamager().hasMetadata("Alpha")){
                e.setCancelled(true);
            }

        }


        if(!(e.getEntity() instanceof Player))
            return;
        Player p = (Player) e.getEntity();
        if(!playerdata.get(p.getUniqueId()).isInGame()){e.setCancelled(true);}
        if(playerdata.get(p.getUniqueId()).isInLobby()){e.setCancelled(true);}


        if(e.getDamager() instanceof Firework){
            Firework fw = (Firework) e.getDamager();
            if (fw.hasMetadata(playerdata.get(p.getUniqueId()).getTeam())) {
                e.setCancelled(true);
            }




        }

    }

    @EventHandler
    public void fallDmg(EntityDamageEvent e){
        if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
            if(!playerdata.get(e.getEntity().getUniqueId()).isFallDamage()){
                e.setCancelled(true);
            }
            if(e.getDamage() > 12){
                ((LivingEntity) e.getEntity()).damage(10);
            }
            e.setCancelled(true);
        }

    }


    public boolean CheckPlayerInConfig(Player p){
        for (String key : plugin.getConfig().getConfigurationSection("Users").getKeys(false)) {
            if (key.equals(p.getUniqueId().toString())) {
                return true;
            }
        }

        return false;
    }

    public void AddPlayerToConfig(Player p){
        plugin.getConfig().set("Users." + p.getUniqueId() + ".RANK", "DEFAULT");
        plugin.getConfig().set("Users." + p.getUniqueId() + ".WINS", "0");
        plugin.getConfig().set("Users." + p.getUniqueId() + ".CREDIT", "0");
    }

    public static ItemStack getMenuItem(){
        //Menu Item
        ItemStack menu_item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = menu_item.getItemMeta();

        meta.setDisplayName(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.GOLD + "" + ChatColor.BOLD + "MENU" + ChatColor.BLACK + "" + ChatColor.BOLD + "]");
        meta.addEnchant(Enchantment.DURABILITY,1,false);
        menu_item.setItemMeta(meta);

        return menu_item;
    }

    @EventHandler
    public void MoveEvent(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(p.getLocation().getBlock().getType() == Material.BARRIER || p.getEyeLocation().getBlock().getType() == Material.BARRIER){
            p.setVelocity(p.getLocation().getDirection().multiply(-2));}

        if(e.hasExplicitlyChangedBlock() || e.hasChangedPosition()) {
            if(playerdata.get(e.getPlayer().getUniqueId()).isRestrictMovement())
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockStateChange(BlockFadeEvent e){
        e.setCancelled(true);
    }


}

