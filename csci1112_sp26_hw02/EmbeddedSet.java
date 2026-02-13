/*--------------------------------------------------------------------------
GWU CSCI 1112 Fall 2025
author: Morgan Fisher

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

        // Control for empty inputs
        if (word == null) {
            return null;
        }
        if (words == null) {
            return null;
        }

        // Converting original word from character array to String
        String new_word = new String(word);

        // Creating a new string array
        String[] new_words = new String[words.length + 1];

        // Looping through copying original array
        for (int i =0; i < words.length; i++) {
            new_words[i] = words[i];
        }

        // Adding new word to end
        new_words[words.length]=new_word;

        return new_words;
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
        
        // Control for empty inputs
        if (word == null){
            return -1;
        }
        if (cipher == null){
            return -1;
        }

        // Loop through the cipher
        for (int i=0; i <= cipher.length - word.length; i++) {

            // loop through each character in word
            for (int j=0; j < word.length; j++){

                // Check to see if all letters are in word
                if (cipher[i+j] != word[j]){
                    break;
                }
                if (j == (word.length-1)){
                    return i;
                }
            }

        }
        // For loop exited & word not found
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
        String[] words = new String[0];

        return recursion( cipher, dictionary, words );
    }

    
    
    private static String[] recursion( char[] cipher, String[] dictionary, String[] words ) {
        // Base cases:

        // No letters left in cipher
        if (cipher == null){
            return words;
        }

        // Empty dictionary
        if (dictionary == null){
            return words;
        }

        
        // Recursive case:

        // Loop through each word in dictionary
        for (int i=0; i < dictionary.length; i++){
            char[] dict_word =  dictionary[i].toCharArray();

            // check if word is in cipher
            int index = wordInCipher(dict_word, cipher);
            if (index != -1) {
                // add word to list
                words = append(dict_word, words);

                // adjust cipher
                cipher = removeWordFromCipher(cipher, dict_word, index);

                // repeat recursion
                words = recursion(cipher, dictionary, words);
                break;

            }
        }

        // Occurs when there are no words left in a nonempty cipher
        return words;
    }

    private static char[] removeWordFromCipher (char[] cipher, char[] word, int index) {
        // Create a new cipher
        char[] new_cipher = new char[cipher.length - word.length];

        // Copy only letters not in word to new cipher
        for (int i = 0; i < index; i++) {
            new_cipher[i] = cipher[i];
        }
        for (int j = index; j < cipher.length - word.length; j++) {
            new_cipher[j] = cipher[j + word.length];
        }

        return new_cipher;
    }
}
