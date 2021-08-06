package hadences.reforgedmha.Quirk;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import static hadences.reforgedmha.PlayerManager.playerdata;

public class Stamina {
    public static boolean checkStamina(Player p, int ability){
        int Stamina = playerdata.get(p.getUniqueId()).getStamina();
        int StaminaReq = 0;
        if(ability == 0){
            StaminaReq = (int) playerdata.get(p.getUniqueId()).getQuirk().getAbility1Stamina();
        }else if(ability == 1){
            StaminaReq = (int) playerdata.get(p.getUniqueId()).getQuirk().getAbility2Stamina();
        }else if(ability == 2){
            StaminaReq = (int) playerdata.get(p.getUniqueId()).getQuirk().getAbility3Stamina();
        }

        if(Stamina < StaminaReq){
            p.sendTitle(" ",ChatColor.RED + "Not Enough Stamina!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1f, 1f);
            return false;
        }

        return true;
    }
}
