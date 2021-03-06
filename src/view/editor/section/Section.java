package view.editor.section;

import model.model.Student;
import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import view.FxmlElement;


public class Section extends FxmlElement
{
    public Section(EventHandler<ActionEvent> onUp, EventHandler<ActionEvent> onDown)
    {
        load("/view/editor/section/Section.fxml");
        
        this.onUp = onUp;
        this.onDown = onDown;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        super.initialize(url, rb);
        
        setSection(model.model.Section.createDefault());
    }
    
    private model.model.Section section;
    
    @FXML private EventHandler<ActionEvent> onUp;
    @FXML private EventHandler<ActionEvent> onDown;
    
    @FXML private Button upBtn;
    @FXML private Button downBtn;
    @FXML private Button removeBtn;
    @FXML private TextField title;
    @FXML private TextField path;
    @FXML private TextField conclusionText;
    @FXML private TextField introductionText;
    @FXML private Label nbStudents;
    @FXML private TitledPane bigTP;
    @FXML private Button leftBtn;
    @FXML private Button rightBtn;
    
    public model.model.Section getSection()
    {
        return section;
    }
    public void setSection(model.model.Section section)
    {
        this.section = section;
        
        updateStudents(section.students);
        title.setText(section.name);
        bigTP.setText(section.name);
        introductionText.setText(section.introductionText);
        conclusionText.setText(section.conclusionText);
        if(section.positionLeft){
            leftBtn.fire();
        }else{
            rightBtn.fire();
        }
        path.setText(section.students.isEmpty() ? "" : section.students.size()-2 + " étudiants en mémoire");        
    }

    @Override
    public void resize(double width, double height)
    {
        super.resize(width, height);
        leftBtn.setLayoutX(width - 200);
        rightBtn.setLayoutX(width - 140);
        upBtn.setLayoutX(width - 80);
        downBtn.setLayoutX(width - 55);
        removeBtn.setLayoutX(width - 30);
    }
    
    public void setStudents(Collection<Student> students)
    {
        section.setStudents(students);
        updateStudents(students);
    }
    protected void updateStudents(Collection<Student> students)
    {
        String plur = students.size()-2 > 1 ? "s" : "";
        nbStudents.setText((students.size()-2 <0  ? students.size() : students.size()-2) + " étudiant" + plur + " trouvé" + plur + "!");

        if(students.isEmpty())
            nbStudents.setTextFill(Paint.valueOf("red"));
        else
            nbStudents.setTextFill(Paint.valueOf("green"));
    }
    
    private Collection<Student> extractFolder(File dir)
    {
        Collection<Student> students =  Stream.of(dir.listFiles())
                                        .filter(File::exists)
                                        .filter(File::isFile)
                                        .filter(Student::isValid)
                                        .map(Student::createFromFile)
                                        .collect(Collectors.toList());
        Collections.sort((List<Student>) students);
        
        //Add IntroductionText and ConclusionText as student
        Collection<Student> finalStudents = new LinkedList<>();
        finalStudents.add(new Student(model.model.Section.SECTION_INTRODUCTION_KEY, null, null));
        finalStudents.addAll(students);
        finalStudents.add(new Student(model.model.Section.SECTION_CONCLUSION_KEY, null, null));
        return finalStudents;
    }
    
    @FXML protected void handleBrowse(ActionEvent event)
    {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Choisissez un dossier contenant les images.");
        
        File dir = dc.showDialog(((Node)event.getSource()).getScene().getWindow());
        if(dir != null && dir.exists())
        {
            path.setText(dir.getPath());
            setStudents(extractFolder(dir));
        }
    }
    
    @FXML protected void handleTitleChanged(KeyEvent event)
    {
        section.name = title.getText();
        bigTP.setText(section.name);
    }
    @FXML protected void handleIntroChanged(KeyEvent event)
    {
        section.introductionText = introductionText.getText();
    }
    @FXML protected void handleConclChanged(KeyEvent event)
    {
        section.conclusionText = conclusionText.getText();
    }
    
    @FXML protected void handleIsLeft(ActionEvent event)
    {
        leftBtn.setStyle("-fx-base: #a4a4a4;");
        rightBtn.setStyle("");
        section.positionLeft = true;
    }
    @FXML protected void handleIsRight(ActionEvent event)
    {
        rightBtn.setStyle("-fx-base: #a4a4a4;");
        leftBtn.setStyle("");
        section.positionLeft = false;
    }
    @FXML protected void handleUp(ActionEvent event)
    {
        onUp.handle(new ActionEvent(this, event.getTarget()));
    }
    @FXML protected void handleDown(ActionEvent event)
    {
        onDown.handle(new ActionEvent(this, event.getTarget()));
    }
    @FXML protected void handleRemove(ActionEvent event)
    {
        ((Pane)this.getParent()).getChildren().remove(this);
    }   
    
}
