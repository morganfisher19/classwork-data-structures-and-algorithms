/*--------------------------------------------------------------------------
GWU CSCI 1112 Spring 2026
author: <your name>

This class encapsulates the game logic needed to support a Scrabble like
tile based spelling game
--------------------------------------------------------------------------*/
class TileGame {
    //                            A,B,C,D, E,F,G,H,I,J,K,L,M,N,O,P, Q,R,S,T,U,V,W,X,Y, Z
    /// the points for a given alpha
    static final int[] points  = {1,3,3,2, 1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
    /// the maximum number of tiles (uses) for a given alpha in a single word
    static final int[] tilebag = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2, 1,6,4,6,4,2,2,1,2, 1};
    public static final int MAXTILES = 98;  // ignores 2 blanks


    // ----------------------------------------------------------------------
    /// Looks up the points associated with a given character and returns
    /// that point value
    /// @param ch the character to look up a point value for
    /// @return a positive point value representing the point value for 
    ///         that character if ch is an uppercase alpha; otherwise, a 
    ///         zero value
    public static int getTilePoints( char ch ) {
        // Convert ch into its ordinal alphabetical position 
        int ord = (int)ch - (int)'A';

        // sanity check - validate ch is in the range of uppercase alphas
        if(ord < 0) { return 0; }
        if(ord >= 26) { return 0; }

        // ch must be an uppercase alpha, so return the value in the ordinal
        // position of the points table
        return points[ord];
    }

    // ----------------------------------------------------------------------
    /// Computes the total score for a word and returns that point total
    /// @param word a character array that contains the word that we need
    ///        the point total for 
    /// @return the total score for the word passed in.  If the function
    ///         fails for any reason, it returns zero
    public static int getWordScore( char[] word ) {
        // sanity check - validate that the word is valid
        if( word == null ) { return 0; }

        // the running total for the summation
        int total = 0;

        // iterate over every character in the word and add its points 
        // to the total  
        for( int i = 0; i < word.length; i++ ) {
            total += getTilePoints( word[i] );
        }

        // return the total
        return total;
    }

    // ----------------------------------------------------------------------
    /// Make a deep copy of the model tilebag array.  The model must not be 
    /// changed, so need to copy the array and use the copy as a 'scratchpad'
    /// for computing tile usage
    /// @return a copy of the tilebag array/an array of the number of tiles
    ///         available for each alpha ordinal position.  
    public static int[] copyTileBag() {
        // TODO : Add your code here
        // the new array
        int[] tb = new int[tilebag.length];

        // perform a deep copy of each value into the new array
        for( int i = 0; i < tb.length; i++ ) {
            tb[i] = tilebag[i];
        }
    
        // return the reference to the new array
        return tb;
    }

    // ----------------------------------------------------------------------
    /// Draw a random subset of tiles from the tilebag
    /// @param count the number of tiles to draw from the tilebag
    /// @return an array of characters drawn from the tilebag 
    ///         according to the limits of the number of tiles
    ///         for a given character; or null if the count is 
    ///         invalid or the operation fails for any reason
    public static char[] drawHand( int count ) {
        // sanity check - validate that the count does not exceed
        // the number of available tiles
        if( count > MAXTILES ) { return null; }

        // deep copy the tilebag.  Why?  because if we don't
        // any changes to the data would destroy the model and the
        // game would no longer have consistent rules from round to
        // round.
        int[] tb = copyTileBag();
        // create a hand of the specified size
        char[] hand = new char[count];

        // draw a tile to fill the next open hand position
        for( int i = 0; i < count; i++ ) {
            // but the tile must be valid 
            // can't draw more tiles than are available in tilebag
            boolean valid = false;
            while( !valid ) {
                // generate a random number from a uniform distribution
                int pos = UniformRandom.uniform(0,25);
                // if the tile generated is available
                if( tb[pos] > 0 ) {
                    valid = true;   // it's valid
                    tb[pos]--;      // decrement the available count
                    hand[i] = (char)((int)'A' + pos);  // put in hand
                }
            }
        }
        return hand;
    }
   
    // ----------------------------------------------------------------------
    /// Determines where the characters in hand can be used to 
    /// spell the word.  Each character in hand can only be used once.
    /// If we are trying to spell a word with two A's in it, there
    /// must be at least two A's in hand.
    /// @param hand the jumble of characters available to spell with
    /// @param word the word that we are testing trying to spell
    /// @return true if the characters in hand can be used to spell
    ///         the word; otherwise, false.
    public static boolean canSpell( char[] hand, char[] word ) {
        // TODO : Add your code here

        // sanity check - null pointers suggest invalid data
        if( word == null ) { return false; }  
        if( hand == null ) { return false; }

        // we will remove the letter from the hand to account for 
        // duplicates, so we need to operate on a copy of the hand.
        // H is our safe to destroy hand.
        char[] H = new char[hand.length];
        for(int i = 0; i < hand.length; i++) {
            H[i] = hand[i];
        }

        // iterate through the hand and sample the next letter
        for(int i = 0; i < word.length; i++) {

            // the next letter in the hand is wch -> word char
            int wch = (int) word[i];

            // tracks when the hand char is found
            boolean found = false;

            // iterate through word looking for the hand char
            for(int j = 0; j < H.length; j++) {

                // the next letter in the word is hch -> hand char
                int hch = (int) H[j];

                // if the hand char is the same as the word char
                if(wch == hch) {
                    // we can continue to spell this word
                    found = true;

                    // remove the character from the hand so it is not
                    // reused for a duplicate character
                    char[] Hprime = new char[H.length-1];
                    for(int k = 0; k < H.length - 1; k++) {
                        if( k < j ) {
                            Hprime[k] = H[k];
                        } else {
                            Hprime[k] = H[k+1];
                        }
                    }
                    H = Hprime;
    
                    // we can stop looking through the rest of the hand
                    break;
                }
            }

            // if this line is reached and found was never flipped as we 
            // searched through the word, then the word cannot be spelled
            if( !found ) {
                return false;
            }
        }

        // this is proof by exhaustion but the structuring of the logic
        // is awkward.  We succeed here because above as we went through
        // the entire hand, we failed to find a single case where a letter
        // in the word is NOT in the hand.
        return true;
    }

    // ----------------------------------------------------------------------
    /// Returns the highest scoring word according to getWordScore from 
    /// dictionary that can be spelled by the characters in hand according
    /// to canSpell
    /// @param hand the jumble of characters available to spell with
    /// @param dictionary an array of words that we test against
    /// @return the highest scoring word that can be spelled given the
    ///         input hand according to getWordScore; otherwise, null
    ///         if no word in dictionary can be spelled with hand
    public static String getBestWord( char[] hand, String[] dictionary ) {
        // TODO : Add your code here
        int topscore = 0;
        int topidx = -1;
        String result = null;

        // sanity check - null pointers suggest invalid data
        if( dictionary == null ) { return null; }  
        if( hand == null ) { return null; }
        
        // check all words in the dictionary as they could all be the best
        for( int i = 0; i < dictionary.length; i++ ) {
            char[] C = dictionary[i].toCharArray();
            // word is longer than the tiles in the hand
            if( C.length > hand.length ) continue;

            // ignore the word if it cant be spelt with the hand
            if( !canSpell(hand, C) ) continue;

            // word can be spelt, get the score and compare to word 
            //      previously thought to be the best word
            int score = TileGame.getWordScore(C);
            if( score >= topscore ) {
                topscore = score;
                topidx = i;
                //System.out.println( topscore + ":" + topidx + ":" + dictionary[topidx] );
            }
        }
        if(topidx > -1) result = dictionary[topidx];
        return result;
    }
}
