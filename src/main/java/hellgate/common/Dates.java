package hellgate.common;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;
import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_TIME;

/**
 * Date 工具。
 * <p>
 * 针对 {@link Date} 类进行相关处理，包括：
 * <p>
 * 1. 格式化
 * <p>
 * 2. 字符串解析
 * <p>
 * 3. 日期比较
 * <p>
 * 4. 日期间隔
 * <p>
 * 注意：主要使用 java.time 包下的类进行适配，启示开发者应该直接使用这些日期及时间类。
 * <p>
 * 这个工具类是线程安全的。
 */
public final class Dates {
    private Dates() {
        // no instances
    }

    /* for test */static final DateTimeFormatter SIMPLE_DATE_TIME
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /* for test */static final DateTimeFormatter SIMPLE_TIME
            = DateTimeFormatter.ofPattern("HH:mm");
    /* for test */static final DateTimeFormatter SIMPLE_DATE
            = DateTimeFormatter.ofPattern("MM-dd HH:mm");

    public static final String KEY_UNKNOWN = "unknown";
    public static final String KEY_JUST_NOW = "just_now";
    public static final String KEY_MINUTES_AGO = "minutes_ago";
    public static final String KEY_TODAY = "today";
    public static final String KEY_YESTERDAY = "yesterday";

    /* for test */static final String DEF_UNKNOWN = "未知";
    /* for test */static final String DEF_JUST_NOW = "刚刚";
    /* for test */static final String DEF_MINUTES_AGO = "%s 分钟前";
    /* for test */static final String DEF_TODAY = "今天 %s";
    /* for test */static final String DEF_YESTERDAY = "昨天 %s";

    private static final Map<String, String> UNTIL_MESSAGE_CACHED = Maps.newHashMap();

    static {
        UNTIL_MESSAGE_CACHED.put(KEY_UNKNOWN, DEF_UNKNOWN);
        UNTIL_MESSAGE_CACHED.put(KEY_JUST_NOW, DEF_JUST_NOW);
        UNTIL_MESSAGE_CACHED.put(KEY_MINUTES_AGO, DEF_MINUTES_AGO);
        UNTIL_MESSAGE_CACHED.put(KEY_TODAY, DEF_TODAY);
        UNTIL_MESSAGE_CACHED.put(KEY_YESTERDAY, DEF_YESTERDAY);
    }

    /**
     * 默认格式化 Date 类。
     * <p>
     * 2011-12-03 10:15:30
     */
    public static String format(Date date) {
        Preconditions.checkNotNull(date, "date == null");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime.format(SIMPLE_DATE_TIME);
    }

    /**
     * 以指定模板格式化 Date 类。
     */
    public static String format(Date date, String pattern) {
        Preconditions.checkNotNull(date, "date == null");
        Preconditions.checkNotNull(pattern, "pattern == null");
        pattern = CharMatcher.whitespace().trimFrom(pattern);
        Preconditions.checkArgument(!pattern.isEmpty(), "pattern can not be whitespace");
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return offsetDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 以经典方式格式化 Date 类。
     * <p>
     * 20111203
     */
    public static String formatBasic(Date date) {
        Preconditions.checkNotNull(date, "date == null");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime.format(BASIC_ISO_DATE);
    }

    /**
     * 以 Instant 格式化 Date 类。
     * <p>
     * 注意：时区是 UTC 而不是本地时区。
     * <p>
     * 2011-12-03T10:15:30Z
     */
    public static String formatInstant(Date date) {
        Preconditions.checkNotNull(date, "date == null");
        return ISO_INSTANT.format(date.toInstant());
    }

    /**
     * 以 LocalDateTime 格式化 Date 类。
     * <p>
     * 2011-12-03T10:15:30
     */
    public static String formatLocal(Date date) {
        Preconditions.checkNotNull(date, "date == null");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime.format(ISO_LOCAL_DATE_TIME);
    }

    /**
     * 以 LocalDate 格式化 Date 类。
     * <p>
     * 2011-12-03
     */
    public static String formatLocalDate(Date date) {
        Preconditions.checkNotNull(date, "date == null");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime.format(ISO_LOCAL_DATE);
    }

    /**
     * 以 LocalTime 格式化 Date 类。
     * <p>
     * 10:15 or 10:15:30
     */
    public static String formatLocalTime(Date date) {
        Preconditions.checkNotNull(date, "date == null");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime.format(ISO_LOCAL_TIME);
    }

    /**
     * 以 OffsetDateTime 格式化 Date 类。
     * <p>
     * 2011-12-03T10:15:30+01:00
     */
    public static String formatOffset(Date date) {
        Preconditions.checkNotNull(date, "date == null");
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return offsetDateTime.format(ISO_OFFSET_DATE_TIME);
    }

    /**
     * 以 OffsetDate 格式化 Date 类。
     * <p>
     * 2011-12-03+01:00
     */
    public static String formatOffsetDate(Date date) {
        Preconditions.checkNotNull(date, "date == null");
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return offsetDateTime.format(ISO_OFFSET_DATE);
    }

    /**
     * 以 OffsetTime 格式化 Date 类。
     * <p>
     * 10:15+01:00 or 10:15:30+01:00
     */
    public static String formatOffsetTime(Date date) {
        Preconditions.checkNotNull(date, "date == null");
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return offsetDateTime.format(ISO_OFFSET_TIME);
    }

    /**
     * 默认解析为 Date 类。
     * <p>
     * 2011-12-03 10:15:30
     */
    public static Date parse(String text) {
        Preconditions.checkNotNull(text, "text == null");
        text = CharMatcher.whitespace().trimFrom(text);
        Preconditions.checkArgument(!text.isEmpty(), "text can not be whitespace");
        Instant instant = LocalDateTime.parse(text, SIMPLE_DATE_TIME)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return Date.from(instant);
    }

    /**
     * 以经典方式解析为 Date 类。
     * <p>
     * 20111203
     */
    public static Date parseBasic(String text) {
        Preconditions.checkNotNull(text, "text == null");
        text = CharMatcher.whitespace().trimFrom(text);
        Preconditions.checkArgument(!text.isEmpty(), "text can not be whitespace");
        Instant instant = LocalDate.parse(text, BASIC_ISO_DATE)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        return Date.from(instant);
    }

    /**
     * 以 Instant 解析为 Date 类。
     * <p>
     * 2011-12-03T10:15:30Z
     */
    public static Date parseInstant(String text) {
        Preconditions.checkNotNull(text, "text == null");
        text = CharMatcher.whitespace().trimFrom(text);
        Preconditions.checkArgument(!text.isEmpty(), "text can not be whitespace");
        return Date.from(Instant.parse(text));
    }

    /**
     * 以 LocalDateTime 解析为 Date 类。
     * <p>
     * 2011-12-03T10:15:30
     */
    public static Date parseLocal(String text) {
        Preconditions.checkNotNull(text, "text == null");
        text = CharMatcher.whitespace().trimFrom(text);
        Preconditions.checkArgument(!text.isEmpty(), "text can not be whitespace");
        Instant instant = LocalDateTime.parse(text)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return Date.from(instant);
    }

    /**
     * 以 LocalDate 解析为 Date 类。
     * <p>
     * 2011-12-03
     */
    public static Date parseLocalDate(String text) {
        Preconditions.checkNotNull(text, "text == null");
        text = CharMatcher.whitespace().trimFrom(text);
        Preconditions.checkArgument(!text.isEmpty(), "text can not be whitespace");
        Instant instant = LocalDate.parse(text)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        return Date.from(instant);
    }

    /**
     * 以 LocalTime 解析为 Date 类。
     * <p>
     * 10:15:30
     */
    public static Date parseLocalTime(String text) {
        Preconditions.checkNotNull(text, "text == null");
        text = CharMatcher.whitespace().trimFrom(text);
        Preconditions.checkArgument(!text.isEmpty(), "text can not be whitespace");
        Instant instant = LocalTime.parse(text)
                .atDate(LocalDate.ofEpochDay(0))
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return Date.from(instant);
    }

    /**
     * 以 OffsetDateTime 解析为 Date 类。
     * <p>
     * 2011-12-03T10:15:30+01:00
     */
    public static Date parseOffset(String text) {
        Preconditions.checkNotNull(text, "text == null");
        text = CharMatcher.whitespace().trimFrom(text);
        Preconditions.checkArgument(!text.isEmpty(), "text can not be whitespace");
        Instant instant = OffsetDateTime.parse(text).toInstant();
        return Date.from(instant);
    }

    /**
     * 以 OffsetTime 解析为 Date 类。
     * <p>
     * 10:15+01:00 or 10:15:30+01:00
     */
    public static Date parseOffsetTime(String text) {
        Preconditions.checkNotNull(text, "text == null");
        text = CharMatcher.whitespace().trimFrom(text);
        Preconditions.checkArgument(!text.isEmpty(), "text can not be whitespace");
        Instant instant = OffsetTime.parse(text)
                .atDate(LocalDate.ofEpochDay(0))
                .toInstant();
        return Date.from(instant);
    }

    /**
     * 检测 first 是否早于 second
     * <p>
     * 以时间线为 X 轴，如果 first 在 second 的左边，则返回 true；否则返回 false。
     * <p>
     * 举例来说：
     * <p>
     * 2021-11-30 14:00:00 isBefore 2021-11-30 14:05:00 == true
     * <p>
     * 2021-11-31 00:00:00 isBefore 2021-11-30 14:05:00 == false
     * <p>
     * 核心逻辑是毫秒值的差值比较。
     */
    public static boolean isBefore(Date first, Date second) {
        Preconditions.checkNotNull(first, "first == null");
        Preconditions.checkNotNull(first, "second == null");
        return first.toInstant().isBefore(second.toInstant());
    }

    /**
     * 检测 first 是否晚于 second
     * <p>
     * 以时间线为 X 轴，如果 first 在 second 的右边，则返回 true；否则返回 false。
     * <p>
     * 举例来说：
     * <p>
     * 2021-11-30 14:00:00 isAfter 2021-11-30 14:05:00 == false
     * <p>
     * 2021-11-31 00:00:00 isAfter 2021-11-30 14:05:00 == true
     * <p>
     * 核心逻辑是毫秒值的差值比较。
     */
    public static boolean isAfter(Date first, Date second) {
        Preconditions.checkNotNull(first, "first == null");
        Preconditions.checkNotNull(first, "second == null");
        return first.toInstant().isAfter(second.toInstant());
    }

    /**
     * 默认比较两个 Date 类之间的毫秒间隔。
     */
    public static long between(Date start, Date end) {
        Preconditions.checkNotNull(start, "start == null");
        Preconditions.checkNotNull(end, "end == null");
        return ChronoUnit.MILLIS.between(start.toInstant(), end.toInstant());
    }

    /**
     * 比较两个 Date 类的 Day 差值。
     * <p>
     * 以一天 86400 秒为基准，取得两个日期之间的秒数差值，再除以基准值得到天数。
     * <p>
     * 比如：
     * <p>
     * 2021-11-30 00:00:00 和 2021-12-01 00:00:00 之间相差 2 天
     * <p>
     * 2021-11-30 00:00:00 和 2021-11-31 23:59:59 之间相差 1 天
     */
    public static long betweenDay(Date start, Date end) {
        Preconditions.checkNotNull(start, "start == null");
        Preconditions.checkNotNull(end, "end == null");
        return ChronoUnit.DAYS.between(start.toInstant(), end.toInstant());
    }

    /**
     * 比较两个 Date 类的 Hours 差值。
     * <p>
     * 以一小时 3600 秒为基准，取得两个日期之间的秒数差值，再除以基准值得到小时数。
     * <p>
     * 比如：
     * <p>
     * 2021-11-30 00:00:00 和 2021-11-30 02:00:00 之间相差 2 小时
     * <p>
     * 2021-11-30 00:00:00 和 2021-11-30 01:59:59 之间相差 1 小时
     */
    public static long betweenHours(Date start, Date end) {
        Preconditions.checkNotNull(start, "start == null");
        Preconditions.checkNotNull(end, "end == null");
        return ChronoUnit.HOURS.between(start.toInstant(), end.toInstant());
    }

    /**
     * 比较两个 Date 类的 Minutes 差值。
     * <p>
     * 以一分钟 60 秒为基准，取得两个日期之间的秒数差值，再除以基准值得到分钟数。
     * <p>
     * 比如：
     * <p>
     * 2021-11-30 00:00:00 和 2021-11-30 00:02:00 之间相差 2 分钟
     * <p>
     * 2021-11-30 00:00:00 和 2021-11-30 00:01:59 之间相差 1 分钟
     */
    public static long betweenMinutes(Date start, Date end) {
        Preconditions.checkNotNull(start, "start == null");
        Preconditions.checkNotNull(end, "end == null");
        return ChronoUnit.MINUTES.between(start.toInstant(), end.toInstant());
    }

    /**
     * 比较两个 Date 类的 Seconds 差值。
     * <p>
     * 直接取得两个日期之间的秒数差值。
     * <p>
     * 对于正的秒数来说，如果纳秒差值小于 0 那么秒数 -1，对于负的秒数来说，如果纳秒差值大于 0 那么秒数 +1。
     * <p>
     * 比如：
     * <p>
     * 2021-11-30 00:00:00.111 和 2021-11-30 00:00:03.000 之间相差 2 秒钟
     * <p>
     * 2021-11-30 00:00:02.000 和 2021-11-30 00:00:00.111 之间相差 -1 秒钟
     */
    public static long betweenSeconds(Date start, Date end) {
        Preconditions.checkNotNull(start, "start == null");
        Preconditions.checkNotNull(end, "end == null");
        return ChronoUnit.SECONDS.between(start.toInstant(), end.toInstant());
    }

    /**
     * 显示目标 Date 类距离现在过去了多久。
     * <p>
     * 默认的显示规则如下：
     * <p>
     * now < target：未知
     * <p>
     * 1 分钟以内：刚刚
     * <p>
     * 1 小时以内： N 分钟前
     * <p>
     * 半天以内： HH:mm:ss
     * <p>
     * 1 天以内： 今天 HH:mm
     * <p>
     * 2 天以内：昨天 HH:mm
     * <p>
     * 1 年以内： MM-dd
     * <p>
     * 其他： yyyy-MM-dd
     */
    public static String untilNow(Date target) {
        return untilNow(target, UNTIL_MESSAGE_CACHED::get);
    }

    /**
     * 显示目标 Date 类距离现在过去了多久。
     * <p>
     * 显示规则由传递的 messageApply 决定。
     */
    public static String untilNow(Date target, Function<String, String> messageApply) {
        Preconditions.checkNotNull(target, "target == null");
        Preconditions.checkNotNull(messageApply, "messageApply == null");

        Instant targetInstant = target.toInstant();
        // now < target == unknown
        if (Instant.now().isBefore(targetInstant)) {
            return messageApply.apply(KEY_UNKNOWN);
        }

        LocalDateTime dateTime = LocalDateTime.ofInstant(targetInstant, ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now();

        // now - date time < 1 minutes == just now
        long minutes = dateTime.until(now, ChronoUnit.MINUTES);
        if (minutes == 0) {
            return messageApply.apply(KEY_JUST_NOW);
        }

        // now - date time < 1 hours == N minutes ago
        long hours = dateTime.until(now, ChronoUnit.HOURS);
        if (hours == 0) {
            String minutesAgoTemplate = messageApply.apply(KEY_MINUTES_AGO);
            return Strings.lenientFormat(minutesAgoTemplate, minutes);
        }

        // today midnight [00:00:00]
        LocalDateTime todayMidnight = now.with(LocalTime.MIDNIGHT);
        // now > date time > today midnight == today
        if (dateTime.isAfter(todayMidnight)) {
            String todayTemplate = messageApply.apply(KEY_TODAY);
            String timeOfDays = SIMPLE_TIME.format(dateTime);
            return Strings.lenientFormat(todayTemplate, timeOfDays);
        }

        // yesterday midnight
        LocalDateTime yesterdayMidnight = now.minusDays(1).with(LocalTime.MIDNIGHT);
        if (dateTime.isAfter(yesterdayMidnight)) {
            String yesterdayTemplate = messageApply.apply(KEY_YESTERDAY);
            String timeOfDays = SIMPLE_TIME.format(dateTime);
            return Strings.lenientFormat(yesterdayTemplate, timeOfDays);
        }

        // first day of year midnight
        LocalDateTime dayOfYearMidnight = now.withDayOfYear(1).with(LocalTime.MIDNIGHT);
        if (dateTime.isAfter(dayOfYearMidnight)) {
            return SIMPLE_DATE.format(dateTime);
        }

        return ISO_LOCAL_DATE.format(dateTime);
    }
}
