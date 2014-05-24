package by.pantosha.itirod.lab3.symbol_analyzer;

public final class StatisticsResult {
    private final char character;
    private final float frequency;

    public StatisticsResult(char character, float frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return String.format("%c: %4.2f%%", character, frequency);
    }
}
