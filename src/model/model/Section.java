package model.model;

import java.io.UnsupportedEncodingException;
import model.serializable.XMLSerializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Section implements XMLSerializable
{
    public Section(String name, String introductionText, String conclusionText, boolean positionLeft)
    {
        this.name = name;
        this.introductionText = introductionText;
        this.conclusionText = conclusionText;
        this.positionLeft = positionLeft;
        this.students = new LinkedList<>();
    }
    
    public Collection<Student> students;
    public String name;
    public boolean positionLeft;
    public String introductionText;
    public String conclusionText;
    public static final String SECTION_INTRODUCTION_KEY = "0x0000";
    public static final String SECTION_CONCLUSION_KEY = "0x0001";
    
    public static Section createDefault()
    {
        return new Section("Informatique","Ingénieurs Informatique", "Bravo aux Ingénieurs Informatique!", true);
    }
    
    public void addStudent(Student student)
    {
        students.add(student);
    }
    public void addStudents(Collection<Student> students)
    {
        this.students.addAll(students);
    }
    public void setStudents(Collection<Student> students)
    {
        this.students.clear();
        this.students.addAll(students);
    }

    @Override
    public String toXML()
    {
        String xml = "";
        
        xml += "<section>";
        
        xml += students.stream()
                .map(XMLSerializable::toXML)
                .reduce("", (s1,s2) -> s1 + s2);
        
        xml += "<name>" + name + "</name>";
        
        xml += "<introductionText>" + introductionText + "</introductionText>";
        
        xml += "<conclusionText>" + conclusionText + "</conclusionText>";
        
        xml += "<positionLeft>" + positionLeft + "</positionLeft>";
        
        xml += "</section>";
        try{
            return new String(xml.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Details.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
