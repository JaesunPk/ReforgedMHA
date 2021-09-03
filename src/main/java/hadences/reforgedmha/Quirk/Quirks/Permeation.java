package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.Quirk.UniversalSkill;
import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.RaycastUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static hadences.reforgedmha.PlayerManager.playerdata;

public class Permeation extends QuirkCastManager {
    ReforgedMHA plugin = ReforgedMHA.getPlugin(ReforgedMHA.class);
    UniversalSkill universalSkill = new UniversalSkill();
    public boolean CastAbility1(Player p){
        universalSkill.Punch(p,playerdata.get(p.getUniqueId()).getQuirk().getAbility1Dmg(),playerdata.get(p.getUniqueId()).getQuirk().getAbility1Effect());
        return true;
    }
    public boolean CastAbility2(Player p){
        universalSkill.Punch(p,playerdata.get(p.getUniqueId()).getQuirk().getAbility2Dmg(),playerdata.get(p.getUniqueId()).getQuirk().getAbility2Effect());
        //p.sendMessage(playerdata.get(p.getUniqueId()).getQuirk().getAbility2Effect() + "");
        return true;
    }
    public boolean CastAbility3(Player p){

        return StartPhase(p);
    }

    public boolean StartPhase(Player p){
        //if(!isOnGround(p)) return false;
        if(!isPhasingBlock(p)) {
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 2.0f,2.0f);
            p.sendMessage(ChatColor.RED + "You are not facing a block!");
            return false;
        }
        playSound(p);
        p.setGameMode(GameMode.SPECTATOR);
        p.setVelocity(p.getEyeLocation().getDirection().normalize().multiply(0.95));
        new BukkitRunnable(){
            Location phaseLoc;
            int time =0;
            boolean unphase = false;
            @Override
            public void run() {
                if(time == 6){
                    phaseLoc = p.getEyeLocation();
                }
                if(time >= 6 && (int) p.getEyeLocation().getY() != (int) phaseLoc.getY()){
                    unphase = true;
                }
                if ((time >= 5 && !p.getLocation().getBlock().isSolid())) {
                    p.setInvulnerable(true);
                    p.setGameMode(GameMode.ADVENTURE);
                    playSound(p);
                    p.setInvulnerable(false);
                    this.cancel();
                }
                if(time >= 20 && p.getGameMode() == GameMode.ADVENTURE){
                    playSound(p);
                    this.cancel();
                }
                if(time >= 20 || unphase){
                    UnPhase(p);
                }
                time++;
            }

        }.runTaskTimer(plugin,0,0);
        return true;
    }

    public static void playSound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 2.0f,2.0f);
    }

    public static void UnPhase(Player p){
        if (p.getEyeLocation().getBlock().isSolid() || p.getLocation().getBlock().isSolid()) {
            p.setVelocity(new Vector(0, 1, 0).normalize().multiply(0.75));
        }
    }

    public static boolean isPhasingBlock(Player p){
        Location endpoint = RaycastUtils.StartRaycast(p,3,0.2,false);
        if(endpoint.getBlock().isSolid() && endpoint.getBlock().getType() != Material.BARRIER){
            return true;
        }
        return false;
    }

    public static boolean isOnGround(Player p){
        return p.isOnGround();
    }
}
