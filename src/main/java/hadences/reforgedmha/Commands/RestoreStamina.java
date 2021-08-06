package hadences.reforgedmha.Commands;

import hadences.reforgedmha.ReforgedMHA;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static hadences.reforgedmha.PlayerManager.playerdata;

public class RestoreStamina implements CommandExecutor {

    ReforgedMHA plugin = ReforgedMHA.getPlugin(ReforgedMHA.class);
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You cannot do this!");
        }
        Player p = (Player) sender;

        if(label.equals("rstamina")){
            for(Player pl : Bukkit.getOnlinePlayers()){
                pl.setHealth((Integer) plugin.getConfig().get("Game.Settings.StartingHealth"));
                playerdata.get(pl.getUniqueId()).setStamina((Integer) plugin.getConfig().get("Game.Settings.StartingStamina"));
            }
        }


        return false;
    }
}
