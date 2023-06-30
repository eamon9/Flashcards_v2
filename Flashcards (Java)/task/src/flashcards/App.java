package flashcards;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    Scanner input = new Scanner(System.in);
    List<FlashCards> flashCardsList;

    /*final String FILE_PATH =
            "C:\\Users\\imants.brokans\\IdeaProjects\\Flashcards (Java)\\" +
            "Flashcards (Java)\\task\\src\\flashcards\\files\\list.txt";*/

    final String FILE_PATH =
            "listNew.txt";
    App() {
        flashCardsList = new ArrayList<>();
    }

    public void run() {
        loadFlashCardRecords(flashCardsList);

        chooseAction();

        addCards();
        checkCards();
    }

    public void chooseAction() {
        while (true) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            String action = input.nextLine();

            switch (action) {
                case "add" -> addCards();
                case "remove" -> System.out.println("remove");
                case "import" -> System.out.println("import");
                case "export" -> System.out.println("export");
                case "ask" -> checkCards();
                case "exit" -> System.out.println("exit");
            }
        }

    }

    public void addCards() {
        System.out.println("Input the number of cards:");
        int numberOfCards = input.nextInt();
        input.nextLine();

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
