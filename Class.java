/**
 * This class is used to represent each Course offered at the Institution.
 * Creates an instance for each section of every Course.
 */
public class Class{
  // Represents the classCode of this course.
  private String classCode;
  // Represents when the class starts.
  // .5 represents 30 minutes.
  private double beginTime;
  // Represents when the class ends.
  private double endTime;
  // Represents which days this course is offered. Order of indices
  // represent monday to friday, and a true value represents that the 
  // class is offered that day.
  private boolean[] days;
  // All the days a course can fall on.
  private String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday",
                                 "Thursday", "Friday"};

  /**
   * Ctor for the class Class
   *
   * @param code Represents the class Code.
   * @param begin Represents the beginning time of the class.
   * @param end   Represents the ending time of the class.
   * @param days  Represents whether the class is three days or two
   */
  public Class(String code, double begin, double end, boolean[] days){
    classCode = code;
    beginTime = begin;
    endTime = end;
    this.days = days;
  }
  /**
   * Changes a value of the boolean array to true to represent that
   * the course is offered that day.
   * @param day Integer that represents the index to be set to true.
   */
  public void setDay(int day){
    days[day] = true;
  }
  /**
   * @return Returns the boolean array of this class.
   */
  public boolean[] getDays(){
    return days;
  }
  /**
   * @return returns the time, represented in amount of hours, of when
   *         the class starts.
   */
  public double getBegin(){
    return beginTime;
  }
  /**
   * @return Returns the time, represented in amount of hours, of when
   *         the class ends.
   */
  public double getEnd(){
    return endTime;
  }
  /**
   * @return Returns the Code of this course.
   */
  public String getCode(){
    return classCode;
  }
  /**
   * Converts the beginning of the class, or end of the class, time
   * to a string.
   * @return Returns the string of the time.
   */
  public String toTime(double time) {
      // Truncates the value beyond '.' in the double.
      int hour = (int) time;
      // Subtracts the hour from total time and multiplies it by 60 to
      // Get the amount of minutes.
      int minute = (int) ((time - hour) * 60);
      // If minute is equal to 0, concatenate another 0 to represent
      // the full time.
      if (minute == 0)
        return "" + hour + ":" + minute + "0";
      // Returns the concatenation of hour, ':' and minute.
      return "" + hour + ":" + minute;
  }
  /**
   * Overrides the to string method to return a string that gives us
   * the days, and time frames this class is offered.
   * @return Returns a string that gives us the course code and the times
   *         the class is provided.
   */
  @Override
  public String toString() {
    // Initializes the string with just the Class Code.
    String classes = classCode;
    // Iterates through all five weekdays
    for (int i = 0; i < days.length; i++){
      // If the boolean switch is true then concatenate the respective day
      // of the week and the time frame of the class.
      if (days[i]) {
        classes = classes + " " + daysOfWeek[i] + ": " + 
                  toTime(beginTime) + "-" + toTime(endTime) + " ";
      }
    } 
    return classes;
  }
}
