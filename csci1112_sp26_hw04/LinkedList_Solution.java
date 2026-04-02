/*--------------------------------------------------------------------------
GWU - CS1112 Data Structures and Algorithms - Spring 2026

This is the implementation layer for a linked list based Music Catalog.  It
implements the interface prescribed by the MusicCatalog class.

This class could be more generalized; however, it is specialized to make
the Music Catalog application a little easier to implement for new students

authors: <your name>, Charles Peeke
--------------------------------------------------------------------------*/
public class LinkedList implements MusicCatalog {

    // For a linked-list based list, the head pointer
    private CatalogItem head;
    // The counter to track the number of elements in the list 
    private int count;

    // Parameterless Constructor
    public LinkedList() {
        count = 0;
        head = null;
    }

    /// Adds a Song to the catalog
    /// @param Song the Song to add to the catalog
    public void add(Song song) {
        CatalogItem item = new CatalogItem(song);
  
        // start at the beginning of the list
        CatalogItem it = head;
        // if the list is empty, handle the edge case
        if(it == null) {
            head = item;
            count = 1;
            return;
        }
        // find the tail
        while(it.next != null) {
            it = it.next;
        }
        // assign the new list item to tail.next
        it.next = item;
        item.next = null;
        count++;
    }

    /// Remove the first Song in the catalog shortening the list by one and
    /// returning the Song that was removed
    /// @return the Song that was removed if the catalog was not empty;
    ///         otherwise, null
    public Song remove() {
        if( head == null) {
            return null;
        }
        
        Song p = head.getSong();
        head = head.next;

        count -= 1;
        return p;
    }    

    /// Remove a specified Song from the catalog; shortening the list by one
    /// and returning the Song that was removed
    /// @param song the Song to search for and remove from the catalog
    /// @return the Song that was removed if the catalog was not empty;
    ///         otherwise, null
    public Song remove(Song song) {
        Song p;

        // if list empty, nothing to remove
        if( head == null ) {
            return null;
        }
        // start at the head
        CatalogItem it = head;
        // check to see if the head is the song, if so advance the head
        if( it != null && it.getSong().equals(song) ) {
            p = head.getSong();
            head = it.next;
            count -= 1;
            return p;
        }
        // iterate as long as it.next is not null and not the song
        while( it.next != null && !it.next.getSong().equals(song) ) {
            it = it.next;
        }
        // if it.next is null, then Song not in the list
        if(it.next == null) {
            return null;
        }
        // otherwise, the Song is it.next
        p = it.next.getSong();
        it.next = it.next.next;
        count -= 1;

        return p;
    }

    /// Removes all songs from the catalog and resets it to an empty state
    public void clear() {
        head = null;
        count = 0;
    }
    
    /// Checks the catalog to see if it contains no songs.
    /// @return true if the catalog contains no songs; otherwise, false
    public boolean isEmpty() {
        if(head == null) {
            return true;
        }
        return false;
    }
    
    /// Returns the number of songs stored in the catalog
    /// @return the number of songs stored in the catalog
    public int count() {
        return count;
    }
    
    /// Returns a Song in the list based on its index.  The index must
    /// be less than the count of the catalog and if not a null is returned
    /// @param i the index of the Song to get in the catalog where i must
    ///          be in the range [0, count-1]
    /// @return the Song stored at index i, or null if i is invalid
    public Song get(int i) {
        // Sanity Check: if i is larger than the count, index out of bounds
        if( i < 0 || i >= count ) {
            return null;
        }
        
        // for the linked-list, must seek the element from the head
        int idx = 0;
        CatalogItem it = head;
        while(idx < i) {
            it = it.next;
            idx++;
        }
        return it.getSong();
    }

    /// Returns a truth value that indicates whether a reference to the 
    /// specified song currently exists in the catalog
    /// @param song the Song to search for
    /// @return true if a reference to the Song exists in the catalog;
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
        if(count == 0 && head == null) {
            return true;
        }
        if(count == 1 && head != null && head.next == null) {
            return true;
        }

        int n = 1;
        CatalogItem it = head;
        while(it.next != null) {
            it = it.next;
            n++;
        }

        if(n != count) {
            return false;
        }

        return true;
    }

    /// Returns a string that contains information about the list and the 
    /// contents of the list.  This is mostly useful for visual debugging 
    /// @return a string containing information about the contents of the 
    ///         catalog
    public String toString() {
        String s = "";
        s = "LinkedList::count=" + count(); 
        s += ", isEmpty=" + isEmpty(); 
        s += ", ["; 
        CatalogItem it = head;
        while(it != null) {
            if(it != head) {
                s += ", ";
            }
            s += it.getSong().getTitle();
            s += " | ";
            s += it.getSong().getYear();
            it = it.next;
        }
        s += "]";

        return s; 
    }

    /// Returns the earliest and most recent years of all the songs in the
    /// catalog and then clears the catalog of all songs
    /// @return an array of the years of the earliest and most recent songs
    public int[] publish() {
        int[] years = new int[2];
        int oldYear = Integer.MAX_VALUE;
        int newYear = Integer.MIN_VALUE;
        years[0] = oldYear;
        years[1] = newYear;

        CatalogItem it = head;
        while(it != null) {
            int curYear = it.getSong().getYear();
            if (curYear < oldYear) {
                oldYear = curYear;
                years[0] = oldYear;
            }
            if (curYear > newYear) {
                newYear = curYear;
                years[1] = newYear;
            }
            it = it.next;
        }
        clear();
        return years;
    }

    //--------------------------------------------------------------------
    // Utilities
    //--------------------------------------------------------------------

    /// This function gathers all the search operations into one function
    /// @param Song the Song to search for
    /// @return if found, the index at which the Song resides; otherwise,
    ///         this function returns a -1
    private int search(Song song) {
        int i = -1;
        CatalogItem it = head;
        if( it == null ) {
            return i;
        }
        while(it != null) {
            i++;
            Song p = it.getSong();
            if(p.compareTo(song) == 0) {
                return i;
            }
            it = it.next;
        }
        return -1;
    }

    /// Insert a Song into the array by index
    /// @param p the Song to insert
    /// @param idx the index at which to insert p
    private void insert(Song p, int idx) {
        if(idx < 0 || idx > count || p == null) {
            return;
        }

        CatalogItem item = new CatalogItem(p);
        CatalogItem it;

        if(idx == 0) {
            if(head == null) {
                head = item;
            } else {
                head.next = head;
                head = item;
            }
        } else {
            it = head;
            int i = 0;
            while(it.next != null) {
                i++;
                if(i == idx) {
                    item.next = it.next;
                    it.next = item;
                    break;
                }
                it = it.next;
            }        
        }

        count++;
    }
}
