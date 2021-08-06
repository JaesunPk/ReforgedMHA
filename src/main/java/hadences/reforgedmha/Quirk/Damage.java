package hadences.reforgedmha.Quirk;

import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.RaycastUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

import static hadences.reforgedmha.PlayerManager.playerdata;

public class Damage {
    public static void damageList(Player p, ArrayList<Entity> entities, double hearts){
        entities = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p,entities,false);
        for(Entity e : entities){
        if(e instanceof Player){
            if(!playerdata.get(p.getUniqueId()).getTeam().equalsIgnoreCase(playerdata.get(e.getUniqueId()).getTeam())){
                ((LivingEntity) e).damage(hearts);
            }
        }else{
            try{
                ((LivingEntity) e).damage(hearts);
            }catch (Exception exception){
            }
        }
        }
    }

    public static void immobilizeList(Player p, ArrayList<Entity> entities, int seconds){
        entities = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p,entities,false);
        for(Entity e : entities){
                e.getWorld().playSound(e.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE,5.0f,2.0f);
            if(e instanceof Player) {
                if(!playerdata.get(p.getUniqueId()).getTeam().equalsIgnoreCase(playerdata.get(e.getUniqueId()).getTeam())){
                    ((Player) e).sendTitle(ChatColor.GOLD + "[" + ChatColor.RED + "!" + ChatColor.GOLD + "] " + ChatColor.YELLOW + "IMMOBILIZED", "");
                playerdata.get(e.getUniqueId()).setRestrictMovement(true);
                playerdata.get(e.getUniqueId()).setAllowSkill(false);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (e instanceof Player) {
                            playerdata.get(e.getUniqueId()).setAllowSkill(true);
                            playerdata.get(e.getUniqueId()).setRestrictMovement(false);
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.JUMP);
                        }
                        this.cancel();
                    }
                }.runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class), seconds);
            }
            }else{
                ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,seconds,100,true,false));
                ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.JUMP,seconds,150,true,false));
            }
        }
    }

    public static void addPotionEffect(Player p, ArrayList<Entity> entities,PotionEffect potionEffect){
        entities = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p,entities,false);
        for(Entity e : entities){
            if(e instanceof Player){
                if(!playerdata.get(p.getUniqueId()).getTeam().equalsIgnoreCase(playerdata.get(e.getUniqueId()).getTeam())){
                    ((LivingEntity) e).addPotionEffect(potionEffect);
                }
            }else{
                try{
                    ((LivingEntity) e).addPotionEffect(potionEffect);
                }catch (Exception exception){
                }
            }
        }
    }

    public static void Burn(Player p, ArrayList<Entity> entities, int seconds){
        entities = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p,entities,false);
        for(Entity e : entities){
            if(e instanceof Player) {
                if(!playerdata.get(p.getUniqueId()).getTeam().equalsIgnoreCase(playerdata.get(e.getUniqueId()).getTeam())){
                    e.setFireTicks(seconds);
                }
            }else{
                e.setFireTicks(seconds);

            }
        }
    }

}
