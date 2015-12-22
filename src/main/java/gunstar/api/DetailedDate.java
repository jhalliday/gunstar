package gunstar.api;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DetailedDate implements Comparable<DetailedDate> {

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public final Date date;
    public final long extra;

    public DetailedDate(Date date, long extra) {
        this.date = date;
        this.extra = extra;
    }


    public long getDetailedTime() {
        return (date.getTime()*1000000) + extra;
    }

    @Override
    public int compareTo(DetailedDate o) {
        int m = Long.compare(this.date.getTime(), o.date.getTime());
        return m == 0 ? Long.compare(this.extra, o.extra) : m;
    }

    public boolean before(DetailedDate when) {
        return this.compareTo(when) == -1;
    }

    public boolean after(DetailedDate when) {
        return this.compareTo(when) == 1;
    }

    public static DetailedDate parse(String date) {
        if(date == null) {
            return null;
        }
        if(date.length() < 23 || date.charAt(19) != '.') {
            throw new IllegalArgumentException();
        }
        Date d = dateTimeFormatter.parseDateTime(date.substring(0,23)+"Z").toDate();


        int limit = 30;
        int padding = 0;
        if(date.length() < limit) {
            padding = limit-date.length();
            limit = date.length();
        }
        String extraString = date.substring(23, limit-1);
        Long extra = Long.parseLong(extraString);
        if(padding > 0) {
            extra = extra*((long)Math.pow(10, padding));
        }
        return new DetailedDate(d, extra);
    }
}
