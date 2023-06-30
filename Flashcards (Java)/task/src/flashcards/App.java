package flashcards;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    Scanner input = new Scanner(System.in);
    List<FlashCards> flashCardsList;

    // to find where is this txt file go to IdeaProjects\Flashcards (Java)\[txt file]
    final String FILE_PATH = "listNew.txt";

    App() {
        flashCardsList = new ArrayList<>();
    }

    public void run() {
        loadFlashCardRecords(flashCardsList);

        chooseAction();

        System.out.println("Input the number of cards:");
        int numberOfCards = input.nextInt();
        input.nextLine();

        addCards(numberOfCards);
        checkCards();
    }

    public void chooseAction() {
        System.out.println("Input the action (add, remove, import, export, ask, exit):");
        String action = input.nextLine();
        boolean noSuchAction = true;
        while (noSuchAction) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            action = input.nextLine();
            noSuchAction = false;
        }
        switch (action) {
            case "add" :
                System.out.println("add");;
                break;
            case "remove" :
                System.out.println("remove");
                break;
            case "import" :
                System.out.println("import");
                break;
            case "export" :
                System.out.println("export");
                break;
            case "ask" :
                System.out.println("ask");
                break;
            case "exit" :
                System.out.println("exit");
                break;
            default:
                System.out.println("NO NO NO");
        }
    }

    public void addCards(int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            System.out.println("Card #" + (i + 1) + ":");
            String term = input.nextLine();

            // check if term already exists on FlashCardsList
            boolean termExists = checkIfTermExist(term, flashCardsList);
            while (termExists) {
                System.out.println("The term \"" + term + "\" already exists. Try again:");
                term = input.nextLine();
                termExists = checkIfTermExist(term, flashCardsList);
            }

            System.out.println("The definition for card #" + (i + 1) + ":");
            String definition = input.nextLine();

            // check if definition already exists on FlashCardsList
            boolean definitionExists = checkIfDefinitionExist(definition, flashCardsList);
            while (definitionExists) {
                System.out.println("The definition \"" + definition + "\" already exists. Try again:");
                definition = input.nextLine();
                definitionExists = checkIfDefinitionExist(definition, flashCardsList);
            }

            // add term and definition to List
            FlashCards flashCard = new FlashCards(term, definition);
            flashCardsList.add(flashCard);
            saveFlashCardRecords(flashCardsList);

            printFlashCardRecords(flashCardsList);
        }
    }

    public void checkCards() {
        for (int i = 0; i < flashCardsList.size(); i++) {
            String tempTerm = flashCardsList.get(i).getTERM();
            System.out.println("Print the definition of \"" + tempTerm + "\":");
            String answer = input.nextLine();

            boolean answerIsCorrect = checkIfAnswerIsCorrect(tempTerm, answer, flashCardsList);
            if (!answerIsCorrect) {
                int indexOfTerm = -1;
                for (int j = 0; j < flashCardsList.size(); j++) {
                    FlashCards flashCard = flashCardsList.get(j);
                    if (flashCard.getTERM().equalsIgnoreCase(tempTerm)) {
                        indexOfTerm = j;
                    }
                }

                int indexOfAnswer = findDefinitionIndexByAnswer(flashCardsList, answer);
                boolean wrongAnswer = !flashCardsList.get(i).getDEFINITION().equalsIgnoreCase(answer);

                if (wrongAnswer && indexOfAnswer != indexOfTerm && indexOfAnswer != -1) {

                    System.out.println("Wrong. The right answer is \""
                            + flashCardsList.get(indexOfTerm).getDEFINITION()
                            + "\", but your definition is correct for \""
                            + flashCardsList.get(indexOfAnswer).getTERM()
                            + "\".");
                } else {

                    System.out.println("Wrong. The right answer is \""
                            + flashCardsList.get(indexOfTerm).getDEFINITION()
                            + "\"");
                }

            } else {

                System.out.println("Correct!");
            }
        }
    }

    boolean checkIfTermExist(String term, List<FlashCards> flashCardsList) {
        for (FlashCards flashCard : flashCardsList) {
            if (flashCard.getTERM().equalsIgnoreCase(term)) {
                return true;
            }
        }
        return false;
    }

    boolean checkIfDefinitionExist(String definition, List<FlashCards> flashCardsList) {
        for (FlashCards flashCard : flashCardsList) {
            if (flashCard.getDEFINITION().equalsIgnoreCase(definition)) {
                return true;
            }
        }
        return false;
    }

    static boolean checkIfAnswerIsCorrect(String term, String answer, List<FlashCards> flashCardsList) {

        int indexOfTerm = -1;
        for (int i = 0; i < flashCardsList.size(); i++) {
            FlashCards flashCard = flashCardsList.get(i);
            if (flashCard.getTERM().equalsIgnoreCase(term)) {
                indexOfTerm = i;
            }
        }

        return flashCardsList.get(indexOfTerm).getDEFINITION().equalsIgnoreCase(answer);
    }

    static int findDefinitionIndexByAnswer(List<FlashCards> flashCardsList, String answer) {
        for (int i = 0; i < flashCardsList.size(); i++) {
            FlashCards flashCard = flashCardsList.get(i);
            if (flashCard.getDEFINITION().equalsIgnoreCase(answer)) {
                return i;
            }
        }
        return -1;
    }

    void loadFlashCardRecords(List<FlashCards> flashCardsList) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String term = parts[0];
                String definition = parts[1];

                FlashCards flashCard = new FlashCards(term, definition);
                flashCardsList.add(flashCard);
            }
            System.out.println("FlashCard records loaded successfully.\n");
        } catch (IOException e) {
            // Ignore if the file doesn't exist or any other read error occurs
        }
    }

    void saveFlashCardRecords(List<FlashCards> flashCardsList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (FlashCards flashCard : flashCardsList) {
                writer.write(flashCard.getTERM() + ",");
                writer.write(flashCard.getDEFINITION() + "\n");
            }
            System.out.println("FlashCard records saved successfully.\n");
        } catch (IOException e) {
            System.out.println("Error occurred while saving flashCard records.");
            e.printStackTrace();
        }
    }

    void printFlashCardRecords(List<FlashCards> flashCardsList) {
        System.out.println("Registered FlashCards:");
        for (FlashCards flashCard : flashCardsList) {
            System.out.println("Term: \"" + flashCard.getTERM() + "\" Definition: \"" + flashCard.getDEFINITION() + "\"");
        }
    }
}
