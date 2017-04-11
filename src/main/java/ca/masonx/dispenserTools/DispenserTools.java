package ca.masonx.dispenserTools;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.DirectionalContainer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DispenserTools extends JavaPlugin implements Listener{
	@Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        getCommand("dtools").setExecutor(this);
    }
 
    @Override
    public void onDisable() {
    }
    
    protected Location getTargetBlock(Block b) {
		BlockFace bf = ((DirectionalContainer) b.getState().getData()).getFacing();
		Location l = b.getLocation();
		switch (bf) {
		case NORTH:
			l.add(0, 0, -1);
			break;
		case SOUTH:
			l.add(0, 0, 1);
			break;
		case EAST:
			l.add(1, 0, 0);
			break;
		case WEST:
			l.add(-1, 0, 0);
			break;
		case UP:
			l.add(0, 1, 0);
			break;
		case DOWN:
			l.add(0, -1, 0);
			break;
		default:
			break;
		}
		return l;
    }
    @EventHandler
    public void onDispense(BlockDispenseEvent e) {
    	final ItemStack i = e.getItem();
    	if (i.getType() == Material.DIAMOND_PICKAXE || i.getType() == Material.GOLD_PICKAXE || i.getType() == Material.IRON_PICKAXE || i.getType() == Material.STONE_PICKAXE) {
        	final Dispenser d = (Dispenser) e.getBlock().getState();
    		final Block target = getTargetBlock(e.getBlock()).getBlock();
    		
    		new BukkitRunnable() {
                @Override
                public void run() {
                	Inventory inv = d.getInventory();
            		
                	for (ItemStack itm : inv.getContents()) {
            			if (itm == null) {
            				System.out.println("Error: Null ItemStack");
            			} else if (itm.getType() == i.getType()) {
            				switch (target.getType()) {
            				/* invalid */
            				case AIR:
            				case BEDROCK:
            				case BARRIER:
            				case COMMAND:
            				case COMMAND_CHAIN:
            				case COMMAND_REPEATING:
            				case END_GATEWAY:
            				case ENDER_PORTAL_FRAME:
            				case PORTAL:
            				case STRUCTURE_BLOCK:
            				case LAVA:
            				case WATER:
            				case OBSIDIAN:
            					return;
            				/* need iron pick */
            				case DIAMOND_ORE:
            				case EMERALD_ORE:
            				case GOLD_ORE:
            				case REDSTONE_ORE:
            				case DIAMOND_BLOCK:
            				case EMERALD_BLOCK:
            				case GOLD_BLOCK:
            					if (i.getType() == Material.STONE_PICKAXE) return;
            					/* fall through since it's iron, gold, or diamond */
							default:
	            				target.breakNaturally(i);
	                			itm.setDurability((short) (itm.getDurability() + 1));
	                	    	if (itm.getDurability() >= itm.getType().getMaxDurability()) {
	                	   			itm.setAmount(0);
	                	    	}
								break;
            				}
            	    		break;
            			}
            		}
                }
            }.runTaskLater(this, 1L);
    		
    		e.setCancelled(true);
    	}/* else if (e.getItem().getType() == Material.CARROT_ITEM || e.getItem().getType() == Material.POTATO_ITEM || e.getItem().getType() == Material.SEEDS) {
    		getTargetBlock(e.getBlock()).getBlock().breakNaturally(e.getItem());
    		e.setCancelled(true);
    	}*/
    		
    }
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return true;
	}
}
