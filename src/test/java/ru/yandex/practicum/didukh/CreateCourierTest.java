package ru.yandex.practicum.didukh;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.didukh.courier.Authorization;
import ru.yandex.practicum.didukh.courier.Courier;
import ru.yandex.practicum.didukh.courier.CourierClient;
import ru.yandex.practicum.didukh.courier.FakeCourier;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    private CourierClient courierClient;
    private int courierId;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    public void createCourierIsSuccess() {
        Courier courier = FakeCourier.getFakeCourier();
        courierClient.createCourier(courier)
                .then().statusCode(201).body("ok", equalTo(true));
        courierId = courierClient.loginCourier(Authorization.from(courier))
                .then().extract().path("id");
    }

    @Test
    public void createTwoIdenticalCouriersIsFailed() {
        Courier courier = FakeCourier.getFakeCourier();
        courierClient.createCourier(courier);
        courierClient.createCourier(courier)
                .then().statusCode(409).body("message", equalTo("Этот логин уже используется"));
        courierId = courierClient.loginCourier(Authorization.from(courier))
                .then().extract().path("id");
    }

    @Test
    public void createCourierWithoutLoginIsFailed() {
        Courier courier = FakeCourier.getFakeCourierWithoutLogin();
        courierClient.createCourier(courier)
                .then().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithoutPasswordIsFailed() {
        Courier courier = FakeCourier.getFakeCourierWithoutPassword();
        courierClient.createCourier(courier)
                .then().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createTwoCouriersWithIdenticalLoginIsFailed() {
        Courier courier = FakeCourier.getFakeCourier();
        String login = courier.getLogin();
        courierClient.createCourier(courier);
        Courier secondCourier = new Courier(login, "password", "firstName");
        courierClient.createCourier(secondCourier)
                .then().statusCode(409).body("message", equalTo("Этот логин уже используется"));
        courierId = courierClient.loginCourier(Authorization.from(courier))
                .then().extract().path("id");
    }

    @AfterEach
    public void tearDown() {
        if (courierId != 0) courierClient.deleteCourier(courierId);
    }
}
