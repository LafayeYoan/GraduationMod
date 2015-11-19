package model.model;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.serializable.XMLSerializable;
import model.image.Image;


public class Talk implements XMLSerializable
{
    public Talk(String title, String text, Image picture)
    {
        this.title = title;
        this.text = text;
        this.picture = picture;
    }
    
    public String title;
    public String text;
    public Image picture;
    
    public static Talk createDefault()
    {
        return new Talk("Discours", "", null);
    }

    @Override
    public String toXML()
    {
        String xml = "";
        
        xml += "<talk>";
        
        xml += "<title>" + title + "</title>";
        xml += "<text>" + text + "</text>";
        
        if(picture == null)
            xml += "<image/>";
        else
            xml += "<image>" + picture.getID().toString(10) + "</image>";
        
        xml += "</talk>";
        
        try{
            return new String(xml.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Details.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
