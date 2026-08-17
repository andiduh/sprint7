package ru.yandex.practicum.didukh;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.didukh.courier.Authorization;
import ru.yandex.practicum.didukh.courier.Courier;
import ru.yandex.practicum.didukh.courier.CourierClient;
import ru.yandex.practicum.didukh.courier.FakeCourier;

import static org.hamcrest.Matchers.equalTo;

public class LoginCourierTest {
    private CourierClient courierClient;
    Courier courier;
    private int courierId;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
        courier = FakeCourier.getFakeCourier();
        courierClient.createCourier(courier);
        courierId = courierClient.loginCourier(Authorization.from(courier))
                .then().extract().path("id");
    }

    @Test
    public void loginCourierIsSuccess() {
        courierClient.loginCourier(Authorization.from(courier))
                .then().statusCode(200).body("id", equalTo(courierId));
    }

    @Test
    public void loginCourierWithoutLoginIsFailed() {
        courierClient.loginCourier(new Authorization(null, courier.getPassword()))
                .then().statusCode(400).body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginCourierWithoutPasswordIsFailed() {
        courierClient.loginCourier(new Authorization(courier.getLogin(), null))
                .then().statusCode(400).body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginCourierWithWrongLoginIsFailed() {
        courierClient.loginCourier(new Authorization("wrongLogin", courier.getPassword()))
                .then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginCourierWithWrongPasswordIsFailed() {
        courierClient.loginCourier(new Authorization(courier.getLogin(), "wrongPassword"))
                .then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWrongCourierIsFailed() {
        courierClient.loginCourier(new Authorization("wrongLogin", "wrongPassword"))
                .then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }

    @AfterEach
    public void tearDown() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierClient.loginCourier(Authorization.from(courier))
                    .then().extract().path("id"));
        }
    }
}
