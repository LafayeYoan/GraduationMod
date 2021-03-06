package controller;

import java.awt.GraphicsEnvironment;
import view.editor.window.EditorWindow;


public class Graduation
{
    /**
     * Entry Point
     * 
     * @param args Program arguments
     */
    public static void main(String[] args)
    {
        System.setProperty("file.encoding","UTF-8");
        EditorWindow.run();
    }
    
    protected static void listAllFonts()
    {
        for(String s : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames())
            System.out.println(s);
    }
}
