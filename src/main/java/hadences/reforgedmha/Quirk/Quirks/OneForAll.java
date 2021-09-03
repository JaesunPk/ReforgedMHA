package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.PlayerManager;
import hadences.reforgedmha.Quirk.Damage;
import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.Quirk.UniversalSkill;
import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.RayTrace;
import hadences.reforgedmha.Utility.RaycastUtils;
import hadences.reforgedmha.Utility.VectorUtils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;

import static hadences.reforgedmha.PlayerManager.FixQuirkSchedulers;
import static hadences.reforgedmha.PlayerManager.playerdata;
import static hadences.reforgedmha.Quirk.Cooldown.*;

public class OneForAll extends QuirkCastManager implements Listener {
    Blackwhip blackwhip = new Blackwhip();
    UniversalSkill universalSkill = new UniversalSkill();
    private boolean passive = true;
    PlayerManager pM;
    public boolean CastAbility1(Player p){
        Vector pos;
        Location loc = p.getEyeLocation();
        pM = playerdata.get(p.getUniqueId());
        if(pM.isQuirkinState()){
            p.playSound(p.getLocation(),Sound.BLOCK_DISPENSER_DISPENSE,0.5f,1);
            pM.getCombo().add('1');
            if(pM.getCombo().size()>=2)
                ComboCheck(p);


            if(pM.getCombo().size() == 1 && pM.isQuirkinState()) {
                pM.getCombo().clear();
            }
            return false;
        }else{
            universalSkill.Punch(p,pM.getQuirk().getAbility1Dmg(),0);
            for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 12) {
                pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(2 * Math.sin(theta), 1.2, 2 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
                loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 5, 0.12, 0.12, 0.12, 0.5, new Particle.DustOptions(Color.fromRGB(78, 212, 102 ), 1.2f));

                pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(2 * Math.sin(theta), 5, 2 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
                loc.getWorld().spawnParticle(Particle.CLOUD,loc.clone().add(pos),5,0.02,0.02,0.02,0.2);

                pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(1.2 * Math.sin(theta), 3, 1.2 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
                loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 5, 0.12, 0.12, 0.12, 0.5, new Particle.DustOptions(Color.fromRGB(78, 212, 102 ), 1.2f));

            }

        }



        return true;
    }
    public boolean CastAbility2(Player p){
        pM = playerdata.get(p.getUniqueId());

        if(pM.isQuirkinState()){
            p.playSound(p.getLocation(),Sound.BLOCK_DISPENSER_DISPENSE,0.5f,1);
            pM.getCombo().add('2');
            if(pM.getCombo().size()>=2)
                ComboCheck(p);
            if(pM.getCombo().size() == 1 && pM.isQuirkinState()) {
                pM.getCombo().clear();
            }
            return false;
        }else{
            blackwhip.CastAbility1(p);
        }


        return true;
    }
    public boolean CastAbility3(Player p){
        pM = playerdata.get(p.getUniqueId());
        if(pM.isQuirkinState()){
            p.playSound(p.getLocation(),Sound.BLOCK_DISPENSER_DISPENSE,0.5f,1);
            pM.getCombo().add('3');
            if(pM.getCombo().size()>=2)
                ComboCheck(p);

            if(pM.getCombo().size() <= 1 && pM.isQuirkinState()) {
                BukkitTask ComboTask = new ComboScheduler(p, ReforgedMHA.getPlugin(ReforgedMHA.class)).runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class), 22);
                FixQuirkSchedulers(p, playerdata.get(p.getUniqueId()).getQuirk().getName()+"1", ComboTask);
            }

            return false;
        }

        if(!pM.isQuirkinState()){
            //Full Cowl
            BukkitTask OFATask = new OFAScheduler(p,ReforgedMHA.getPlugin(ReforgedMHA.class)).runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class),playerdata.get(p.getUniqueId()).getQuirk().getAbility3Effect());
            FixQuirkSchedulers(p,playerdata.get(p.getUniqueId()).getQuirk().getName(),OFATask);
            p.playSound(p.getLocation(),"custom.fullcowling",0.5f,1);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,playerdata.get(p.getUniqueId()).getQuirk().getAbility3Effect(),2,false,false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,playerdata.get(p.getUniqueId()).getQuirk().getAbility3Effect(),4,false,false));

            pM.setQuirkinState(true);
        }


        return false;
    }

    public void CastSmokeScreen(Player p){
        p.getWorld().playSound(p.getLocation(),Sound.BLOCK_FIRE_EXTINGUISH,2,1);
        p.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE,p.getLocation(),200,4,4,4,0);
        Damage.addPotionEffect(p, (ArrayList<Entity>) p.getLocation().getNearbyEntities(5,5,5),new PotionEffect(PotionEffectType.BLINDNESS,60,10));

        sendComboAbility(p,ChatColor.GRAY + "" + "Smokescreen");
    }
    public void CastFloat(Player p){
        p.getWorld().playSound(p.getLocation(),Sound.ENTITY_BLAZE_SHOOT,2,1);
        p.setVelocity(new Vector(0,2,0));
        new BukkitRunnable(){
            @Override
            public void run() {
                p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,40,255,false,false));
            }
        }.runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class),5);

        sendComboAbility(p,ChatColor.BLUE + "" + "Float");
    }
    public void CastDetroitSmash(Player p){
            Vector pos;
            Location loc = p .getEyeLocation();
                universalSkill.Punch(p,playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg()+6,0);
                loc.getWorld().playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,0.5f);
                //draw effect
                for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 12) {
                    pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(2.3 * Math.sin(theta), 3, 2.3 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
                    loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 5, 0.12, 0.12, 0.12, 0.5, new Particle.DustOptions(Color.fromRGB(67, 209, 92), 1.2f));

                    pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(2 * Math.sin(theta), 5, 2 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
                    loc.getWorld().spawnParticle(Particle.CLOUD,loc.clone().add(pos),10,0.02,0.02,0.02,0.2);

                    pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(3.6 * Math.sin(theta), 6, 3.6 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
                    loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 5, 0.12, 0.12, 0.12, 0.5, new Particle.DustOptions(Color.fromRGB(67, 209, 92 ), 1.2f));
                }

        cooldowns.remove(p.getName());
        cooldowns.put(p.getName(), System.currentTimeMillis() + (playerdata.get(p.getUniqueId()).getQuirk().getAbility1CD() * 1000));
        cooldowns2.remove(p.getName());
        cooldowns2.put(p.getName(), System.currentTimeMillis() + (playerdata.get(p.getUniqueId()).getQuirk().getAbility2CD() * 1000));


        sendComboAbility(p,ChatColor.GREEN + ""  + "Detroit Smash");
    }
    public void CastManchesterSmash(Player p){
        if(!p.isOnGround()){p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 2.0f,2.0f);
            p.sendMessage(net.md_5.bungee.api.ChatColor.RED + "Must be on the Ground!");  return;}
        universalSkill.Punch(p,0,15);
        new BukkitRunnable(){
            @Override
            public void run() {
                p.getWorld().playSound(p.getLocation(),Sound.ENTITY_BLAZE_SHOOT,2,1);
                p.setVelocity(new Vector(0,4,0));
                playerdata.get(p.getUniqueId()).setFallDamage(false);
            }
        }.runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class),5);

        new BukkitRunnable(){
            @Override
            public void run() {
                p.setVelocity(new Vector(0,2,0).multiply(-1));
            }
        }.runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class),10);

        new BukkitRunnable(){
            Vector pos;
            double height = 0;
            @Override
            public void run() {
                for(double theta = 0; theta < Math.PI*2; theta += Math.PI/16){
                    pos = new Vector(5*Math.sin(theta),0, 5*Math.cos(theta));
                    p.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, p.getLocation().clone().add(pos),2,0,0,0,0);
                }

                Location loc = p.getLocation();
                for(double theta = 0; theta < Math.PI*2; theta += Math.PI/16){
                    pos = new Vector(Math.sin(theta)*1.2 , height , Math.cos(theta)*1.2);
                    loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(67, 209, 92), 2.2f));
                    height += 0.4;
                }
                p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,0.5f);
                ArrayList<Entity> tar = (ArrayList<Entity>) p.getLocation().getNearbyEntities(2,2,2);
                tar = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p,tar,false);
                for(Entity e : tar){
                    if(e instanceof  LivingEntity){
                        ((LivingEntity) e).damage(playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg()+4);
                        e.setVelocity(new Vector(0,2.2,0));
                    ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,50,5));}
                }
                playerdata.get(p.getUniqueId()).setFallDamage(false);
            }
        }.runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class),20);



        sendComboAbility(p,ChatColor.GREEN + ""  + "Manchester Smash");
    }
    public void CastDelawareSmashAirForce(Player p){
        new BukkitRunnable(){
            int times = 0;
            @Override
            public void run() {
                if(times >= 2)
                    this.cancel();

                airBlast(p);

                times++;
            }
        }.runTaskTimer(ReforgedMHA.getPlugin(ReforgedMHA.class),0,7);
        sendComboAbility(p,ChatColor.GREEN + "" + "Delaware Smash Air Force");
    }
    public void CastUnitedStatesOfSmash(Player p){
        if(RaycastUtils.cleanTargetList(p,(ArrayList<Entity>) p.getLocation().getNearbyEntities(2,2,2),false).isEmpty())
           return;
        Vector pos;
        double height = 0;
        new BukkitRunnable(){
            int i=0;
            @Override
            public void run() {
                if(i>=10)
                    this.cancel();
                p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,0.5f);
                p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP,2,1.2f);

                i++;
            }
        }.runTaskTimer(ReforgedMHA.getPlugin(ReforgedMHA.class),0,0);

        p.getWorld().spawnParticle(Particle.CLOUD,p.getLocation(),100,1,1,1,0.5);
        for(double theta = 0; theta < Math.PI*6; theta += Math.PI/16){
            pos = new Vector(Math.sin(theta)*4 , height , Math.cos(theta)*4);
            p.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, p.getLocation().clone().add(pos),4,0,0,0,0.2);
            height += 0.4;
        }
        ArrayList<Entity> tar = (ArrayList<Entity>) p.getLocation().getNearbyEntities(10,10,10);
        tar = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p,tar,false);
        for(Entity e : tar){
            if(e instanceof  LivingEntity){
                ((LivingEntity) e).damage(playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg()+12);
                e.setVelocity(new Vector(0,3,0));
                ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,60,10));}
        }


        sendComboAbility(p,ChatColor.RED + "" + "U" + ChatColor.WHITE + "S" + ChatColor.BLUE + "A" + ChatColor.WHITE +" Smash!");
    }

    public void ComboCheck(Player p){
        String combo = "";
        for(Character c : playerdata.get(p.getUniqueId()).getCombo()){
            combo += c +"";
        }
        //p.sendMessage(combo);
        //SmokeScreen
        if(combo.equalsIgnoreCase("322")){
            playerdata.get(p.getUniqueId()).setStamina(playerdata.get(p.getUniqueId()).getStamina()-10);
            CastSmokeScreen(p);
            pM.getCombo().clear();}
        //Float
        if(combo.equalsIgnoreCase("311")){
            playerdata.get(p.getUniqueId()).setStamina(playerdata.get(p.getUniqueId()).getStamina()-10);
            CastFloat(p);
            pM.getCombo().clear();}
        //Detroit Smash
        if(combo.equalsIgnoreCase("312")){playerdata.get(p.getUniqueId()).setStamina(playerdata.get(p.getUniqueId()).getStamina()-25);
            CastDetroitSmash(p);
            pM.getCombo().clear();}
        //Manchester Smash
        if(combo.equalsIgnoreCase("321")){
            playerdata.get(p.getUniqueId()).setStamina(playerdata.get(p.getUniqueId()).getStamina()-30);
            CastManchesterSmash(p);
            pM.getCombo().clear();}
        //Delaware Smash Air Force
        if(combo.equalsIgnoreCase("313")){
            playerdata.get(p.getUniqueId()).setStamina(playerdata.get(p.getUniqueId()).getStamina()-15);
            CastDelawareSmashAirForce(p);
            pM.getCombo().clear();}
        //United States of Smash
        if(combo.equalsIgnoreCase("32312")){
            playerdata.get(p.getUniqueId()).setStamina(playerdata.get(p.getUniqueId()).getStamina()-35);
            CastUnitedStatesOfSmash(p);
            pM.getCombo().clear();
        }

    }

    @Override
    public boolean isPassive() {
        return passive;
    }

    @EventHandler
    public void Movement(PlayerMoveEvent e){
        Player p = e.getPlayer();
        pM = playerdata.get(p.getUniqueId());
        Location loc = p.getLocation();
        if(pM.getQuirk().getName().equalsIgnoreCase("One For All") && pM.isQuirkinState()){
            loc.subtract(new Vector(loc.getDirection().getX()/4,loc.getDirection().getY()/4,loc.getDirection().getZ()/4));
            for(double i = 0; i < 1.2; i+= 0.4){
                loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(new Vector(0,i,0)), 10, 0.08, 0.08, 0.08, 0.05, new Particle.DustOptions(Color.fromRGB(67, 209, 92), 0.8f));
            }

        }

    }

    public void sendComboAbility(Player p, String s){
        p.playSound(p.getLocation(),Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,2);
        p.sendTitle(" ", ChatColor.GOLD + "Combo Skill : " + ChatColor.WHITE + "[" + s + ChatColor.WHITE + "]");
    }

    public void airBlast(Player p){
        p.getWorld().playSound(p.getLocation(),Sound.ENTITY_BAT_TAKEOFF,2, 1F);
        Vector dir = p.getEyeLocation().getDirection();
        RayTrace rayTrace = new RayTrace(p.getEyeLocation().toVector(), dir);
        ArrayList<Vector> positions = rayTrace.traverse(20, 1);

        new BukkitRunnable(){
            int i =0;
            @Override
            public void run() {
                if(i >= positions.size()-1)
                    this.cancel();

                Location position = positions.get(i).toLocation(p.getWorld());
                position.getWorld().spawnParticle(Particle.CLOUD, position, 10, 0.12, 0.12, 0.12, 0.02);
                Damage.damageList(p, (ArrayList<Entity>) position.getNearbyEntities(1,1,1),playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg()-2);
                ArrayList<Entity> enemies = (ArrayList<Entity>) position.getNearbyEntities(1,1,1);
                enemies = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p,enemies,false);
                for(Entity e : enemies){
                    if(e instanceof LivingEntity)
                        e.setVelocity(dir.multiply(2));
                }

                i++;
            }
        }.runTaskTimer(ReforgedMHA.getPlugin(ReforgedMHA.class),0,1);

    }

}

class OFAScheduler extends BukkitRunnable {

    Player p;
    ReforgedMHA plugin;
    public OFAScheduler(Player e, ReforgedMHA plugin){
        this.p = e;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        playerdata.get(p.getUniqueId()).setQuirkinState(false);
        playerdata.get(p.getUniqueId()).setQuirkStorage(0);
        playerdata.get(p.getUniqueId()).getCombo().clear();
        p.playSound(p.getLocation(),Sound.BLOCK_BEACON_DEACTIVATE,2,1);
        cooldowns3.remove(p.getName());
        cooldowns3.put(p.getName(), System.currentTimeMillis() + (playerdata.get(p.getUniqueId()).getQuirk().getAbility3CD() * 1000));
    }
}

class ComboScheduler extends BukkitRunnable {

    Player p;
    ReforgedMHA plugin;
    public ComboScheduler(Player e, ReforgedMHA plugin){
        this.p = e;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        playerdata.get(p.getUniqueId()).setQuirkStorage(0);
        playerdata.get(p.getUniqueId()).getCombo().clear();
        p.playSound(p.getLocation(),Sound.ENTITY_ITEM_BREAK,2,1);
    }
}
