package collabrite.nlp.sentiment;

/**
 * Process of Lemmatizing the word to derive the root
 * of the word
 * @author anil
 */
public class Lemmatization {

    public String lemmatize(String word){
        //remove "s" at the end
        int length = word.length();
        //Does the word end with "s"
        if(word.endsWith("s")){
            //remove "s" at the end
            word = word.substring(0, length - 1);
            
            //Since the word ended with "s"
            word = regularCheck(word);
            
        } else if(word.endsWith("ed")){
            word = word.substring(0, length -2);
            word = doubleLastLetters(word);
        } else if(word.endsWith("ly")){
            word = word.substring(0, length -2);
            word = lemmatize(word);
        } else {
            word = regularCheck(word);
        }
        return word;
    }
    
    private String regularCheck(String word){
      //Check if it ends with "ion"
        int length = word.length();
        if(word.endsWith("ion")){
            word = word.substring(0, length - 3);
            word = doubleLastLetters(word);
        } else if(word.endsWith("e")){
            word = word.substring(0,length-1);
        } else if(word.endsWith("ing")){
            word = word.substring(0, length - 3);
            word = doubleLastLetters(word);
        }
        return word;
    }
    
    private String doubleLastLetters(String word){
        int length = word.length();
        //Look for double last letter
        if(word.charAt(length -1) == word.charAt(length - 2)){
            word = word.substring(0, length -1);
        }
        return word;
        
    }
}