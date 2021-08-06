package hadences.reforgedmha;

import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;

import static hadences.reforgedmha.PlayerManager.playerdata;

public class scoreboard {
    private Scoreboard Board;
    public void createScoreboard(){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Board = manager.getNewScoreboard();
        //Health
        Objective Health = Board.registerNewObjective(ChatColor.RED +"Health ❤", Criterias.HEALTH);
        Health.setDisplaySlot(DisplaySlot.BELOW_NAME);

        //Team_Red
        Team Red = Board.registerNewTeam("Alpha");
        Red.setAllowFriendlyFire(false);
        Red.setCanSeeFriendlyInvisibles(true);
        Red.setPrefix(ChatColor.WHITE + "" + ChatColor.BOLD + "<" + ChatColor.RED + "" + ChatColor.BOLD + "Alpha" + ChatColor.WHITE + "" + ChatColor.BOLD + "> ");
        Red.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "<" + ChatColor.RED + "" + ChatColor.BOLD + "Alpha" + ChatColor.WHITE + "" + ChatColor.BOLD + "> ");
        Red.color(NamedTextColor.RED);
        //Team_Blue
        Team Blue = Board.registerNewTeam("Beta");
        Blue.setAllowFriendlyFire(false);
        Blue.setCanSeeFriendlyInvisibles(true);
        Blue.setPrefix(ChatColor.WHITE + "" + ChatColor.BOLD + "<" + ChatColor.BLUE + "" + ChatColor.BOLD + "Beta" + ChatColor.WHITE + "" + ChatColor.BOLD + "> ");
        Blue.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "<" + ChatColor.BLUE + "" + ChatColor.BOLD + "Beta" + ChatColor.WHITE + "" + ChatColor.BOLD + "> ");
        Blue.color(NamedTextColor.BLUE);
        //PlayerData Board
        Objective PlayerDataBoard = Board.registerNewObjective("PlayerDataBoard", "dummy");
        PlayerDataBoard.setDisplayName(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "Project MHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "]");
        PlayerDataBoard.setDisplaySlot(DisplaySlot.SIDEBAR);

        //GameLobby Board
        Objective LobbyBoard = Board.registerNewObjective("LobbyBoard", "dummy");
        LobbyBoard.setDisplayName(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "Project MHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "]");

        //inGame Board
        Objective GameBoard = Board.registerNewObjective("GameBoard", "dummy");
        GameBoard.setDisplayName(ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "Project MHA" + ChatColor.BLACK + "" + ChatColor.BOLD + "]");

    }

    public void updatePlayerDataBoard(Scoreboard scoreboard) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setScoreboard(scoreboard);

            resetBoard(scoreboard, playerdata.get(p.getUniqueId()).getPastPlayerDataBoard());
            playerdata.get(p.getUniqueId()).getPastPlayerDataBoard().clear();

            scoreboard.getObjective("PlayerDataBoard").setDisplaySlot(DisplaySlot.SIDEBAR);
            scoreboard.getObjective("PlayerDataBoard").getScore(ChatColor.WHITE + "" + ChatColor.BOLD + " -" + ChatColor.RED + "" + ChatColor.BOLD + "------------------" + ChatColor.WHITE + "" + ChatColor.BOLD + "-").setScore(10);
            scoreboard.getObjective("PlayerDataBoard").getScore("").setScore(9);
            scoreboard.getObjective("PlayerDataBoard").getScore("  " + ChatColor.WHITE + "[" + ChatColor.GREEN + "♟" + ChatColor.WHITE + "]" + ChatColor.GRAY + " Online Players : " + ChatColor.GREEN + Bukkit.getOnlinePlayers().size()).setScore(8);
            playerdata.get(p.getUniqueId()).getPastPlayerDataBoard().put(8, "  " + ChatColor.WHITE + "[" + ChatColor.GREEN + "♟" + ChatColor.WHITE + "]" + ChatColor.GRAY + " Online Players : " + ChatColor.GREEN + Bukkit.getOnlinePlayers().size());

            scoreboard.getObjective("PlayerDataBoard").getScore("   ").setScore(3);
            scoreboard.getObjective("PlayerDataBoard").getScore(" " + ChatColor.WHITE + "" + ChatColor.BOLD + "-" + ChatColor.RED + "" + ChatColor.BOLD + "------------------" + ChatColor.WHITE + "" + ChatColor.BOLD + "-").setScore(2);
            scoreboard.getObjective("PlayerDataBoard").getScore("    ").setScore(1);
        }
    }

    public void updateLobbyBoard(){

    }

    public void updateGameScoreboard(Player p, Scoreboard scoreboard, int BlueTeamAlive, int RedTeamAlive, int minutes, int seconds){
            HashMap<Integer,String> pastGameBoard = playerdata.get(p.getUniqueId()).getPastinGameBoard();
            if (playerdata.get(p.getUniqueId()).isInGame()) {
                p.setScoreboard(scoreboard);

                resetBoard(scoreboard,pastGameBoard);

                pastGameBoard.clear();

                scoreboard.getObjective("GameBoard").setDisplaySlot(DisplaySlot.SIDEBAR);
                scoreboard.getObjective("GameBoard").getScore(ChatColor.WHITE + "" + ChatColor.BOLD + " -" + ChatColor.RED + "" + ChatColor.BOLD + "------------------" + ChatColor.WHITE + "" + ChatColor.BOLD + "-").setScore(10);

                scoreboard.getObjective("GameBoard").getScore("").setScore(9);
                scoreboard.getObjective("GameBoard").getScore("  " + ChatColor.WHITE + "[" + ChatColor.BLUE + "♦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Players Alive : " + ChatColor.BLUE + BlueTeamAlive).setScore(8);
                pastGameBoard.put(8,"  " + ChatColor.WHITE + "[" + ChatColor.BLUE + "♦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Players Alive : " + ChatColor.BLUE + BlueTeamAlive);

                scoreboard.getObjective("GameBoard").getScore(" ").setScore(7);
                scoreboard.getObjective("GameBoard").getScore("  " + ChatColor.WHITE + "[" + ChatColor.RED + "♦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Players Alive : " + ChatColor.RED + RedTeamAlive).setScore(6);
                pastGameBoard.put(6,"  " + ChatColor.WHITE + "[" + ChatColor.RED + "♦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Players Alive : " + ChatColor.RED + RedTeamAlive);

                scoreboard.getObjective("GameBoard").getScore("  ").setScore(5);

                if(seconds < 10){
                    scoreboard.getObjective("GameBoard").getScore("  " + ChatColor.WHITE + "[" + ChatColor.GREEN + "✦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Time : " + ChatColor.GREEN + minutes + ":0" + seconds).setScore(4);
                    pastGameBoard.put(4,"  " + ChatColor.WHITE + "[" + ChatColor.GREEN + "✦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Time : " + ChatColor.GREEN + minutes + ":0" + seconds);

                }else {
                    scoreboard.getObjective("GameBoard").getScore("  " + ChatColor.WHITE + "[" + ChatColor.GREEN + "✦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Time : " + ChatColor.GREEN + minutes + ":" + seconds).setScore(4);
                    pastGameBoard.put(4,"  " + ChatColor.WHITE + "[" + ChatColor.GREEN + "✦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Time : " + ChatColor.GREEN + minutes + ":" + seconds);
                }
                scoreboard.getObjective("GameBoard").getScore("   ").setScore(3);
                scoreboard.getObjective("GameBoard").getScore("    ").setScore(2);

            }
    }

    public void updateLobbyScoreboard(Player p, Scoreboard scoreboard, int BlueTeamPlayers, int RedTeamPlayers, int NoTeamPlayers, boolean checkAllPlayersReady){
                if (playerdata.get(p.getUniqueId()).isInGame()) {
                    p.setScoreboard(scoreboard);

                    HashMap<Integer,String> pastLobbyBoard =playerdata.get(p.getUniqueId()).getPastLobbyBoard();
                    resetBoard(scoreboard,pastLobbyBoard);
                    pastLobbyBoard.clear();

                    scoreboard.getObjective("LobbyBoard").setDisplaySlot(DisplaySlot.SIDEBAR);
                    scoreboard.getObjective("LobbyBoard").getScore(ChatColor.WHITE + "" + ChatColor.BOLD + " -" + ChatColor.RED + "" + ChatColor.BOLD + "------------------" + ChatColor.WHITE + "" + ChatColor.BOLD + "-").setScore(10);

                    scoreboard.getObjective("LobbyBoard").getScore("").setScore(9);
                    scoreboard.getObjective("LobbyBoard").getScore("  " + ChatColor.WHITE + "[" + ChatColor.BLUE + "♦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Players : " + ChatColor.BLUE + BlueTeamPlayers).setScore(8);
                    pastLobbyBoard.put(8,"  " + ChatColor.WHITE + "[" + ChatColor.BLUE + "♦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Players : " + ChatColor.BLUE + BlueTeamPlayers);

                    scoreboard.getObjective("LobbyBoard").getScore(" ").setScore(7);
                    scoreboard.getObjective("LobbyBoard").getScore("  " + ChatColor.WHITE + "[" + ChatColor.RED + "♦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Players : " + ChatColor.RED + RedTeamPlayers).setScore(6);
                    pastLobbyBoard.put(6,"  " + ChatColor.WHITE + "[" + ChatColor.RED + "♦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Players : " + ChatColor.RED + RedTeamPlayers);

                    scoreboard.getObjective("LobbyBoard").getScore("  ").setScore(5);
                    scoreboard.getObjective("LobbyBoard").getScore("  " + ChatColor.WHITE + "[" + ChatColor.GRAY + "♣" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Players : " + ChatColor.WHITE + NoTeamPlayers).setScore(4);
                    pastLobbyBoard.put(4,"  " + ChatColor.WHITE + "[" + ChatColor.GRAY + "♣" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Players : " + ChatColor.WHITE + NoTeamPlayers);

                    scoreboard.getObjective("LobbyBoard").getScore("   ").setScore(3);
                    scoreboard.getObjective("LobbyBoard").getScore("  " + ChatColor.WHITE + " All Players Ready : " + ChatColor.GOLD + checkAllPlayersReady).setScore(2);
                    pastLobbyBoard.put(2,"  " + ChatColor.WHITE + " All Players Ready : " + ChatColor.GOLD + checkAllPlayersReady);

                    scoreboard.getObjective("LobbyBoard").getScore("    ").setScore(1);

                }
    }


    public void resetBoard(Scoreboard board, HashMap<Integer,String> list){
        for(int key : list.keySet()){
            board.resetScores(list.get(key));
        }

    }

    public Scoreboard getBoard(){
        return Board;
    }
}
