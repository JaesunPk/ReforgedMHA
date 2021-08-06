package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.Damage;
import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.VectorUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static hadences.reforgedmha.PlayerManager.playerdata;

public class HalfColdHalfHot extends QuirkCastManager {

    Location loc;
    public boolean CastAbility1(Player p){
        loc = p.getLocation();
        double height = 0.2;
        float yaw = loc.getYaw();
        float pitch = loc.getPitch();
        ArrayList<Block> ice = new ArrayList<>();
        if(!p.isOnGround()){p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 2.0f,2.0f);
            p.sendMessage(ChatColor.RED + "Must be on the Ground!"); return false;}
        for(int i = 0; i <= 25; i++){
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
                //Damage.immobilizeList(p,(ArrayList<Entity>) loc.getNearbyEntities(1.2,height,1.2),playerdata.get(p.getUniqueId()).getQuirk().getAbility1Effect());
                Damage.addPotionEffect(p,(ArrayList<Entity>) loc.getNearbyEntities(1.2,height,1.2),new PotionEffect(PotionEffectType.SLOW,80,2));
            }
        }




        new BukkitRunnable() {
            @Override
            public void run() {
                for (Block b : ice) {
                    if (b.isSolid()) {
                        b.setType(Material.AIR);
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_GLASS_BREAK, 2, 1);
                    }
                }
                ice.clear();
            }
        }.runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class), 50);
        return true;
    }
    public boolean CastAbility2(Player p){
        loc = p.getLocation().add(0,1,0);
        loc.add(loc.getDirection().multiply(1.2));
        Vector pos;
        ArrayList<Block> flame = new ArrayList<>();
        //if(!p.isOnGround()){p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 2.0f,2.0f);
            //p.sendMessage(ChatColor.RED + "Must be on the Ground!"); return false;}
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, 1);
        for(int i = 0; i < 12; i++) {
            loc.add(loc.getDirection());
            for (double theta = 0; theta <= Math.PI; theta += Math.PI / 12) {
                pos = new Vector(Math.sin(theta) * 1.2, 0, Math.cos(theta) * 1.2);
                pos = VectorUtils.rotateVector(pos, 270, 0);
                pos = VectorUtils.rotateVector(pos, loc.getYaw(), 0);
                if(i == 0) {loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 35, 0.1, 0.1, 0.1, 0.2, new Particle.DustOptions(Color.fromRGB(255, 34, 12 ), 1.2f));
                    loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 35, 0.1, 0.1, 0.1, 0.2, new Particle.DustOptions(Color.fromRGB(255, 167, 12  ), 1.2f));
                }
                 if (loc.clone().add(pos).getBlock().getType() == Material.AIR) {
                    loc.clone().add(pos).getBlock().setType(Material.FIRE);
                    flame.add(loc.clone().add(pos).getBlock());
                }
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                p.getWorld().playSound(p.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 2, 1);
                for (Block b : flame) {
                        b.setType(Material.AIR);
                }
                flame.clear();
            }
        }.runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class), 25);
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
                //if(tick == 20) {seconds++; tick = 0;}
                if(tick >= playerdata.get(p.getUniqueId()).getQuirk().getAbility3Effect()) this.cancel();
                p.setVelocity(new Vector(p.getEyeLocation().getDirection().getX(),0,p.getEyeLocation().getDirection().getZ()).normalize().multiply(0.6));
                p.getWorld().spawnParticle(Particle.FLAME,p.getEyeLocation().subtract(new Vector(p.getEyeLocation().getDirection().getX(),0.4,p.getEyeLocation().getDirection().getZ())),5,0.05,0.05,0.05,0.02);
                p.getWorld().spawnParticle(Particle.SNOWFLAKE, p.getLocation(), 15,0.5,0.1,0.5,0);
                p.getWorld().playSound(p.getLocation(),Sound.BLOCK_GLASS_BREAK, (float) 0.1,2);
            }
        }.runTaskTimer(ReforgedMHA.getPlugin(ReforgedMHA.class),0,0);
        return true;
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
