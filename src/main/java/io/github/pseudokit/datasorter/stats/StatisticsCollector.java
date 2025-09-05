package io.github.pseudokit.datasorter.stats;

public interface StatisticsCollector {
    void addInteger(long value);
    void addFloat(float value);
    void addString(String value);
    void print();
}
