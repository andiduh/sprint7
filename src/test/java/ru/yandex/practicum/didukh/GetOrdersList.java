package ru.yandex.practicum.didukh;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.didukh.order.OrderClient;

import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersList {

    @Test
    public void getList() {
        new OrderClient().getListOfOrders()
                .then().statusCode(200).body("orders", notNullValue());
    }
}
