import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

public class StudentApp extends JFrame implements ActionListener{
  private SpinnerModel beginningTime;
  private SpinnerModel endingTime;
  private JButton setTimeButton;
  private JTextField classPlanOne;
  private JTextField classPlanTwo;
  private JTextField classPlanThree;
  private JTextField classPlanFour;
  private JTextArea VACHelper;
  private JButton planButton;
  private JButton newSchedule;
  private JButton newClassTime;
  private JSpinner beginSpin;
  private JSpinner endSpin;
  private Schedule mySchedule;
  private ClassCatalog myClasses;

  public StudentApp(String fname){
    myClasses = new ClassCatalog(fname);
    beginningTime = new SpinnerNumberModel(8, 8, 20, 1);
    endingTime = new SpinnerNumberModel(22, 10, 22, 1);
    JPanel planTimeFrame = new JPanel(new BorderLayout());
    planTimeFrame.add(new JLabel("What time do you want to start and end"+
                                 " your day? "), "West");
    JPanel timeSpinners = new JPanel();
    beginSpin = new JSpinner(beginningTime);
    endSpin = new JSpinner(endingTime);
    timeSpinners.add(beginSpin);
    timeSpinners.add(endSpin);
    JPanel timePanel1 = new JPanel(new GridLayout(1, 2));
    timePanel1.add(planTimeFrame);
    timePanel1.add(timeSpinners);
    setTimeButton = new JButton("Set Time");
    JPanel timePanel2 = new JPanel();
    timePanel2.add(setTimeButton, "Center");
    JPanel timePanel = new JPanel(new GridLayout(2, 1));
    timePanel.add(timePanel1);
    timePanel.add(timePanel2);
    classPlanOne = new JTextField(10);
    classPlanTwo = new JTextField(10);
    classPlanThree = new JTextField(10);
    classPlanFour = new JTextField(10);
    flipTextEdit(false);
    planButton = new JButton("Plan Courses");
    newSchedule = new JButton("Plan New Courses");
    newClassTime = new JButton("Plan New Time");
    planButton.setEnabled(false);
    newSchedule.setEnabled(false);
    newClassTime.setEnabled(false);
    JPanel classEnrollPane = new JPanel(new GridLayout(6, 1));
    classEnrollPane.add(classPlanOne);  
    classEnrollPane.add(classPlanTwo);  
    classEnrollPane.add(classPlanThree);  
    classEnrollPane.add(classPlanFour);  
    JPanel classEnrollButtons = new JPanel(new GridLayout(1, 3));
    classEnrollButtons.add(newSchedule);
    classEnrollButtons.add(planButton);
    classEnrollButtons.add(newClassTime);
    classEnrollPane.add(classEnrollButtons);
    VACHelper = new JTextArea(10, 10);
    VACHelper.setEditable(false);
    JPanel mainpane = new JPanel(new BorderLayout());
    mainpane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    mainpane.add(timePanel, "North");
    mainpane.add(classEnrollPane);  
    mainpane.add(VACHelper, "South");
    setTimeButton.addActionListener(this);
    planButton.addActionListener(this);
    newSchedule.addActionListener(this);
    newClassTime.addActionListener(this);
    this.getContentPane().add(mainpane);
    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }
  public void actionPerformed(ActionEvent e){
    if(e.getSource() == setTimeButton){
      int starting = (Integer)beginSpin.getValue();
      int ending = (Integer)endSpin.getValue();
      if(validTime(starting, ending)){
        setTimeButton.setEnabled(false);
        beginSpin.setEnabled(false);
        endSpin.setEnabled(false);
        flipTextEdit(true);
        planButton.setEnabled(true);
      }
    }
    if(e.getSource() == planButton){
        planButton.setEnabled(false);
        String[] classChoices = new String[5];
        classChoices[0] = classPlanOne.getText().trim().replaceAll("\\s+","");
        classChoices[1] = classPlanTwo.getText().trim().replaceAll("\\s+","");
        classChoices[2] = classPlanThree.getText().trim().replaceAll("\\s+","");
        classChoices[3] = classPlanFour.getText().trim().replaceAll("\\s+","");   
        boolean foundClasses = true;
        for(int i=0; i<classChoices.length; i++){

          if(!myClasses.findClass(classChoices[i])){
            foundClasses = false;
          }
        }
        if(foundClasses){
          boolean success = true;
          flipTextEdit(false);
          outerloop:
          for(int i=0; i<classChoices.length; i++){
            LinkedList<Class> possibleChoices =
              myClasses.getClasses(classChoices[i]);
            Iterator<Class> potential = possibleChoices.iterator();
            while(potential.hasNext()){
              Class possible = potential.next();
              if(mySchedule.planCourse(possible)){
                break;
              }
              if(!potential.hasNext()){
                success = false;
                break outerloop;
              }
            }
          }
          finalSchedule(success, mySchedule.getPlan());
          newSchedule.setEnabled(true);
          newClassTime.setEnabled(true);
        }else{
          VACHelper.setText("Not all classes were found, re-enter" +
                            " new classes.");
          planButton.setEnabled(true);
        }
    }
    if(e.getSource() == newSchedule){
      flipTextEdit(true);
      planButton.setEnabled(true);
      beginSpin.setEnabled(true);
      endSpin.setEnabled(true);
      mySchedule.resetSchedule();
    }
    if(e.getSource() == newClassTime){
      flipTextEdit(false);
      setTimeButton.setEnabled(true);
    }
  }
  /**
   * Changes the Editable state of the four JTextFields
   *
   * @param flip represents the boolean to be passed to the
   *        setEditable() method for the four JTextFields.
   */ 
  private void flipTextEdit(boolean flip){
    classPlanOne.setEditable(flip);
    classPlanTwo.setEditable(flip); 
    classPlanThree.setEditable(flip);
    classPlanFour.setEditable(flip);
  }
  private void finalSchedule(boolean done, ArrayList<Class> arr){
    if(done){
      VACHelper.setText("Here is a suggested plan for your schedule with your"+
                        " desired courses.");
      for(int i=0; i<arr.size(); i++){
        VACHelper.append("\n" +arr.get(i).toString() + arr.size()); 
      }
    }else{
      VACHelper.setText("We were unable to finalize a Schedule with your" +
                        " Selected courses, please enter a new time frame,"+
                        " and/or a new set of courses."); 
    }
          
  }
  /**
   * Creates a new Schedule passing in the desired starting and ending time for
   * the student's school day.
   * 
   * @param start Represents the starting time of the student's schoolday
   * @param end Represents the ending time of the student's schoolday
   * @return Returns true if start is lesser than end and a new Schedule was
   *         created. Returns false if the inputs were not valid.
   */
  private boolean validTime(int start, int end){
    if(start < end){
      mySchedule = new Schedule(start, end);
      return true;
    }
    return false;
  }
  public static void main(String[] args){
    new StudentApp(args[0]);
  }
}
