package io.github.pseudokit.datasorter.stats;

public class FullStaticsticsCollector implements StatisticsCollector {
    private final FullIntegerStats integerStats = new FullIntegerStats();
    private final FullFloatStats floatStats = new FullFloatStats();
    private final FullStringStats stringStats = new FullStringStats();

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
        System.out.println("Full Statistics:");
        System.out.println("------------------------------------");

        if (integerStats.getCount() > 0) {
            System.out.printf("Integers: %d elements, min: %d, max: %d, sum: %d, average: %.2f%n",
                    integerStats.getCount(), integerStats.getMin(), integerStats.getMax(),
                    integerStats.getSum(), integerStats.getAverage());
        } else {
            System.out.println("Integers: no data");
        }

        if (floatStats.getCount() > 0) {
            System.out.printf("Floating point numbers: %d elements, min: %.2f, max: %.2f, sum: %.2f, average: %.2f%n",
                    floatStats.getCount(), floatStats.getMin(), floatStats.getMax(),
                    floatStats.getSum(), floatStats.getAverage());
        } else {
            System.out.println("Floating point numbers: no data");
        }

        if (stringStats.getCount() > 0) {
            System.out.printf("Strings: %d elements, shortest: %d, longest: %d%n",
                    stringStats.getCount(), stringStats.getShortestLength(), stringStats.getLongestLength());
        } else {
            System.out.println("Strings: no data");
        }

        System.out.println("------------------------------------");
    }
}
