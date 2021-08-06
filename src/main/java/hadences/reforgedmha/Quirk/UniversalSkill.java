package hadences.reforgedmha.Quirk;

import hadences.reforgedmha.Utility.RayTrace;
import hadences.reforgedmha.Utility.RaycastUtils;
import hadences.reforgedmha.Utility.VectorUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class UniversalSkill {
    Location location;
    Vector vector;
    RaycastUtils raycastUtils;

    public void Punch(Player p,double dmg, int immobilizeSeconds){
        location = raycastUtils.StartRaycast(p,3,1);
        Damage.damageList(p, (ArrayList<Entity>) location.getNearbyEntities(1,1,1),dmg);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,1);
        if(checkimmobilized(immobilizeSeconds)){ Damage.immobilizeList(p, (ArrayList<Entity>) location.getNearbyEntities(1,1,1),immobilizeSeconds);}
            //draw effect
        location = p.getEyeLocation();
        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 25) {
            vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0.3 * Math.sin(theta), 1.6, 0.3 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(255,255,255), 0.5f));
            vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0.6 * Math.sin(theta), 0.5, 0.6 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(255,255,255), 0.5f));
        }
        vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0, 2, 0), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
        location.getWorld().spawnParticle(Particle.CLOUD,location.clone().add(vector),50,0,0,0,0.3);
    }

    public boolean checkimmobilized(int seconds){
        if(seconds > 0){
            return true;
        }
        return false;
    }

}
