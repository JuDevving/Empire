package eu.judevving.empire.clock;

public class Day {

    public static final Day ZERO = new Day(0, 0);
    private final int year, day;

    public Day(int year, int day) {
        this.year = year;
        this.day = day;
    }

    public boolean isNext(Day next) {
        if (year == next.year) {
            return next.day == day + 1;
        }
        if (next.year != year + 1) return false;
        if (next.day != 1) return false;
        if (isLeapYear()) return day == 366;
        return day == 365;
    }

    public boolean isNextNext(Day next) {
        if (year == next.year) {
            return next.day == day + 2;
        }
        if (next.year != year + 1) return false;
        if (next.day > 2) return false;
        if (isLeapYear()) return day == 366 - 2 + next.day;
        return day == 365 - 2 + next.day;
    }

    public static Day fromInt(int i) {
        return new Day(i / 1000, i % 1000);
    }

    public int asInt() {
        return year * 1000 + day;
    }

    public boolean isLeapYear() {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Day d) return year == d.year && day == d.day;
        return false;
    }

    @Override
    public String toString() {
        return "Day{" + year + ',' + day + '}';
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }
}
