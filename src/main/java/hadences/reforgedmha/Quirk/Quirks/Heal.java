package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.Utility.RaycastUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static hadences.reforgedmha.PlayerManager.playerdata;
import static hadences.reforgedmha.Quirk.Cooldown.*;
import static hadences.reforgedmha.Quirk.Cooldown.cooldowns3;

public class Heal extends QuirkCastManager {

    public boolean CastAbility1(Player p) {
        Location loc = p.getEyeLocation();
        for(int i = 0; i < 5; i++){
            loc.add(loc.getDirection());
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(245, 238, 78), 1.2f));

        }

        ArrayList<Entity> target = (ArrayList<Entity>) RaycastUtils.StartRaycast(p,5,1,true).getNearbyEntities(1,1,1);
        target = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p,target,false);
        if(!target.isEmpty())
            if(target.get(0) instanceof Player){
                Player tar = (Player) target.get(0);
                if(playerdata.get(tar.getUniqueId()).getTeam() != playerdata.get(p.getUniqueId()).getTeam()) {
                    tar.damage(playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg());
                    if(p.getHealth()+playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg() < p.getMaxHealth()){
                        p.setHealth(p.getHealth() + playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg());
                    }else{
                        p.setHealth(p.getMaxHealth());
                    }

                }
            }
        return true;

    }

    public boolean CastAbility2(Player p) {
        Location loc = p.getEyeLocation();
        for(int i = 0; i < 5; i++){
            loc.add(loc.getDirection());
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(245, 238, 78), 1.2f));

        }

        ArrayList<Entity> target = (ArrayList<Entity>) RaycastUtils.StartRaycast(p,5,1,true).getNearbyEntities(1,1,1);
        target = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p,target,true);
        if(!target.isEmpty())
            if(target.get(0) instanceof Player){
                Player tar = (Player) target.get(0);
                if(playerdata.get(tar.getUniqueId()).getTeam() == playerdata.get(p.getUniqueId()).getTeam()) {
                    playerdata.get(tar.getUniqueId()).setStamina(playerdata.get(tar.getUniqueId()).getStamina()+playerdata.get(p.getUniqueId()).getQuirk().getAbility2Effect()/20);
                    p.getWorld().playSound(p.getLocation(), Sound.ITEM_BONE_MEAL_USE,2,2);
                    return true;
                }
            }
        return true;

    }

    public boolean CastAbility3(Player p) {
        Location loc = p.getEyeLocation();
        for(int i = 0; i < 5; i++){
            loc.add(loc.getDirection());
            loc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,loc,15,0.1,0.1,0.1,0.05);
        }

        ArrayList<Entity> target = (ArrayList<Entity>) RaycastUtils.StartRaycast(p,5,1,true).getNearbyEntities(1,1,1);
        target = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p,target,true);
        if(!target.isEmpty())
            if(target.get(0) instanceof Player){
                Player tar = (Player) target.get(0);
                if(playerdata.get(tar.getUniqueId()).getTeam() == playerdata.get(p.getUniqueId()).getTeam()) {
                    if(p.getHealth() > 0 ){
                        if(tar.getHealth()+playerdata.get(p.getUniqueId()).getQuirk().getAbility3Dmg() < tar.getMaxHealth()) {
                            tar.setHealth(tar.getHealth() + playerdata.get(p.getUniqueId()).getQuirk().getAbility3Dmg());
                        }else{
                            tar.setHealth(tar.getMaxHealth());

                        }
                        p.setHealth(p.getHealth()-playerdata.get(p.getUniqueId()).getQuirk().getAbility3Dmg());
                    }
                    p.getWorld().playSound(p.getLocation(), Sound.ITEM_BONE_MEAL_USE,2,2);
                    return true;
                }
            }
        return true;
    }


}
