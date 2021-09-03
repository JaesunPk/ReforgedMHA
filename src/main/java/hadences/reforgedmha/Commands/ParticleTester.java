package hadences.reforgedmha.Commands;

import hadences.reforgedmha.Utility.RayTrace;
import hadences.reforgedmha.Utility.VectorUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;


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
        Location location = p.getEyeLocation();
        Vector vector;
        Vector vector1;
        Vector vector2;
        //double y = -0.35;
        double y = 0;

        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 25) {
            vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0.8 * Math.sin(theta), 0.5, 0.8 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(245, 238, 78), 1.2f));

            vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0.8 * Math.sin(theta), 1.5, 0.8 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(245, 238, 78), 1.2f));
        }
        for(double time = 0; time < 4.72; time+=0.1){
            vector = VectorUtils.rotateVector(new Vector(time,y,Math.cos(2*time)),location.getYaw(),location.getPitch());
            vector1 = VectorUtils.rotateVector(new Vector(time,y,Math.cos(2*time)),location.getYaw(),location.getPitch());
            vector2 = VectorUtils.rotateVector(new Vector(time,y,0),location.getYaw(),location.getPitch());

            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(245, 238, 78), 1.2f));
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector1), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(245, 238, 78), 1.2f));
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector2), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(250, 255, 153), 1.2f));
        }
    }


}


