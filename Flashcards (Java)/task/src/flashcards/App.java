package flashcards;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private final Scanner input = new Scanner(System.in);
    private final List<FlashCards> flashCardsList;
    private final String FILE_PATH = "listNew.txt";
    public App() {
        flashCardsList = new ArrayList<>();
    }

    public void run() {
        chooseAction();
    }

    private void chooseAction() {
        while (true) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            String action = input.nextLine();

            switch (action) {
                case "add" -> addCards();
                case "remove" -> removeCard();
                case "import" -> importCards(); // loadFlashCardRecords(flashCardsList)
                case "export" -> System.out.println("export");
                case "ask" -> checkCards();
                case "exit" -> exitApp();
            }
        }

    }

    private void addCards() {
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

    private void removeCard() {
        System.out.println("Which card?");
        String card = input.nextLine();
        int cardIndex = findTermIndexByAnswer(flashCardsList, card);

        if (cardIndex != -1) {
            flashCardsList.remove(cardIndex);
            saveFlashCardRecords(flashCardsList);
            System.out.println("The card has been removed.");
        } else {
            System.out.println("Can't remove \"" + card + "\": there is no such card.");
        }
    }

    private void checkCards() {
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

    private void importCards() {
        System.out.println("File name:");
        String fileName = input.nextLine();
        File file = new File(fileName);

        if (file.exists()) {
            System.out.println("File not found");
        } else {
            int numberOfCards = 0;
            System.out.println(numberOfCards + " cards have been loaded.");
        }
    }

    private void exitApp() {
        System.out.println("Bye bye!");
        System.exit(0);
    }

    private boolean checkIfTermExist(String term, List<FlashCards> flashCardsList) {
        for (FlashCards flashCard : flashCardsList) {
            if (flashCard.getTERM().equalsIgnoreCase(term)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfDefinitionExist(String definition, List<FlashCards> flashCardsList) {
        for (FlashCards flashCard : flashCardsList) {
            if (flashCard.getDEFINITION().equalsIgnoreCase(definition)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfAnswerIsCorrect(String term, String answer, List<FlashCards> flashCardsList) {

        int indexOfTerm = -1;
        for (int i = 0; i < flashCardsList.size(); i++) {
            FlashCards flashCard = flashCardsList.get(i);
            if (flashCard.getTERM().equalsIgnoreCase(term)) {
                indexOfTerm = i;
            }
        }

        return flashCardsList.get(indexOfTerm).getDEFINITION().equalsIgnoreCase(answer);
    }

    private int findDefinitionIndexByAnswer(List<FlashCards> flashCardsList, String answer) {
        for (int i = 0; i < flashCardsList.size(); i++) {
            FlashCards flashCard = flashCardsList.get(i);
            if (flashCard.getDEFINITION().equalsIgnoreCase(answer)) {
                return i;
            }
        }
        return -1;
    }

    private int findTermIndexByAnswer(List<FlashCards> flashCardsList, String answer) {
        for (int i = 0; i < flashCardsList.size(); i++) {
            FlashCards flashCard = flashCardsList.get(i);
            if (flashCard.getTERM().equalsIgnoreCase(answer)) {
                return i;
            }
        }
        return -1;
    }

    private void loadFlashCardRecords(List<FlashCards> flashCardsList) {
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
            e.printStackTrace();
        }
    }

    private void saveFlashCardRecords(List<FlashCards> flashCardsList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (FlashCards flashCard : flashCardsList) {
                writer.write(flashCard.getTERM() + ",");
                writer.write(flashCard.getDEFINITION() + "\n");
            }
            //System.out.println("FlashCard records saved successfully.\n");
        } catch (IOException e) {
            System.out.println("Error occurred while saving flashCard records.");
            e.printStackTrace();
        }
    }

    private void printFlashCardRecords(List<FlashCards> flashCardsList) {
        System.out.println("Registered FlashCards:");
        for (FlashCards flashCard : flashCardsList) {
            System.out.println("Term: \"" + flashCard.getTERM() + "\" Definition: \"" + flashCard.getDEFINITION() + "\"");
        }
    }
}
