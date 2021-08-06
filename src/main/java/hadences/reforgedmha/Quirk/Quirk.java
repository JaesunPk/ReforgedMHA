package hadences.reforgedmha.Quirk;

import hadences.reforgedmha.Quirk.Quirks.*;

import java.util.ArrayList;

public class Quirk {

    public static ArrayList<Quirk> quirklist = new ArrayList<>();

    private String name;
    private String type;
    private String role;

    private int Ability1CD;
    private int Ability2CD;
    private int Ability3CD;

    private double Ability1Dmg;
    private double Ability2Dmg;
    private double Ability3Dmg;

    private int Ability1Effect;
    private int Ability2Effect;
    private int Ability3Effect;

    private double Ability1Stamina;
    private double Ability2Stamina;
    private double Ability3Stamina;

    private String Ability1Info;
    private String Ability2Info;
    private String Ability3Info;

    private String Ability1Comment;
    private String Ability2Comment;
    private String Ability3Comment;

    private QuirkCastManager QuirkCastManager;
    private String DisplayName;

    public Quirk(String Name, String Type, String Role, int a1cd, int a2cd, int a3cd, double a1dmg, double a2dmg, double a3dmg, int a1e, int a2e, int a3e, double a1s,double a2s, double a3s, String a1i, String a2i, String a3i, String a1c, String a2c, String a3c, String displayName){
        name = Name;
        type = Type;
        role = Role;
        Ability1CD = a1cd;
        Ability2CD = a2cd;
        Ability3CD = a3cd;
        Ability1Dmg = a1dmg;
        Ability2Dmg = a2dmg;
        Ability3Dmg = a3dmg;
        Ability1Effect = a1e;
        Ability2Effect = a2e;
        Ability3Effect = a3e;
        Ability1Stamina = a1s;
        Ability2Stamina = a2s;
        Ability3Stamina = a3s;
        Ability1Info = a1i;
        Ability2Info = a2i;
        Ability3Info = a3i;
        Ability1Comment = a1c;
        Ability2Comment = a2c;
        Ability3Comment = a3c;
        QuirkCastManager = null;
        DisplayName = displayName;
        setQuirkCastManager();
    }

    public void setQuirkCastManager() {
        if(name.equalsIgnoreCase("Quirkless")) QuirkCastManager = new Quirkless();
        if(name.equalsIgnoreCase("Blackwhip")) QuirkCastManager = new Blackwhip();
        if(name.equalsIgnoreCase("Permeation")) QuirkCastManager = new Permeation();
        if(name.equalsIgnoreCase("Erasure")) QuirkCastManager = new Erasure();
        if(name.equalsIgnoreCase("Zero Gravity")) QuirkCastManager = new ZeroGravity();
        if(name.equalsIgnoreCase("Half Cold Half Hot")) QuirkCastManager = new HalfColdHalfHot();
        if(name.equalsIgnoreCase("Explosion")) QuirkCastManager = new Explosion();
        if(name.equalsIgnoreCase("Tape")) QuirkCastManager = new Tape();
        if(name.equalsIgnoreCase("Decay")) QuirkCastManager = new Decay();
        if(name.equalsIgnoreCase("Engine")) QuirkCastManager = new Engine();
        if(name.equalsIgnoreCase("FaJin")) QuirkCastManager = new Fajin();
        if(name.equalsIgnoreCase("Hardening")) QuirkCastManager = new Hardening();
        //if(name.equalsIgnoreCase("Overhaul")) QuirkCastManager = new Overhaul();
        if(name.equalsIgnoreCase("Cremation")) QuirkCastManager = new Cremation();



    }
    //Initialize Quirks

    public static Quirk getQuirk(String quirk){
        for(int i = 0; i < quirklist.size(); i++){
            if(quirklist.get(i).getName().equalsIgnoreCase(quirk))
                return quirklist.get(i);
        }
        return null;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getAbility1Effect() {
        return Ability1Effect;
    }

    public void setAbility1Effect(int ability1Effect) {
        Ability1Effect = ability1Effect;
    }

    public int getAbility2Effect() {
        return Ability2Effect;
    }

    public void setAbility2Effect(int ability2Effect) {
        Ability2Effect = ability2Effect;
    }

    public int getAbility3Effect() {
        return Ability3Effect;
    }

    public void setAbility3Effect(int ability3Effect) {
        Ability3Effect = ability3Effect;
    }

    public hadences.reforgedmha.Quirk.QuirkCastManager getQuirkCastManager() {
        return QuirkCastManager;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public int getAbility1CD() {
        return Ability1CD;
    }

    public void setAbility1CD(int ability1CD) {
        Ability1CD = ability1CD;
    }

    public int getAbility2CD() {
        return Ability2CD;
    }

    public void setAbility2CD(int ability2CD) {
        Ability2CD = ability2CD;
    }

    public int getAbility3CD() {
        return Ability3CD;
    }

    public void setAbility3CD(int ability3CD) {
        Ability3CD = ability3CD;
    }

    public double getAbility1Dmg() {
        return Ability1Dmg;
    }

    public void setAbility1Dmg(double ability1Dmg) {
        Ability1Dmg = ability1Dmg;
    }

    public double getAbility2Dmg() {
        return Ability2Dmg;
    }

    public void setAbility2Dmg(double ability2Dmg) {
        Ability2Dmg = ability2Dmg;
    }

    public double getAbility3Dmg() {
        return Ability3Dmg;
    }

    public void setAbility3Dmg(double ability3Dmg) {
        Ability3Dmg = ability3Dmg;
    }

    public double getAbility1Stamina() {
        return Ability1Stamina;
    }

    public void setAbility1Stamina(double ability1Stamina) {
        Ability1Stamina = ability1Stamina;
    }

    public double getAbility2Stamina() {
        return Ability2Stamina;
    }

    public void setAbility2Stamina(double ability2Stamina) {
        Ability2Stamina = ability2Stamina;
    }

    public double getAbility3Stamina() {
        return Ability3Stamina;
    }

    public void setAbility3Stamina(double ability3Stamina) {
        Ability3Stamina = ability3Stamina;
    }

    public String getAbility1Info() {
        return Ability1Info;
    }

    public void setAbility1Info(String ability1Info) {
        Ability1Info = ability1Info;
    }

    public String getAbility2Info() {
        return Ability2Info;
    }

    public void setAbility2Info(String ability2Info) {
        Ability2Info = ability2Info;
    }

    public String getAbility3Info() {
        return Ability3Info;
    }

    public void setAbility3Info(String ability3Info) {
        Ability3Info = ability3Info;
    }

    public String getAbility1Comment() {
        return Ability1Comment;
    }

    public void setAbility1Comment(String ability1Comment) {
        Ability1Comment = ability1Comment;
    }

    public String getAbility2Comment() {
        return Ability2Comment;
    }

    public void setAbility2Comment(String ability2Comment) {
        Ability2Comment = ability2Comment;
    }

    public String getAbility3Comment() {
        return Ability3Comment;
    }

    public void setAbility3Comment(String ability3Comment) {
        Ability3Comment = ability3Comment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

