package hadences.reforgedmha.Quirk.Quirks;


import hadences.reforgedmha.Quirk.Damage;
import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.RaycastUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static hadences.reforgedmha.PlayerManager.playerdata;
import static hadences.reforgedmha.Utility.RaycastUtils.cleanTargetList;

public class Explosion extends QuirkCastManager {
    private ReforgedMHA plugin = ReforgedMHA.getPlugin(ReforgedMHA.class);
    Location loc;
    public boolean CastAbility1(Player p){
        loc = p.getEyeLocation().add(p.getEyeLocation().getDirection().normalize().multiply(2));
        double hitbox = 1.2;

        Location endpoint = RaycastUtils.StartRaycast(p,5,hitbox,false);
        List<Entity> target = (List<Entity>) endpoint.getNearbyEntities(hitbox,hitbox,hitbox);
        if(target.contains(p)) target.remove(p);
        if(target.size() >=1) {
            for (Entity e : target) {
                if (!(e instanceof Item) && e instanceof LivingEntity)
                    e.setVelocity(p.getEyeLocation().getDirection().normalize().multiply(0.8));
            }
        }
        Damage.damageList(p, (ArrayList<Entity>) endpoint.getNearbyEntities(hitbox,hitbox,hitbox),playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg());
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,1);
        drawExplosion(loc,0.5);


        p.setVelocity(p.getEyeLocation().getDirection().normalize().multiply(-0.5));

        target.clear();
        return true;
    }
    public boolean CastAbility2(Player p){
        Vector pos;
        loc = p.getLocation();
        loc.add(p.getEyeLocation().getDirection().normalize().multiply(3));
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,2);
        Damage.damageList(p, (ArrayList<Entity>) loc.getNearbyEntities(2,2,2),playerdata.get(p.getUniqueId()).getQuirk().getAbility2Dmg());
        List<Entity> blindtarget = (List<Entity>) loc.getNearbyEntities(2.5,2.5,2.5);
        blindtarget = cleanTargetList(p,blindtarget,false);
        for(Entity e : blindtarget){
            if(e instanceof LivingEntity){
                ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,playerdata.get(p.getUniqueId()).getQuirk().getAbility2Effect(),10,false,true));
            }
        }
        blindtarget.clear();
        double radius;
        for(double height = 0; height < 3; height++){
            for(double theta = 0; theta < 2*Math.PI; theta += Math.PI/2){
                if(height == 0 || height ==2){
                    radius = 0.5;
                }else{
                    radius = 1.5;
                }
                pos = new Vector(radius * Math.sin(theta),height, radius * Math.cos(theta));
                drawExplosion(loc.clone().add(pos),0.05);
            }
        }
        p.setVelocity(p.getEyeLocation().getDirection().normalize().multiply(-0.8));
        return true;
    }

    public boolean CastAbility3(Player p){
        Location Eyeloc = p.getEyeLocation();
        Vector direction = Eyeloc.getDirection();
        p.setVelocity(direction.normalize().multiply(1.2));
        Location explosionlocation = p.getEyeLocation().subtract(p.getEyeLocation().getDirection().normalize());
        p.playSound(explosionlocation,Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,1);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,20,180,false,false));
        drawExplosion(explosionlocation,0.05);
        return true;
    }

    public void drawExplosion(Location loc,double offset){
        loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE,loc,3,offset,offset,offset,0.5);
        loc.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE,loc,25,offset,offset,offset,0.05);
        loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 25, 1.2,1.2,1.2, 0, new Particle.DustOptions(Color.fromRGB(255, 136, 49 ), 3f));
        loc.getWorld().spawnParticle(Particle.LAVA,loc,5,offset,offset,offset,0.03);
    }

}
