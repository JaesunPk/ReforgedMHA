package hadences.reforgedmha;

import org.bukkit.Location;

import java.util.HashMap;

public class Arena {
    public static HashMap<String, Arena> ArenaList = new HashMap<>();

    private Location RedSpawn;
    private Location BlueSpawn;
    private Location LobbySpawn;
    private Location RedObjective;
    private Location BlueObjective;
    private String Name;
    private boolean Finalized;

    public Arena(String name){
        Name = name;
        RedSpawn = null;
        BlueSpawn = null;
        LobbySpawn = null;
        Finalized = false;
        RedObjective = null;
        BlueObjective = null;
    }

    public Arena(String name, Location LSpawn, Location Rspawn, Location Bspawn, boolean finalized, Location BO, Location RO){
        Name = name;
        RedSpawn = Rspawn;
        BlueSpawn = Bspawn;
        LobbySpawn = LSpawn;
        Finalized = finalized;
        RedObjective = RO;
        BlueObjective = BO;

    }

    public Location getRedObjective() {
        return RedObjective;
    }

    public void setRedObjective(Location redObjective) {
        RedObjective = redObjective;
    }

    public Location getBlueObjective() {
        return BlueObjective;
    }

    public void setBlueObjective(Location blueObjective) {
        BlueObjective = blueObjective;
    }

    public void setFinalized(boolean finalized) {
        Finalized = finalized;
    }

    public boolean isFinalized() {
        return Finalized;
    }

    public String getName() {
        return Name;
    }

    public Location getBlueSpawn() {
        return BlueSpawn;
    }

    public Location getLobbySpawn() {
        return LobbySpawn;
    }

    public Location getRedSpawn() {
        return RedSpawn;
    }

    public void setBlueSpawn(Location blueSpawn) {
        BlueSpawn = blueSpawn;
    }

    public void setLobbySpawn(Location lobbySpawn) {
        LobbySpawn = lobbySpawn;
    }

    public void setRedSpawn(Location redSpawn) {
        RedSpawn = redSpawn;
    }

    public void setName(String name) {
        Name = name;
    }
}
