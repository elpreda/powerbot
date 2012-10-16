
package elpreda_potato_picker.util;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class PaintGlobals {  
   
    private final static float GreyAlpha = 0.6f;
    private final static float BlueAlpha = 0.65f;
    private final static float BlackAlpha = 1f;
    private final static int AlphaType = AlphaComposite.SRC_OVER; 
    public final static AlphaComposite GreyComposite = AlphaComposite.getInstance(AlphaType, GreyAlpha);
    public final static AlphaComposite BlueComposite = AlphaComposite.getInstance(AlphaType, BlueAlpha);
    public final static AlphaComposite BlackComposite = AlphaComposite.getInstance(AlphaType, BlackAlpha);
   
    public final static Color Color1 = new Color(204, 204, 204);
    public final static Color Color2 = new Color(0, 0, 0);
    public final static Color Color3 = new Color(204, 204, 255);    
    
    public final static BasicStroke Stroke1 = new BasicStroke(1);

    public final static Font Gothic = new Font("Century Gothic", 0, 13);
    public final static Font Calibri = new Font("Calibri", 1, 13);

    public final static String CursorURL = "http://s11.postimage.org/ies1e4c4v/219525284105243.gif";
    public final static Image Cursor = getImage(CursorURL);
    public static int MouseX;
    public static int MouseY;
    
    public static Image getImage(String url) {
        try {            
            return ImageIO.read(new URL(url));
            //return ImageIO.read(new File(file));
        } catch(IOException e) {
            return null;
        }
    }
}
