package hadences.reforgedmha;

import hadences.reforgedmha.Commands.MHAarena;
import hadences.reforgedmha.Commands.ParticleTester;
import hadences.reforgedmha.Commands.RestoreStamina;
import hadences.reforgedmha.Commands.ToggleDev;
import hadences.reforgedmha.GUI.*;
import hadences.reforgedmha.GUI.Events.GUIEvent;
import hadences.reforgedmha.Quirk.Quirk;
import hadences.reforgedmha.Quirk.QuirkCastManager;
import hadences.reforgedmha.Quirk.QuirkManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

import static hadences.reforgedmha.Arena.ArenaList;
import static hadences.reforgedmha.PlayerManager.playerdata;
import static hadences.reforgedmha.Quirk.Cooldown.*;

public final class ReforgedMHA extends JavaPlugin {

    public scoreboard board = new scoreboard();
    public Menu menu = new Menu();
    public PlayMenu playMenu = new PlayMenu();
    public LobbyMenu lobbyMenu = new LobbyMenu();
    public QuirkMenu quirkMenu = new QuirkMenu();
    public GamemodeMenu gamemodeMenu = new GamemodeMenu();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new MainEvent(),this);
        getServer().getPluginManager().registerEvents(new GUIEvent(),this);
        getServer().getPluginManager().registerEvents(new QuirkManager(),this);

        getCommand("mhaarena").setExecutor(new MHAarena());
        getCommand("dev").setExecutor(new ToggleDev());
        getCommand("pt").setExecutor(new ParticleTester());
        getCommand("rstamina").setExecutor(new RestoreStamina());

        loadConfig();
        loadArenas();
        loadQuirks();

        loadGui();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        updateConfig();
        HandlerList.unregisterAll(this);
    }

    public void updateConfig() {
        updateArenaConfig();
        updatePlayerConfig();
    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void updateArenaConfig(){

        for(String arena : ArenaList.keySet()){
            this.getConfig().set("Arenas." + ArenaList.get(arena).getName() + ".NAME",String.valueOf(ArenaList.get(arena).getName()));

            this.getConfig().set("Arenas." + ArenaList.get(arena).getName() + ".LOBBYSPAWN.X", Double.valueOf(ArenaList.get(arena).getLobbySpawn().getX()));
            this.getConfig().set("Arenas." + ArenaList.get(arena).getName() + ".LOBBYSPAWN.Y", Double.valueOf(ArenaList.get(arena).getLobbySpawn().getY()));
            this.getConfig().set("Arenas." + ArenaList.get(arena).getName() + ".LOBBYSPAWN.Z", Double.valueOf(ArenaList.get(arena).getLobbySpawn().getZ()));

            this.getConfig().set("Arenas." + ArenaList.get(arena).getName() + ".REDSPAWN.X", Double.valueOf(ArenaList.get(arena).getRedSpawn().getX()));
            this.getConfig().set("Arenas." + ArenaList.get(arena).getName() + ".REDSPAWN.Y", Double.valueOf(ArenaList.get(arena).getRedSpawn().getY()));
            this.getConfig().set("Arenas." + ArenaList.get(arena).getName() + ".REDSPAWN.Z", Double.valueOf(ArenaList.get(arena).getRedSpawn().getZ()));

            this.getConfig().set("Arenas." + ArenaList.get(arena).getName() + ".BLUESPAWN.X", Double.valueOf(ArenaList.get(arena).getBlueSpawn().getX()));
            this.getConfig().set("Arenas." + ArenaList.get(arena).getName() + ".BLUESPAWN.Y", Double.valueOf(ArenaList.get(arena).getBlueSpawn().getY()));
            this.getConfig().set("Arenas." + ArenaList.get(arena).getName() + ".BLUESPAWN.Z", Double.valueOf(ArenaList.get(arena).getBlueSpawn().getZ()));

            this.getConfig().set("Arenas." + ArenaList.get(arena).getName() + ".COMPLETE", Boolean.valueOf(ArenaList.get(arena).isFinalized()));

        }

    }

    public void updatePlayerConfig(){
        String Credit = "";
        String Rank = "";
        String Wins = "";

        // Plugin shutdown logic
        for(Player p : Bukkit.getOnlinePlayers()) {
            Credit = playerdata.get(p.getUniqueId()).getCredit() + "";
            Rank = playerdata.get(p.getUniqueId()).getRank();
            Wins = playerdata.get(p.getUniqueId()).getWins() + "";

            this.getConfig().set("Users." + p.getUniqueId() + ".RANK", Rank);
            this.getConfig().set("Users." + p.getUniqueId() + ".WINS", Wins);
            this.getConfig().set("Users." + p.getUniqueId() + ".CREDIT", Credit);
            this.saveConfig();
        }
    }

    public void loadArenas(){
        String Name;
        Location LobbySpawn;
        Location RedSpawn;
        Location BlueSpawn;
        boolean Finalized;
        try {
            if (!this.getConfig().getConfigurationSection("Arenas").getKeys(false).isEmpty()){
                for (String key : this.getConfig().getConfigurationSection("Arenas").getKeys(false)) {

                    Name = this.getConfig().get("Arenas." + key + ".NAME").toString();
                    LobbySpawn = new Location(Bukkit.getServer().getWorlds().get(0),(Double) this.getConfig().get("Arenas." + key + ".LOBBYSPAWN.X"),(Double) this.getConfig().get("Arenas." + key + ".LOBBYSPAWN.Y"),(Double) this.getConfig().get("Arenas." + key + ".LOBBYSPAWN.Z"));
                    RedSpawn = new Location(Bukkit.getServer().getWorlds().get(0),(Double) this.getConfig().get("Arenas." + key + ".REDSPAWN.X"),(Double) this.getConfig().get("Arenas." + key + ".REDSPAWN.Y"),(Double) this.getConfig().get("Arenas." + key + ".REDSPAWN.Z"));
                    BlueSpawn = new Location(Bukkit.getServer().getWorlds().get(0),(Double) this.getConfig().get("Arenas." + key + ".BLUESPAWN.X"),(Double) this.getConfig().get("Arenas." + key + ".BLUESPAWN.Y"),(Double) this.getConfig().get("Arenas." + key + ".BLUESPAWN.Z"));
                    Finalized = (Boolean) this.getConfig().get("Arenas." + key + ".COMPLETE");

                    ArenaList.put(Name,new Arena(Name,LobbySpawn,RedSpawn,BlueSpawn,Finalized));
                }
            }
        }catch (Exception e){
            getServer().broadcastMessage("testing");
        }

    }

    public void loadQuirks(){
        String Name;
        String Type;
        String Role;
        int Ability1CD;
        int Ability2CD;
        int Ability3CD;
        double Ability1Dmg;
        double Ability2Dmg;
        double Ability3Dmg;
        int Ability1Effect;
        int Ability2Effect;
        int Ability3Effect;
        double Ability1Stamina;
        double Ability2Stamina;
        double Ability3Stamina;
        String Ability1Info;
        String Ability2Info;
        String Ability3Info;
        String Ability1Comment;
        String Ability2Comment;
        String Ability3Comment;
        QuirkCastManager quirkCastManager;
        String DisplayName;
        //try {
            if (!this.getConfig().getConfigurationSection("Quirks").getKeys(false).isEmpty()){
                for (String key : this.getConfig().getConfigurationSection("Quirks").getKeys(false)) {
                    Name = key;
                    Type = this.getConfig().get("Quirks."+key+".Type").toString();
                    Role = ChatColor.translateAlternateColorCodes('&',this.getConfig().get("Quirks."+key+".Role").toString());
                    Ability1CD = (Integer) this.getConfig().get("Quirks."+key+".Ability1CD");
                    Ability2CD = (Integer) this.getConfig().get("Quirks."+key+".Ability2CD");
                    Ability3CD = (Integer) this.getConfig().get("Quirks."+key+".Ability3CD");
                    Ability1Dmg = (Double) this.getConfig().get("Quirks."+key+".Ability1Dmg") * 2;
                    Ability2Dmg = (Double) this.getConfig().get("Quirks."+key+".Ability2Dmg") * 2;
                    Ability3Dmg = (Double) this.getConfig().get("Quirks."+key+".Ability3Dmg") * 2;
                    Ability1Effect = (Integer) this.getConfig().get("Quirks."+key+".Ability1Effect")*20;
                    Ability2Effect = (Integer) this.getConfig().get("Quirks."+key+".Ability2Effect")*20;
                    Ability3Effect = (Integer) this.getConfig().get("Quirks."+key+".Ability3Effect")*20;
                    Ability1Stamina = (Double) this.getConfig().get("Quirks."+key+".Ability1Stamina");
                    Ability2Stamina = (Double) this.getConfig().get("Quirks."+key+".Ability2Stamina");
                    Ability3Stamina = (Double) this.getConfig().get("Quirks."+key+".Ability3Stamina");
                    Ability1Info = this.getConfig().get("Quirks."+key+".Ability1Info").toString();
                    Ability2Info = this.getConfig().get("Quirks."+key+".Ability2Info").toString();
                    Ability3Info = this.getConfig().get("Quirks."+key+".Ability3Info").toString();
                    Ability1Comment = ChatColor.translateAlternateColorCodes('&',this.getConfig().get("Quirks."+key+".Ability1Comment").toString());
                    Ability2Comment = ChatColor.translateAlternateColorCodes('&',this.getConfig().get("Quirks."+key+".Ability2Comment").toString());
                    Ability3Comment = ChatColor.translateAlternateColorCodes('&',this.getConfig().get("Quirks."+key+".Ability3Comment").toString());

                    //quirkCastManager = new QuirkCastManager();
                    DisplayName = ChatColor.translateAlternateColorCodes('&',this.getConfig().get("Quirks."+key+".DisplayName").toString());

                    Quirk.quirklist.add(new Quirk(Name,Type,Role,Ability1CD,Ability2CD,Ability3CD,Ability1Dmg,Ability2Dmg,Ability3Dmg,Ability1Effect,Ability2Effect,Ability3Effect,Ability1Stamina,Ability2Stamina,Ability3Stamina,Ability1Info,Ability2Info,Ability3Info,Ability1Comment,Ability2Comment,Ability3Comment,DisplayName));
                }
            }
        //}catch (Exception e){
           // getServer().broadcastMessage("testing");
        //}
    }
    public void loadGui(){
        // creation logic

        //scoreboard
        board.createScoreboard();

        //gui
        gamemodeMenu.createInventory();
        menu.createInventory();
        playMenu.createInventory();
        lobbyMenu.createInventory();
        quirkMenu.createInventory();

    }
    public void loop() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if(p.getFoodLevel() < 20){
                p.setFoodLevel(20);
            }
            p.sendActionBar(ChatColor.GOLD + "<<< "
                    + ChatColor.GRAY + "[" + ChatColor.GOLD + "1" + ChatColor.GRAY + "] : " + ChatColor.RED + checkCD(p, cooldowns) + " "
                    + ChatColor.GRAY + "[" + ChatColor.GOLD + "2" + ChatColor.GRAY + "] : " +  ChatColor.RED + checkCD(p, cooldowns2) + " "
                    + ChatColor.GRAY + "[" + ChatColor.GOLD + "RC" + ChatColor.GRAY + "] : " +  ChatColor.RED + checkCD(p, cooldowns3) + " "
                    + ChatColor.GRAY + "[" + ChatColor.GOLD + "♦" + ChatColor.GRAY + "] : " +  ChatColor.YELLOW + playerdata.get(p.getUniqueId()).getStamina() + " "
                    + ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "✦" + ChatColor.GRAY + "] : " +  ChatColor.AQUA + playerdata.get(p.getUniqueId()).getQuirkStorage()
                    + ChatColor.GOLD + " >>>");
        }
    }

    public String checkCD(Player p, HashMap<String,Long> cd) {
        String r = "";
        if (cd.get(p.getName()) > java.lang.System.currentTimeMillis()) {
            long timeleft = (cd.get(p.getName()) - java.lang.System.currentTimeMillis()) / 1000;
            return r + timeleft;
        }
        return ChatColor.GREEN + "R";
    }

}
