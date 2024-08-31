import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;


public class ApiCreateCourierTest {
    private static final String COURIER_URL = "https://qa-scooter.praktikum-services.ru/couriers";
    Courier courier = new Courier("fast_and_furious", "798465132");
    CourierRequest courierRequest = new CourierRequest();

    @Before
    public void setUp() {
 courierRequest.setUp();
    }

    @Test

    @DisplayName("Позитивная проверка создания курьера")
    @Description("Проверка успешного создания нового курьера")
    public void createCourierTest() {
        courierRequest.setCourier(new Courier("fast_and_furious", "798465132"));
        courierRequest.createCourier()
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Негативная проверка создания уже существующего курьера (с повторным логином)")
    @Description("Проверка невозможности создания двух одинаковых курьеров")
    public void createDuplicateCourierTest() {
        courierRequest.setCourier(new Courier("fast_and_furious", "798465132"));
        courierRequest.createCourier()
                .then()
                .statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Создание курьера с незаполненными данными")
    @Description("Проверка заполнения обязательных полей при создании курьера")
    public void createCourierWithoutRequiredFieldsTest() {
        courierRequest.setCourier(new Courier("",""));
        courierRequest.createCourier()
                .then().statusCode(400)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Создание курьера без указания логина")
    @Description("Проверка заполнения обязательных полей при создании курьера")
    public void createCourierWithoutLoginTest() {
        courierRequest.setCourier(new Courier("","798465132"));
        courierRequest.createCourier()
                .then().statusCode(400)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без указания пароля")
    @Description("Проверка щаполнения обязательных полей при создании курьера")
    public void createCourierWithoutPasswordTest() {
        courierRequest.setCourier(new Courier("fast_and_furious",""));
        courierRequest.createCourier()
                .then().statusCode(400)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        courierRequest.deleteCourier();
    }

}
