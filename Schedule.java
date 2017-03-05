import java.util.ArrayList;

public class Schedule{
  private int beginday;
  private int endday;
  private boolean[][] timeUsed;
  private ArrayList<Class> planned;

  public Schedule(int begin, int end){
    beginday = begin;
    endday = end;
    timeUsed = new boolean[5][(end-begin)*2];
    planned = new ArrayList();
  }

  public int getBegDay(){
    return beginday;
  }
  public int getEndDay(){
    return endday;
  }
  public ArrayList<Class> getPlan(){
    return planned;
  }
  public boolean planCourse(Class planned){
    boolean success = true;
    if(planned.getBegin() < beginday || planned.getBegin() > endday){
      success = false;
      return success;
    }
    if(planned.getEnd() < beginday || planned.getEnd() > endday){
      success = false;
      return success;
    }
    int startOfClass = (int)((planned.getBegin() - beginday)*2);
    int endOfClass = (int)((planned.getEnd() - beginday)*2);
    for(int i=0; i<timeUsed.length; i++){
      if(planned.getDays()[i]){
        for(int k=startOfClass; k<endOfClass; k++){
          if(timeUsed[i][k]){
            success = false;
            return success;
          }
        } 
      }
    }
    successfullyPlan(planned, startOfClass, endOfClass);
    this.planned.add(planned);
    return success;
  }
  private void successfullyPlan(Class planned, int startOfClass, int endOfClass){
    for(int i=0; i<timeUsed.length; i++){
      if(planned.getDays()[i]){
        for(int k=startOfClass; k<endOfClass; k++){
          timeUsed[i][k] = true;
        }
      }
    }
  }
  public void resetSchedule(){
    timeUsed = new boolean[5][(endday-beginday)*2];
    planned = new ArrayList();
  }
}
