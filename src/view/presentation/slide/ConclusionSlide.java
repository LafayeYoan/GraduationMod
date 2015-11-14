/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.presentation.slide;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JFrame;
import model.model.Details;
import view.presentation.Drawer;
import static view.presentation.slide.Text.MARGIN;

/**
 *
 * @author 11502326
 */
public class ConclusionSlide extends Drawer<Details>{

    public ConclusionSlide(model.model.Details obj, JFrame frame)
    {
        super(obj, frame);
    }
    
    private BufferedImage image = null;
    protected BufferedImage getImage()
    {
        if(image == null && getObject().polytechImage != null)
        {
            try
            {
                image = getObject().polytechImage.getImage();
            }
            catch (IOException ex)
            { }
        }
        
        return image;
    }    
    private static final int IMAGE_MARGIN = 400;   

    @Override
    public void draw(Graphics g)
    {                
        
        // Draw background image
        g.drawImage(getBackgroundTopImage(), MARGIN, MARGIN / 2, g.getClipBounds().width - MARGIN * 2, (int)((getBackgroundTopImage().getHeight() / (double)getBackgroundTopImage().getWidth()) * g.getClipBounds().height) * 2 - MARGIN * 2, null);
        drawLogo(MARGIN, g);
        
        // Draw title
        g.setFont(g.getFont().deriveFont(200f / 1920 * g.getClipBounds().width));
        g.setColor(new Color(255, 255, 255, 255));
        g.drawString(getObject().computedCongratulationText(), getCenter(g, getObject().computedCongratulationText()), 150);
        
        
        // Draw image
        if(getImage() != null)
        {
            int imgh = (int)(g.getClipBounds().height-IMAGE_MARGIN);
            int imgw = (int)(getImage().getWidth()*((double)imgh/(double)getImage().getHeight()));
            
            int imgx = g.getClipBounds().width/2 - imgw/2;
            int imgy = g.getClipBounds().height/2 - imgh/2 + MARGIN;            
            
            g.drawImage(getImage(),imgx, imgy, imgw, imgh, null);
        }
    }
}
