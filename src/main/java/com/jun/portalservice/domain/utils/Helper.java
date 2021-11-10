package com.jun.portalservice.domain.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Helper {

  public static int randomBetween(int min, int max) {
    return (int) (Math.random() * (max - min)) + min;
  }

  public static String genUserName(int tenantId, String partnerId) {
    return tenantId + "_" + partnerId;
  }

  public static LocalDate getTodayDateHCMZone() {
    return getTodayDateTimeHCMZone().toLocalDate();
  }

  public static java.time.LocalDateTime getTodayDateTimeHCMZone() {
    return java.time.LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
  }

  public static boolean regexPhoneNumber(String phoneNumber) {
    if (phoneNumber == null) {
      return false;
    }
    String regex = "^0[37859]{1}[0-9]{1}\\d{7}$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(phoneNumber);
    return matcher.find();
  }

  public static boolean regexEmail(String email) {
    if (email == null) {
      return false;
    }
    String regex =
        "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(email);
    return matcher.find();
  }

  public static String generateCode() {
    Random r = new Random(System.currentTimeMillis());
    return String.valueOf(((1 + r.nextInt(2)) * 100000 + r.nextInt(100000)));
  }

  public static String md5Token(String token) {
    return DigestUtils.sha256Hex(token);
  }

  public static boolean regexUrl(String url) {
    if (url == null) {
      return false;
    }
    String regex =
        "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(url);
    return matcher.find();
  }

  public static boolean compareDate(Date dateField) {
    if (dateField == null) {
      return false;
    }
    long millis = System.currentTimeMillis();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
    String stringDate = formatter.format(new Date(millis));
    String inputDate = formatter.format(dateField);
    try {
      Date currentDate = formatter.parse(stringDate);
      Date birthDay = formatter.parse(inputDate);
      int status = birthDay.compareTo(currentDate);
      if (status == 1) {
        return false;
      }
      return true;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static <T> Collector<T, ?, Optional<T>> toSingleton() {
    return Collectors.collectingAndThen(
        Collectors.toList(),
        list -> list.size() == 1 ? Optional.of(list.get(0)) : Optional.empty());
  }

  public static String dateTimeToString(DateTimeFormatter formatter, LocalDateTime dateTime) {
    return dateTime.format(formatter);
  }

  public static String formatDuration(long duration) {
    long hours = TimeUnit.MILLISECONDS.toHours(duration);
    long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
    long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60;
    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
  }

  public static String longToDateFormat(long millis, DateFormat format) {
    Date currentDate = new Date(millis);
    return format.format(currentDate);
  }

  public static Date stringToDate(String date) throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    return formatter.parse(date);
  }

  public static java.time.LocalDateTime convertFromHCMToUTC(java.time.LocalDateTime dateTimeHCM) {
    return dateTimeHCM
        .atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
        .withZoneSameInstant(ZoneId.of("UTC"))
        .toLocalDateTime();
  }

  public static LocalDateTime convertToHCM(ZoneId zone, LocalDateTime dateFrom) {
    return dateFrom
        .atZone(zone)
        .withZoneSameInstant(ZoneId.of("Asia/Ho_Chi_Minh"))
        .toLocalDateTime();
  }

  public static LocalDateTime convertToUTC(ZoneId zone, LocalDateTime dateFrom) {
    return dateFrom.atZone(zone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
  }

  public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {

    List<LocalDate> dates =
        Stream.iterate(startDate, date -> date.plusDays(1))
            .limit(ChronoUnit.DAYS.between(startDate, endDate) + 1)
            .collect(Collectors.toList());
    return dates;
  }

  public static LocalDateTime convertFromStringToLocalDateTime(String time) {
    LocalDateTime localDateTime =
        LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    return localDateTime;
  }

  public static String convertLocalDateTimeToString(LocalDateTime dateTime) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //    LocalDateTime startDateTime = dateTime.with(LocalTime.MIN);
    return dateTime.format(dateTimeFormatter);
  }

  public static LocalDateTime startOfDay(LocalDateTime dateTime) {
    return dateTime.with(LocalTime.MIN);
  }

  public static LocalDateTime endOfDay(LocalDateTime dateTime) {
    return dateTime.with(LocalTime.MAX);
  }

  public static long toEpochDay(String date) {
    return LocalDate.parse(date).toEpochDay();
  }

  public static long localDateToLong(LocalDateTime localDateTime) {
    return localDateTime.toEpochSecond(ZoneOffset.UTC);
  }
}
