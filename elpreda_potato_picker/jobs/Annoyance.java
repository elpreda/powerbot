/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elpreda_potato_picker.jobs;

import elpreda_potato_picker.util.Globals;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 *
 * @author Alin
 */
public class Annoyance extends Node{

    @Override
    public boolean activate() {
        SceneObject Gate = SceneEntities.getNearest(Globals.ClosedGateID);
        return Game.getClientState() != 12 && ((Gate != null && Gate.isOnScreen()) || Inventory.containsOneOf(Globals.Unwanted));
    }

    @Override
    public void execute() {
        if (!Inventory.containsOneOf(Globals.Unwanted)){
            SceneObject Gate = SceneEntities.getNearest(Globals.ClosedGateID);
            if(Gate.isOnScreen()){
                Globals.Status = "Opening Gate";
                Gate.interact("Open");
                sleep(800, 1000);
            }
            else {
                Camera.turnTo(Gate);
            }
        }
        else {
            Globals.Status = "Dropping";
            Inventory.getItem(Globals.Unwanted).getWidgetChild().interact("Drop");
            sleep(700, 900);
        }
    }
    
}
