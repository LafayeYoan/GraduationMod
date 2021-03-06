package view.presentation;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import view.presentation.window.PresentationWindow;


public abstract class Drawer<T>
{
    public Drawer(T obj, JFrame frame)
    {
        this.obj = obj;
        this.frame = frame;
    }
    
    private final JFrame frame;
    
    private final T obj;
    protected T getObject()
    {
        return obj;
    }
    
    public abstract void draw(Graphics g);
    
    public void onNext(int listIndex)
    { }
    
    public boolean isEnded()
    {
        return true;
    }
    
    public void onEnter()
    { }
    public void onLeave()
    { }
    
    protected static Collection<String> splitToFit(String text, Graphics g, int margin)
    {
        return splitToFit(text, g, margin, g.getClipBounds().width);
    }
    protected static Collection<String> splitToFit(String text, Graphics g, int margin, int w)
    {
        text = text.trim();
        if(text.contains("\n"))
        {
            return Stream.of(text.split("\n"))
                    .map(s -> splitToFit(s, g, margin, w))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
        
        FontMetrics fm = g.getFontMetrics();
        Collection<String> strs = new LinkedList<>();
        
        do
        {
            String currentText = text;
            while(fm.getStringBounds(currentText, g).getWidth() >= w - margin * 2)
            {
                currentText = currentText.substring(0, currentText.lastIndexOf(" "));
            }
            if(currentText.length() == 0)
            {
                int index = text.indexOf(" ");
                if(index == -1)
                {
                    strs.add(text);
                    return strs;
                }
                else
                {
                    currentText = text.substring(index);
                }
            }
            
            strs.add(currentText);
            text = text.substring(currentText.length());
        } while(!text.isEmpty());
        
        return strs;
    }
    
    protected void refresh()
    {
        ((PresentationWindow)frame).refresh();
    }
    
    protected int getCenter(Graphics g, String text)
    {
        return (int)(g.getClipBounds().width - g.getFontMetrics().getStringBounds(text, g).getWidth())/2;
    }
    protected int getCenter(Graphics g)
    {
        return (int)g.getClipBounds().width/2;
    }
    
    
    private static BufferedImage backgroundTopImage = null;
    private static BufferedImage logoPolytechImage = null;
    private static BufferedImage logoUCBLImage = null;    
    
    protected static BufferedImage getBackgroundTopImage()
    {
        if(backgroundTopImage == null)
            try
            {
                backgroundTopImage = ImageIO.read(Drawer.class.getClassLoader().getResourceAsStream("view/presentation/resources/bg_big.jpg"));
            }
            catch (IOException ex)
            { }
        
        return backgroundTopImage;
    }
    
    protected static BufferedImage getLogoPolytechImage()
    {
        if(logoPolytechImage == null)
            try
            {
                logoPolytechImage = ImageIO.read(Drawer.class.getClassLoader().getResourceAsStream("view/presentation/resources/logo_polytech.jpg"));
            }
            catch (IOException ex)
            { }
        
        return logoPolytechImage;
    }
    
    protected static BufferedImage getlogoUCBLImage()
    {
        if(logoUCBLImage == null)
            try
            {
                logoUCBLImage = ImageIO.read(Drawer.class.getClassLoader().getResourceAsStream("view/presentation/resources/logo_quadri.jpg"));
            }
            catch (IOException ex)
            { }
        
        return logoUCBLImage;
    }
    
    protected static boolean drawLogo(int margin, Graphics g) {
        BufferedImage polytechImg = getLogoPolytechImage();
        BufferedImage UCBLImg = getlogoUCBLImage();
        
        int localHeight = g.getClipBounds().height;
        int localWidth = g.getClipBounds().width;
        int polytechX = localWidth - polytechImg.getWidth()/3 - margin/2;
        int polytechY = localHeight - polytechImg.getHeight()/4 - margin/2;
        int ucblX = margin/2;
        int ucblY = localHeight - UCBLImg.getHeight()/3 -margin/2;
        
        g.drawImage(polytechImg, polytechX, polytechY, g.getClipBounds().width / 5, (int)((polytechImg.getHeight() / (double)polytechImg.getWidth()) * g.getClipBounds().height) / 5, null);
        g.drawImage(UCBLImg, ucblX, ucblY, g.getClipBounds().width / 3, (int) ((UCBLImg.getHeight() / (double)UCBLImg.getWidth()) * g.getClipBounds().height) / 3, null);
        
        return true;
    }
}
