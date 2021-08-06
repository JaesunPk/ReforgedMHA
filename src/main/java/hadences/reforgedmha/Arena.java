package hadences.reforgedmha;

import org.bukkit.Location;

import java.util.HashMap;

public class Arena {
    public static HashMap<String, Arena> ArenaList = new HashMap<>();

    private Location RedSpawn;
    private Location BlueSpawn;
    private Location LobbySpawn;

    private String Name;
    private boolean Finalized;

    public Arena(String name){
        Name = name;
        RedSpawn = null;
        BlueSpawn = null;
        LobbySpawn = null;
        Finalized = false;
    }

    public Arena(String name, Location LSpawn, Location Rspawn, Location Bspawn, boolean finalized){
        Name = name;
        RedSpawn = Rspawn;
        BlueSpawn = Bspawn;
        LobbySpawn = LSpawn;
        Finalized = finalized;

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
