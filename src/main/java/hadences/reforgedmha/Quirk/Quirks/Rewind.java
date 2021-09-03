package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.Utility.RaycastUtils;
import hadences.reforgedmha.Utility.VectorUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;

import static hadences.reforgedmha.PlayerManager.playerdata;
import static hadences.reforgedmha.Quirk.Cooldown.cooldowns;
import static hadences.reforgedmha.Quirk.Cooldown.cooldowns2;
import static hadences.reforgedmha.Quirk.Cooldown.cooldowns3;

public class Rewind extends QuirkCastManager {
    public boolean CastAbility1(Player p) {
        p.getWorld().playSound(p.getLocation(),Sound.ENTITY_WITHER_DEATH,2,2);
        double height = 0;
        Vector pos;
        Location loc = p.getLocation();
        for(double theta = 0; theta < Math.PI*2; theta += Math.PI/16){
            pos = new Vector(Math.sin(theta)*1.2 , height , Math.cos(theta)*1.2);
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(245, 238, 78), 2.2f));
            height += 0.2;
        }
        loc.getWorld().spawnParticle(Particle.CLOUD, loc.clone(), 25, 0.12, 0.12, 0.12, 0.1);

        ArrayList<Entity> target = (ArrayList<Entity>) loc.getNearbyEntities(2,2,2);
        target = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p,target,false);
        for(Entity e : target){
            if(e instanceof LivingEntity){
                ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,playerdata.get(p.getUniqueId()).getQuirk().getAbility1Effect(),10));
            }
        }


        return true;
    }

    public boolean CastAbility2(Player p) {
        Location location = p.getLocation();
        Vector pos;
        ArrayList<Entity> target = (ArrayList<Entity>) RaycastUtils.StartRaycast(p,5,1,false).getNearbyEntities(5,5,5);
        target = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p,target,false);
        for(Entity e : target){
            if(e instanceof Player){
                e.setVelocity(((Player) e).getEyeLocation().getDirection().multiply(-3));
            }else{
                e.setVelocity(e.getLocation().getDirection().multiply(-3));
            }
            p.getWorld().playSound(p.getLocation(),Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2, 0.5F);
            for(double theta = 0; theta < Math.PI*2; theta += Math.PI/16){
                pos = new Vector(Math.sin(theta)*4 , 0 , Math.cos(theta)*4);
                location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(pos), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(245, 238, 78), 2.2f));
            }
                location.getWorld().spawnParticle(Particle.FLASH,location,35,1,1,1,0);
        }


        return true;
    }

    public boolean CastAbility3(Player p) {
        ArrayList<Entity> target = (ArrayList<Entity>) RaycastUtils.StartRaycast(p,5,1,true).getNearbyEntities(1,1,1);
        target = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p,target,true);
        drawRewind(p);
        if(!target.isEmpty())
        if(target.get(0) instanceof Player){
            Player tar = (Player) target.get(0);
            if(playerdata.get(tar.getUniqueId()).getTeam() == playerdata.get(p.getUniqueId()).getTeam()) {
                cooldowns.remove(tar.getName());
                cooldowns.put(tar.getName(), System.currentTimeMillis() + (0 * 1000));
                cooldowns2.remove(tar.getName());
                cooldowns2.put(tar.getName(), System.currentTimeMillis() + (0 * 1000));
                cooldowns3.remove(tar.getName());
                cooldowns3.put(tar.getName(), System.currentTimeMillis() + (0 * 1000));
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON,2,2);
                return true;
            }
        }

        return true;
    }

    public void drawRewind(Player p) {
        Location location = p.getEyeLocation();
        Vector vector;
        Vector vector1;
        Vector vector2;
        //double y = -0.35;
        double y = 0;

        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 25) {
            vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0.8 * Math.sin(theta), 0.5, 0.8 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(245, 238, 78), 1.2f));

            vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0.8 * Math.sin(theta), 1.5, 0.8 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(245, 238, 78), 1.2f));
        }
        for(double time = 0; time < 4.72; time+=0.1){
            vector = VectorUtils.rotateVector(new Vector(time,y,Math.cos(2*time)),location.getYaw(),location.getPitch());
            vector1 = VectorUtils.rotateVector(new Vector(time,y,Math.cos(2*time)),location.getYaw(),location.getPitch());
            vector2 = VectorUtils.rotateVector(new Vector(time,y,0),location.getYaw(),location.getPitch());

            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(245, 238, 78), 1.2f));
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector1), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(245, 238, 78), 1.2f));
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector2), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(250, 255, 153), 1.2f));
        }
    }

}
