package org.powerbot.core.randoms;

import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

@Manifest(name = "Maze", authors = {"elpreda"}, version = 1.0)
public class Maze extends AntiRandom{
 
private final int MazeShrineID = 3634;
private final int MazeDoorID = 3628;
private final int MazeDoorBack1ID = 3630;
private final int MazeDoorBack2ID = 3631;
private final int MazeDoorBack3ID = 3629;
private final int MazeDoorBack4ID = 3632;
private final int MazeWallID = 3626;
private final Tile Goal = new Tile(2911,4576,0);
private SceneObject LastDoor;


private Tile reachable(Tile tile){
    if(tile.canReach())
        return tile;
    Tile x = new Tile(tile.getX()+1,tile.getY(),tile.getPlane());
    if(x.canReach())
        return x;
    x = new Tile(tile.getX()-1,tile.getY(),tile.getPlane());
    if(x.canReach())
        return x;
    x = new Tile(tile.getX(),tile.getY()+1,tile.getPlane());
    if(x.canReach())
        return x;
    x = new Tile(tile.getX(),tile.getY()-1,tile.getPlane());
    if(x.canReach())
        return x;
    return null;
}

private Tile reachableBack(Tile tile){
    if(!tile.canReach()){
        Tile x = new Tile(tile.getX()+1,tile.getY(),tile.getPlane());
        if(x.canReach())
            return x;
        x = new Tile(tile.getX()-1,tile.getY(),tile.getPlane());
        if(x.canReach())
            return x;
        x = new Tile(tile.getX(),tile.getY()+1,tile.getPlane());
        if(x.canReach())
            return x;
        x = new Tile(tile.getX(),tile.getY()-1,tile.getPlane());
        if(x.canReach())
            return x;
    }
    return null;
}

private final Filter<SceneObject> DoorFilter = new Filter<SceneObject>() {

            @Override
            public boolean accept(SceneObject t) {
                return (t.getId() == MazeDoorID) && reachable(t.getLocation()) != null;
            }
        };
private final Filter<SceneObject> DoorFilter2 = new Filter<SceneObject>() {

            @Override
            public boolean accept(SceneObject t) {
                return (t.getId() == MazeDoorID) && reachable(t.getLocation()) != null && ((t.getLocation().getX() != LastDoor.getLocation().getX() || t.getLocation().getY() != LastDoor.getLocation().getY()));
            }
        };

private final Filter<SceneObject> DoorFilter3 = new Filter<SceneObject>() {

            @Override
            public boolean accept(SceneObject t) {
                return (t.getId() == MazeDoorBack1ID || t.getId() == MazeDoorBack2ID) && reachableBack(t.getLocation()) != null ;
            }
        };

private final Filter<SceneObject> DoorFilter4 = new Filter<SceneObject>() {

            @Override
            public boolean accept(SceneObject t) {
                return (t.getId() == MazeDoorBack3ID || t.getId() == MazeDoorBack4ID) && reachableBack(t.getLocation()) != null ;
            }
        };


    private SceneObject FurthestWall(){
        int max = 0;
        double maxd = 0;
        SceneObject walls[] = SceneEntities.getLoaded(new Filter<SceneObject>(){

            @Override
            public boolean accept(SceneObject t) {
                return t.getId() == MazeWallID && t.getLocation().canReach();
            }
            
        });
        for(int i = 0; i < walls.length; i++){
            if (walls[i].getLocation().distanceTo() > maxd){
                maxd = walls[i].getLocation().distanceTo();
                max = i;
            }
        }
        return walls[max];
    }
        
    private SceneObject NextDoor(){
        SceneObject door = SceneEntities.getNearest(DoorFilter2);
        if (door == null){                       
            door = SceneEntities.getNearest(DoorFilter3);
        } 
        if (door == null){                      
            door = SceneEntities.getNearest(DoorFilter4);
        } 
            return door;        
    }
    
    private void goThrough(SceneObject door){
            Tile temp = reachable(door.getLocation());
            
                if(door.isOnScreen()){
                    door.interact("Open");
                    while(temp.getX() == reachable(door.getLocation()).getX() && temp.getY() == reachable(door.getLocation()).getY()){                        
                        Camera.turnTo(door);
                        door.interact("Open");
                        sleep(1000,2000);
                    }
                }
                else{                
                    Walking.walk(temp);
                    while(!door.isOnScreen()){
                        if(!Players.getLocal().isMoving())
                            Walking.walk(temp);
                        sleep(1000,2000);
                    }
                    door.interact("Open");
                    while(temp.getX() == reachable(door.getLocation()).getX() && temp.getY() == reachable(door.getLocation()).getY()){
                        Camera.turnTo(door);
                        door.interact("Open");
                        sleep(1000,2000);
                    }
                }
                LastDoor = door;
    }

    @Override
    public boolean activate() {
        return SceneEntities.getNearest(MazeWallID) != null;
    }

    @Override
    public void execute() { 
        goThrough(SceneEntities.getNearest(DoorFilter));
        while((Players.getLocal().getLocation().getX() != Goal.getX() || Players.getLocal().getLocation().getY() != Goal.getY())){
            goThrough(NextDoor());
        }
            SceneEntities.getNearest(MazeShrineID).interact("Touch");
            sleep(2500,3500);
    }
    
}
