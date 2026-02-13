/*--------------------------------------------------------------------------
GWU CSCI 1112 Fall 2025
author: <your name>

This class encapsulates the logic needed to support deciphering messages
layered in a cipher
--------------------------------------------------------------------------*/
class EmbeddedSet {

    /// appends (add to the end) a word to a set of words.  
    /// @param word the word to append to the list of words
    /// @param words the list of words to be appended to
    /// @return a reference to the newly appended list of words if
    ///         successful; otherwise, returns null on failure
    public static String[] append(char[] word, String[] words) {
        // TODO - Write your code here

        return null;
    }

    //---------------------------------------------------------------------
    /// Scans cipher for a matching word.  If the word is found, returns 
    /// the position of the beginning of the word in cipher; otherwise, 
    /// returns -1
    /// @param word a character array containing the sequence to search for
    /// @param cipher a character array containing the sequence to search in
    /// @return the posiiton of the word in the cipher if it exists; 
    ///         otherwise, returns -1 to indicate failure
    public static int wordInCipher(char[] word, char[] cipher) {
        // TODO - Write your code here

        return -1;
    }

    //---------------------------------------------------------------------
    /// extracts the set of words in the order in which they are found from
    /// the cipher given a dictionary of words.  Scanning is always 
    /// performed left to right.  The cipher is reprocessed after an 
    /// extraction for additional words that may form.
    /// @param cipher the stream of characters in which to search 
    /// @param dictionary the set of words to search for in the cipher
    /// @return the set of words in the order in which they are found in the
    ///         cipher.  The set must exist, but it may be empty.  Never 
    ///         returns null.
    public static String[] extractWords( char[] cipher, String[] dictionary ) {
        // TODO - Write your code here

        return null;
    }

}
