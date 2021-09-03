package hadences.reforgedmha;

import hadences.reforgedmha.Quirk.Quirk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static hadences.reforgedmha.Quirk.Quirk.quirklist;

public class PlayerManager {

    public static HashMap<UUID, PlayerManager> playerdata = new HashMap<>();
    private HashMap<Integer, String> pastPlayerDataBoard = new HashMap<>();
    private HashMap<Integer,String> pastinGameBoard = new HashMap<>();
    private HashMap<Integer,String> pastLobbyBoard = new HashMap<>();

    private Player player;
    //Player Information
    private String name;
    private String rank;
    private int wins;
    private boolean devMode;
    private String world;
    private int credit;
    private UUID uuid;
    private GameManager Game;

    //QuirkBased
    private ArrayList<Entity> QuirkTaggedEntities;
    private Entity QuirkTaggedEntity;
    private int QuirkStorage;
    private boolean QuirkinState;

    //Game Variables
    private boolean inGame;
    private boolean isAlive;
    private int stamina;
    private boolean isReady;
    private String team;
    private boolean inLobby;
    private boolean allowSkill;
    private Quirk quirk;
    private boolean restrictMovement;
    private ArrayList<Character> Combo;
    private boolean FallDamage;

    private HashMap<String,BukkitTask> runnables = new HashMap<>();

    public PlayerManager(Player p, String rank, int credit, int wins){
        this.name = p.getName();
        this.rank = rank;
        this.wins = wins;
        devMode = false;
        world = "LOBBY";
        uuid = p.getUniqueId();
        credit = credit;
        inGame = false;
        isAlive = false;
        stamina = 0;
        quirk = Quirk.getQuirk("Quirkless");
        isReady = false;
        team = "NONE";
        inLobby = false;
        player = p;
        allowSkill = false;
        restrictMovement = false;
        QuirkTaggedEntities = new ArrayList<>();
        QuirkTaggedEntity = null;
        QuirkStorage = 0;
        QuirkinState = false;
        Combo = new ArrayList<>();
        FallDamage = true;

    }

    public static void FixQuirkSchedulers(Player p, String key,BukkitTask sched){
        HashMap<String,BukkitTask> runnables = playerdata.get(p.getUniqueId()).getRunnables();
        if(runnables.containsKey(key)){
            runnables.get(key).cancel();
            runnables.remove(key);
        }
        runnables.put(key,sched);
    }

    public boolean isFallDamage() {
        return FallDamage;
    }

    public void setFallDamage(boolean fallDamage) {
        FallDamage = fallDamage;
    }

    public ArrayList<Character> getCombo() {
        return Combo;
    }

    public void setCombo(ArrayList<Character> combo) {
        Combo = combo;
    }

    public static HashMap<UUID, PlayerManager> getPlayerdata() {
        return playerdata;
    }

    public static void setPlayerdata(HashMap<UUID, PlayerManager> playerdata) {
        PlayerManager.playerdata = playerdata;
    }

    public HashMap<String,BukkitTask> getRunnables() {
        return runnables;
    }

    public void setRunnables(HashMap<String,BukkitTask> runnables) {
        this.runnables = runnables;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isQuirkinState() {
        return QuirkinState;
    }

    public void setQuirkinState(boolean quirkinState) {
        QuirkinState = quirkinState;
    }

    public ArrayList<Entity> getQuirkTaggedEntities() {
        return QuirkTaggedEntities;
    }

    public void setQuirkTaggedEntities(ArrayList<Entity> quirkTaggedEntities) {
        QuirkTaggedEntities = quirkTaggedEntities;
    }

    public Entity getQuirkTaggedEntity() {
        return QuirkTaggedEntity;
    }

    public void setQuirkTaggedEntity(Entity quirkTaggedEntity) {
        QuirkTaggedEntity = quirkTaggedEntity;
    }

    public int getQuirkStorage() {
        return QuirkStorage;
    }

    public void setQuirkStorage(int quirkStorage) {
        QuirkStorage = quirkStorage;
    }

    public GameManager getGame() {
        return Game;
    }

    public void setGame(GameManager game) {
        Game = game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public boolean isDevMode() {
        return devMode;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public boolean isInLobby() {
        return inLobby;
    }

    public void setInLobby(boolean inLobby) {
        this.inLobby = inLobby;
    }

    public boolean isAllowSkill() {
        return allowSkill;
    }

    public void setAllowSkill(boolean allowSkill) {
        this.allowSkill = allowSkill;
    }

    public Quirk getQuirk() {
        return quirk;
    }

    public void setQuirk(Quirk quirk) {
        this.quirk = quirk;
    }

    public boolean isRestrictMovement() {
        return restrictMovement;
    }

    public void setRestrictMovement(boolean restrictMovement) {
        this.restrictMovement = restrictMovement;
    }

    public HashMap<Integer, String> getPastPlayerDataBoard() {
        return pastPlayerDataBoard;
    }

    public void setPastPlayerDataBoard(HashMap<Integer, String> pastPlayerDataBoard) {
        this.pastPlayerDataBoard = pastPlayerDataBoard;
    }



    public HashMap<Integer, String> getPastinGameBoard() {
        return pastinGameBoard;
    }

    public void setPastinGameBoard(HashMap<Integer, String> pastinGameBoard) {
        this.pastinGameBoard = pastinGameBoard;
    }

    public HashMap<Integer, String> getPastLobbyBoard() {
        return pastLobbyBoard;
    }

    public void setPastLobbyBoard(HashMap<Integer, String> pastLobbyBoard) {
        this.pastLobbyBoard = pastLobbyBoard;
    }
}
