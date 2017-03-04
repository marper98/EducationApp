import java.util.ArrayList;

public class Schedule{
  private int beginday;
  private int endday;
  private boolean[] timeleftTwoDays;
  private boolean[] timeleftThreeDays;
  private ArrayList<Class> planned;

  public Schedule(int begin, int end){
    beginday = begin;
    endday = endday;
    timeleftTwoDays = new boolean[beginday-endday];
    timeleftThreeDays = new boolean[beginday-endday];
    planned = new ArrayList();
  }

  public int getBegDay(){
    return beginday;
  }
  public int getEndDay(){
    return endday;
  }
  public boolean getTimeleft(int time, boolean twodays){
    if(twodays){
      return timeleftTwoDays[time];
    }
    return timeleftThreeDays[time];
  }
  private void setTimeLeft(int time, boolean twodays){
    if(twodays){
      timeleftTwoDays[time] = true;
    }else{
      timeleftThreeDays[time] = true;
    }
  }
  public void planNewClass(Class newClass, int time, boolean twodays){
    planned.add(newClass);
    setTimeLeft(time, twodays);
  }
  public ArrayList<Class> getPlan(){
    return planned;
  }
}
