package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.Damage;
import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.Quirk.UniversalSkill;
import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.RaycastUtils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static hadences.reforgedmha.PlayerManager.playerdata;

public class ZeroGravity extends QuirkCastManager {
    Location loc;
    UniversalSkill universalSkill = new UniversalSkill();
    public boolean CastAbility1(Player p) {
        loc = RaycastUtils.StartRaycast(p,4,1,false);
        ArrayList<Entity> list;
        list = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p, (List<Entity>) loc.getNearbyEntities(1,1,1),true);
        universalSkill.Punch(p,playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg(),0);
        if(!list.isEmpty())
            for(int i =0; i < list.size(); i++)
                if(list.get(i) instanceof LivingEntity){
                    playerdata.get(p.getUniqueId()).getQuirkTaggedEntities().add(list.get(i));
                    p.sendTitle(ChatColor.GREEN + "Tagged " + ChatColor.LIGHT_PURPLE + list.get(i).getName(), "",0,20,10);
                    ((LivingEntity) list.get(i)).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,playerdata.get(p.getUniqueId()).getQuirk().getAbility1Effect(),16,true,true));
                    p.playSound(p.getLocation(),Sound.ENTITY_EXPERIENCE_ORB_PICKUP,2,1);
                    return true;
                }
       return true;
    }
    public boolean CastAbility2(Player p) {
        if(playerdata.get(p.getUniqueId()).getQuirkTaggedEntities().isEmpty()){p.sendMessage(ChatColor.RED + "No Tagged Entities!"); p.playSound(loc, Sound.ENTITY_CAT_AMBIENT,2,1);return false;}
        loc = p.getLocation();
        Vector pos;
        p.playSound(p.getLocation(),Sound.BLOCK_BEACON_DEACTIVATE,2,2);
        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 25) { pos = new Vector(2* Math.sin(theta),1,2 * Math.cos(theta));loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(252, 44, 255), 0.5f)); }
        for(Entity e : playerdata.get(p.getUniqueId()).getQuirkTaggedEntities()){if(e instanceof LivingEntity){ ((LivingEntity) e).removePotionEffect(PotionEffectType.LEVITATION);} }
        playerdata.get(p.getUniqueId()).getQuirkTaggedEntities().clear();
        return true;
    }
    public boolean CastAbility3(Player p) {
        (p).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,playerdata.get(p.getUniqueId()).getQuirk().getAbility3Effect(),16,true,true));
        p.playSound(p.getLocation(),Sound.ENTITY_EXPERIENCE_ORB_PICKUP,2,1);
        playerdata.get(p.getUniqueId()).getQuirkTaggedEntities().add(p);
        return true;
        /*p.getWorld().playSound(p.getLocation(),Sound.ENTITY_BLAZE_SHOOT,2,1);
        p.setVelocity(new Vector(0,1,0).multiply(4));
        new BukkitRunnable(){
            @Override
            public void run() {
                p.setVelocity(p.getEyeLocation().getDirection().multiply(1.2));
            }
        }.runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class),1);
        return true; */
    }
}
