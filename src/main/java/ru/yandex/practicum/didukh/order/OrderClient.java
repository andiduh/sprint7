package ru.yandex.practicum.didukh.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.didukh.constants;

import static io.restassured.RestAssured.given;

public class OrderClient {

    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(constants.URL)
                .body(order)
                .post("/api/v1/orders");
    }

    @Step("Получение списка заказов")
    public Response getListOfOrders() {
        return given()
                .baseUri(constants.URL)
                .get("/api/v1/orders");
    }
}
