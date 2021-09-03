package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.Quirk.UniversalSkill;
import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.RaycastUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.ArrayList;

import static hadences.reforgedmha.PlayerManager.FixQuirkSchedulers;
import static hadences.reforgedmha.PlayerManager.playerdata;

public class Engine extends QuirkCastManager implements Listener{
    UniversalSkill universalSkill = new UniversalSkill();

    private boolean passive = true;

    public boolean CastAbility1(Player p) {
        double multiplier = 1 + p.getWalkSpeed();
        universalSkill.Punch(p,playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg()*multiplier,playerdata.get(p.getUniqueId()).getQuirk().getAbility1Effect());
        return true;
    }

    public boolean CastAbility2(Player p) {
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,2);
        p.setWalkSpeed(0.8f);
        BukkitTask EngineTask = new EngineScheduler(p,ReforgedMHA.getPlugin(ReforgedMHA.class)).runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class),playerdata.get(p.getUniqueId()).getQuirk().getAbility2Effect());
        FixQuirkSchedulers(p,playerdata.get(p.getUniqueId()).getQuirk().getName(),EngineTask);
        return true;
    }

    public boolean CastAbility3(Player p) {
        Location loc = RaycastUtils.StartRaycast(p,3,0.8,false);
        ArrayList<Entity> target = (ArrayList<Entity>) loc.getNearbyEntities(0.8,0.8,0.8);
        target = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p,target,true);
        /*if(playerdata.get(p.getUniqueId()).getQuirkTaggedEntity() != null){
            try{
                p.removePassenger(playerdata.get(p.getUniqueId()).getQuirkTaggedEntity());
            }catch (Exception e){

            }
        }
        if(target.isEmpty())
            return false;
        playerdata.get(p.getUniqueId()).setQuirkTaggedEntity(target.get(0));*/
        if(target.isEmpty())
            return false;
        p.addPassenger(target.get(0));

        return true;
    }

    @Override
    public boolean isPassive() {
        return passive;
    }

    @EventHandler
    public void playerDismount(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        if (p.isSneaking()) {
            try {
                for(Entity t : p.getPassengers()){
                    p.removePassenger(t);
                }

            } catch (Exception ee) {

            }
        }
    }

    @EventHandler
    public void passive(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        if (playerdata.get(p.getUniqueId()).getQuirk().getName().equalsIgnoreCase("Engine")) {
            if (p.getWalkSpeed() < 0.6) p.setWalkSpeed(0.4f);
            if (p.getWalkSpeed() > 0.7) {
                loc.getWorld().spawnParticle(Particle.CLOUD, loc, 5, 0.05, 0.05, 0.05, 0.05);
                loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 5, 0.05, 0.05, 0.05, 0.05);
                loc.getWorld().spawnParticle(Particle.FLAME, loc, 5, 0.05, 0.05, 0.05, 0.05);
            }

        }

    }


}

class EngineScheduler extends BukkitRunnable{

    Player p;
    ReforgedMHA plugin;
    public EngineScheduler(Player e, ReforgedMHA plugin){
        this.p = e;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        p.setWalkSpeed(0.4f);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH,2,1);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,30,3));
    }
}
