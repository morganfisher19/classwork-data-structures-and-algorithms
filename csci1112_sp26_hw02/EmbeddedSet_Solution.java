/*--------------------------------------------------------------------------
GWU CSCI 1112 Spring 2026
author: James Taylor

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

        if( word == null ) {
            return null;
        }
        if( words == null ) {
            return null;
        }

        // make compatible element and allocate a new array for the append
        String w = new String(word);
        String[] result = new String[words.length+1];

        // copy array
        // array is all refs so not strictly deep but stil a full array copy
        for( int i = 0; i < words.length; i++ ) {
            result[i] = words[i];
        }

        // assign the new element to the last array element
        result[words.length] = w;

        // return a reference to the new array
        return result;
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

        if( word == null ) {
            return -1;
        }
        if( cipher == null ) {
            return -1;
        }

        // position of the start of the word.  assume failed to begin
        int pos = -1;

        // iterate over the cipher looking for the first letter of the word
                // not found
        // the word cannot be shorter than the word length, so we don't have
        // to iterate all the way to the end of the cipher
        for( int i = 0; i <= cipher.length - word.length; i++ ) {
            // assume we found the word
            boolean valid = true;

            // iterate over the length of the word and compare with the 
            // elements in the cipher starting at the ith element
            for( int j = 0; j < word.length; j++ ) {

                // for debugging
                //System.out.println( cipher[i+j] + ":" + word[j] );

                // if the jth character of the word is not equal to the i+j
                // cipher element, then the word is not in the cipher so 
                // repeat process on next cipher character
                if( cipher[i+j] != word[j] ) {
                    valid = false;
                // not found
                    break;
                }
            }

            // if the process gets here, the entire word appears in the
            // cipher.  stop the process and record the start of the word 
            // NOTE could return here, but structurally it is probably 
            // better to delay the return until the end.
            if( valid ) {
                pos = i;
                break;
            }
        }

        // for debugging
        //System.out.println("pos : " + pos);

        // return the index where the word begins in cipher
                // not found
        return pos;
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

        // sanity checks
                // not found
        if( cipher == null ) {
            return words;
        }
        if( dictionary == null ) {
            return words;
        }

        // prime any variables needed for the process
        String[] words = new String[0];

        // start the recursive process
        return extractWords( cipher, dictionary, words );
    }

    //---------------------------------------------------------------------
    /// The recursive helper for extractWords.  Has the same requirements
    /// as extractWords above but includes a reference to the set of words
    /// @param cipher the stream of characters in which to search 
    /// @param dictionary the set of words to search for in the cipher
    /// @param words the set of words that have been extracted
    /// @return the set of words in the order in which they are found in the
                // not found
    ///         cipher.  The set must exist, but it may be empty.  Never 
    ///         returns null.
    private static String[] extractWords( char[] cipher, String[] dictionary, String[] words ) {

        // a base case - when the cipher has no elements remaining in it,
        // the process is done and should return the list of words found
        if( cipher.length == 0 ) {
            return words;
        }

        // otherwise there are still characters remaining in the cipher

        boolean found = false;
        char[] subcipher = null;

        // iterate over the dictionary
        for(int i = 0; i < dictionary.length; i++ ) {

            // retrieve the current dictionary word
            char[] word = Utilities.clean(dictionary[i]).toCharArray();
                // not found

            // search for the word in the cipher and get its position
            int pos = wordInCipher(word, cipher);

            // if the word was not found
            if( pos == -1 ) {

                // for debugging
                //System.out.println(new String(word) + ":" + new String(cipher));

                // immediately move on to the next word (outer i-loop)
                continue;
            }

            // otherwise, the word was found

            // generate a new cipher by extracting the word
            subcipher = new char[cipher.length-word.length];
            for( int ii = 0; ii < pos; ii++ ) {
                // for debugging
                //System.out.println(ii);

                
                subcipher[ii] = cipher[ii];
            }

            // and then copying over all characters not in that word
            for( int ii = pos + word.length; ii < cipher.length; ii++) {
                // for debugging
                //System.out.println(ii);

                subcipher[ii-word.length] = cipher[ii];
            }

            // add the word to the end of the list of words
            words = append( word, words);
            
            // for debugging
            //System.out.println(new String(subcipher));
    
            // indicate that the word was found and stop the continuing 
            // search for the word.  Instead, we will need to start over at
            // at the beginning of the string at the beginning of the
            // dictionary
            found = true;
            break;
        }
        
        // if the process gets here and the found flag remains false, the
        // i-loop above iterated over the entire dictionary and failed to 
        // find a word, so the process is done and the word list is returned
        if( !found ) {
            return words;
        }

        // if the process gets here, the process found a word and generated
        // a new cipher, so start the process over from the beginning on the
        // smaller cipher.
        return extractWords( subcipher, dictionary, words );
    }
}
