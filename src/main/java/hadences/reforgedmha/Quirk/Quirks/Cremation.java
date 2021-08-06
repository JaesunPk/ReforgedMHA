package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.Damage;
import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.Utility.RayTrace;
import hadences.reforgedmha.Utility.VectorUtils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static hadences.reforgedmha.PlayerManager.playerdata;

public class Cremation extends QuirkCastManager {
    Location loc;
    RayTrace rayTrace;
    public boolean CastAbility1(Player p) {
        loc = p.getLocation().add(0,1,0);
        loc.add(loc.getDirection().multiply(2));
        loc.add(loc.getDirection().multiply(2));

        FireworkEffect effect;
        Firework fw;
        FireworkMeta fm;
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, 1);
        for(int i = 0; i < 14; i++) {
            if (14 % 2 == 0){
                loc.add(loc.getDirection());
            loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc.clone(), 15, 0.5, 0.5, 0.5, 0.5);
            fw = p.getWorld().spawn(loc.clone(), Firework.class);
            effect = FireworkEffect.builder().trail(false).flicker(false).withColor(Color.fromRGB(0, 219, 230)).withColor(Color.fromRGB(28, 214, 223)).withColor(Color.fromRGB(28, 188, 223)).with(FireworkEffect.Type.BURST).build();
            fm = fw.getFireworkMeta();
            fw.setSilent(true);
            fm.clearEffects();
            fm.addEffect(effect);

            Field f;
            try {
                f = fm.getClass().getDeclaredField("power");
                f.setAccessible(true);
                f.set(fm, -2);
            } catch (Exception exception) {

            }
            fw.setFireworkMeta(fm);
        }

        }
        return true;
    }
    public boolean CastAbility2(Player p) {
        loc = p.getLocation().add(0,1,0);
        //loc.add(loc.getDirection().multiply(1.2));
        Vector pos;

        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, 1);
        for(int i = 0; i < 6; i++) {
            loc.add(loc.getDirection());
            for (double theta = 0; theta <= Math.PI; theta += Math.PI / 14) {
                pos = new Vector(Math.sin(theta) * 3, 0, Math.cos(theta) * 3);
                pos = VectorUtils.rotateVector(pos, 270, 0);
                pos = VectorUtils.rotateVector(pos, loc.getYaw(), loc.getPitch());
                if(i == 0) {loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 15, 0.1, 0.1, 0.1, 0.2, new Particle.DustOptions(Color.fromRGB(12, 189, 255 ), 0.8f));
                    loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 15, 0.1, 0.1, 0.1, 0.2, new Particle.DustOptions(Color.fromRGB(14, 142, 243 ), 0.8f));
                }
                    loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc.clone().add(pos), 5, 0.2, 0, 0.2, 0.1);
                    loc.getWorld().spawnParticle(Particle.SOUL, loc.clone().add(pos), 5, 0.1, 0, 0.1, 0.1);

                Damage.Burn(p, (ArrayList<Entity>) loc.clone().add(pos).getNearbyEntities(1,1,1),playerdata.get(p.getUniqueId()).getQuirk().getAbility2Effect());
            }
        }
        return true;
    }
    public boolean CastAbility3(Player p) {
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_FIRE_AMBIENT,2,2);
        rayTrace = new RayTrace(p.getEyeLocation().toVector(), p.getEyeLocation().getDirection());
        ArrayList<Vector> positions = rayTrace.traverse(6, 1);
        for(int i = 0; i < positions.size(); i++){
            Location position = positions.get(i).toLocation(p.getWorld());
                //position.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, position, 1, 0, 0, 0, 0.1);
                position.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, position, 10, 0.1, 0.1, 0.1, 0.05);
                position.getWorld().spawnParticle(Particle.SOUL, position, 1, 0.05, 0.05, 0.05, 0.1);
                Damage.damageList(p, (ArrayList<Entity>) position.getNearbyEntities(1,1,1),playerdata.get(p.getUniqueId()).getQuirk().getAbility3Dmg());
        }

        return true;
    }
}
