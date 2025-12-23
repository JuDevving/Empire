package eu.judevving.empire.clock;

import eu.judevving.empire.file.BackupCreator;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.Bukkit;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Clock {

    private long tick;
    private final Calendar calendar;
    private Day day;

    private void tick() {
        Main.getPlugin().getEarth().synchronousTick();
    }

    private void asynchronousTick() {
        setDay();
        Main.getPlugin().getEarth().asynchronousTick();
        if (tick > 0) {
            if (tick % GlobalFinals.PERIOD_SAVE == 0) BackupCreator.save();
        }
    }

    private void setDay() {
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, -GlobalFinals.CLOCK_NEW_DAY_HOUR);
        Day newDay = new Day(calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_YEAR));
        if (day != null) {
            if (!newDay.equals(day)) {
                if (!Main.getPlugin().getEarth().getLastOnlineDay().equals(newDay)) {
                    Main.getPlugin().getEarth().setLastOnlineDay(day, true);
                }
            }
        }
        day = newDay;
    }

    public Clock() {
        this.tick = -1;
        this.calendar = new GregorianCalendar();
        setDay();
        start();
    }

    private void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
            tick++;
            tick();
            Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), this::asynchronousTick);
        }, GlobalFinals.CLOCK_INTERVAL_TICKS, GlobalFinals.CLOCK_INTERVAL_TICKS);
    }

    public static String millisToString(long millis) {
        if (millis <= 0) return GlobalFinals.STRING_UNKNOWN;
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(millis);
        int minute = calendar.get(Calendar.MINUTE);
        return calendar.get(Calendar.DAY_OF_MONTH)
                + "." + (calendar.get(Calendar.MONTH) + 1)
                + "." + calendar.get(Calendar.YEAR)
                + " " + calendar.get(Calendar.HOUR_OF_DAY)
                + ":" + (minute < 10 ? "0" : "") + calendar.get(Calendar.MINUTE);
    }

    public long getTick() {
        return tick;
    }

    public Day getDay() {
        return day;
    }

    public long getTimeInMillis() {
        return calendar.getTimeInMillis() + GlobalFinals.CLOCK_NEW_DAY_HOUR * 1000 * 60 * 60;
    }
}
