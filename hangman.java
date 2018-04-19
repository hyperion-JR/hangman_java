
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class hangman {

    static ArrayList<String> words = new ArrayList<>();
    static ArrayList<String> usedWords = new ArrayList<>();
    static boolean isCorrect;

    public static void main(String[] args) {

        // Prompts user for words text file name and throws an exception if it can't find it
        System.out.print("Enter filename with file path: ");
        Scanner scanner = new Scanner(System.in);
        File filename = new File(scanner.nextLine());
        if (!filename.exists()) {
            System.out.println(filename.getAbsolutePath());
            System.out.println(filename + " does not exist.");
            System.exit(1);
        }

        // Tries to add words from the filename to an ArrayList
        try {
            Scanner input = new Scanner(filename);
            while (input.hasNext()) {
                words.add(input.next());
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        Scanner input = new Scanner(System.in);
        String playStats = "y";
        int wins = 0;
        int losses = 0;
        while (playStats.equals("y")) {
            String word = getWord();
            String hiddenWord = getHiddenWord(word);
            int missCount = 0;
            while (!word.equals(hiddenWord) && missCount < 11) {

                System.out.print("Guess a letter " + hiddenWord + " : ");
                char ch = input.next().charAt(0);

                if (!isAlreadyInWord(hiddenWord, ch)) {

                    hiddenWord = getGuess(word, hiddenWord, ch);

                    if (!isCorrect) {
                        missCount++;
                        System.out.println(ch + ": is not in the word. Guesses: "+missCount);
                    }

                }
                else {
                    // Doesn't count if you already guessed the letter
                    System.out.println(ch + " is already in the word. Guesses: "+missCount);
                }

            }
            if (missCount < 11) {
                System.out.println("You win! The word is "+ word +". "+"Number of misses: "+missCount);
                wins++;
            }
            else {
                System.out.println("You lose. The word is "+ word +". "+"Number of misses: "+missCount);
                losses++;
            }
            usedWords.add(word);
            System.out.println("Do you want to guess another word? Enter y or n.");
            playStats = input.next();
        }
        // Prints the score
        System.out.println("Wins: "+ wins);
        System.out.println("Losses: "+ losses);
        System.out.println("Used words: "+ usedWords);

    }

    // Randomly selects a word from filename
    public static String getWord() {
        return words.get((int) (Math.random() * words.size()));
    }

    // Hides word from end user; each letter in the word is displayed as an asterisk.
    public static String getHiddenWord(String word) {

        String hidden = "";
        for (int i = 0; i < word.length(); i++) {
            hidden += "*";
        }
        return hidden;
    }

    // Processes guesses
    static public String getGuess(String word, String hiddenWord, char ch) {

        isCorrect = false;
        StringBuilder s = new StringBuilder(hiddenWord);
        for (int i = 0; i < word.length(); i++) {

            if (ch == word.charAt(i) && s.charAt(i) == '*') {
                isCorrect = true;
                s = s.deleteCharAt(i);
                s = s.insert(i, ch);
            }
        }
        return s.toString();
    }

    // If the word has already been guessed...
    public static boolean isAlreadyInWord(String hiddenWord, char ch) {

        for (int i = 0; i < hiddenWord.length(); i++) {

            if (ch == hiddenWord.charAt(i)) {
                return true;
            }
        }
        return false;
    }
}