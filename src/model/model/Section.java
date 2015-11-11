package model.model;

import model.serializable.XMLSerializable;
import java.util.Collection;
import java.util.LinkedList;


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
        
        return xml;
    }
}
