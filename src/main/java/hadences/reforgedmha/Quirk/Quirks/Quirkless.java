package hadences.reforgedmha.Quirk.Quirks;

import hadences.reforgedmha.Quirk.QuirkCastManager;
import org.bukkit.entity.Player;

public class Quirkless extends QuirkCastManager {

    public boolean CastAbility1(Player p){
        p.sendMessage("Quirkless 1");
        return false;
    }
    public boolean CastAbility2(Player p){
        p.sendMessage("Quirkless 2");
        return false;
    }
    public boolean CastAbility3(Player p){
        p.sendMessage("Quirkless 3");
        return false;
    }
}
