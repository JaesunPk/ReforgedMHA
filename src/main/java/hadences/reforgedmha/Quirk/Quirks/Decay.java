package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Object.MHABlock;
import hadences.reforgedmha.Quirk.Damage;
import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.RayTrace;
import hadences.reforgedmha.Utility.RaycastUtils;
import hadences.reforgedmha.Utility.VectorUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
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
import static hadences.reforgedmha.Utility.RaycastUtils.cleanTargetList;

public class Decay extends QuirkCastManager {

    public boolean CastAbility1(Player p) {
        int decay_timer = playerdata.get(p.getUniqueId()).getQuirk().getAbility1Effect();
        Location endpoint = RaycastUtils.StartRaycast(p,2.5,1,false);
        List<Entity> target = (List<Entity>) endpoint.getNearbyEntities(1,1,1);
        target = cleanTargetList(p,target,false);
        Location loc = p.getEyeLocation();
        Vector pos;
        loc.getWorld().playSound(loc, Sound.ENTITY_BLAZE_SHOOT,0.5f,0.1f);
        for(double theta =0; theta <= Math.PI; theta += Math.PI/12) {
            pos = new Vector(0, Math.sin(theta) * 1.8, Math.cos(theta) * 1.8);
            pos = VectorUtils.rotateVector(pos, 270, 90);
            pos = VectorUtils.rotateVector(pos, loc.getYaw(), loc.getPitch());
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 35, 0.1, 0.1, 0.1, 0.2, new Particle.DustOptions(Color.fromRGB(99, 117, 111), 0.7f));
            loc.getWorld().spawnParticle(Particle.ASH, loc.clone().add(pos), 10, 0.12, 0.12, 0.12, 0.01);
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 20, 0.12, 0.1, 0.12, 0.2, new Particle.DustOptions(Color.fromRGB(  87, 91, 90), 0.7f));
        }
        Damage.damageList(p, (ArrayList<Entity>) target,playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg());
        for(Entity e : target){
            if(e instanceof Player){
                if(playerdata.get(p.getUniqueId()).getTeam() != playerdata.get(e.getUniqueId()).getTeam()){
                    decay(e,decay_timer,2);
                }
            }else{
                decay(e,decay_timer,2);
            }
            playSound(e.getLocation());
            decay_effect(e.getLocation());
        }
        return true;
    }
    public boolean CastAbility2(Player p) {
        if(!p.isOnGround()){ p.sendMessage(ChatColor.RED + "You must be on the ground!"); return false;}
            Location loc = p.getLocation();
            int travel_distance = 15;


        Vector dir = loc.getDirection();
            loc.getWorld().playSound(loc.clone().add(new Vector(0, 0.4, 0)), Sound.BLOCK_FIRE_EXTINGUISH, 0.8f, 0.5f);
            RayTrace rayTrace = new RayTrace(new Vector(loc.getX(), loc.getY() - 1, loc.getZ()), new Vector(dir.getX(), 0, dir.getZ()));
            RayTrace rayTrace1 = new RayTrace(new Vector(loc.getX(), loc.getY() - 1, loc.getZ()), VectorUtils.rotateVector(new Vector(dir.getX(), 0, dir.getZ()), 262, 0));
            RayTrace rayTrace2 = new RayTrace(new Vector(loc.getX(), loc.getY() - 1, loc.getZ()), VectorUtils.rotateVector(new Vector(dir.getX(), 0, dir.getZ()), 278, 0));
            ArrayList<Vector> positions = rayTrace.traverse(travel_distance, 1);
             positions.addAll(rayTrace1.traverse(travel_distance - 2, 1));
             positions.addAll(rayTrace2.traverse(travel_distance - 2, 1));

        //GameBlocks.add(new MHABlock())





            ArrayList<DecayBlock> decayblocks = new ArrayList<>();
            positions.addAll(rayTrace1.traverse(travel_distance - 2, 1));
            positions.addAll(rayTrace2.traverse(travel_distance - 2, 1));
            for (Vector v : positions) {
                Location position = v.toLocation(p.getWorld());
                Block block = p.getWorld().getBlockAt(position);
                if(block.getType() != Material.CRIMSON_HYPHAE && block.getType() != Material.WARPED_HYPHAE)
                if (block.getType() != Material.AIR) {
                        decayblocks.add(new DecayBlock(block,p));
                    }
            }
           startDecay(p,decayblocks);
            positions.clear();

        return true;
    }
    public boolean CastAbility3(Player p) {
        p.getWorld().playSound(p.getLocation(),Sound.ENTITY_VEX_CHARGE,2,2);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,playerdata.get(p.getUniqueId()).getQuirk().getAbility3Effect(),3));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,playerdata.get(p.getUniqueId()).getQuirk().getAbility3Effect(),3));

        return true;
    }

    public void decay_effect(Location loc){
        loc.getWorld().spawnParticle(Particle.SMOKE_NORMAL,loc,30,0.5,1,0.5,0.02);
        loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 35, 0.4,1,0.4, 0.2, new Particle.DustOptions(Color.fromRGB(99, 117, 111), 1.6f));
        loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 35, 0.2,0.5,0.2, 0.2, new Particle.DustOptions(Color.fromRGB(87, 91, 90), 1.6f));
        loc.getWorld().spawnParticle(Particle.ASH,loc, 10, 0.2,0.5,0.2, 0.01);

    }

    public void startDecay(Player p,ArrayList<DecayBlock> blocks){
        new BukkitRunnable(){
            int time = 0;
            @Override
            public void run() {
                for(DecayBlock b : blocks) {
                    if (time >= 8) {
                        b.getBlock().setType(b.getOriginal_type());
                    } else{
                        List<Entity> target = (List<Entity>) b.getBlock().getLocation().getNearbyEntities(0.6, 2, 0.6);
                    target = RaycastUtils.cleanTargetList(p, target,false);
                    for (Entity e : target) {
                        if (e instanceof Player) {
                            decay(e, playerdata.get(p.getUniqueId()).getQuirk().getAbility2Effect(), 2);
                        } else {
                            decay(e, playerdata.get(p.getUniqueId()).getQuirk().getAbility2Effect(), 2);
                        }
                    }
                }
                }
                if(time >= 8) { this.cancel();}
                time++;
            }
        }.runTaskTimer(ReforgedMHA.getPlugin(ReforgedMHA.class),0,20);
    }

    public void decay(Entity e,int decaytimer,int wither_amplifier){
        if(e instanceof Player) {
            ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, decaytimer, 5));
            ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, decaytimer, 1));
            ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, decaytimer, wither_amplifier));
        }
    }

    public void playSound(Location loc){
        loc.getWorld().playSound(loc, Sound.ENTITY_ZOMBIE_VILLAGER_CURE,1,0.5f);
    }
}

class DecayBlock{
    private Material original_type;
    private Block block;

    public DecayBlock(Block b,Player p){
        block = b;
        original_type = b.getType();
        if(playerdata.get(p.getUniqueId()).getTeam().equalsIgnoreCase("BETA"))
            b.setType(Material.WARPED_HYPHAE);
        if(playerdata.get(p.getUniqueId()).getTeam().equalsIgnoreCase("ALPHA"))
            b.setType(Material.CRIMSON_HYPHAE);
    }

    public Material getOriginal_type() {
        return original_type;
    }

    public void setOriginal_type(Material original_type) {
        this.original_type = original_type;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
