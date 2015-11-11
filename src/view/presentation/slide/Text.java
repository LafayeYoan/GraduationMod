package view.presentation.slide;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import view.presentation.Drawer;


public class Text extends Drawer<String>
{
    public Text(String obj, JFrame frame)
    {
        super(obj, frame);
    }
    
    protected int opacity;
    private Timer timer;

    protected static final int MARGIN = 50;
    protected static final int TEXT_ROW_SPACE = 50;
    @Override
    public void draw(Graphics g)
    {
        // get Window size + define (X,Y) for logos
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int max_height = (int)dimension.getHeight();
        int max_width  = (int)dimension.getWidth();
        int polytechX = max_height + MARGIN * 4;
        int polytechY = max_width / 2 - MARGIN;
        int ucblX = MARGIN;
        int ucblY = max_width / 2 - MARGIN;
        
        // Draw background image        
        g.drawImage(getBackgroundTopImage(), MARGIN, MARGIN / 2, g.getClipBounds().width - MARGIN * 2, (int)((getBackgroundTopImage().getHeight() / (double)getBackgroundTopImage().getWidth()) * g.getClipBounds().height) * 2 - MARGIN * 2, null);
        g.drawImage(getLogoPolytechImage(), polytechX, polytechY, g.getClipBounds().width / 4, (int)((getLogoPolytechImage().getHeight() / (double)getLogoPolytechImage().getWidth()) * g.getClipBounds().height) / 2, null);
        g.drawImage(getlogoUCBLImage(), ucblX, ucblY, g.getClipBounds().width / 4, (int) ((getlogoUCBLImage().getHeight() / (double)getlogoUCBLImage().getWidth()) * g.getClipBounds().height) / 2, null);
        
        // Draw text
        g.setColor(new Color(0, 0, 0, opacity));
        g.setFont(g.getFont().deriveFont(200f / 1920 * (g.getClipBounds().width - MARGIN)));
        
        int y = MARGIN + 10;
        int gh = g.getFontMetrics().getHeight();
        for(String s : splitToFit(getObject(), g, MARGIN + 10))
        {
            g.drawString(s, getCenter(g, s), y + gh);
            y += gh + TEXT_ROW_SPACE;
        }
    }

    @Override
    public void onEnter()
    {
        super.onEnter();
        opacity = 0;
        
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                opacity += 5;
                refresh();
                
                if(opacity >= 255)
                    timer.cancel();
            }
        }, 0, 50);
    }

    @Override
    public void onLeave()
    {
        super.onLeave();
        timer.cancel();
    }
}
