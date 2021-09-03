package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.Damage;
import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.RaycastUtils;
import hadences.reforgedmha.Utility.VectorUtils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static hadences.reforgedmha.PlayerManager.playerdata;
import static hadences.reforgedmha.Utility.RaycastUtils.cleanTargetList;

public class Blackwhip extends QuirkCastManager {
    ReforgedMHA plugin = ReforgedMHA.getPlugin(ReforgedMHA.class);
    Location loc;
    Location endpoint;

    public boolean CastAbility1(Player p){
        loc = p.getLocation();
        double hitbox = 0.5;
        endpoint = RaycastUtils.StartRaycast(p,20,hitbox,false);
        playSound(p);
        double length = RaycastUtils.calculateDistance(loc,endpoint);
        StartBlackwhip(p,length,true,playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg(),0,0,endpoint,hitbox);
        List<Entity> target = (List<Entity>) endpoint.getNearbyEntities(hitbox,hitbox,hitbox);
        target = cleanTargetList(p,target,false);
        for(Entity e : target){
            if(e.getUniqueId() != p.getUniqueId()){
                BlackwhipAbilityEntity(e,p);
                return true;
            }
        }        return true;
    }
    public boolean CastAbility2(Player p){
        loc = p.getLocation();
        playSound(p);
        double hitbox = 1;
        endpoint = RaycastUtils.StartRaycast(p,6,hitbox,false);
        double length = RaycastUtils.calculateDistance(loc,endpoint);
        StartBlackwhip(p,length,true,playerdata.get(p.getUniqueId()).getQuirk().getAbility2Dmg(),0,0,endpoint,hitbox);
        StartBlackwhip(p,length,true,playerdata.get(p.getUniqueId()).getQuirk().getAbility2Dmg(),15,0,endpoint,hitbox);
        StartBlackwhip(p,length,true,playerdata.get(p.getUniqueId()).getQuirk().getAbility2Dmg(),-15,0,endpoint,hitbox);
        return true;
    }
    public boolean CastAbility3(Player p){
        loc = p.getLocation();
        double hitbox = 0.2;
        endpoint = RaycastUtils.StartRaycast(p,15,hitbox,false);
        double length = RaycastUtils.calculateDistance(loc,endpoint);
        if(endpoint.getBlock().isSolid() && endpoint.getBlock().getType() != Material.BARRIER) {
            playSound(p);
            StartBlackwhip(p,length,true,playerdata.get(p.getUniqueId()).getQuirk().getAbility3Dmg(),0,0,endpoint,hitbox);
            BlackwhipAbilityBlock(endpoint, p);
        }else{
            p.sendMessage(ChatColor.RED+ "No Target!");
            return false;
        }
        return true;
    }

    public void StartBlackwhip(Player p, double length, boolean dealDmg, double hearts, float yaw, float pitch, Location endpoint, double hitbox){
        Vector pos = new Vector(0,0,0);
        Vector pos_left = new Vector(0,0,0);
        Vector pos_right = new Vector(0,0,0);
        loc = p.getEyeLocation();
        if(dealDmg) {
            Damage.damageList(p,(ArrayList<Entity>) endpoint.getNearbyEntities(hitbox,hitbox,hitbox),hearts);
        }
        double y = -0.35;
        for(double t = 0; t <= length; t += 0.1){
            if(t >= 0 && t < 2){
                pos = VectorUtils.rotateVector(new Vector(t,y,(0.25*t)-0.5),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(0.25*t)-0.7),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(0.25*t)-0.3),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t > 2 && t < 3.5){
                pos = VectorUtils.rotateVector(new Vector(t,y,(-.2*t)+0.4),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(-.2*t)+0.6),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(-.2*t)+0.2),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t > 3.5 && t < 6){
                pos = VectorUtils.rotateVector(new Vector(t,y,(0.2*t) -1),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(0.2*t) -1.2),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(0.2*t) -0.8),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t>6 && t < 9){
                pos = VectorUtils.rotateVector(new Vector(t,y,(-0.1*t) + 0.8),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(-0.1*t) + 1),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(-0.1*t) + 0.6),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t>9 && t < 14){
                pos = VectorUtils.rotateVector(new Vector(t,y,-0.1),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,-0.3),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,+0.1),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }
            //Black Particle
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 10, 0.05, 0.05, 0.05, 0,new Particle.DustOptions(Color.fromRGB(42, 42, 42), 0.5F));
            //Dark_Aqua Particle
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos_left), 10, 0.05, 0.05, 0.05, 0,new Particle.DustOptions(Color.fromRGB(2, 101, 87 ), 0.5F));
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos_right), 10, 0.05, 0.05, 0.05, 0,new Particle.DustOptions(Color.fromRGB(2, 101, 87 ), 0.5F));
        }
    }
    public void BlackwhipAbilityEntity(Entity e, Player p){
        new BukkitRunnable(){
            Location entityLoc = e.getLocation();
            Location playerLoc = p.getLocation();
            Vector v;
            int time = 0;
            @Override
            public void run() {
                if(playerLoc.distance(entityLoc) < 7 || time > 80){
                    this.cancel();
                }
                entityLoc = e.getLocation();
                playerLoc = p.getLocation();
                v = playerLoc.toVector().subtract(entityLoc.toVector());
                e.setVelocity(v.normalize().multiply(1.5));
                time++;
            }
        }.runTaskTimer(plugin,0,0);
    }
    public void BlackwhipAbilityBlock(Location block, Player p){
        new BukkitRunnable(){
            Location playerLoc = p.getLocation();
            Vector v;
            int time = 0;
            @Override
            public void run() {
                if(block.distance(playerLoc) < 7 || time > 80){
                    this.cancel();
                }
                playerLoc = p.getLocation();
                v = block.toVector().subtract(playerLoc.toVector());
                p.setVelocity(v.normalize().multiply(1.5));
                time++;
            }
        }.runTaskTimer(plugin,0,0);
    }
    public void playSound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PHANTOM_BITE, 2.0f,2.0f);
    }
}
