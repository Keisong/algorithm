/* HashTableChained.java */

package dict;

import java.util.Random;

import list.*;
/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

private List[] listarray;
public int size;
public int sizeDup;
public int length = 127;
	
private boolean isPrime(int n) {
	if (n<=2) return false;
	for(int i=2;i*i<=n;i+=1){
		if (n%i==0) return false;
	}
	return true;
}

  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
	  while(!isPrime(sizeEstimate)) {
		  sizeEstimate++;
	  }
	  listarray = new List[sizeEstimate];
	  for (int i =0;i<sizeEstimate;i++) {
		  listarray[i]=new DList();
	  }
	  length = sizeEstimate;
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
	  listarray = new List[127];
	  for (int i =0;i<127;i++) {
		  listarray[i]=new DList();
	  }
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    int a = 7;
    int b = 13;
    int p = listarray.length+1;
    while(!isPrime(p)) p++;
    int N = listarray.length;
    int h = ((a*code + b)%p)%N;
    
    return h;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
    return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    return size==0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    Entry newEntry = new Entry ();
    newEntry.key=key;
    newEntry.value=value;
    int hashKey = compFunction(key.hashCode()); 
    listarray[hashKey].insertBack(newEntry);
    if(listarray[hashKey].length()>1) sizeDup++;
    size++;
    return newEntry;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
	int hashKey = compFunction(key.hashCode());
	if(listarray[hashKey]==null||listarray[hashKey].isEmpty()) return null;
	else if (listarray[hashKey].length()==1) {
		try {
			return (Entry)listarray[hashKey].front().item();
		}
		catch (InvalidNodeException e){
			e.printStackTrace();
			return null;
		}
	}
	else {
		try {
			Random rand = new Random();
			int index = rand.nextInt(listarray[hashKey].length());
			ListNode node=listarray[hashKey].front();
			for (int i =0; i<index-1;i++) {
				node=node.next();
			}
			return (Entry)node.item();
		}catch (InvalidNodeException e){
			e.printStackTrace();
			return null;
		}
	}
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    Entry deleteEntry = find(key);
    if (deleteEntry==null) return null;
    else {
    	try{
    		int hashKey = compFunction(key.hashCode());
    	
    	Random rand = new Random();
		int index = rand.nextInt(listarray[hashKey].length());
		ListNode node=listarray[hashKey].front();
		for (int i =0; i<index-1;i++) {
			node=node.next();
		}
		node.remove();
		size--;
    	}catch (InvalidNodeException e){
			e.printStackTrace();
			return null;
    	}
    	Entry entry=new Entry();
    	entry.key=find(key).key();
    	entry.value=find(key).value();
    	return entry;   	
    }
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
	  listarray=new List[listarray.length];
	  for(int i=0;i<listarray.length;i++)
		  listarray[i]=new DList();
	  size = 0;
  }
  
  public double collision(){
	//  return (double)(size-length+length*Math.pow((double)(1-1.0/(double)length), (double)size));
	  return this.size-listarray.length+listarray.length*Math.pow((1-(double)(1.0/(double)listarray.length)),(double) size);
  }

}
