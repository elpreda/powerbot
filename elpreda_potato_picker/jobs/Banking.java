
package elpreda_potato_picker.jobs;

import elpreda_potato_picker.util.Globals;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class Banking extends Node {

    @Override
    public boolean activate() {
        SceneObject Gate = SceneEntities.getNearest(Globals.ClosedGateID);
        return Inventory.isFull() && Game.getClientState() != 12 && (Gate == null || (Gate != null && !Gate.isOnScreen())) && !Inventory.containsOneOf(Globals.Unwanted);
    }

    @Override
    public void execute() {
        SceneObject Booth = SceneEntities.getNearest(Globals.BoothID);
        
        if (Globals.BankArea.contains(Players.getLocal().getLocation())){
            Globals.Status = "Banking";
            if(Bank.isOpen()){
              Bank.depositInventory();
              sleep(800,1300);               
            }
            else {
                if(Booth != null){
                    if(Booth.isOnScreen()){
                            Booth.interact("Bank");
                            sleep(1000, 1500);
                        }
                }
                else {
                    Camera.turnTo(Booth);
                }
            }
        }
        else {
            Globals.Status = "Walking";
            Walking.newTilePath(Globals.GivePath()).reverse().traverse();
            sleep(600,800);
        }
    }
    
}
