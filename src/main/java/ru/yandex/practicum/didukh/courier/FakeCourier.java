package ru.yandex.practicum.didukh.courier;

import com.github.javafaker.Faker;

public class FakeCourier {
    private static final Faker faker = new Faker();

    public static Courier getFakeCourier() {
        return new Courier(
                faker.name().username(),
                faker.internet().password(),
                faker.name().firstName()
        );
    }

    public static Courier getFakeCourierWithoutLogin() {
        return new Courier(
                null,
                faker.internet().password(),
                faker.name().firstName()
        );
    }

    public static Courier getFakeCourierWithoutPassword() {
        return new Courier(
                faker.name().username(),
                null,
                faker.name().firstName()
        );
    }
}
