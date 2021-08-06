package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.Quirk.UniversalSkill;
import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.RayTrace;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;


import static hadences.reforgedmha.PlayerManager.playerdata;

public class Tape extends QuirkCastManager {
    Location loc;
    Location endpoint;
    UniversalSkill universalSkill = new UniversalSkill();
    public boolean CastAbility1(Player p){
        ArrayList<Block> tape_blocks = new ArrayList<>();
        Location loc = p.getEyeLocation();
        Vector dir = loc.getDirection();
        RayTrace rayTrace = new RayTrace(loc.clone().add(loc.getDirection()).toVector(), new Vector(dir.getX(), dir.getY(), dir.getZ()));
        ArrayList<Vector> positions = rayTrace.traverse(12, 1);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SPIDER_AMBIENT,2,2);
        for (Vector v : positions) {
            Location position = v.toLocation(p.getWorld());
            Block block = p.getWorld().getBlockAt(position);
            if (block.getType() == Material.AIR) {
                tape_blocks.add(block);
                block.setType(Material.COBWEB);
                StartTape(p,tape_blocks);
            }
        }
        return true;
    }

    public boolean CastAbility2(Player p){
        universalSkill.Punch(p,playerdata.get(p.getUniqueId()).getQuirk().getAbility2Dmg(),playerdata.get(p.getUniqueId()).getQuirk().getAbility2Effect());
        return true;
    }
    public boolean CastAbility3(Player p){
        Erasure e = new Erasure();
        e.CastAbility3(p);
        return true;
    }

    public void StartTape(Player p,ArrayList<Block> tapes){
        new BukkitRunnable(){
            @Override
            public void run() {
                for(Block b : tapes){b.setType(Material.AIR);} tapes.clear();
            }
        }.runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class),playerdata.get(p.getUniqueId()).getQuirk().getAbility1Effect());
    }
}


