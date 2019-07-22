/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aoocw1;

import aoocw1.Controller2;
import aoocw1.QuizModel;
import aoocw1.TeamBox;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

/**
 *
 * @author 15078306
 */
public class View3 extends Application implements Observer{
    private QuizModel model;
    private Controller2 controller;
    
    private Button nextQuestionButton;
    private TeamBox[] teamBoxes;
    private TextArea questionDisplay;
    
    private PlayerNumberPopup p;
    private TripleAnswerPopup t;
    
    private Stage mainStage;
    
    
    @Override
    public void start(Stage primaryStage) {
        
        model=new QuizModel("questionTextFile.txt");
        model.addObserver(this);
        
        controller=new Controller2(model);
        controller.setView(this);
        
        HBox teams=setUpTeamAreas();
        
        nextQuestionButton=new Button(">");
        nextQuestionButton.setOnAction((ActionEvent event) -> {
           controller.nextQuestion();
        });
        //this is the space used to display questions on screen
        questionDisplay=new TextArea();
        questionDisplay.setText("Start of game");
        questionDisplay.setEditable(false);
       
        TextField roomDisplay=new TextField("Enter room");
        TextField readerDisplay=new TextField("Enter reader name");
        TextField roundDisplay=new TextField("Enter round");
        TextField tournamentDisplay=new TextField("Enter tournament");
        
        //add all nodes to a single root
        VBox root = new VBox(25);
        
        root.getChildren().add(teams);
        root.getChildren().add(nextQuestionButton);
        root.getChildren().add(questionDisplay);
        root.getChildren().add(roomDisplay);
        root.getChildren().add(readerDisplay);
        root.getChildren().add(roundDisplay);
        root.getChildren().add(tournamentDisplay);
        
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setTitle("Advanced Object Oriented");
        primaryStage.setScene(scene);
        mainStage=primaryStage;
        mainStage.show();
        
        update(null,null);
        
        p=new PlayerNumberPopup(controller);
        t=new TripleAnswerPopup(controller);    
    }

    private HBox setUpTeamAreas() {
        TeamBox t1=new TeamBox(controller,1);
        TeamBox t2=new TeamBox(controller,2);
        teamBoxes=new TeamBox[2];
        teamBoxes[0]=t1;
        teamBoxes[1]=t2;
        HBox teams=new HBox(50);
        teams.getChildren().add(teamBoxes[0]);
        teams.getChildren().add(teamBoxes[1]);
        return teams;
    }

    /**
     * @param args the command line arguments
     */
    
    public void enableBonusButton(int teamNumber, boolean enable){
        teamBoxes[teamNumber-1].bonusButton.setVisible(enable);
    }

    public void enableBuzzButton(int teamNumber, boolean enable){
        teamBoxes[teamNumber-1].buzzButton.setVisible(enable);
    }
    
    //disables all buttons 
    public void endGame(){
        questionDisplay.setText("end");
        nextQuestionButton.setVisible(false);
        controller.configureButtons();
    }
   // newQuestionText represents the text of a question and its answer
   public void changeQuestionText(String[] newQuestionText){
       questionDisplay.setText(newQuestionText[0]+"\n"+newQuestionText[1]);
       
   }
   // new TeamText variable represent each teams scores
   public void setLabels(String[] newTeam1Text,String[]newTeam2Text){
       for (int i=2;i<15;i++){
           teamBoxes[0].changeLabelText(i,newTeam1Text[i-2]);
           teamBoxes[1].changeLabelText(i,newTeam2Text[i-2]);
       }
       
   }
   
   public void openMainView(){
      mainStage.show();
      t.hide();
      p.hide();
      
   }
   public void openPlayerView(){
      mainStage.hide();
      t.hide();
      p.show();//
       
   }
   
   public void openDoubleView(){
      mainStage.hide();
      t.show();
      t.hideInterButton();
      p.hide();
   }
   
   public void openTripleView(){
      mainStage.hide();
      t.show();
      t.showInterButton();
      p.hide();
   }
 
    public static void main(String[] args) {
        launch(args);
    }
    
    //updates whether the buttons are usable and the scores of the two teams
    @Override
    public void update(Observable o, Object arg) {
        controller.configureButtons();
        controller.updateScores();
    }
    


    

    


    
 
    


    
    
}
