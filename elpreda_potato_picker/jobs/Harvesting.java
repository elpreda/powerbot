/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elpreda_potato_picker.jobs;

import elpreda_potato_picker.util.Globals;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 *
 * @author Alin
 */
public class Harvesting extends Node {

    @Override
    public boolean activate() {
        SceneObject Gate = SceneEntities.getNearest(Globals.ClosedGateID);
        return !Inventory.isFull() && Game.getClientState() != 12 && (Gate == null || (Gate != null && !Gate.isOnScreen())) && !Inventory.containsOneOf(Globals.Unwanted);
    }

    @Override
    public void execute() {
        SceneObject Potato = SceneEntities.getNearest(Globals.PotatoID);
        int check = Inventory.getCount();
        if (check > Globals.Countcheck){
            Globals.Count++;
        }    
        Globals.Countcheck = check;
        
        if(Globals.PotatoArea.contains(Players.getLocal().getLocation())){
            if(Potato.isOnScreen()){
                if (!Players.getLocal().isMoving() && Players.getLocal().getAnimation() != Globals.HarvestingAnimationID) {
                    Globals.Status = "Harvesting";
                    Potato.interact("Pick");                    
                    sleep(1200, 1500);                       
                }                
            }
            else {
                Camera.turnTo(Potato);
            }
            
        }
        else {
          if (Walking.getEnergy() < 20){
              Globals.Status = "Resting";
              Globals.wdgc.interact("Rest");
              sleep(24000, 27000);
          }
          Globals.Status = "Walking";
          Walking.newTilePath(Globals.GivePath()).traverse();
          sleep(600,800);  
        }
    }
    
}
