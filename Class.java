public class Class{
  private String classCode;
  private int begintime;
  private int endtime;
  private boolean twodays;
  /**
   * Ctor for the class Class
   *
   * @param code Represents the class Code.
   * @param begin Represents the beginning time of the class.
   * @param end   Represents the ending time of the class.
   * @param days  Represents whether the class is three days or two
   */
  public Class(String code, int begin, int end, boolean days){
    classCode = code;
    begintime = begin;
    endtime = end;
    twodays = days;
  }

  public int getBegin(){
    return begintime;
  }
  public int getEnd(){
    return endtime;
  }
  public String getCode(){
    return classCode;
  }
  public boolean getDays(){
    return twodays;
  }
}
