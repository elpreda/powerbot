
package elpreda_potato_picker.util;


import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;


public class Globals {
    
    public static int bankNow = 0;
    
    public static final long StartTime = System.currentTimeMillis();
    public static long Count = 0;
    public static long Countcheck = Inventory.getCount();
    public static String Status = "";
    public static int RestStandard = 20;
    
    public static final int BoothID = 2012;
    public static final int PotatoID = 312;
    public static final int ClosedGateID = 45208;
    public static final int HarvestingAnimationID = 827;
    public static final int EventGiftID = 14664;
    public static final int PotatoSeedID = 5318;
    public static final int Unwanted[] = {14664, 5318};
    
    public static final Widget wdg = new Widget(548);
    public static final WidgetChild wdgc = new WidgetChild(wdg, 163);
    
    public static final Area BankArea = new Area(new Tile[] { new Tile(3088, 3255, 0), new Tile(3088, 3240, 0), new Tile(3096, 3240, 0), 
					new Tile(3096, 3246, 0), new Tile(3093, 3247, 0), new Tile(3093, 3255, 0) });
    public static final Area GateArea = new Area(new Tile[] { new Tile(3140, 3296, 0), new Tile(3140, 3291, 0), new Tile(3150, 3291, 0), 
					new Tile(3150, 3296, 0) });
    public static final Area PotatoArea = new Area(new Tile[] { new Tile(3138, 3292, 0), new Tile(3137, 3269, 0), new Tile(3142, 3268, 0), 
					new Tile(3152, 3268, 0), new Tile(3155, 3273, 0), new Tile(3155, 3281, 0), 
					new Tile(3156, 3286, 0), new Tile(3153, 3291, 0) });
    public static final Tile[][] Paths = new Tile[][] {new Tile[] { new Tile(3093, 3242, 0), new Tile(3092, 3249, 0), new Tile(3099, 3250, 0), new Tile(3104, 3253, 0), 
				new Tile(3109, 3254, 0), new Tile(3116, 3256, 0), new Tile(3121, 3260, 0), 
				new Tile(3128, 3263, 0), new Tile(3133, 3268, 0), new Tile(3134, 3275, 0), 
				new Tile(3135, 3283, 0), new Tile(3136, 3289, 0), new Tile(3140, 3293, 0), 
				new Tile(3144, 3292, 0), new Tile(3144, 3286, 0), new Tile(3145, 3279, 0) }, new Tile[] { new Tile(3093, 3242, 0), new Tile(3091, 3247, 0), new Tile(3097, 3250, 0), new Tile(3103, 3250, 0), 
				new Tile(3111, 3252, 0), new Tile(3115, 3256, 0), new Tile(3120, 3261, 0), 
				new Tile(3127, 3263, 0), new Tile(3133, 3266, 0), new Tile(3134, 3274, 0), 
				new Tile(3134, 3282, 0), new Tile(3135, 3289, 0), new Tile(3139, 3293, 0), 
				new Tile(3143, 3293, 0), new Tile(3145, 3288, 0), new Tile(3145, 3283, 0), 
				new Tile(3145, 3280, 0) } };
    
    public static Tile[] GivePath(){
        return Paths[Random.nextInt(0, Paths.length)];
    }
    
    public static long getPerHour(long value){
        return value*3600000/(System.currentTimeMillis() - StartTime);
    }
}
