public class Class{
  private String classCode;
  private double beginTime;
  private double endTime;
  private boolean[] days;
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

  public void setDay(int day){
    days[day] = true;
  }

  public boolean[] getDays(){
    return days;
  }

  public double getBegin(){
    return beginTime;
  }

  public double getEnd(){
    return endTime;
  }

  public String getCode(){
    return classCode;
  }

  public String toTime(double time) {
      int hour = (int) time;
      int minute = (int) ((time - hour) * 60);
      return "" + hour + ":" + minute;
  }

  @Override
  public String toString() {
    String classes = classCode;
    for (int i = 0; i < days.length; i++) {
      if (days[i]) {
        classes = classes + ": " + daysOfWeek[i] + " " + 
                  toTime(beginTime) + "-" + toTime(endTime) + " ";
      }
    } 
    return classes;
  }
}
