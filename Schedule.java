import java.util.ArrayList;
/**
 * Represents the suggested Schedule of the student based on the inputs of their
 * desired time frame, and courses.
 */
public class Schedule{
  // Represents the time when the student wants to begin their schoolday.
  private int beginday;
  // Represents the time when the student wants to end their schoolday.
  private int endday;
  // 2D array to represent the remaining available time the student has for
  // their courses. Available time is represented by false values. Each
  // individual index represents 30 minutes.
  private boolean[][] timeUsed;
  // All their planned courses.
  private ArrayList<Class> planned;

  /**
   * Ctor for the class that takes the student's desired beginning time
   * and ending time.
   * 
   * @param begin Represents the desired beginning time of the Student.
   * @param end Represents the desired ending time of the Student.
   */
  public Schedule(int begin, int end){
    // Sets beginday and endday to the respective parameters.
    beginday = begin;
    endday = end;
    // Initializes the 2D array to have represent the desired time frame
    // over the 5 days.
    timeUsed = new boolean[5][(end-begin)*2];
    // Initializes the ArrayList for planned courses.
    planned = new ArrayList();
  }
  /**
   * @return Returns when the Student desires to start their day.
   */
  public int getBegDay(){
    return beginday;
  }
  /**
   * @return Returns when the Student desires to end their day.
   */
  public int getEndDay(){
    return endday;
  }
  /**
   * @return Returns the student's suggested courses to plan.
   */
  public ArrayList<Class> getPlan(){
    return planned;
  }
  /**
   * Plans the course for the student. Returns true if the section of the
   * course fits into the student's schedule thus far, and returns false
   * if the section does not fit within the student's schedule.
   *
   * @param planned Represents the section to be planned.
   * @return Returns a boolean depending on whether the course was planned
   *         successfully or not.
   */
  public boolean planCourse(Class planned){
    // initialize the boolean to be returned to true.
    boolean success = true;
    // If the course starts outside of the Student's desired time frame
    // then set success to false and return the boolean.
    if(planned.getBegin() < beginday || planned.getBegin() > endday){
      success = false;
      return success;
    }
    // If the course ends outside of the Student's desired time frame
    // then set success to false and return the boolean.
    if(planned.getEnd() < beginday || planned.getEnd() > endday){
      success = false;
      return success;
    }
    // Substracts the beginning of the Student's day from the beginning and end
    // of the Course and multiplies that value by two to get the range of
    // indices to be iterated through the 2D array.
    int startOfClass = (int)((planned.getBegin() - beginday)*2);
    int endOfClass = (int)((planned.getEnd() - beginday)*2);
    // Iterate through each weekday.
    for(int i=0; i<timeUsed.length; i++){
      // If the course is offered on the weekday represented by the index,
      // then iterate through the respective array.
      if(planned.getDays()[i]){
        // Starting at the int startOfClass til the int endOfClass check if the
        // any of the indices are already true.
        for(int k=startOfClass; k<endOfClass; k++){
          // If one of the indices is true then set success to false and return
          // the boolean.
          if(timeUsed[i][k]){
            success = false;
            return success;
          }
        } 
      }
    }
    // If there is no coflict of schedule then plan the course and return true.
    successfullyPlan(planned, startOfClass, endOfClass);
    return success;
  }
  private void successfullyPlan(Class planned, int startOfClass, int endOfClass){
    // Iterate through each weekday.
    for(int i=0; i<timeUsed.length; i++){
      // Starting at the int startOfClass til the int endOfClass check if the
      // any of the indices are already true.
      if(planned.getDays()[i]){
        // Starting at the int startOfClass til the int endOfClass, switch all
        // the booleans to true.
        for(int k=startOfClass; k<endOfClass; k++){
          timeUsed[i][k] = true;
        }
      }
    }
    // Add the course to the arraylist.
    this.planned.add(planned);
  }
  /**
   * Resets the 2D array and the ArrayList of classes.
   */
  public void resetSchedule(){
    timeUsed = new boolean[5][(endday-beginday)*2];
    planned = new ArrayList();
  }
}
