package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.Damage;
import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.RayTrace;
import hadences.reforgedmha.Utility.VectorUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static hadences.reforgedmha.PlayerManager.playerdata;

public class HalfColdHalfHot extends QuirkCastManager {

    Location loc;
    public boolean CastAbility1(Player p){
        loc = p.getLocation();
        float yaw = loc.getYaw();
        float pitch = loc.getPitch();
        ArrayList<Block> ice = new ArrayList<>();
        if(!p.isOnGround()){p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 2.0f,2.0f);
            p.sendMessage(ChatColor.RED + "Must be on the Ground!"); return false;}

        new BukkitRunnable(){
            int i = 0;
            double height = 0.2;

            @Override
            public void run() {
                if(i >= 20)
                    this.cancel();
                if (i >= 14) {
                    height += 0.5;
                }
                if(i%2 == 1) {
                    height += 0.35;
                    loc.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK, 2, 2);
                    loc.add(new Vector(loc.getDirection().normalize().getX(), 0, loc.getDirection().normalize().getZ()));
                    spawnVerticalIce(loc, height, 90, 0, yaw, pitch, ice);
                    spawnVerticalIce(loc, height - 0.4, 110, 0, yaw, pitch, ice);
                    spawnVerticalIce(loc, height - 0.4, 70, 0, yaw, pitch, ice);
                    //spawnVerticalIce(loc, height - 0.4, 160, 0, yaw, pitch, ice);
                    //spawnVerticalIce(loc, height - 0.4, 50, 0, yaw, pitch, ice);
                    loc.getWorld().spawnParticle(Particle.SNOWFLAKE, loc, 10, 1, height, 1, 0.3);
                    Damage.immobilizeList(p,(ArrayList<Entity>) loc.getNearbyEntities(1.2,height,1.2),playerdata.get(p.getUniqueId()).getQuirk().getAbility1Effect());
                }

                i++;
            }
        }.runTaskTimer(ReforgedMHA.getPlugin(ReforgedMHA.class),0,0);


       /* for(int i = 0; i <= 25; i++){
            if (i == 14) {
                height += 0.5;
            }
            if(i%2 == 1) {
                height += 0.35;
                loc.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK, 2, 2);
                loc.add(new Vector(loc.getDirection().normalize().getX(), 0, loc.getDirection().normalize().getZ()));
                spawnVerticalIce(loc, height, 90, 0, yaw, pitch, ice);
                spawnVerticalIce(loc, height - 0.4, 110, 0, yaw, pitch, ice);
                spawnVerticalIce(loc, height - 0.4, 70, 0, yaw, pitch, ice);
                spawnVerticalIce(loc, height - 0.4, 160, 0, yaw, pitch, ice);
                spawnVerticalIce(loc, height - 0.4, 50, 0, yaw, pitch, ice);
                loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 10, 1, height, 1, 0.3);
                Damage.immobilizeList(p,(ArrayList<Entity>) loc.getNearbyEntities(1.2,height,1.2),playerdata.get(p.getUniqueId()).getQuirk().getAbility1Effect());
            }
        }*/


        new BukkitRunnable() {
            @Override
            public void run() {
                for (Block b : ice) {
                    if (b.isSolid()) {
                        b.setType(Material.AIR);
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_GLASS_BREAK, 0.1f, 1);
                    }
                }
                ice.clear();
            }
        }.runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class), 160);
        return true;
    }
    public boolean CastAbility2(Player p){
        new BukkitRunnable(){
            int times = 0;
            @Override
            public void run() {
                if(times >= 4)
                    this.cancel();

                fireBlast(p);

                times++;
            }
        }.runTaskTimer(ReforgedMHA.getPlugin(ReforgedMHA.class),0,7);
        return true;
    }
    public boolean CastAbility3(Player p){
        if(!p.isOnGround()){p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 2.0f,2.0f);
            p.sendMessage(ChatColor.RED + "Must be on the Ground!"); return false;}
        new BukkitRunnable(){
            int tick = 0;
            //int seconds = 0;
            @Override
            public void run() {
                tick++;
                if(tick >= playerdata.get(p.getUniqueId()).getQuirk().getAbility3Effect()) this.cancel();
                p.setVelocity(new Vector(p.getEyeLocation().getDirection().getX(),0,p.getEyeLocation().getDirection().getZ()).normalize().multiply(0.6));
                p.getWorld().spawnParticle(Particle.FLAME,p.getEyeLocation().subtract(new Vector(p.getEyeLocation().getDirection().getX(),0.4,p.getEyeLocation().getDirection().getZ())),5,0.05,0.05,0.05,0.02);
                p.getWorld().spawnParticle(Particle.SNOWFLAKE, p.getLocation(), 15,0.5,0.1,0.5,0);
                p.getWorld().playSound(p.getLocation(),Sound.BLOCK_GLASS_BREAK, (float) 0.1,2);
            }
        }.runTaskTimer(ReforgedMHA.getPlugin(ReforgedMHA.class),0,0);
        return true;
    }

    public void fireBlast(Player p){
        p.getWorld().playSound(p.getLocation(),Sound.ENTITY_BLAZE_SHOOT,2, 1F);

        RayTrace rayTrace = new RayTrace(p.getEyeLocation().toVector(), p.getEyeLocation().getDirection());
        ArrayList<Vector> positions = rayTrace.traverse(10, 1);

        new BukkitRunnable(){
            int i =0;
            @Override
            public void run() {
                if(i >= positions.size()-1)
                    this.cancel();

                Location position = positions.get(i).toLocation(p.getWorld());
                position.getWorld().spawnParticle(Particle.FLAME, position, 10, 0.12, 0.12, 0.12, 0.05);
                position.getWorld().spawnParticle(Particle.SMALL_FLAME, position, 5, 0.05, 0.05, 0.05, 0.1);
                Damage.damageList(p, (ArrayList<Entity>) position.getNearbyEntities(1,1,1),playerdata.get(p.getUniqueId()).getQuirk().getAbility2Dmg());
                Damage.Burn(p, (ArrayList<Entity>) position.getNearbyEntities(1,1,1),playerdata.get(p.getUniqueId()).getQuirk().getAbility2Effect());

                i++;
            }
        }.runTaskTimer(ReforgedMHA.getPlugin(ReforgedMHA.class),0,0);

    }

    public void spawnVerticalIce(Location loc, double height, float yaw, float pitch, float pyaw, float ppitch, List<Block> ice){
        Vector pos;
        Location newLoc;
        for(double theta = 0; theta < 2*Math.PI; theta += Math.PI/24){
            pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(height*Math.sin(theta),height*Math.cos(theta),0),yaw,pitch),pyaw,ppitch);
            newLoc =loc.clone().add(pos);
            if(newLoc.getY() > loc.getY())
                if(loc.getWorld().getBlockAt(newLoc).getType() == Material.AIR) {
                    loc.getWorld().getBlockAt(newLoc).setType(Material.ICE);
                    ice.add(loc.getWorld().getBlockAt(newLoc));
                }
        }
    }
}