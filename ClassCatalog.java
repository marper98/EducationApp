import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class ClassCatalog{

  public static void getCourses(String fname){
    File file = new File(fname);
    try{
      Scanner scanner = new Scanner(file);
      while(scanner.hasNextLine()){
        String nextLine = scanner.nextLine().toLowerCase()
                          .replaceAll("\\s+","");
        String timeFrames = scanner.nextLine();
      }
    }catch(FileNotFoundException e){}
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
