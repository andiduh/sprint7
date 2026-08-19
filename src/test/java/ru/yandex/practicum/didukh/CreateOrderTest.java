package ru.yandex.practicum.didukh;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.yandex.practicum.didukh.order.Order;
import ru.yandex.practicum.didukh.order.OrderClient;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest {
    private OrderClient orderClient;

    @BeforeEach
    public void setUp() {
        orderClient = new OrderClient();
    }

    @ParameterizedTest
    @MethodSource({"colours"})
    public void createOrderWithBlackColourIsSuccess(String[] colour) {
        Order order = new Order("Naruto", "Uzumaki", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", colour);

        orderClient.createOrder(order)
                .then().statusCode(201).body("track", notNullValue());
    }

    static Stream<Arguments> colours() {
        return Stream.of(
                Arguments.of((Object) new String[]{"BLACK"}),
                Arguments.of((Object) new String[]{"GREY"}),
                Arguments.of((Object) new String[]{"BLACK", "GREY"}),
                Arguments.of((Object) new String[]{})
        );
    }

}
