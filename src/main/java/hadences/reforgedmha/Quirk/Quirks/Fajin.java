package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.Quirk.UniversalSkill;
import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.VectorUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import static hadences.reforgedmha.PlayerManager.FixQuirkSchedulers;
import static hadences.reforgedmha.PlayerManager.playerdata;

public class Fajin extends QuirkCastManager implements Listener {
    UniversalSkill universalSkill = new UniversalSkill();
    private boolean passive = true;
    public boolean CastAbility1(Player p){
        Location loc = p.getEyeLocation();
        Vector pos;
        playerdata.get(p.getUniqueId()).setQuirkStorage(playerdata.get(p.getUniqueId()).getQuirkStorage()+30);
        double dmg = playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg() * getMultiplier(p);
        universalSkill.Punch(p,dmg,0);
        if(dmg > 6){
            loc.getWorld().playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,0.5f);
            //draw effect
            for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 12) {
                pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(2.3 * Math.sin(theta), 5, 2.3 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
                loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 5, 0.12, 0.12, 0.12, 0.5, new Particle.DustOptions(Color.fromRGB(41, 225, 225 ), 1.2f));

                pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(2 * Math.sin(theta), 5, 2 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
                loc.getWorld().spawnParticle(Particle.CLOUD,loc.clone().add(pos),10,0.02,0.02,0.02,0.2);

                pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(3.6 * Math.sin(theta), 9, 3.6 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
                loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 5, 0.12, 0.12, 0.12, 0.5, new Particle.DustOptions(Color.fromRGB(30, 205, 205  ), 1.2f));

                pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(3 * Math.sin(theta), 7, 3 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
                loc.getWorld().spawnParticle(Particle.CLOUD,loc.clone().add(pos),10,0.02,0.02,0.02,0.2);
            }
        }
        if(!playerdata.get(p.getUniqueId()).isQuirkinState())
            if(playerdata.get(p.getUniqueId()).getQuirkStorage() > 15){
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20,1,false,true));
            }
        return true;
    }
    public boolean CastAbility2(Player p){
        playerdata.get(p.getUniqueId()).setQuirkinState(true);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT,2 ,2);
        p.sendMessage(ChatColor.GREEN + "+ " + ChatColor.AQUA + getMultiplier(p) + " in Speed and Power for 10 seconds");

        BukkitTask FajinTask = new FajinScheduler(p,ReforgedMHA.getPlugin(ReforgedMHA.class)).runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class),playerdata.get(p.getUniqueId()).getQuirk().getAbility2Effect());
        FixQuirkSchedulers(p,playerdata.get(p.getUniqueId()).getQuirk().getName(),FajinTask);

        return true;
    }
    public boolean CastAbility3(Player p){
        Location loc = p.getLocation();
        Location EyeLoc = p.getEyeLocation();
        Vector Direction = EyeLoc.getDirection();


        playerdata.get(p.getUniqueId()).setQuirkStorage(playerdata.get(p.getUniqueId()).getQuirkStorage()+15);

        //Particle
        loc.getWorld().spawnParticle(Particle.CLOUD,loc,10,0.5,0,0.5,0.5);
        loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE,loc,5,0,0,0,0.5);

        //Sound
        loc.getWorld().playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,2);
        loc.getWorld().playSound(loc, Sound.ENTITY_BLAZE_SHOOT,2,2);

        //Dash Ability
        p.setVelocity(Direction.multiply(getMultiplier(p)));

        if(!playerdata.get(p.getUniqueId()).isQuirkinState())
            if(playerdata.get(p.getUniqueId()).getQuirkStorage() > 15){
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20,1,false,true));
            }
        return true;
    }

    public double getMultiplier(Player p){
        double multiplier = 1.5;

        multiplier = (multiplier * (1 + getDivisible(playerdata.get(p.getUniqueId()).getQuirkStorage())));

        if(multiplier > 6){
            multiplier = 6;
        }

        multiplier = roundAvoid(multiplier,2);

        if(playerdata.get(p.getUniqueId()).isQuirkinState())
            return multiplier;

        return 1;
    }

    public double getDivisible(double num){
        if(num ==0){
            return 0;
        }

        return roundAvoid(num/100,2);
    }

    public double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    @Override
    public boolean isPassive() {
        return passive;
    }

    @EventHandler
    public void Movement (PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        if (!playerdata.get(p.getUniqueId()).getQuirk().getName().equalsIgnoreCase("Fajin"))
            return;
        if (playerdata.get(p.getUniqueId()).isQuirkinState()) {
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone(), 1, 0.12, 0.12, 0.12, 0.5, new Particle.DustOptions(Color.fromRGB(41, 225, 225 ), 0.8f));
        }


    }
}

class FajinScheduler extends BukkitRunnable{

    Player p;
    ReforgedMHA plugin;
    public FajinScheduler(Player e, ReforgedMHA plugin){
        this.p = e;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        playerdata.get(p.getUniqueId()).setQuirkinState(false);
        playerdata.get(p.getUniqueId()).setQuirkStorage(0);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE,2 ,2);
    }
}
