package hadences.reforgedmha;

import hadences.reforgedmha.GUI.GamemodeMenu;
import hadences.reforgedmha.Quirk.Quirk;
import hadences.reforgedmha.Quirk.Quirks.Engine;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static hadences.reforgedmha.Arena.ArenaList;
import static hadences.reforgedmha.GUI.Events.GUIEvent.QuirkEvents;
import static hadences.reforgedmha.GUI.Events.GUIEvent.createClassInfo;
import static hadences.reforgedmha.PlayerManager.playerdata;
import static hadences.reforgedmha.Quirk.Quirk.quirklist;
import static org.bukkit.Bukkit.getServer;

public class GameManager {
    ReforgedMHA plugin = ReforgedMHA.getPlugin(ReforgedMHA.class);
    Scoreboard board = plugin.board.getBoard();
    private int PlayersNeeded;
    private ArrayList<Player> PlayerList;

    private int RedTeamAlive;
    private int BlueTeamAlive;
    private int PlayersInGame;
    private String MapID;
    private boolean lobby;

    //Lobby
    private int RedTeamPlayers;
    private int BlueTeamPlayers;
    private int NoTeamPlayers;

    private boolean startGame;

    private int seconds;
    private int minutes;
    private Arena arena;
    private String Gamemode;
    private int startingStamina;
    private int startingHealth;

    public GameManager(String mapID, int minutes, int seconds, int startingHealth, int startingStamina){
        PlayersNeeded = 1;
        PlayerList = new ArrayList<>();
        RedTeamAlive = 0;
        BlueTeamAlive = 0;
        PlayersInGame = 0;
        startGame = false;
        RedTeamPlayers = 0;
        BlueTeamPlayers = 0;
        NoTeamPlayers = 0;
        MapID = mapID;
        lobby = true;
        this.seconds = seconds;
        this.minutes = minutes;
        arena = ArenaList.get(mapID);
        this.startingHealth = startingHealth;
        this.startingStamina = startingStamina;
        Gamemode = "None";
    }

    public void addPlayersToGameList(Collection<? extends Player> playersList){
        for(Player p : playersList){
            PlayerList.add(p);
        }
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public String getGamemode() {
        return Gamemode;
    }

    public void setGamemode(String gamemode) {
        Gamemode = gamemode;
    }

    public int getStartingStamina() {
        return startingStamina;
    }

    public void setStartingStamina(int startingStamina) {
        this.startingStamina = startingStamina;
    }

    public int getStartingHealth() {
        return startingHealth;
    }

    public void setStartingHealth(int startingHealth) {
        this.startingHealth = startingHealth;
    }

    public int getPlayersNeeded() {
        return PlayersNeeded;
    }

    public void setPlayersNeeded(int playersNeeded) {
        PlayersNeeded = playersNeeded;
    }

    public ArrayList<Player> getPlayerList() {
        return PlayerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        PlayerList = playerList;
    }

    public int getRedTeamAlive() {
        return RedTeamAlive;
    }

    public void setRedTeamAlive(int redTeamAlive) {
        RedTeamAlive = redTeamAlive;
    }

    public int getBlueTeamAlive() {
        return BlueTeamAlive;
    }

    public void setBlueTeamAlive(int blueTeamAlive) {
        BlueTeamAlive = blueTeamAlive;
    }

    public int getPlayersInGame() {
        return PlayersInGame;
    }

    public void setPlayersInGame(int playersInGame) {
        PlayersInGame = playersInGame;
    }

    public String getMapID() {
        return MapID;
    }

    public void setMapID(String mapID) {
        MapID = mapID;
    }

    public boolean isLobby() {
        return lobby;
    }

    public void setLobby(boolean lobby) {
        this.lobby = lobby;
    }

    public int getRedTeamPlayers() { return RedTeamPlayers; }

    public void setRedTeamPlayers(int redTeamPlayers) {
        RedTeamPlayers = redTeamPlayers;
    }

    public int getBlueTeamPlayers() {
        return BlueTeamPlayers;
    }

    public void setBlueTeamPlayers(int blueTeamPlayers) {
        BlueTeamPlayers = blueTeamPlayers;
    }

    public int getNoTeamPlayers() {
        return NoTeamPlayers;
    }

    public void setNoTeamPlayers(int noTeamPlayers) {
        NoTeamPlayers = noTeamPlayers;
    }

    public boolean isStartGame() {
        return startGame;
    }

    public void setStartGame(boolean startGame) {
        this.startGame = startGame;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void LobbyInitialize(String Arena){
        //teleport players to lobby
        //change status of players to <inGame> and <inLobby>
        for(Player p : PlayerList){ p.teleport(ArenaList.get(Arena).getLobbySpawn()); playerdata.get(p.getUniqueId()).setInGame(true); playerdata.get(p.getUniqueId()).setInLobby(true); }
    }

    public void initiate(){
        new BukkitRunnable(){
            @Override
            public void run() {
                plugin.loop();
                if(!startGame && lobby) {
                    //Lobby Methods
                    //check if all players ready up
                    if(checkAllPlayersReady() && getPlayerList().size() >= getPlayersNeeded() && NoTeamPlayers <= 0){ startgame(); }
                    //update teams
                    updateTeams(board);
                    //update lobby scoreboard
                    updateLobbyScoreboard(plugin.board);
                }else if(startGame && !lobby){
                    //Team vs Team Mode
                    if(Gamemode.equalsIgnoreCase("TVT")) {
                        if (TvTGame()) {
                            this.cancel();
                            Winner("null");
                        }

                        if(RedTeamAlive == 0 && BlueTeamAlive == 0){
                            this.cancel();
                            Winner("DRAW");
                        }

                    }
                }
            }
        }.runTaskTimer(plugin,0,20L);
    }

    public boolean TvTGame(){
        //update alive players
        updateAlivePlayers(board);
        //update game scoreboard
        updateGameScoreboard(plugin.board);
        //update timer
        updateTimer("-");
        //check timer
        checkTimerTVT();

        //check who wins
        if(checkifWinner(RedTeamAlive,BlueTeamAlive)){
            return true;
        }
        return false;
    }

    public void registerEvents(){
        for(Player p : getPlayerList()){
            if(playerdata.get(p.getUniqueId()).getQuirk().getQuirkCastManager().isPassive()){
                if(!QuirkEvents.containsKey(playerdata.get(p.getUniqueId()).getQuirk().getName())) {
                    QuirkEvents.put(playerdata.get(p.getUniqueId()).getQuirk().getName(), (Listener) playerdata.get(p.getUniqueId()).getQuirk().getQuirkCastManager());
                    getServer().getPluginManager().registerEvents((Listener) playerdata.get(p.getUniqueId()).getQuirk().getQuirkCastManager(), ReforgedMHA.getPlugin(ReforgedMHA.class));

                }
            }
        }
    }

    public void unregisterEvents(){

        for(Object l : QuirkEvents.values()) {
            HandlerList.unregisterAll((Listener) l);
        }
        QuirkEvents.clear();
    }

    public void endgame(){

        setStartGame(false);
        unregisterEvents();
        for(Player p : getPlayerList()){
            playerdata.get(p.getUniqueId()).setAlive(false);
            playerdata.get(p.getUniqueId()).setInGame(false);
            playerdata.get(p.getUniqueId()).setReady(false);
            p.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());

            p.setHealth(20);
            p.setMaxHealth(20);
            p.setWalkSpeed(0.2f);
            p.setFlySpeed(0.1f);
            p.setFoodLevel(20);
            playerdata.get(p.getUniqueId()).setQuirk(Quirk.getQuirk("Quirkless"));
            playerdata.get(p.getUniqueId()).setStamina(0);

            playerdata.get(p.getUniqueId()).setAllowSkill(false);

            p.getInventory().setItem(8, MainEvent.getMenuItem());
            p.setGameMode(GameMode.ADVENTURE);
            //playerdata.get(p.getUniqueId()).setTaggedEntity(null);
            if(playerdata.get(p.getUniqueId()).getTeam().equalsIgnoreCase("Alpha"))
                board.getTeam("Alpha").removePlayer(p);
            if(playerdata.get(p.getUniqueId()).getTeam().equalsIgnoreCase("Beta"))
                board.getTeam("Beta").removePlayer(p);
            p.setDisplayName( ChatColor.LIGHT_PURPLE + "" + playerdata.get(p.getUniqueId()).getWins() + " " + ChatColor.GOLD + "✪ " + ChatColor.WHITE + p.getName());

            playerdata.get(p.getUniqueId()).setTeam("NONE");
        }
        getPlayerList().clear();

    }

    public boolean checkifWinner(int RedTeamAlive, int BlueTeamAlive){
        if(RedTeamAlive <= 0 && BlueTeamAlive <=0){
            return true;
        }else if(RedTeamAlive <= 0 && BlueTeamAlive > 0){
            return true;
        }else return RedTeamAlive > 0 && BlueTeamAlive <= 0;
    }

    public void Winner(String win){
        String winner = win;

        if(RedTeamAlive <= 0 && BlueTeamAlive <=0){
            winner = "DRAW";
        }else if(RedTeamAlive <= 0 && BlueTeamAlive > 0){
            winner = "BETA";
        }else if(RedTeamAlive > 0 && BlueTeamAlive <= 0){
            winner = "ALPHA";
        }

        if(winner.equalsIgnoreCase("DRAW")){
            sendTitletoAll(ChatColor.GOLD + "" + ChatColor.BOLD + "NO CONTEST",ChatColor.AQUA + "No Winner!");
        }else if(winner.equalsIgnoreCase("BETA")){
            grantWins(winner);
            sendTitletoAll(ChatColor.BLUE + "" + ChatColor.BOLD + "BLUE WINS",ChatColor.GREEN + "Congrats!");
        }else if(winner.equalsIgnoreCase("ALPHA")){
            grantWins(winner);
            sendTitletoAll(ChatColor.RED + "" + ChatColor.BOLD + "RED WINS",ChatColor.GREEN + "Congrats!");
        }

        if(!winner.equalsIgnoreCase("null")) {
            for (Player p : getPlayerList()) {
                final Firework f = p.getWorld().spawn(p.getLocation(), Firework.class);
                FireworkMeta fm = f.getFireworkMeta();
                if(winner.equalsIgnoreCase("ALPHA")) {
                    fm.addEffect(FireworkEffect.builder()
                            .flicker(true)
                            .trail(true)
                            .with(FireworkEffect.Type.STAR)
                            .with(FireworkEffect.Type.BALL)
                            .with(FireworkEffect.Type.BALL_LARGE)
                            .withColor(Color.RED)
                            .build());
                }else if(winner.equalsIgnoreCase("BETA")) {
                    fm.addEffect(FireworkEffect.builder()
                            .flicker(true)
                            .trail(true)
                            .with(FireworkEffect.Type.STAR)
                            .with(FireworkEffect.Type.BALL)
                            .with(FireworkEffect.Type.BALL_LARGE)
                            .withColor(Color.BLUE)
                            .build());
                }
                fm.setPower(0);
                f.setFireworkMeta(fm);
                p.playSound(p.getLocation(),Sound.ENTITY_ENDER_DRAGON_GROWL,5f,2);

                p.getInventory().clear();
                p.setGameMode(GameMode.SPECTATOR);
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    endgame();
                    this.cancel();
                }
            }.runTaskLater(plugin, 100L);
        }
    }

    public void grantWins(String winner){
        for(Player p : getPlayerList()){
            if(playerdata.get(p.getUniqueId()).getTeam().equalsIgnoreCase(winner)){
                playerdata.get(p.getUniqueId()).setWins(playerdata.get(p.getUniqueId()).getWins() + 1);
                plugin.board.updatePlayerDataBoard(board);
            }
        }
    }

    public void checkTimerTVT() {
        if(minutes == 2 && seconds == 0){
            for(Player p : PlayerList){
                p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,60,2,false,false));
            }
        }

        if (minutes == 0 && seconds == 30) {
            sendMessageinGamePlayers(ChatColor.GREEN + "30 seconds left!");
            playsoundtoAll(Sound.ENTITY_WITHER_BREAK_BLOCK,1.0f);
            TeleportToStalemate(getPlayerList(), MapID);
        } else if (minutes == 0 && seconds <= 5 && seconds != 0) {
            sendMessageinGamePlayers(ChatColor.GREEN + String.valueOf(seconds) + " seconds left!");
        } else if (minutes == 0 && seconds == 0) {
            playsoundtoAll(Sound.ENTITY_ENDER_DRAGON_GROWL,1.0f);
            sendMessageinGamePlayers(ChatColor.GREEN + "Force End!");

            if (RedTeamAlive > BlueTeamAlive) {
                BlueTeamAlive = 0;
            } else if (RedTeamAlive < BlueTeamAlive) {
                RedTeamAlive = 0;
            } else {
                RedTeamAlive = 0;
                BlueTeamAlive = 0;
            }
        }
    }

    public void updateTimer(String mode){
        if(mode.equalsIgnoreCase("+")) {
            seconds = seconds + 1;
            if (seconds == 60) {
                minutes = minutes + 1;
                seconds = 0;
            }
        }else if(mode.equalsIgnoreCase("-")){
            if(seconds <= 0 && minutes >= 1){
                minutes--;
                seconds = 59;
            }else if(seconds <= 0 && minutes == 0){
                seconds = 0;
            }
            seconds--;
        }
    }

    public void updateGameScoreboard(scoreboard board){
        for(Player p : PlayerList) { board.updateGameScoreboard(p,board.getBoard(),BlueTeamAlive,RedTeamAlive,minutes,seconds); }
    }

    public void updateAlivePlayers(Scoreboard board){
        RedTeamAlive = 0;
        BlueTeamAlive = 0;
        PlayersInGame = getPlayerList().size();
        for(Player p : getPlayerList()){
            if(board.getTeam("Alpha").getPlayers().isEmpty() && board.getTeam("Beta").getPlayers().isEmpty()){
                RedTeamAlive = 0;
                BlueTeamAlive = 0;
            }else if(playerdata.get(p.getUniqueId()).isAlive() == true && board.getTeam("Alpha").getPlayers().contains(p)){
                RedTeamAlive++;
            }else if(playerdata.get(p.getUniqueId()).isAlive() == true && board.getTeam("Beta").getPlayers().contains(p)){
                BlueTeamAlive++;
            }
        }
    }

    public boolean checkAllPlayersReady(){
        for(Player p : PlayerList){
            if(!playerdata.get(p.getUniqueId()).isReady())
                return false;
        }
        return true;
    }

    public void updateTeams(Scoreboard board){
        NoTeamPlayers = PlayerList.size();
        try{
            RedTeamPlayers = board.getTeam("Alpha").getPlayers().size();
            BlueTeamPlayers = board.getTeam("Beta").getPlayers().size();
            NoTeamPlayers = NoTeamPlayers - (RedTeamPlayers + BlueTeamPlayers);
        }catch (Exception e){
        }
    }

    public void updateLobbyScoreboard(scoreboard board){
        for(Player p : PlayerList) { board.updateLobbyScoreboard(p,board.getBoard(),BlueTeamPlayers,RedTeamPlayers,NoTeamPlayers,checkAllPlayersReady()); }
    }

    public void initializeStart(){
        for(Player p : PlayerList){
            playerdata.get(p.getUniqueId()).setInLobby(false);
            playerdata.get(p.getUniqueId()).setAlive(true);
            playerdata.get(p.getUniqueId()).setRestrictMovement(true);

            if(playerdata.get(p.getUniqueId()).getTeam().equalsIgnoreCase("Alpha")){
                p.teleport(arena.getRedSpawn());
            }else
                p.teleport(arena.getBlueSpawn());
        }
    }

    public void start(){
        new BukkitRunnable(){
            int time = 0;
            @Override
            public void run() {
                if(time == 6){
                    initializeStart();
                    start_after();
                    registerEvents();
                    this.cancel();
                }
                if(time == 0){
                    lobby = false;
                    Bukkit.broadcastMessage(ChatColor.WHITE + "Game Starting in " + ChatColor.GREEN + "5");
                }if(time == 1){
                    Bukkit.broadcastMessage(ChatColor.WHITE + "Game Starting in " + ChatColor.GREEN + "4");
                }if(time == 2){
                    Bukkit.broadcastMessage(ChatColor.WHITE + "Game Starting in " + ChatColor.GREEN + "3");
                }if(time == 3){
                    Bukkit.broadcastMessage(ChatColor.WHITE + "Game Starting in " + ChatColor.GREEN + "2");
                }if(time == 4){
                    Bukkit.broadcastMessage(ChatColor.WHITE + "Game Starting in " + ChatColor.GREEN + "1");
                }if(time == 5) {
                    Bukkit.broadcastMessage(ChatColor.WHITE + "Game Starting in " + ChatColor.GREEN + "Starting!");
                }
                playsoundtoAll(Sound.BLOCK_DISPENSER_DISPENSE,2.0f);

                time++;
            }
        }.runTaskTimer(plugin,0,20l);
    }

    public void start_after(){
        new BukkitRunnable(){
            int time = 0;
            @Override
            public void run() {
                if(time == 1){
                    sendTitleToAll("3");
                    playsoundtoAll(Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f);
                }if(time == 2){
                    sendTitleToAll("2");
                    playsoundtoAll(Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f);
                }if(time == 3){
                    sendTitleToAll("1");
                    playsoundtoAll(Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f);
                }if(time == 4){
                    sendTitleToAll("START");
                    playsoundtoAll(Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f);
                    startToAll();
                    setStartGame(true);
                    this.cancel();
                }
                time++;
            }
        }.runTaskTimer(plugin,0,20l);
    }

    public void startToAll(){
        ItemStack item_skill = new ItemStack(Material.LIME_DYE);
        ItemMeta meta = item_skill.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        for(Player p : PlayerList){
            playerdata.get(p.getUniqueId()).setRestrictMovement(false);
            p.getInventory().setHeldItemSlot(2);
            p.getInventory().clear();
            p.getInventory().setItem(8, MainEvent.getMenuItem());
            p.setMaxHealth(startingHealth);
            p.setHealth(startingHealth);
            p.setFoodLevel(20);
            p.setGameMode(GameMode.ADVENTURE);

            playerdata.get(p.getUniqueId()).setAllowSkill(true);
            playerdata.get(p.getUniqueId()).setQuirkStorage(0);

            playerdata.get(p.getUniqueId()).setStamina(startingStamina);

            item_skill.setType(Material.LIME_DYE);
            meta.setDisplayName(ChatColor.GREEN + "[Ability 1] " + ChatColor.WHITE + playerdata.get(p.getUniqueId()).getQuirk().getAbility1Comment());
            lore.clear();
            lore.add(ChatColor.WHITE + "* " + ChatColor.GREEN + "Press the number slot to use!");
            meta.setLore(lore);
            meta.addEnchant(Enchantment.DURABILITY,1,false);
            meta.setCustomModelData(1);
            item_skill.setItemMeta(meta);

            p.getInventory().setItem(0,item_skill);
            meta.setDisplayName(ChatColor.GREEN + "[Ability 2] " + ChatColor.WHITE + playerdata.get(p.getUniqueId()).getQuirk().getAbility2Comment());
            meta.setCustomModelData(2);
            item_skill.setItemMeta(meta);
            p.getInventory().setItem(1,item_skill);

            item_skill.setType(Material.MAGENTA_DYE);
            meta.setDisplayName(ChatColor.GREEN + "[Ability 3] " + ChatColor.WHITE + playerdata.get(p.getUniqueId()).getQuirk().getAbility3Comment());
            lore.clear();
            lore.add(ChatColor.WHITE + "* " + ChatColor.GREEN + "Right Click to use!");
            meta.setLore(lore);
            meta.setCustomModelData(1);
            item_skill.setItemMeta(meta);
            p.getInventory().setItem(2,item_skill);
            p.getInventory().setItem(7,createClassInfo(p));

        }
    }

    public void sendTitleToAll(String string){
        for(Player p : getPlayerList()){
            p.sendTitle(ChatColor.WHITE + "<" + ChatColor.GREEN + string + ChatColor.WHITE +">","");
        }
    }

    public void playsoundtoAll(Sound sound,float pitch){
        for(Player p : getPlayerList()){
            p.playSound(p.getLocation(), sound, 0.5F, pitch);
        }
    }

    public void sendMessageinGamePlayers(String message){
        for(Player p : getPlayerList()){
            p.sendMessage(message);
        }
    }

    public void TeleportToStalemate(Collection<? extends Player> playersList, String Arena){
        Location LobbySpawn = ArenaList.get(Arena).getLobbySpawn();
        for(Player p : playersList){
            p.teleport(LobbySpawn);
        }
    }


    private void sendTitletoAll(String s, String s1) {
        for(Player p : getPlayerList()){
            p.sendTitle(s,s1);
        }
    }

    public void startgame(){
        start();

    }



}


