package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.Damage;
import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.Quirk.UniversalSkill;
import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.RaycastUtils;
import hadences.reforgedmha.Utility.VectorUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static hadences.reforgedmha.PlayerManager.FixQuirkSchedulers;
import static hadences.reforgedmha.PlayerManager.playerdata;
import static hadences.reforgedmha.Utility.RaycastUtils.cleanTargetList;

public class Erasure extends QuirkCastManager {
    ReforgedMHA plugin = ReforgedMHA.getPlugin(ReforgedMHA.class);
    UniversalSkill universalSkill = new UniversalSkill();

    Location loc;
    Location endpoint;

    public boolean CastAbility1(Player p) {
        universalSkill.Punch(p,playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg(),playerdata.get(p.getUniqueId()).getQuirk().getAbility1Effect());
        return true;
    }
    public boolean CastAbility2(Player p) {
        CastErasure(p);
        return true;
    }
    public boolean CastAbility3(Player p) {
        loc = p.getLocation();
        double hitbox = 0.2;
        endpoint = RaycastUtils.StartRaycast(p,15,hitbox,false);
        double length = RaycastUtils.calculateDistance(loc,endpoint);
        if(endpoint.getBlock().isSolid() && endpoint.getBlock().getType() != Material.BARRIER) {
            p.getWorld().playSound(p.getLocation(),Sound.ENTITY_BAT_TAKEOFF,2,1);
            DrawBandage(p,length,false,0,0,0,endpoint,hitbox);
            ErasureAbilityBlock(endpoint, p);
        }else{
            p.sendMessage(ChatColor.RED + "No Target");
            return false;
        }
        return true;
    }
    public void DrawBandage(Player p, double length, boolean dealDmg, double hearts, float yaw, float pitch, Location endpoint, double hitbox){
        Vector pos;
        loc = p.getEyeLocation();
        if(dealDmg) {
            Damage.damageList(p,(ArrayList<Entity>) endpoint.getNearbyEntities(hitbox,hitbox,hitbox),hearts);
        }
        double y = -0.1;
        for(double t = 0; t <= length; t += 0.1){
            pos = VectorUtils.rotateVector(new Vector(t,y,0),loc.getYaw()+yaw,loc.getPitch()+pitch);
            //Black Particle
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 10, 0.08, 0.08, 0.08, 0,new Particle.DustOptions(Color.fromRGB(255,255,255), 0.5F));
        }
    }

    public void CastErasure(Player p){
        loc = p.getEyeLocation();
        Vector pos;
        double hitbox = 0.5;
        endpoint = RaycastUtils.StartRaycast(p,50,hitbox,false);
        List<Entity> TargetList = (List<Entity>) endpoint.getNearbyEntities(hitbox,hitbox,hitbox);
        TargetList = cleanTargetList(p,TargetList,false);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL,2,2);
        //draw effect
        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 25) {
            pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0.3 * Math.sin(theta), 0.5, 0.3 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(255,0,0), 0.5f));
        }
        if(TargetList.isEmpty()) return;
        for(Entity entity : TargetList){
            if(entity instanceof Player){
                //player
                if(p.getUniqueId() != entity.getUniqueId()){
                    p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER,2,1);
                    //if(playerdata.get(p.getUniqueId()).getTeam() != playerdata.get(entity.getUniqueId()).getTeam()){
                    QuirkErased((Player) entity,p);
                    //}
                }
            }else if((!(entity instanceof Item))){
                //not a player
                return;
            }
        }
    }

    public void QuirkErased(Player e, Player p){
        playerdata.get(e.getUniqueId()).setAllowSkill(false);
        e.sendTitle(ChatColor.RED + "QUIRK ERASED","");
        BukkitTask ErasureTask = new ErasureScheduler(e,ReforgedMHA.getPlugin(ReforgedMHA.class)).runTaskLater(plugin,playerdata.get(p.getUniqueId()).getQuirk().getAbility2Effect());
        FixQuirkSchedulers(e,playerdata.get(p.getUniqueId()).getQuirk().getName(),ErasureTask);
    }


    public void ErasureAbilityBlock(Location block, Player p){
        new BukkitRunnable(){
            Location playerLoc = p.getLocation();
            Vector v;
            int time = 0;
            @Override
            public void run() {
                if(block.distance(playerLoc) < 7 || time > 80){
                    this.cancel();
                }
                playerLoc = p.getLocation();
                v = block.toVector().subtract(playerLoc.toVector());
                p.setVelocity(v.normalize().multiply(1.5));
                time++;
            }
        }.runTaskTimer(plugin,0,0);
    }
}

class ErasureScheduler extends BukkitRunnable{

    Player e;
    ReforgedMHA plugin;
    public ErasureScheduler(Player e, ReforgedMHA plugin){
        this.e = e;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        playerdata.get(e.getUniqueId()).setAllowSkill(true);
        this.cancel();
    }
}

