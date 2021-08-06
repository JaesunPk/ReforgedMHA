package hadences.reforgedmha.Commands;


import hadences.reforgedmha.Arena;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static hadences.reforgedmha.Arena.ArenaList;


public class MHAarena implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You cannot do this!");
            return true;
        }

        Player p = ((Player) sender);
        if(label.equalsIgnoreCase("MHAarena")){

            // /MHAarena
            if(args.length == 0){
                p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] "+ ChatColor.RED + "Usage: /MHAarena <command>");
                return true;
            }

            // /MHAarena create
            if(args.length == 1 && args[0].equalsIgnoreCase("create")){
                p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] "+ ChatColor.RED + "Specify the name of the arena! Usage: /MHAarena create <name>");
                return true;
            }

            // /MHAarena create <Name>
            if(args.length == 2 && args[0].equalsIgnoreCase("create")){
                if(ArenaNameinList(args[1])){
                    p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] "+ ChatColor.RED + "This Arena Name is Taken!");
                    return true;
                }else {
                    p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] " + ChatColor.WHITE + "Arena " + args[1] + " created!");
                    createArena(args[1]);
                    return true;
                }

            }

            // /MHAarena List
            if(args.length == 1 && args[0].equalsIgnoreCase("list")){
                p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] "+ ChatColor.GREEN + getArenaList());
                return true;
            }

            // /MHAarena get <name>
            if((args.length == 1 && args[0].equalsIgnoreCase("get")) || (args.length == 2 && ArenaNameinList(args[1]))){
                p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] "+ ChatColor.RED + "/MHAarena get <name> setLobbySpawn/setRedSpawn/setBlueSpawn/Finalize");
                return true;
            }

            if(args.length == 3 && args[0].equalsIgnoreCase("get") && ArenaNameinList(args[1])){
                // /MHAarena get <name> setLobbySpawn
                if(args[2].equalsIgnoreCase("setLobbySpawn")) {
                    ArenaList.get(args[1]).setLobbySpawn(p.getLocation());
                    p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] " + ChatColor.GREEN + "Lobby Spawn Set for Arena " + args[1]);
                    return true;
                }
                // /MHAarena get <name> setRedSpawn
                if(args[2].equalsIgnoreCase("setRedSpawn")) {
                    p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] "+ ChatColor.GREEN + "Red Spawn Set for Arena " + args[1]);
                    ArenaList.get(args[1]).setRedSpawn(p.getLocation());
                    return true;
                }
                // /MHAarena get <name> setBlueSpawn
                if(args[2].equalsIgnoreCase("setBlueSpawn")){
                    ArenaList.get(args[1]).setBlueSpawn(p.getLocation());
                    p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] "+ ChatColor.GREEN + "Blue Spawn Set for Arena " + args[1]);
                    return true;
                }
                // /MHAarena get <name> finalize
                if(args[2].equalsIgnoreCase("finalize")){
                    if(ArenaList.get(args[1]).getBlueSpawn() == null || ArenaList.get(args[1]).getRedSpawn() == null || ArenaList.get(args[1]).getLobbySpawn() == null ){
                        p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] "+ ChatColor.RED + "Arena Cannot be finalized for " + args[1]);
                        return true;
                    }else {
                        ArenaList.get(args[1]).setFinalized(true);
                        p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] " + ChatColor.GREEN + "Arena is finalized!");
                        return true;
                    }
                }

            }else{
                p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] "+ ChatColor.RED + "Arena does not exist!");
                    return true;
            }



        }



        return false;
    }

    public boolean ArenaNameinList(String name) {
        if(ArenaList.isEmpty()){
            return false;
        }
        for(String key : ArenaList.keySet()){
            if(ArenaList.get(key).getName().equals(name)){
                return true;
            }
        }


        return false;
    }

    public void createArena(String name){
        //Create Arena
        ArenaList.put(name,new Arena(name));
    }

    public String getArenaList(){
        String list = "";
        if(ArenaList.isEmpty()){
            return ChatColor.GREEN + " So Empty...";
        }

        for(String key : ArenaList.keySet()){
            list += ArenaList.get(key).getName() + ", ";
        }


        return list;
    }

}
