package hadences.reforgedmha.Quirk;

import hadences.reforgedmha.Quirk.Cooldown;
import hadences.reforgedmha.Quirk.Stamina;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.HashMap;

import static hadences.reforgedmha.PlayerManager.playerdata;
import static hadences.reforgedmha.Quirk.Cooldown.*;

public class QuirkManager implements Listener {
    @EventHandler
    public void useAbility(PlayerItemHeldEvent e){
        Player p = e.getPlayer();
        if(p.getInventory().getItem(e.getNewSlot()) == null) return;
        if(playerdata.get(p.getUniqueId()).getQuirk().getName().equalsIgnoreCase("Quirkless")) return;
        if(playerdata.get(p.getUniqueId()).isInGame() == false){
            p.sendMessage(ChatColor.GOLD + "[" +ChatColor.RED  + "!" + ChatColor.GOLD + "] " + ChatColor.WHITE + "You cannot use abilities!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
            e.setCancelled(true);
            return;
        }
        if(e.getNewSlot() == 0 || e.getNewSlot() == 1){
            if(e.getPlayer().getInventory().getItem(e.getNewSlot()).getType() != Material.LIME_DYE){
                e.setCancelled(true);
                return;
            }

            if(e.getNewSlot() == 0)
                CastAbility(p,cooldowns,e.getNewSlot());
            if(e.getNewSlot() == 1)
                CastAbility(p,cooldowns2,e.getNewSlot());

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void RightClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(playerdata.get(p.getUniqueId()).getQuirk().getName().equalsIgnoreCase("Quirkless")) return;
        if(e.getItem() == null) return;
        if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getItem().getItemMeta().getDisplayName().contains(ChatColor.GREEN + "[Ability 3]")) {
            if(playerdata.get(p.getUniqueId()).isInGame()) {
                CastAbility(p, cooldowns3,2);
            }else{
                p.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "!" + ChatColor.GOLD + "] " + ChatColor.WHITE + "You cannot use abilities!");
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
            }
        }

    }

    public void CastAbility(Player p, HashMap<String,Long> cd, int slot) {
        if (!Cooldown.checkCD(p, cd)) return;
        if (!Stamina.checkStamina(p, slot)) return;
        if (!playerdata.get(p.getUniqueId()).isAllowSkill()) return;
        if (useAbility(slot, p)) {
            cd.put(p.getName(), System.currentTimeMillis() + (getCooldown(p,slot) * 1000));
            playerdata.get(p.getUniqueId()).setStamina(playerdata.get(p.getUniqueId()).getStamina() - (int) getStamina(p,slot));
        }
    }

    public int getCooldown(Player p,int slot){
        if(slot == 0) return playerdata.get(p.getUniqueId()).getQuirk().getAbility1CD();
        else if(slot ==1) return playerdata.get(p.getUniqueId()).getQuirk().getAbility2CD();
        else return playerdata.get(p.getUniqueId()).getQuirk().getAbility3CD();
    }

    public double getStamina(Player p,int slot){
        if(slot == 0) return playerdata.get(p.getUniqueId()).getQuirk().getAbility1Stamina();
        else if(slot ==1) return playerdata.get(p.getUniqueId()).getQuirk().getAbility2Stamina();
        else return playerdata.get(p.getUniqueId()).getQuirk().getAbility3Stamina();
    }

    public boolean useAbility(int slot, Player p){
        if(slot == 0)
            return playerdata.get(p.getUniqueId()).getQuirk().getQuirkCastManager().CastAbility1(p);
        if(slot == 1)
            return playerdata.get(p.getUniqueId()).getQuirk().getQuirkCastManager().CastAbility2(p);
        if(slot == 2)
            return playerdata.get(p.getUniqueId()).getQuirk().getQuirkCastManager().CastAbility3(p);
        return false;

    }

}
