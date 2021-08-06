package hadences.reforgedmha.Quirk;

import org.bukkit.entity.Player;

public class QuirkCastManager {

    private boolean passive = false;

    String type = "Quirkless";

    public void QuirkCastManager(String type) {
        this.type = type;
    }

    public boolean CastAbility1(Player p) {
        if (type.equalsIgnoreCase("Quirkless")) {
            p.sendMessage("You are Quirkless!");
        }
        return false;
    }

    public boolean CastAbility2(Player p) {
        if (type.equalsIgnoreCase("Quirkless")) {
            p.sendMessage("You are Quirkless!");
        }
        return false;
    }

    public boolean CastAbility3(Player p) {
        if (type.equalsIgnoreCase("Quirkless")) {
            p.sendMessage("You are Quirkless!");
        }
        return false;
    }

    public boolean isPassive() {
        return passive;
    }

    public void setPassive(boolean passive) {
        this.passive = passive;
    }
}

