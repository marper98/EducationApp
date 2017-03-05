import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class ClassCatalog{
  private HashTable catalog;

  public ClassCatalog(String fname) {
    catalog = new HashTable(50);
    getCourses(fname);
  }

  public void getCourses(String fname){
    File file = new File(fname);
    try {
      Scanner scanner = new Scanner(file);
      while(scanner.hasNextLine()){
        String code = scanner.nextLine().toLowerCase()
                          .replaceAll("\\s+","");
        String timeFrames = scanner.nextLine();

        Scanner wordscan = new Scanner(timeFrames);
        
        //Converts the days of week into boolean array
        boolean[] days = whichDays(wordscan.next());
        //Parses the beginning time
        double beginTime = timeToDouble(wordScan.next());
        //Skips the dash
        wordScan.next();
        //Parses the end time
        double endTime = timeToDouble(wordScan.next());
      
        //Inserts the class into the hash table
        Class course = new Class(code, beginTime, endTime, days);
        hashTable.insert(course);
      }
    } 
    catch(FileNotFoundException e){}
  }

  /**
   * Returns the boolean array for which days of the week this class is on
   * @param days The string stating the days this class is meeting
   * @return The boolean array for days of the week
   */
  private boolean[] whichDays(String days) {
    boolean[] week = new boolean[5];
    char[] daysOfWeek = {'M', 'T', 'W', 'R', 'F'};

    //Checks if OM is in array and remove
    for (int i = 0; i < days.length; i++) {
      if (days.charAt(i) == 'O') {
        days = days.subString(days.indexOf(",") + 1, days.length());
      }
    }

    //Turns days into a char array
    char[] daysLetters = days.toCharArray();

    for (int i = 0; i < daysLetters.length; i++) {
      for (int j = 0; j < daysOfWeek.length; j++) {
        if (daysLetters[i] == daysOfWeek[j]) {
          week[j] = true;
        }
      }
    } 
    return week;
  }

  private double timeToDouble(String time) {
    try {
      int colon = time.indexOf(":");
      String hourStr = time.subString(0, colon);
      String minuteStr = time.subString(colon + 1, time.length());

      double hour = Double.parseDouble(hourStr);
      double minute = Double.parseDouble(minuteStr) / 60;

      //Rounds the hour up if minute is larger than 30 ex. 11:55
      if (minute > 0.5) {
        return hour+1;
      }
      //Returns the time as a decimal otherwise
      else {
        return hour + minute;
      }
    }
    catch (IndexOutOfBoundsException e) {}
  }

  protected class HashTable{
    private LinkedList<Class>[] hashtable;
    private int nelems;
    public HashTable(int size){
      hashtable = new LinkedList[size];
      for(int i=0; i<hashtable.length; i++){
        hashtable[i] = new LinkedList<Class>();
      }
    }
    public boolean insert(Class current){
      if(current == null){
        throw new NullPointerException();
      }
      if(load > 2.0/3){
        rehash();
      }
      int newInd = hash(current.getCode(), hashtable.length, 0);
      if(hashtable[newInd].contains(current)){
        return false;
      }
      hashtable[newInd].add(current);
      nelems++;
      load = numClasses()/(double)hashtable.length;
      return true;
    }
    private int numClasses(){
      int numOfClasses = 0;
      for(int i=0; i<hashtable.length; i++){
        if(hashtable[i].size() > 0){
          numOfClasses++;
        }
      }
      return numOfClasses;
    }
    private void rehash(){
      LinkedList<Class>[] newHashTable = new 
                                         LinkedList[hashtable.length*2];
      for(int i=0; i<newHashTable.length; i++){
        newHashTable[i] = new LinkedList<Class>();
      }
      for(int k=0; k<hashtable.length; k++){
        newInd = hash(hashtable[k].get(0).getCode, newHashTable.length);
        newHashTable[newInd].addAll(hashtable[k]);
      }
      hashtable = newHashTable;
    }
    private int hash(String value, int tablesize, int collisions){
      int hashValue = 0;
      hashValue = ((value.hashCode() % tablesize) + tablesize + collisions
                  ) % tablesize;
      if(hashtable[hashValue].size() > 0 && 
         !hashtable[hashValue].get(0).getCode().equals(value)){
        return hash(value, tablesize, collisions + 1);
      }
      return hashValue;
    }
  }

}
