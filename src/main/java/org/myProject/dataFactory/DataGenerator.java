package org.myProject.dataFactory;

import net.datafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class DataGenerator {
    private static Faker faker;

    public DataGenerator() {
        faker = new Faker(new Locale("en","IND"));
    }

    public static String getNamePrefix() {
        return faker.name().prefix();
    }

    public  String getFullName() {
        return faker.name().fullName();
    }

    public static String getFirstName() {
        return faker.name().firstName();
    }

    public static String getLastName() {
        return faker.name().lastName();
    }

    public String getAddress() {
        return faker.address().streetAddress();
    }

    public String getCity() {
        return faker.address().city();
    }

    public static String getState() {
        return faker.address().state();
    }

    public String getZipCode() {
        return faker.address().zipCode();
    }

    public static String getCountry() {
        return faker.address().country();
    }

    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    public String getPhoneNumber() {
        return faker.phoneNumber().phoneNumber();
    }

    public static String getUserName() {
        return faker.name().username();
    }

    public static String getPassword() {
        return faker.internet().password();
    }

    public static String getDynamicEmail(String prefixName) {
        return prefixName + faker.internet().emailAddress();
    }

    public static String getDynamicPhoneNumber(String prefixName) {
        return prefixName + faker.phoneNumber().phoneNumber();
    }

    public String getFakeGender() {
        return faker.options().option("Male", "Female");
    }

    public String getFakeAge() {
        return String.valueOf(faker.number().numberBetween(18,80));
    }

    public static String getFakeOccupation() {
        return faker.job().title();
    }

    public static String getFakeEmail() {
        return faker.internet().emailAddress();
    }

    public static String getFakeFirstName() {
        return faker.name().firstName();
    }

    public static String getFakeLastName() {
        return faker.name().lastName();
    }

    public static String getFakePhoneNumber() {
        return faker.phoneNumber().phoneNumber();
    }

    public static String getFakeState() {
        return faker.address().state();
    }

    public static String getDynamicUserName(String prefixName) {
        return prefixName + faker.name().username();
    }

    public static String getDynamicPassword(String prefixName) {
        return prefixName + faker.internet().password();
    }

    public static String getDynamicAddress(String prefixName) {
        return prefixName + faker.address().streetAddress();
    }

    public static String getDynamicCity(String prefixName) {
        return prefixName + faker.address().city();
    }

    public static String getDynamicState(String prefixName) {
        return prefixName + faker.address().state();
    }

    public static String getDynamicZipCode(String prefixName) {
        return prefixName + faker.address().zipCode();
    }

    public static String getDynamicCountry(String prefixName) {
        return prefixName + faker.address().country();
    }

    public static String getDynamicFullName(String prefixName) {
        return prefixName + faker.name().fullName();
    }

    public static String getDynamicPrefix(String prefixName) {
        return prefixName + faker.name().prefix();
    }

    public static String getDynamicFirstName(String prefixName) {
        return prefixName + faker.name().firstName();
    }

    public static String getDynamicLastName(String prefixName) {
        return prefixName + faker.name().lastName();
    }

    public static String getDynamicEmailDomain(String prefixName) {
        return prefixName + faker.internet().domainName();
    }

    public static String getDynamicUserNameDomain(String prefixName) {
        return prefixName + faker.name().username();
    }

    public static String getDynamicName(String prefixName) {
        return prefixName + faker.idNumber().valid();
    }

    public String generateRandomNumber(int count) {
        String name = RandomStringUtils.randomNumeric(count);
        System.out.println(name);
        return name;
    }

    public String generateRandomAlphabetic(int count) {
        String name = RandomStringUtils.randomAlphabetic(count);
        System.out.println(name);
        return name;
    }

    public String generateRandomAlphanumeric(int count) {
        String name = RandomStringUtils.randomAlphanumeric(count);
        System.out.println(name);
        return name;
    }

    public String generateRandomNumeric(int count) {
        String name = RandomStringUtils.randomNumeric(count);
        System.out.println(name);
        return name;
    }

    public String generateRandomString(int count) {
        String name = RandomStringUtils.randomAlphabetic(count);
        System.out.println(name);
        return name;
    }

    public static void main(String[] args) {
        DataGenerator dataGenerator = new DataGenerator();
        System.out.println("Age :=>"+dataGenerator.getFakeAge());
    }

}
