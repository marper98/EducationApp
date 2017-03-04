import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class StudentApp extends JFrame implements ActionListener{
  SpinnerModel beginningTime;
  SpinnerModel endingTime;
  JButton setTimeButton;
  JTextField classPlanOne;
  JTextField classPlanTwo;
  JTextField classPlanThree;
  JTextField classPlanFour;
  JTextField classPlanFive;
  JButton planButton;
  JSpinner beginSpin;
  JSpinner endSpin;
  Schedule mySchedule;

  public StudentApp(){
  
    beginningTime = new SpinnerNumberModel(8, 8, 22, 1);
    endingTime = new SpinnerNumberModel(22, 9, 22, 1);
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
    classPlanFive = new JTextField(10);
    classPlanOne.setEditable(false);  
    classPlanTwo.setEditable(false);  
    classPlanThree.setEditable(false);  
    classPlanFour.setEditable(false);  
    classPlanFive.setEditable(false);
    planButton = new JButton("Plan Courses");
    planButton.setEnabled(false);
    JPanel classEnrollPane = new JPanel(new GridLayout(6, 1));
    classEnrollPane.add(classPlanOne);  
    classEnrollPane.add(classPlanTwo);  
    classEnrollPane.add(classPlanThree);  
    classEnrollPane.add(classPlanFour);  
    classEnrollPane.add(classPlanFive);  
    classEnrollPane.add(planButton);
    JPanel mainpane = new JPanel(new BorderLayout());
    mainpane.add(timePanel, "North");
    mainpane.add(classEnrollPane, "South");  
    setTimeButton.addActionListener(this);
    planButton.addActionListener(this);
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
        classPlanOne.setEnabled(true);
        classPlanTwo.setEnabled(true);
        classPlanThree.setEnabled(true);
        classPlanFour.setEnabled(true);
        classPlanFive.setEnabled(true);
        planButton.setEnabled(true);
      }
    }
  }
  private boolean validTime(int start, int end){
    if(start < end){
      mySchedule = new Schedule(start, end);
      return true;
    }
    return false;
  }
  public static void main(String[] args){
    new StudentApp();
  }
}
