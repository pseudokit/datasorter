package io.github.pseudokit.datasorter.stats;

class Stats {
    protected long count = 0;
    
    public void incrementCount() {
        count++;
    }
    
    public long getCount() {
        return count;
    }
}

class IntegerStats extends Stats {
    public void add(long value) {
        incrementCount();
    }
}

class StringStats extends Stats {
    public void add(String value) {
        incrementCount();
    }
}

class FloatStats extends Stats {
    public void add(float value) {
        incrementCount();
    }
}

class FullIntegerStats extends Stats {
    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;
    private long sum = 0;
    
    public void add(long value) {
        super.incrementCount();
        if (value < min) {
            min = value;
        }
        if (value > max) {
            max = value;
        }
        sum += value;
    }
    
    public long getMin() { return min; }
    public long getMax() { return max; }
    public long getSum() { return sum; }
    public double getAverage() { return (double) sum / count; }
}

class FullStringStats extends Stats {
    private int shortestLength = Integer.MAX_VALUE;
    private int longestLength = 0;
    
    public void add(String value) {
        super.incrementCount();
        int length = value.length();
        if (length < shortestLength) {
            shortestLength = length;
        }
        if (length > longestLength) {
            longestLength = length;
        }
    }
    
    public int getShortestLength() { return shortestLength; }
    public int getLongestLength() { return longestLength; }
}

class FullFloatStats extends Stats {
    private double min = Double.MAX_VALUE;
    private double max = Double.MIN_VALUE;
    private double sum = 0.0;
    
    public void add(float value) {
        super.incrementCount();
        if (value < min) {
            min = value;
        }
        if (value > max) {
            max = value;
        }
        sum += value;
    }
    
    public double getMin() {
        return min;
    }
    
    public double getMax() {
        return max;
    }
    
    public double getSum() {
        return sum;
    }
    
    public double getAverage() {
        if (count == 0) {
            return 0.0;
        }
        return sum / count;
    }
}