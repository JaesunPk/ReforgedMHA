package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.RaycastUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

import static hadences.reforgedmha.PlayerManager.playerdata;

public class Hardening extends QuirkCastManager implements Listener {
    private boolean passive = true;
    ItemStack helm = new ItemStack(Material.IRON_HELMET);
    ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
    ItemStack leg = new ItemStack(Material.IRON_LEGGINGS);
    ItemStack boot = new ItemStack(Material.IRON_BOOTS);
    ItemStack[] armor = new ItemStack[4];


    Player hardener = null;
    public boolean CastAbility1(Player p) {
        playerdata.get(p.getUniqueId()).setQuirkinState(true);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE,2 ,0.5f);
        p.sendMessage(ChatColor.GREEN + "Hardened for 10 seconds");
        (p).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,180,3));

        armor[0] = boot;
        armor[1] = leg;
        armor[2] = chest;
        armor[3] = helm;

        p.getInventory().setArmorContents(armor);

        hardener = p;
        new BukkitRunnable(){
            @Override
            public void run() {
                playerdata.get(p.getUniqueId()).setQuirkinState(false);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK,2 ,2);

                p.getInventory().setHelmet(null);
                p.getInventory().setChestplate(null);
                p.getInventory().setLeggings(null);
                p.getInventory().setBoots(null);

                hardener = null;
            }
        }.runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class),200L);
        return true;
    }
    public boolean CastAbility2(Player p) {
        Location loc = p.getLocation();
        Vector pos;
        for(double theta = 0; theta < Math.PI*2; theta += Math.PI/16){
            pos = new Vector(5*Math.sin(theta),0, 5*Math.cos(theta));
            p.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, loc.clone().add(pos),2,0,0,0,0);
        }
        p.getWorld().playSound(loc,Sound.ENTITY_GENERIC_EXPLODE,2,1);
        ArrayList<Entity> target = (ArrayList<Entity>) RaycastUtils.cleanTargetList(p,(ArrayList<Entity>) loc.getNearbyEntities(5,1,5),false);
        for(Entity e : target){
            if(e instanceof LivingEntity) {
                e.setVelocity(new Vector(0, 1, 0));
                ((LivingEntity) e).damage(playerdata.get(p.getUniqueId()).getQuirk().getAbility2Dmg());
            }
        }
        target.clear();

        return true;
    }
    public boolean CastAbility3(Player p) {
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT,2,1);
        p.setVelocity(new Vector(0,1,0).multiply(4));
        new BukkitRunnable(){
            @Override
            public void run() {
                p.setVelocity(new Vector(p.getEyeLocation().getDirection().getX(), 0, p.getEyeLocation().getDirection().getZ()).normalize().multiply(1.2));
            }
        }.runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class),1);
        return true;
    }

    @EventHandler
    public void Damage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (playerdata.get((p).getUniqueId()).getQuirk().getName().equalsIgnoreCase("Hardening")) {
                if (playerdata.get(p.getUniqueId()).isQuirkinState()) {
                    e.setCancelled(true);
                    p.playSound((p).getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 2);
                }
            }else{
                if(p.getNearbyEntities(1.5,1.5,1.5).contains(hardener) && playerdata.get(hardener.getUniqueId()).isQuirkinState() && playerdata.get(p.getUniqueId()).getTeam() == playerdata.get(hardener.getUniqueId()).getTeam()){
                    e.setCancelled(true);
                    p.playSound((p).getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 2);
                }
            }


        }
    }

    @Override
    public boolean isPassive() {
        return passive;
    }

}
