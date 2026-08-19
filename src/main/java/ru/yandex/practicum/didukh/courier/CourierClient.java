package ru.yandex.practicum.didukh.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.didukh.constants;

import static io.restassured.RestAssured.given;

public class CourierClient {

    @Step("Создание курьера")
    public Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(constants.URL)
                .body(courier)
                .post("/api/v1/courier");
    }

    @Step("Авторизация курьера")
    public Response loginCourier(Authorization authorization) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(constants.URL)
                .body(authorization)
                .post("/api/v1/courier/login");
    }

    @Step("Удаление курьера")
    public Response deleteCourier(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(constants.URL)
                .post("/api/v1/courier/" + courierId);
    }
}
