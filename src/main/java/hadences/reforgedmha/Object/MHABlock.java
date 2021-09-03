package hadences.reforgedmha.Object;

import org.bukkit.Material;
import org.bukkit.block.Block;


import java.util.ArrayList;

public class MHABlock{
    public static ArrayList<MHABlock> GameBlocks = new ArrayList<>();

    Material original_material;
    Material change_material;
    Block block;
    boolean quirk_block;

    public MHABlock(Material og, Material change, Block b, boolean type) {
        original_material = og;
        change_material = change;
        block = b;
        quirk_block = type;
    }
/*
    public static boolean contains(Block b){
        if(GameBlocks.contains(b))
            return true;
        return false;

    }
*/
    public static int getGameBlock(Block b){
        for(int i = 0; i < GameBlocks.size(); i++){
            if(GameBlocks.get(i).getBlock() == b)
                return i;
        }
        return 0;
    }



    public Material getOriginal_material() {
        return original_material;
    }

    public void setOriginal_material(Material original_material) {
        this.original_material = original_material;
    }

    public Material getChange_material() {
        return change_material;
    }

    public void setChange_material(Material change_material) {
        this.change_material = change_material;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public boolean isQuirk_block() {
        return quirk_block;
    }

    public void setQuirk_block(boolean quirk_block) {
        this.quirk_block = quirk_block;
    }
}
