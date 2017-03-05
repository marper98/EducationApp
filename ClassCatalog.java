import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.*;

public class ClassCatalog{
  // Hashtable that will store all the courses provided by the institution.
  private HashTable catalog;

  public ClassCatalog(String fname) {
    catalog = new HashTable(50);
    populateCatalog(fname);
  }

  public void populateCatalog(String fname){
    File file = new File(fname);
    try {
      Scanner scanner = new Scanner(file);
      scanner.next();
      while(scanner.hasNextLine()){
        String code = scanner.nextLine().toLowerCase()
                          .replaceAll("\\s+","");

        String timeFrames = scanner.nextLine().trim();

        timeFrames = timeFrames.replaceAll("\\P{Print}", " ");
        timeFrames = timeFrames.replaceAll("\\p{C}", " ");
        timeFrames = timeFrames.replaceAll("\\s+", " ");

        String[] timeArr = timeFrames.split(" ");

        if (timeArr.length % 4 == 0) {
          for (int i = 0; i < timeArr.length;) {
            boolean[] days = whichDays(timeArr[i++]);
            double beginTime = timeToDouble(timeArr[i++]);
            i++;
            double endTime = timeToDouble(timeArr[i++]);
            code = code.replaceAll("\\P{Print}", "");
            code = code.replaceAll("\\p{C}", "");
            Class course = new Class(code, beginTime, endTime, days);
            catalog.insert(course);
          }
        }
      }
    } 
    catch(FileNotFoundException e){}
  }

  public LinkedList<Class> getClasses(String code) {
    return catalog.get(code);
  }
  public boolean findClass(String code){
    return catalog.contains(code.toLowerCase());
  }

  private boolean[] whichDays(String days) {
    boolean[] week = new boolean[5];
    char[] daysOfWeek = {'M', 'T', 'W', 'R', 'F'};

    //Checks if OM is in array and remove
    for (int i = 0; i < days.length(); i++) {
      if (days.charAt(i) == 'O') {
        days = days.substring(days.indexOf(",") + 1, days.length());
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

  public LinkedList<Class>[] getList() {
    return catalog.getList();
  }

  private double timeToDouble(String time) {
    try {
      int colon = time.indexOf(":");
      String hourStr = time.substring(0, colon);
      String minuteStr = time.substring(colon + 1, time.length());

      double hour = Double.parseDouble(hourStr);
      double minute = Double.parseDouble(minuteStr) / 60;

      //Rounds the hour up if minute is larger than 30 ex. 11:55
      if (minute > 0.5) {
        return hour+1;
      }
      //Returns the time as a decimal otherwise
      else {
        return hour + 0.5;
      }
    }
    catch (IndexOutOfBoundsException e) {
      return -1;
    }
  }
  /**
   * HashTable implementation to store all offered sections by the type of
   * course they are.
   *
   */
  protected class HashTable{
    // Array of LinkedLists that will store all Courses.
    private LinkedList<Class>[] hashtable;
    // Amount of sections offered.
    private int nelems;
    // Amount of Unique courses, not sections, divided by amount of buckets.
    private double load;
    /**
     * Ctor for the hashtable that initializes the Array of LinkedLists.
     *
     * @param size represents the initial size of the hashtable.
     */
    @SuppressWarnings("unchecked") 
    public HashTable(int size){
      // Initializes the hashtable.
      hashtable = new LinkedList[size];
      // Creates a new empty LinkedLIst in each bucket.
      for(int i=0; i<hashtable.length; i++){
        hashtable[i] = new LinkedList<Class>();
      }
      // Initializes load to be 0.
      load = 0;
    }
    /**
     * Returns the specified course's bucket of sections.
     *
     * @param code Represents the course we desire.
     * @return Returns the linkedlist of each instance of that course.
     */
    public LinkedList<Class> get(String code) {
      return hashtable[hash(code, hashtable.length, 0, hashtable)];
    }
    /**
     * Looks to see if the specified Course exists in the hashtable.
     *
     * @param code Represents the Course code of the Course to be looked for.
     * @return Returns true if the Course with the specified course code has
     *         been found, and returns false if it wasn't.
     */
    public boolean contains(String code){
      // Finds the hash value of the code.
      int findInd = hash(code, hashtable.length, 0, hashtable);
      // If the LinkedList is empty, then that means the course does not exist.
      if(hashtable[findInd].isEmpty()){
        // Returns false since the Course was not found.
        return false;
      }
      // Return true since the Course was found.
      return true;
    }
    /**
     * Inserts the Course to the hashtable, stores sections with the same course
     * code in the same bucket.
     *
     * @param current Represents the Section of the course to be inserted.
     * @return Returns false if the section of the course already is stored in
     *         the hashtable, and returns true if the section was added
     *         successfully.
     */
    public boolean insert(Class current){
      // If null is passed in throw a null pointer exception.
      if(current == null){
        throw new NullPointerException();
      }
      // If the load is greater than 2/3rds then rehash.
      if(load > 2.0/3){
        rehash();
      }
      // Hashes the course code.
      int newInd = hash(current.getCode(), hashtable.length, 0,
                        hashtable);
      // If the section already exists, return false.
      if(hashtable[newInd].contains(current)){
        return false;
      }
      // If the section is unique, then add the section to the LinkedList at the
      // specified bucket, increment nelems, reevaluate the load factor and
      // return true.
      hashtable[newInd].add(current);
      nelems++;
      load = numClasses()/(double)hashtable.length;
      return true;
    }
    /**
     * Gets the number of Unique Courses, not sections, provided by the
     * institution.
     *
     * @return Returns the number of unique Courses.
     */
    private int numClasses(){
      // Initiliazes number of Classes to 0.
      int numOfClasses = 0;
      // Iterates through each bucket and if a LinkedList in the bucket has a
      // size greater than 0, then increment numOfClasses.
      for(int i=0; i<hashtable.length; i++){
        if(hashtable[i].size() > 0){
          numOfClasses++;
        }
      }
      // Return numOfClasses.
      return numOfClasses;
    }
    /**
     * Resizes the hashtable and rehashes its elements.
     */
    @SuppressWarnings("unchecked")
    private void rehash(){
      // Creates a new array of LinkedList with double the size of our current
      // hash table.
      LinkedList<Class>[] newHashTable = new 
                                     LinkedList[hashtable.length*2];
      // Initializes newInd at zero.
      int newInd = 0;
      // Creates a new Empty LinkedList in each bucket of the new hashtable.
      for(int i=0; i<newHashTable.length; i++){
        newHashTable[i] = new LinkedList<Class>();
      }
      // Goes through each bucket of the old hashtable and rehashes the
      // LinkedLists with a size greater than 0.
      for(int k=0; k<hashtable.length; k++){
        if (hashtable[k].size() > 0) {
          newInd = hash(hashtable[k].get(0).getCode(), newHashTable.length
                      , 0, newHashTable);
          // Adds all the elements of the LinkedList to the new bucket.
          newHashTable[newInd].addAll(hashtable[k]);
        }
      }
      // Sets the hashtable to the newHashTable.
      hashtable = newHashTable;
    }
    /**
     * @return Returns the hashtable.
     */
    public LinkedList<Class>[] getList() {
      return hashtable;
    }
    /**
     * hashes the course code passed in for the Class to be placed in the bucket
     * of the index that is returned.
     * 
     * @param value Represents the course code to be hashed.
     * @param tablesize Represents the size of the table the Course is being
     *                  inserted to
     * @param collisions Represents the amount of collisions that have occurred
     *                   so far. 
     * @param hashtable Represents the hashtable that is being used to enter the
     *                  Courses into.
     */
    private int hash(String value, int tablesize, int collisions,
                     LinkedList<Class>[] hashtable){
      // Initializes hashValue at 0.
      int hashValue = 0;
      // mods the hash value of the string passed in, then adds that to the
      // table size and number of colissions. Then mods the sum of those values
      // and sets hashValue to that value.
      hashValue = ((value.hashCode() % tablesize) + tablesize + collisions
                  ) % tablesize;
      // If the hashValue already contains elements and those elements don't
      // share the same course code, then call hash again and increment
      // collisions.
      if(hashtable[hashValue].size() > 0 &&
         !hashtable[hashValue].get(0).getCode().equals(value)){
        return hash(value, tablesize, collisions + 1, hashtable);
      }
      // return hashValue.
      return hashValue;
    }
  }

}
