public class Class{
  private String classCode;
  private int begintime;
  private int endtime;
  private boolean[] Days;
  /**
   * Ctor for the class Class
   *
   * @param code Represents the class Code.
   * @param begin Represents the beginning time of the class.
   * @param end   Represents the ending time of the class.
   * @param days  Represents whether the class is three days or two
   */
  public Class(String code, int begin, int end){
    classCode = code;
    begintime = begin;
    endtime = end;
    Days = new boolean[5];
  }
  public void setDay(int day){
    Days[day] = true;
  }
  public boolean[] getDays(){
    return Days;
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
