package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.RaycastUtils;
import hadences.reforgedmha.Utility.VectorUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Overhaul extends QuirkCastManager {
    public boolean CastAbility1(Player p) {
       return true;
    }
    public boolean CastAbility2(Player p) {
        return true;
    }
    public boolean CastAbility3(Player p) {
        Location loc = p.getEyeLocation();
        Location block = RaycastUtils.StartRayCastBlock(p, 3, 1);
        Location block2 = block.clone().add(block.getDirection());
        Location block3 = block.clone().subtract(block.getDirection());
        if (block.getBlock().getType() != Material.AIR) {
            block.getWorld().playSound(block, Sound.ENTITY_WITHER_BREAK_BLOCK,2,1);
            block.getWorld().playSound(block, Sound.ENTITY_PLAYER_BREATH,2,2);
            new BukkitRunnable() {
                Vector pos;
                double radius = 0.5;
                ArrayList<hadences.reforgedmha.Quirk.Quirks.environment> environment = new ArrayList<>();
                @Override
                public void run() {
                    if (radius > 3) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (environment e : environment) {
                                    e.getBlock().setType(e.getOriginal_material());
                                }
                            }
                        }.runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class), 120);
                        this.cancel();
                    }
                    for (double theta = 0; theta <= Math.PI * 2; theta += Math.PI / 40) {
                        pos = new Vector(Math.sin(theta) * radius, 0, Math.cos(theta) * radius);
                        pos = VectorUtils.rotateVector(pos, 270, 90);
                        pos = VectorUtils.rotateVector(pos, loc.getYaw(), loc.getPitch());
                        if (block.clone().add(pos).getBlock().getType() != Material.AIR && block.clone().add(pos).getY() >= p.getLocation().getY()) {
                            addBlock(environment, new environment(block.clone().add(pos).getBlock(), block.clone().add(pos).getBlock().getType()));
                            addBlock(environment, new environment(block2.clone().add(pos).getBlock(), block2.clone().add(pos).getBlock().getType()));
                            addBlock(environment, new environment(block3.clone().add(pos).getBlock(), block3.clone().add(pos).getBlock().getType()));

                            block.getWorld().spawnParticle(Particle.SMOKE_NORMAL,block.clone().add(pos),5,0,0,0,0);
                        }
                        //block.getWorld().spawnParticle(Particle.REDSTONE, block.clone().add(pos), 35, 0.1,0.1,0.1, 0.2, new Particle.DustOptions(Color.fromRGB(99, 117, 111), 3f));
                    }
                    radius += 1;
                }
            }.runTaskTimer(ReforgedMHA.getPlugin(ReforgedMHA.class), 0, 1);
        }
        return true;
    }

    public void addBlock(ArrayList<environment> list, environment b){
        if(!list.contains(b)){
            list.add(b);
        }
    }
}

class environment{
    private Block block;
    private Material original_material;
    public environment(Block b, Material og){
        block = b;
        original_material = og;
        b.setType(Material.AIR);
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Material getOriginal_material() {
        return original_material;
    }

    public void setOriginal_material(Material original_material) {
        this.original_material = original_material;
    }
}
