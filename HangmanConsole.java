
import java.util.Scanner;

class HangmanConsole {

    static Scanner input = new Scanner(System.in);//Declare scanner for entire class

    public static void clear() {//method to clear console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void printBoard(char[][] board) {//prints board
        for (char[] line : board) {
            for (char c : line) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    static void printWords(char[] guesserWord) {//prints out the words in the hangman
        for (char c : guesserWord) {
            if (c == ' ') {
                System.out.print(c + "  ");
            }
            System.out.print(c + " ");
        }
        System.out.println();
    }

    static char[][] changeLetters(char[] guesserWord, char[] finalWord, char[][] board, char letter) {//goes through and checks all letters
        boolean add = true;
        String word = new String(finalWord);
        if (word.indexOf(letter) >= 0) {
            for (int i = 0; i < finalWord.length; i++) {
                if (letter == finalWord[i]) {
                    if (i == 0) {
                        guesserWord[i] = Character.toString(letter).toUpperCase().charAt(0);
                    } else {
                        guesserWord[i] = Character.toString(letter).toLowerCase().charAt(0);
                    }
                }
            }
            add = false;
        }
        if (add == true) {
            return addPart(board);
        }
        return board;
    }
    static int counter = 0;

    static char[][] addPart(char[][] board) {//Adds body parts
        switch (counter) {
            case 0:
                board[2][5] = 'O';
                break;
            case 1:
                board[3][5] = '|';
                break;
            case 2:
                board[3][4] = '\\';
                break;
            case 3:
                board[3][6] = '/';
                break;
            case 4:
                board[4][5] = '|';
                break;
            case 5:
                board[5][4] = '/';
                break;
            case 6:
                board[5][6] = '\\';
                counter = 0;
                return board;
        }
        counter++;
        return board;
    }

    static char[][] playerTurn(char[][] board, char[] guesserWord, char[] finalWord) throws StringIndexOutOfBoundsException {//Gets word input and starts game
        clear();
        printBoard(board);
        printWords(guesserWord);
        System.out.println("Player, enter a letter you think is in the word or phrase:");
        char letter = input.nextLine().toUpperCase().charAt(0);
        return changeLetters(guesserWord, finalWord, board, letter);
    }

    static boolean checkWin(char[] guesserWord, char[] finalWord, char[][] board) {//Checks win
        String guess = new String(guesserWord);
        String correct = new String(finalWord);
        if (guess.toUpperCase().equals(correct.toUpperCase())) {
            clear();
            printBoard(board);
            printWords(guesserWord);
            System.out.println("Player won! The word was " + correct.toUpperCase().charAt(0) + correct.substring(1).toLowerCase());
            return true;
        } else if (board[2][5] == 'O' && board[3][5] == '|' && board[3][4] == '\\' && board[3][6] == '/' && board[4][5] == '|' && board[5][4] == '/' && board[5][6] == '\\') {
            clear();
            printBoard(board);
            printWords(guesserWord);
            System.out.println("Player lost! The word was " + correct.toUpperCase().charAt(0) + correct.substring(1).toLowerCase());
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws StringIndexOutOfBoundsException {//Runs code
        char[][] board = {{'_', '_', '_', '_', '_', '_'},
        {'|', ' ', ' ', ' ', ' ', '|'},
        {'|', ' ', ' ', ' ', ' ', ' '},
        {'|', ' ', ' ', ' ', ' ', ' ', ' '},
        {'|', ' ', ' ', ' ', ' ', ' '},
        {'|', ' ', ' ', ' ', ' ', ' ', ' '},
        {'^', ' ', ' ', ' ', ' ', ' '}};//empty board
        clear();
        System.out.println("     Welcome to Hangman\n=============================");
        System.out.println("Would you like to view the rules? Y/N");
        String s = input.nextLine();
        if (s.toUpperCase().equals("Y")) {//if rules are asked for.... print them
            System.out.println("\nOne player thinks of a word, phrase or sentence \nand the other tries to guess it by suggesting letters. \nFor each wrong guess, a body part is added to \nthe hanging man. There are 7 body parts in all. \nIf the hanging man has his entire body filled in, \nthe guesser kills the man and loses!");
            System.out.println("\nEnter D when done");
            s = input.nextLine();
        }
        clear();
        System.out.println("Player, enter the word or phrase for the guesser to guess");
        char[] finalWord = input.nextLine().toUpperCase().toCharArray();
        char[] guesserWord = new char[finalWord.length];
        for (int i = 0; i < finalWord.length; i++) {
            if (finalWord[i] != ' ') {
                guesserWord[i] = '_';
            } else {
                guesserWord[i] = ' ';
            }
        }
        while (true) {//does loop and plays the entire game
            board = playerTurn(board, guesserWord, finalWord);
            if (checkWin(guesserWord, finalWord, board)) {
                break;
            }
        }
        while (true) {
            System.out.println("Play again? Y/N");//asks to play again
            String choice = input.nextLine();
            if (choice.toUpperCase().equals("Y")) {
                board[2][5] = ' ';//resets board
                board[3][5] = ' ';
                board[3][4] = ' ';
                board[3][6] = ' ';
                board[4][5] = ' ';
                board[5][4] = ' ';
                board[5][6] = ' ';
                clear();
                System.out.println("Player, enter the word or phrase for the guesser to guess");
                finalWord = input.nextLine().toUpperCase().toCharArray();
                guesserWord = new char[finalWord.length];
                for (int i = 0; i < finalWord.length; i++) {
                    if (finalWord[i] != ' ') {
                        guesserWord[i] = '_';
                    } else {
                        guesserWord[i] = ' ';
                    }
                }
                while (true) {
                    board = playerTurn(board, guesserWord, finalWord);
                    if (checkWin(guesserWord, finalWord, board) == true) {
                        break;
                    }
                }

            } else if (choice.toUpperCase().equals("N")) {
                break;
            } else {
                System.out.println("Not valid, try again");
            }
        }
    }
}
