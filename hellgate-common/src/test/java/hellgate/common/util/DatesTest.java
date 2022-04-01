package hellgate.common.util;

import com.google.common.base.Strings;
import hellgate.common.util.Dates;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期工具测试类。
 */
public final class DatesTest {

    private final Date date = Date.from(Instant.parse("2007-12-03T10:15:30.00Z"));

    @Test
    public void format() {
        String expected = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        String actual = Dates.format(date);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void formatNullDate() {
        Dates.format(null);
    }

    @Test
    public void formatPatternOffset() {
        String expected = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ").format(date);
        String actual = Dates.format(date, "yyyy-MM-dd HH:mm:ssZ");
        assertEquals(expected, actual);
    }

    @Test
    public void formatPatternDate() {
        String expected = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String actual = Dates.format(date, "yyyy-MM-dd");
        assertEquals(expected, actual);
    }

    @Test
    public void formatPatternTime() {
        String expected = new SimpleDateFormat("HH:mm:ss").format(date);
        String actual = Dates.format(date, "HH:mm:ss");
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void formatNullPattern() {
        Dates.format(date, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatWhitespacePattern() {
        Dates.format(date, "    ");
    }

    @Test
    public void formatBasic() {
        String expected = new SimpleDateFormat("yyyyMMdd").format(date);
        String actual = Dates.formatBasic(date);
        assertEquals(expected, actual);
    }

    @Test
    public void formatInstant() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String expected = dateFormat.format(date);
        String actual = Dates.formatInstant(date);
        assertEquals(expected, actual);
    }

    @Test
    public void formatLocal() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String expected = dateFormat.format(date);
        String actual = Dates.formatLocal(date);
        assertEquals(expected, actual);
    }

    @Test
    public void formatLocalDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String expected = dateFormat.format(date);
        String actual = Dates.formatLocalDate(date);
        assertEquals(expected, actual);
    }

    @Test
    public void formatLocalTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String expected = dateFormat.format(date);
        String actual = Dates.formatLocalTime(date);
        assertEquals(expected, actual);
    }

    @Test
    public void formatOffset() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        String expected = dateFormat.format(date);
        String actual = Dates.formatOffset(date);
        assertEquals(expected, actual);
    }

    @Test
    public void formatOffsetDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddXXX");
        String expected = dateFormat.format(date);
        String actual = Dates.formatOffsetDate(date);
        assertEquals(expected, actual);
    }

    @Test
    public void formatOffsetTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ssXXX");
        String expected = dateFormat.format(date);
        String actual = Dates.formatOffsetTime(date);
        assertEquals(expected, actual);
    }

    @Test
    public void parse() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateText = "2011-12-03 10:15:30";
        Date expected = dateFormat.parse(dateText);
        Date actual = Dates.parse(dateText);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void parseNull() {
        Dates.parse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseWhitespace() {
        Dates.parse("    ");
    }

    @Test
    public void parseBasic() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String basicText = "20111203";
        Date expected = dateFormat.parse(basicText);
        Date actual = Dates.parseBasic(basicText);
        assertEquals(expected, actual);
    }

    @Test
    public void parseInstant() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String instantText = "2011-12-03T10:15:30Z";
        Date expected = dateFormat.parse(instantText);
        Date actual = Dates.parseInstant(instantText);
        assertEquals(expected, actual);
    }

    @Test
    public void parseLocal() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String localDateTime = "2011-12-03T10:15:30";
        Date expected = dateFormat.parse(localDateTime);
        Date actual = Dates.parseLocal(localDateTime);
        assertEquals(expected, actual);
    }

    @Test
    public void parseLocalDate() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String localDate = "2011-12-03";
        Date expected = dateFormat.parse(localDate);
        Date actual = Dates.parseLocalDate(localDate);
        assertEquals(expected, actual);
    }

    @Test
    public void parseLocalTime() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String localTime = "10:15:30";
        Date expected = dateFormat.parse(localTime);
        Date actual = Dates.parseLocalTime(localTime);
        assertEquals(expected, actual);
    }

    @Test
    public void parseOffset() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        String offsetDateTime = "2011-12-03T10:15:30+01:00";
        Date expected = dateFormat.parse(offsetDateTime);
        Date actual = Dates.parseOffset(offsetDateTime);
        assertEquals(expected, actual);
    }

    @Test
    public void parseOffsetTime() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ssXXX");
        String offsetTime = "10:15:30+01:00";
        Date expected = dateFormat.parse(offsetTime);
        Date actual = Dates.parseOffsetTime(offsetTime);
        assertEquals(expected, actual);
    }

    @Test
    public void isBefore() {
        Date before = Date.from(date.toInstant().plusSeconds(1));
        String message = Strings.lenientFormat("%s is before %s failed", date, before);
        assertTrue(Dates.isBefore(date, before), message);
    }

    @Test
    public void isAfter() {
        Date before = Date.from(date.toInstant().minusSeconds(1));
        String message = Strings.lenientFormat("%s is after %s failed", date, before);
        assertTrue(Dates.isAfter(date, before), message);
    }

    @Test
    public void between() {
        Date end = Date.from(date.toInstant().plusSeconds(1));
        long expected = end.getTime() - date.getTime();
        long between = Dates.between(date, end);
        assertEquals(expected, between);
    }

    @Test
    public void betweenDay() {
        int expected = 1;
        Date end = Date.from(date.toInstant().plus(Duration.ofDays(expected)));
        long between = Dates.betweenDay(date, end);
        assertEquals(expected, between);
    }

    @Test
    public void betweenHours() {
        int expected = 10;
        Date end = Date.from(date.toInstant().plus(Duration.ofHours(expected)));
        long between = Dates.betweenHours(date, end);
        assertEquals(expected, between);
    }

    @Test
    public void betweenMinutes() {
        int expected = 100;
        Date end = Date.from(date.toInstant().plus(Duration.ofMinutes(expected)));
        long between = Dates.betweenMinutes(date, end);
        assertEquals(expected, between);
    }

    @Test
    public void betweenSeconds() {
        int expected = 1000;
        Date end = Date.from(date.toInstant().plus(Duration.ofSeconds(expected)));
        long between = Dates.betweenSeconds(date, end);
        assertEquals(expected, between);
    }

    @Test
    public void untilUnknown() {
        Date tomorrow = Date.from(OffsetDateTime.now().plus(Duration.ofDays(1)).toInstant());
        String unknown = Dates.untilNow(tomorrow);
        assertEquals(Dates.DEF_UNKNOWN, unknown);
    }

    @Test
    public void untilJustNow() {
        Date now = new Date();
        String justNow = Dates.untilNow(now);
        assertEquals(Dates.DEF_JUST_NOW, justNow);
    }

    @Test
    public void untilOneMinutesAgo() {
        int minutes = 1;
        Date oneMinutesAgo = Date.from(OffsetDateTime.now().minus(Duration.ofMinutes(minutes)).toInstant());
        String actual = Dates.untilNow(oneMinutesAgo);
        String expected = Strings.lenientFormat(Dates.DEF_MINUTES_AGO, minutes);
        assertEquals(expected, actual);
    }

    @Test
    public void untilToday() {
        OffsetDateTime today = OffsetDateTime.now().with(LocalTime.MIDNIGHT.plusSeconds(1));
        String actual = Dates.untilNow(Date.from(today.toInstant()));
        String time = Dates.SIMPLE_TIME.format(today);
        String expected = Strings.lenientFormat(Dates.DEF_TODAY, time);
        assertEquals(expected, actual);
    }

    @Test
    public void untilYesterday() {
        int days = 1;
        OffsetDateTime yesterday = OffsetDateTime.now()
                .minus(Duration.ofDays(days))
                .with(LocalTime.MIDNIGHT.plusSeconds(1));
        String actual = Dates.untilNow(Date.from(yesterday.toInstant()));
        String time = Dates.SIMPLE_TIME.format(yesterday);
        String expected = Strings.lenientFormat(Dates.DEF_YESTERDAY, time);
        assertEquals(expected, actual);
    }

    @Test
    public void untilThisYear() {
        OffsetDateTime thisYear = OffsetDateTime.now().withDayOfYear(1);
        String actual = Dates.untilNow(Date.from(thisYear.toInstant()));
        assertEquals(thisYear.format(Dates.SIMPLE_DATE), actual);
    }

    @Test
    public void untilNormal() {
        OffsetDateTime millennium = OffsetDateTime.now().minusYears(1).withDayOfYear(1);
        String actual = Dates.untilNow(Date.from(millennium.toInstant()));
        assertEquals(millennium.format(DateTimeFormatter.ISO_LOCAL_DATE), actual);
    }
}