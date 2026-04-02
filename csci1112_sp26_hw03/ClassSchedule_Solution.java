/*--------------------------------------------------------------------------
GWU CSCI 1112 Fall 2026
author: <your name>

This class encapsulates the logic to model scheduling a set of classes for a university
--------------------------------------------------------------------------*/

public class ClassSchedule {
    /// Performs a deep copy of the input schedule and returns the deep copy.
    /// This operation might be used to make a permanent backup of the data
    /// as it would make a unique and unlinked copy of the data.
    /// @param schedule the schedule array to copy
    /// @return the deep copy of the schedule array that were copied
    public static String[][] clone(String[][] schedule) {
        //TODO: FILL IN YOUR CODE HERE
    
        String[][] out = new String[schedule.length][schedule[0].length];
        for (int i=0; i < schedule.length; i++) {
            for (int j=0; j < schedule[0].length; j++) {
                out[i][j] = schedule[i][j];
            }
        }
        return out;
    }




    /// A referential copy (shallow copy of each row) and not an 
    /// element-wise copy (deep copy).  We are sorting elements with respect
    /// to the original data rather than generating a new set of data.
    /// @param schedule data containing the rows to reference
    /// @return an array containing a shallow copy of the input schedule 
    ///         rows
    public static String[][] createView(String[][] schedule) {
        // TODO : Implement Here

        String[][] view = new String[schedule.length][];
        for(int i = 0; i < schedule.length; i++) {
            view[i] = schedule[i];
        }
        return view;
    }
 
    //------------------------------------------------------------------    
    /// Compute the differential between start time (index 3) and end time 
    /// (index 4). The differential is not maintained in the data but is
    /// a virtual field derived by the calculation performed here
    /// @param classInfo a record from the scheduling data
    /// @return the difference in time between the end time and start time
    ///         in minutes
    public static int differential(String[] classInfo) {
        // TODO : Implement Here
        int startTime = duration(classInfo[3]); 
        int endTime = duration(classInfo[4]); 
        int difference = (endTime-startTime);
        return difference;
    }

    //-----------------------------------------------------------------    
    /// This utility function converts a time string from the "HH:mm:ss" 
    /// format into a value representing minutes
    /// @param time a string representing a time in "HH"mm:ss" format
    /// @return an integer representing the time converted to minutes
    private static int duration(String time) {
        String[] tokens = time.split(":");
        int h = Integer.parseInt(tokens[0]);
        int m = Integer.parseInt(tokens[1]);
        int t = h * 60 + m;
        return t;
    }

    //-----------------------------------------------------------------    
    /// Performs a comparison between two classes that is equivalent to a 
    /// less than operation so that a sort can use this function to order 
    /// classes. The less than criteria is an evaluation between the 
    /// differentials of two classes.
    /// @param class1 a class record that is used as the "left" operand for
    ///        a less than comparison 
    /// @param class2 a class record that is used as the "right" operand for
    ///        a less than comparison 
    /// @return returns true if the computed differential for class1 is less
    ///         than the computed differential for class2; otherwise, 
    ///         returns false (false implies that differential for class1 is
    ///         greater than or equal to class2)
    public static boolean lessThan(String[] class1, String[] class2) {
        // TODO : Implement Here
        int delta1 = differential(class1);
        int delta2 = differential(class2);
        if (delta1 < delta2 ) {
                return true;
        }
        return false;
    }

    //-----------------------------------------------------------------    
    /// Swaps references to classes.  Note that this is a "shallow" swap and
    /// not a "deep" swap meaning we swap at a reference level (between rows
    /// in view) and not at the value level
    /// @param view A shallow copy of a set of classes 
    /// @param i the index of the first reference to swap
    /// @param j the index of the second reference to swap
    public static void swapClasses(String[][] view, int i, int j) {
        // TODO : Implement Here

        String[] temp = view[i];
        view[i] = view[j];
        view[j] = temp;
    }

    //-----------------------------------------------------------------    
    /// Sorts (shallow) a set of references to classes in descending order 
    /// subject to the differential between ups and downs using selection 
    /// sort
    /// @param view A shallow copy of a set of classes 
    /// @return an array of profile information of 3 buckets with the 
    ///         respective buckets containing a count of 0: allocations, 
    ///         1:comparisons, and 2: swaps
    public static int[] sortSelection(String[][] view) {
        // TODO : Implement Here

        // profile[0:allocs (ignore profile), 1:comparisons, 2:swaps]
        int[] profile = new int[3];
        profile[0] += 3;            // 3 allocs [view, i, j]
    	for (int i = 0; i < view.length-1; i++) {
            int mxidx = i;
	    for (int j = i+1; j < view.length; j++) {
            	profile[1]++;       // count comparison
                    if( lessThan(view[mxidx], view[j]) ) {
                    	mxidx = j;
		    }
    	    }

            swapClasses(view, i, mxidx);
            profile[2]++;   // count swap
            // profile[0:allocs, 1:comparisons, 2:swaps]
    	}
        return profile;
    }

    //-----------------------------------------------------------------    
    /// Sorts (shallow) a set of references to classes in descending order 
    /// subject to the differential between ups and downs using bubble 
    /// sort
    /// @param view A shallow copy of a set of classes 
    /// @return an array of profile information of 3 buckets with the 
    ///         respective buckets containing a count of 0: allocations, 
    ///         1:comparisons, and 2: swaps
    public static int[] sortBubble(String[][] view) {
        // TODO : Implement Here

        // profile[0:allocs (ignore profile), 1:comparisons, 2:swaps]
        int[] profile = new int[3];
        profile[0] += 3;            // 3 allocs [view, i, j]
    	for (int i = 0; i < view.length-1; i++) {
            boolean swapped = false;  // note a mild optimization.  Students
            // don't need to use this feature
	        for (int j = view.length-1; j>i; j--) {
                profile[1]++;       // count comparison
                if( !lessThan(view[j], view[j-1]) ) {
                    swapClasses(view, j, j-1);
                    profile[2]++;   // count swap
                    swapped = true;     // again, students don't need
		        }
    	    }
            if(!swapped) return profile;  // again students don't need
            // profile[0:allocs, 1:comparisons, 2:swaps]
    	}
        return profile;
    }

    //-----------------------------------------------------------------    
    /// Sorts (shallow) a set of references to classes in descending order 
    /// subject to the differential between ups and downs using insertion 
    /// sort
    /// @param view A shallow copy of a set of classes 
    /// @return an array of profile information of 3 buckets with the 
    ///         respective buckets containing a count of 0: allocations, 
    ///         1:comparisons, and 2: swaps
    public static int[] sortInsertion(String[][] view) {
        // TODO : Implement Here

        // profile[0:allocs (ignore profile), 1:comparisons, 2:swaps]
        int[] profile = new int[3];
        profile[0] += 3;            // 3 allocs [view, i, j]
    	for (int i = 0; i < view.length; i++) {
	        for (int j = i; j > 0; j--) {
                profile[1]++;       // count comparison
                if( !lessThan(view[j], view[j-1]) ) {
                    swapClasses(view, j, j-1);
                    profile[2]++;   // count swap
		        } else {
                    break;
                }
    	    }
            // profile[0:allocs, 1:comparisons, 2:swaps]
    	}
        return profile;
    }

    //-----------------------------------------------------------------    
    /// Sorts (shallow) a set of references to classes in descending order 
    /// subject to the differential between ups and downs using a quicksort.
    /// @param view A shallow copy of a set of classes 
    /// @return an array of profile information of 3 buckets with the 
    ///         respective buckets containing a count of 0: allocations, 
    ///         1:comparisons, and 2: swaps
    public static int[] sortQuicksort(String[][] view) {
        // TODO : Implement Here

        // profile[0:allocs (ignore profile), 1:comparisons, 2:swaps]
        int[] profile = new int[3];
        profile[0] += 1;            // 1 allocs [view]
        sortQuicksortRecursive(view, 0, view.length-1, profile);
        return profile;

        // Grading note: the profile information in this approach is not 
        // really accurate in terms of showing the max depth of the stack.
        // It counts the number of allocations over all calls to quicksort 
        // which is not the same as counting the size of the stack.  So this
        // is a lazy approach but it serves to show how much memory is 
        // involved.  
    }

    //-----------------------------------------------------------------    
    /// Recursive wrapper for quicksort that sorts a set of class references
    /// in desceding order subject to the differential betweeen ups and 
    /// downs
    /// @param data A shallow copy of a set of classes
    /// @param left the left-most index of the subarray to sort
    /// @param right the right-most index of the subarray to sort
    /// @param profile a ref to the profile data used in this recursive 
    ///        descent 
    static void sortQuicksortRecursive(String[][] data, int left, int right, int[] profile) {
        profile[0] += 3;            // 3 allocs [data, left, right]
        profile[1]++;               // count comparison
        if (left < right) {
            profile[0] += 1;        // 1 allocs [partition]
            int partition = sortQuicksortPartition (data, left, right, profile);
            sortQuicksortRecursive( data, left, partition-1, profile );
            sortQuicksortRecursive( data, partition+1, right, profile );
        }
    }

    //-----------------------------------------------------------------    
    /// The partition selection for quicksort
    /// @param data A shallow copy of a set of classes
    /// @param left the left-most index of the subarray to sort
    /// @param right the right-most index of the subarray to sort
    /// @param profile a ref to the profile data used in this recursive 
    ///        descent 
    /// @return the position in data of the partition after partitioning
    static int sortQuicksortPartition(String[][] data, int left, int right, int[] profile) {
        profile[0] += 3;            // 3 allocs [data, left, right]
        profile[1]++;               // count comparison
        if (left == right)
            return left;
        profile[0] += 2;            // 3 allocs [partElement, swapPosition, i]
        String[] partElement = data[right];
        int swapPosition = right; 
        for (int i=right-1; i>=left; i--) {
            profile[1]++;           // count comparison
            if( lessThan(data[i], partElement) ) {
                swapPosition--;
                swapClasses(data, swapPosition, i);
                profile[2]++;       // count swap
            }
        }
        swapClasses(data, swapPosition, right);
        profile[2]++;               // count swap
        return swapPosition;
    }

    //-----------------------------------------------------------------    
    /// Performs a linear search on the set of records in view looking for a
    /// matching differential in the records with the diff input.
    /// @param view A shallow copy of a set of classes 
    /// @param diff the difference to find
    /// @return if a record matching diff is found, returns a non-negative 
    ///         number representing the index of the record, otherwise, 
    ///         return a negative number indicating failure.
    public static int searchIterative( String[][] view, int diff ) {
        // TODO : Implement Here
       for (int i=0; i<view.length; i++) {
            //System.out.println( differential(view[i]) );
            if (diff == differential(view[i])) {
                return i;
            }
        }
        return -1;
    }

    //-----------------------------------------------------------------    
    /// Performs a binary search on the set of records in view looking for a
    /// matching differential in the records with the diff input.
    /// @param view A shallow copy of a set of classes 
    /// @param diff the difference to find
    /// @return if a record matching diff is found, returns a non-negative 
    ///         number representing the index of the record, otherwise, 
    ///         return a negative number indicating failure.
    public static int searchBinary( String[][] view, int diff ) {
        // TODO : Implement Here
        if (view == null) return -1;

        return searchBinary(view, diff, 0, view.length-1);
    }

    // Helper function for binary search recursion.  This is the actual
    // binary search.
    /// @param view A shallow copy of a set of classes 
    /// @param diff the difference to find
    /// @param left the left index of the search window
    /// @param right the right index of the search window
    /// @return if a record matching diff is found, returns a non-negative 
    ///         number representing the index of the record, otherwise, 
    ///         return a negative number indicating failure.
    private static int searchBinary( String[][] view, int diff, int left, int right ) {

        // the base case when the search set has shrunk to nothing
        if (left > right) {
            return -1;
        }

        // calculate the midpoint
        int mid = (left+right) / 2;
        
        // calculate this once, so we don't incur the cost multiple times
        int viewdiff = differential(view[mid]);

        // if diff and viewdiff are equal, the record at the midpoint is
        // what we are looking for
        if (diff == viewdiff) {
            return mid;
        }

        // otherwise, check if it is before the midpoint
        // Note decreasing order means this comparison is flipped
        if ( diff > viewdiff ) {
            return searchBinary( view, diff, left, mid-1);
        }
        // or after the midpoint.
        return searchBinary( view, diff, mid+1, right);
    }

}
