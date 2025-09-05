package io.github.pseudokit.datasorter.stats;

public class ShortStaticsticsCollector implements StatisticsCollector {
    private final IntegerStats integerStats = new IntegerStats();
    private final FloatStats floatStats = new FloatStats();
    private final StringStats stringStats = new StringStats();

    public void addInteger(long value) {
        integerStats.add(value);
    }

    public void addFloat(float value) {
        floatStats.add(value);
    }

    public void addString(String value) {
        stringStats.add(value);
    }

    @Override
    public void print() {
        System.out.println("Short statistic:");
        System.out.println("------------------------------------");
        System.out.printf("Integers: %d%n", integerStats.getCount());
        System.out.printf("Floats: %d%n", floatStats.getCount());
        System.out.printf("Strings: %d%n", stringStats.getCount());
        System.out.println("------------------------------------");
    }
}
