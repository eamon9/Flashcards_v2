package flashcards;

public class FlashCards {

    private final String TERM;
    private final String DEFINITION;

    public FlashCards(String term, String definition) {
        this.TERM = term;
        this.DEFINITION = definition;
    }

    public String getTERM() {
        return TERM;
    }

    public String getDEFINITION() {
        return DEFINITION;
    }
}
