package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(Locale.ENGLISH);
    private static Faker cyrillicName = new Faker(new Locale("RU"));

    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        private String number;
        private String month;
        private String year;
        private String holder;
        private String cvc;
    }

    public static String getValidApprovedNumberCard() {
        return "1111 2222 3333 4444";
    }

    public static String getValidDeclinedDCard() {
        return "5555 6666 7777 8888";
    }

    public static String generateCardNumber() {
        return faker.numerify("1111 2222 3333 ####");
    }

    public static String generateCard15Numbers() {
        return faker.numerify("1111 2222 3333 ###");
    }

    public static String emptyField() {
        return "";
    }

    public static String spaceBarField() {
        return "    ";
    }

    public static String generateMonth(int addMonth) {
        return LocalDate.now().plusMonths(addMonth).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateLastMonth() {
        return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String nonexistentMonth(int index) {
        if (index == 0) {
            return "00";
        }
        if (index == 1) {
            return "13";
        }
        return null;
    }

    public static String generateYear(int addYear) {
        return LocalDate.now().plusYears(addYear).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateLastYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateHolder() {
        return faker.name().firstName().toUpperCase() + " " + faker.name().lastName().toUpperCase();
    }

    public static String generateHolderLowerCase() {
        return faker.name().firstName().toLowerCase() + " " + faker.name().lastName().toLowerCase();
    }

    public static String generateCyrillicHolder() {
        return cyrillicName.name().firstName().toUpperCase() + " " + cyrillicName.name().lastName().toUpperCase();
    }

    public static String generateOneWordHolder() {
        return faker.name().firstName().toUpperCase();
    }

    public static String generateHolderWithDashes() {
        return faker.name().firstName() + "-" + faker.name().lastName();
    }

    public static String generateHolderSymbols() {
        return faker.name().firstName() + " ^&@#";
    }

    public static String generateHolderWithNumbers() {
        return faker.number().digits(6);
    }

    public static String generateCvc() {
        return faker.numerify("###");
    }

    public static String generate2NumbersCvc() {
        return faker.numerify("##");
    }
}
