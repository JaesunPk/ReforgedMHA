package hadences.reforgedmha.Commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static hadences.reforgedmha.PlayerManager.playerdata;

public class ToggleDev implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You cannot do this!");
        }
        Player p = (Player) sender;
        if(label.equals("dev")){
            if(playerdata.get(p.getUniqueId()).isDevMode() == false){
                playerdata.get(p.getUniqueId()).setDevMode(true);
                p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] "+ ChatColor.WHITE + "Dev Mode :" + ChatColor.GREEN + " ON");
            }else if(playerdata.get(p.getUniqueId()).isDevMode() == true){
                playerdata.get(p.getUniqueId()).setDevMode(false);
                p.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ProjectMHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "] "+ ChatColor.WHITE + "Dev Mode :" + ChatColor.RED + " OFF");
            }
        }
        return false;
    }
}
