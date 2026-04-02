/*--------------------------------------------------------------------------
GWU - CS1112 Data Structures and Algorithms - Spring 2026

This is the implementation layer for an array based Music Catalog Service.  
It implements the interface prescribed by the MusicCatalog class.

This class could be more generalized; however, it is specialized to make
the MusicCatalog application a little easier to implement for new students

authors: <your name>, Charles Peeke
--------------------------------------------------------------------------*/
public class ArrayList implements MusicCatalog {
    // For an array-based list, the array itself
    private CatalogItem[] data;
    // The counter to track the number of elements in the list
    private int count;

    // Parameterless Constructor
    public ArrayList() {
        count = 0;
        data = new CatalogItem[2];
    }
   
    /// Adds a song to the catalog
    /// @param song the song to add to the catalog
    public void add(Song song) {
         // if the array is too small, allocate new space
        if(count == data.length) {
            reallocate();
        }
        // add the item to the end of the list
        data[count] = new CatalogItem(song);
        // update the count
        count++;
    }

    /// Remove the first song in the catalog shortening the list by one and
    /// returning the song that was removed
    /// @return the song that was removed if the catalog was not empty;
    ///         otherwise, null
    public Song remove() {
        if( count == 0) {
            return null;
        }
        
        Song s = data[0].getSong();
        for(int i = 1; i < count; i++) {
            data[i-1] = data[i];
        }

        count -= 1;
        return s;
    }

    /// Remove a specified song from the catalog shortening the list by one
    /// and returning the song that was removed
    /// @param song the song to search for and remove from the catalog
    /// @return the song that was removed if the catalog was not empty;
    ///         otherwise, null
    public Song remove(Song song) {
        // start with the assumption the item is not in the list
        int idx = -1;
        // find the item
        for(int i = 0; i < count; i++) {
            if(data[i].getSong().equals(song) ) {
                idx = i;
                break;
            }
        }
        // if idx is still negative, item is not in the list
        if(idx == -1) {
            return null;
        }

        Song p = data[idx].getSong();
        // otherwise, shift everything after that item up one position
        for(int i = idx; idx < count-1; i++) {       
            data[i] = data[i+1];                   
        }                                            
        // clear the last bucket for safety          
        data[count] = null;                         
        // update the count
        count--;

        return p;
    }
    
    /// Removes all songs from the catalog and resets it to an empty state
    public void clear() {
        data = new CatalogItem[2];
        count = 0;
    }
    
    /// Checks the catalog to see if it contains no songs.
    /// @return true if the catalog contains no songs; otherwise, false
    public boolean isEmpty() {
        if(count == 0) {
            return true;
        }
        return false;
    }
    
    /// Returns the number of songs stored in the catalog
    /// @return the number of songs stored in the catalog
    public int count() {
        return count;
    }
    
    /// Returns a song in the list based on its index.  The index must
    /// be less than the count of the the catalog and if not a null is 
    /// returned
    /// @param i the index of the song to get in the catalog where i must
    ///          be in the range [0,count-1]
    /// @return the song stored at index i, or null if i is invalid
    public Song get(int i) {
        // Sanity Check: if i is larger than the count, index out of bounds
        if( i < 0 || i >= count ) {
            return null;
        }
        
        // for the array, direct access the element
        return data[i].getSong();
    }

    /// Returns a truth value that indicates whether a reference to the 
    /// specified song currently exists in the catalog
    /// @param song the song to search for
    /// @return true if a reference to the song exists in the catalog;
    ///         otherwise, false
    public boolean contains(Song song) {
        if(search(song) == -1) {
            return false;
        }
        return true;
    }

    /// Returns a truth value indicating whether the catalog's structural
    /// integrity remains valid.  If the integrity is no longer valid,
    /// then the catalog should be invalidated and usage should not be 
    /// trusted
    /// @return true if the catalog integrity is valid; otherwise, false
    public boolean isIntegrityValid() {
        if(count < 0) {
            return false;
        }
        if(data == null) {
            return false;
        }
        if(count > data.length) {
            return false;
        }
        for(int i = 0; i < count; i++) {
            if(data[i] == null) {
                return false;
            }
        }

        return true;
    }

    /// Returns a string that contains information about the list and the 
    /// contents of the list.  This is mostly useful for visual debugging 
    /// @return a string containing information about the contents of the 
    ///         catalog
    public String toString() {
        String s = "";
        s = "ArrayList::allocated=" + data.length;
        s += ", count=" + count(); 
        s += ", isEmpty=" + isEmpty(); 
        s += ", ["; 
        for(int i = 0; i < count; i++) {
            if(i > 0) {
                s += ", ";
            }
            s += data[i].getSong().getTitle();
            s += " | ";
            s += data[i].getSong().getYear();
        }
        s += "]";
        return s;
    }

    /// Returns the earliest and most recent years of all the songs in the
    /// catalog and then clears the catalog of all songs
    /// @return an array of the years of the earliest and most recent songs
    public int[] publish() {
        int oldYear = Integer.MAX_VALUE;
        int newYear = Integer.MIN_VALUE;

        for(int i = 0; i < count; i++) {
            int curYear = data[i].getSong().getYear();
            if (curYear < oldYear) oldYear = curYear;
            if (curYear > newYear) newYear = curYear;
        }
        clear();
        return new int[] { oldYear, newYear };
    }

    //-------------------------------------------------------------------
    // Utilities
    //-------------------------------------------------------------------

    /// This function gathers all the search operations into one function
    /// @param song the song to search for
    /// @return if found, the index at which the song resides; otherwise,
    ///         this function returns a -1
    private int search(Song song) {
        for(int i = 0; i < count; i++) {
            Song s = data[i].getSong();
            if(s.compareTo(song) == 0) {
                return i;
            }
        }
        return -1;
    }

    /// Reallocates and grows the array when necessary.  It doubles in size
    /// when reallocated.
    private void reallocate() {
        CatalogItem[] temp = new CatalogItem[data.length*2];
        for(int i = 0; i < count; i++) {
            temp[i] = data[i];
        }
        data = temp;
    }

    /// Insert a song into the array by index
    /// @param p the song to insert
    /// @param idx the index at which to insert p
    private void insert(Song s, int idx) {
        if(idx < 0 || idx > count || s == null) {
            return;
        }
        if(count >= data.length) {
            reallocate();
        }
        for(int i = count; i > idx; i--) {
            data[i] = data[i-1];
        }
        count++;
        data[idx] = new CatalogItem(s);
    }

}
