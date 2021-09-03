package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.Utility.RaycastUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static hadences.reforgedmha.PlayerManager.playerdata;

public class Smokescreen extends QuirkCastManager {

    //Toggle on/off
    /*
    On Mode :
    Ability 1 - Smokescreen - Full Blast

    Ability 2 - Smokescreen - Smoke Shot

    Off Mode :

    Ability 1 - Punch

    Ability 2 - Smoke Leap
     */


    public boolean CastAbility1(Player p){
        if(playerdata.get(p.getUniqueId()).isQuirkinState()){
            CastAbility1Smoke(p);
        }else{
            CastAbility1Physical(p);
        }

        return false;
    }

    public boolean CastAbility2(Player p){
        //Smoke Mode
        if(playerdata.get(p.getUniqueId()).isQuirkinState()){
            CastAbility2Smoke(p);
        }else{
            CastAbility2Physical(p);
        }
        return false;
    }
    public boolean CastAbility3(Player p){
        //Toggle on

        //off
        if(playerdata.get(p.getUniqueId()).isQuirkinState()){
            p.sendTitle(" ", ChatColor.GREEN +"Mode : " + ChatColor.WHITE + "Physical");
            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK,0.5f,2f);
            playerdata.get(p.getUniqueId()).setQuirkinState(false);
        }else{
            p.sendTitle(" ", ChatColor.GREEN +"Mode : " + ChatColor.GRAY + "Smoke");
            playerdata.get(p.getUniqueId()).setQuirkinState(true);
            p.playSound(p.getLocation(),Sound.BLOCK_FIRE_EXTINGUISH,1,1);
        }




        return false;
    }

    public void CastAbility2Smoke(Player p){
    }

    public void CastAbility2Physical(Player p){

    }

    public void CastAbility1Smoke(Player p){

    }

    public void CastAbility1Physical(Player p){

    }
}


