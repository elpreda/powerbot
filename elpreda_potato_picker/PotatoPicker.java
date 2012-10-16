
package elpreda_potato_picker;

import elpreda_potato_picker.jobs.Annoyance;
import elpreda_potato_picker.jobs.Banking;
import elpreda_potato_picker.jobs.Harvesting;
import elpreda_potato_picker.util.Globals;
import elpreda_potato_picker.util.PaintGlobals;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;


@Manifest( name = "elpreda's Potato Picker", authors = { "elpreda" }, description = "Picks up potatoes", version = 1.0 )
public class PotatoPicker extends ActiveScript implements PaintListener {
    
    private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
	private Tree jobContainer = null;

	public final void provide(final Node... jobs) {
		for (final Node job : jobs) {
			jobsCollection.add(job);
		}
		jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
	}

	@Override
	public void onStart() {
                Camera.setNorth(90);
		provide(new Banking(), new Harvesting(), new Annoyance());
	}

    @Override
    public int loop() {
        if (jobContainer != null) {
			final Node job = jobContainer.state();
			if (job != null) {
				jobContainer.set(job);
				getContainer().submit(job);
				job.join();
			}
	}
        return Random.nextInt(10, 50);
    }

    
    @Override
    public void onRepaint(Graphics grphcs) {
        
        long elapsed = System.currentTimeMillis() - Globals.StartTime;
                
        PaintGlobals.MouseX = Mouse.getX() - 10;
        PaintGlobals.MouseY = Mouse.getY();        
        
        Graphics2D g = (Graphics2D)grphcs;
        
        g.setColor(PaintGlobals.Color1);
        g.setComposite(PaintGlobals.GreyComposite);
        g.fillRoundRect(2, 56, 146, 132, 16, 16);
        g.setColor(PaintGlobals.Color2);
        g.setComposite(PaintGlobals.BlackComposite);
        g.setStroke(PaintGlobals.Stroke1);
        g.drawRoundRect(2, 56, 146, 132, 16, 16);
        g.setFont(PaintGlobals.Gothic);
        g.drawString("Elapsed: " + Time.format(elapsed), 6, 82);
        g.drawString("Picked: " + Globals.getPerHour(Globals.Count) + "(" + Globals.Count + ")", 7, 104);
        g.drawString("Status: " + Globals.Status, 8, 125);
        g.setColor(PaintGlobals.Color3);
        g.setComposite(PaintGlobals.BlueComposite);
        g.fillRect(8, 167, 136, 15);
        g.setFont(PaintGlobals.Calibri);
        g.setColor(PaintGlobals.Color2);
        g.setComposite(PaintGlobals.BlackComposite);
        g.drawString("elpreda's Potato Picker", 10, 178);
        g.drawImage(PaintGlobals.Cursor, PaintGlobals.MouseX, PaintGlobals.MouseY, null);
    }
}
