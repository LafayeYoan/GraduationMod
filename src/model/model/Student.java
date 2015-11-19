package model.model;

import model.serializable.XMLSerializable;
import model.image.LocalImage;
import model.image.Image;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Student implements XMLSerializable,Comparable<Student>
{
    public Student(String name, String firstName, Image picture)
    {
        this.name = name;
        this.firstName = firstName;
        this.picture = picture;
    }
    
    public String name;
    public String firstName;
    public Image picture;
    
    public static boolean isValid(File file)
    {
        String[] names = file.getName().split("\\.");
        return names.length >= 3
                 && names[0].matches("^[0-9]+$");
                 //TODO:  verifier regEx
                 //&& names[1].matches("^[a-zA-Z\\-\\_]+$")
                 //&& names[2].matches("^[a-zA-Z\\-\\_]+$");
    }
    
    public static Student createFromFile(File file)
    {
        String[] names = file.getName().split("\\.");
        /*try{
            String name = new String(names[2].getBytes("UTF-8"));
            String firstname = new String(names[1].getBytes("UTF-8"));
            
        return new Student(name, firstname, new LocalImage(file));
        }catch(Exception ex){}*/
        return new Student(names[2], names[1],new LocalImage(file));
    }
    public static Student createDefault()
    {
        return new Student("", "", null);
    }

    @Override
    public String toXML()
    {
        String xml = "";
        
        xml += "<student>";
        
        xml += "<name>" + name + "</name>";
        xml += "<firstName>" + firstName + "</firstName>";
        
        if(picture == null)
            xml += "<image/>";
        else
            xml += "<image>" + picture.getID().toString(10) + "</image>";
        
        xml += "</student>";
        
        /*try{
            return new String(xml.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Details.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        return xml;
    }

    @Override
    public int compareTo(Student t) {
        if(this.name.toLowerCase().equals(t.name.toLowerCase()))
                return this.firstName.toLowerCase().compareTo(t.firstName.toLowerCase());

            return this.name.toLowerCase().compareTo(t.name.toLowerCase());
    }
}
