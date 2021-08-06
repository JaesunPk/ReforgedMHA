package hadences.reforgedmha.Commands;

import hadences.reforgedmha.ReforgedMHA;
import hadences.reforgedmha.Utility.RayTrace;
import hadences.reforgedmha.Utility.RaycastUtils;
import hadences.reforgedmha.Utility.VectorUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ParticleTester implements CommandExecutor {
    RayTrace rayTrace;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You cannot do this!");
        }
        Player p = (Player) sender;

        if(label.equals("pt")){
            playEffect(p);
        }


        return false;
    }

    public void playEffect(Player p) {
        Location loc = p.getLocation();
        loc.add(loc.getDirection().multiply(2));
        ArrayList<Block> spikes = new ArrayList<>();
        ArrayList<Location> points = getPoints(p);
        for(int i =0; i<3; i++) {
            new BukkitRunnable() {
                Location start_point = points.get((int) (Math.random() * points.size()));
                Location ending_point = RaycastUtils.StartRaycast(p, 22, 0.1);
                int tick = 0;

                @Override
                public void run() {
                    if (ending_point.distance(start_point) < 2) {
                        this.cancel();
                    }
                    Vector v = ending_point.toVector().subtract(start_point.toVector());
                    start_point.add(v.normalize());
                    if (start_point.getBlock().getType() == Material.AIR) {
                        spikes.add(start_point.getBlock());
                        start_point.getBlock().setType(Material.DEEPSLATE);
                    }
                    tick++;
                }
            }.runTaskTimer(ReforgedMHA.getPlugin(ReforgedMHA.class), 0, 0);
        }
        new BukkitRunnable(){
            @Override
            public void run() {
                for(Block b : spikes){
                    b.setType(Material.AIR);
                }
            }
        }.runTaskLater(ReforgedMHA.getPlugin(ReforgedMHA.class),120);
    }

    public ArrayList<Location> getPoints(Player p){
        Vector pos;
        Location loc = p.getEyeLocation();
        ArrayList<Location> points = new ArrayList<>();
        for(double theta = 0; theta < Math.PI*2; theta += Math.PI/6){
            pos = new Vector(Math.sin(theta) * 5, 0, Math.cos(theta) * 5);
            pos = VectorUtils.rotateVector(pos, 270, 90);
            pos = VectorUtils.rotateVector(pos, loc.getYaw(), loc.getPitch());
            if(loc.clone().add(pos).getY() > p.getLocation().getY() && loc.clone().add(pos).getY() < p.getEyeLocation().getY())
                points.add(loc.clone().add(pos));
        }

        return points;
    }

}


