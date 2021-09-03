package hadences.reforgedmha.Utility;

import hadences.reforgedmha.Utility.RayTrace;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static hadences.reforgedmha.PlayerManager.playerdata;

public class RaycastUtils {
    static RayTrace rayTrace;

    public static Location StartRaycast(Player player, double length, double hitbox,boolean includeTeam) {

        rayTrace = new RayTrace(player.getEyeLocation().toVector(), player.getEyeLocation().getDirection());
        ArrayList<Vector> positions = rayTrace.traverse(length, 0.01);

        for (int i = 0; i < positions.size(); i++) {
            Location position = positions.get(i).toLocation(player.getWorld());

            Block block = player.getWorld().getBlockAt(position);


            List<Entity> target = (List<Entity>) position.getNearbyEntities(hitbox, hitbox, hitbox);
            target = cleanTargetList(player,target,includeTeam);
            if (target.size() >= 1) {
                return position;
            }

            if (block != null && block.getType() != Material.AIR) {
                return position;
            }
         if(i == positions.size()-1){
             return position;
         }
        }
        rayTrace = null;
        return null;

    }

    public static Location StartRayCastBlock(Player p,double length,double accuracy){
        rayTrace = new RayTrace(p.getEyeLocation().toVector(), p.getEyeLocation().getDirection());
        ArrayList<Vector> positions = rayTrace.traverse(length, accuracy);
        for (int i = 0; i < positions.size(); i++) {
            Location position = positions.get(i).toLocation(p.getWorld());
            Block block = p.getWorld().getBlockAt(position);
            if (block != null && block.getType() != Material.AIR) {
                return position;
            }
            if(i == positions.size()-1){
                return position;
            }
        }
        return null;
    }

    public static List<Entity> cleanTargetList(Player p, List<Entity> target, boolean includeTeam){
        List<Entity> toRemove = new ArrayList<>();


        if(target.contains(p)) toRemove.add(p);
        if(!target.isEmpty())
        for(Entity e : target){
            if(e instanceof Player){
                if(((Player) e).getGameMode() == GameMode.SPECTATOR || ((Player) e).getGameMode() == GameMode.CREATIVE){
                    toRemove.add(e);
                }
                if(includeTeam == false && playerdata.get(p.getUniqueId()).getTeam() == playerdata.get(e.getUniqueId()).getTeam()){
                    toRemove.add(e);
                }

            }
            if(e.hasMetadata("defendBlue") && playerdata.get(p.getUniqueId()).getTeam().equalsIgnoreCase("Beta")){
                toRemove.add(e);
            }else if(e.hasMetadata("defendRed") && playerdata.get(p.getUniqueId()).getTeam().equalsIgnoreCase("Alpha")){
                toRemove.add(e);
            }


        }

        target.removeAll(toRemove);
        toRemove.clear();

        return target;

    }


    public static double calculateDistance(Location loc1, Location loc2){

        return loc1.distance(loc2);

    }

}
