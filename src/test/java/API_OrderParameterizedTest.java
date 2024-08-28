import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class API_OrderParameterizedTest {
    private final Order order;

    public API_OrderParameterizedTest(Order order) {
        this.order = order;
    }

    @Before
    public void setUp() {
               try {
            orderRequest.setUp();
        } catch (Exception e) {
            System.out.println("Error in setup: " + e.getMessage());
        }
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {new Order("Миша", "Коллинз", "Измайловский пр-кт", "Измайлово", "+79999995966", 3, "2024-09-12", "Позвонить за час", new String[]{"BLACK"})},
                {new Order("Сэм", "Винчестер", "Комсомольский пр-кт", "Комсомльская", "+79997995966", 1, "2024-09-11", "Позвонить за час", new String[]{"GREY"})},
                {new Order("Дин", "Винчестер", "Измайловский пр-кт", "Измайлово", "+79999995977", 4, "2024-09-13", "Позвонить за час", new String[]{"BLACK", "GREY"})},
                {new Order("Доктор", "Хаус", "Черкизовский пр-кт", "Черкизовская", "+79999996666", 1, "2024-09-15", "Позвонить за час", new String[]{})}
        };
    }

    OrderRequest orderRequest = new OrderRequest();

    @Test
    @DisplayName("Создание заказа самоката с разным цветом")
    @Description("Проверка создания заказа самоката с разным цветом")
    public void checkCreateOrder() {
        try {
            orderRequest.setOrder(order);
            orderRequest.createOrderRequest()
                    .then().statusCode(SC_CREATED)
                    .and()
                    .assertThat().body("track", notNullValue());
        } catch (Exception e) {
            System.out.println("Error in creating order: " + e.getMessage());
        }
    }
}