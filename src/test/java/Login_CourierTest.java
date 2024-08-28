import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class Login_CourierTest {
    CourierRequest courierRequest = new CourierRequest();
    Courier courier = new Courier ("fast_n_furious","798465132");

    @Before
    public void setUp(){
        courierRequest.setUp();
    }
    @Test
    @DisplayName("Авторизация курьера в системе(позитивная проверка)")
    @Description("Проверка авторизации с корректными данными (логин и пароль")
    public void checkLoginCourier(){
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.loginCourier()
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Авторизация без логина(негативная проверка)")
    @Description("Проверка невозможности авторизоваться без указания логина")
    public void checkLoginWithoutLogin(){
        courierRequest.setCourier(new Courier("","123"));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Авторизация без пароля(негативная проверка)")
    @Description("Проверка невозможности авторизоваться без указания пароля")
    public void checkLoginWithoutPassword(){
        courierRequest.setCourier(new Courier("SuperCourier",""));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Авторизация вводом несуществующего курьером(негативная проверка)")
    @Description("Проверка авторизации ранее не созданным курьром")
    public void checkLoginNonExisting(){
        courierRequest.setCourier(courier);
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация с указанием неверного логина (негативная проверка)")
    @Description("Проверка авторизации с вводом неверного логина")
    public void checkLoginIncorrectLogin() {
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.setCourier(new Courier("SuperDuperCourier","123"));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
        courierRequest.setCourier(courier);
    }

    @Test
    @DisplayName("Авторизация с указанием неверного пароля (негативная проверка)")
    @Description("Проверка авторизации с вводом неверного пароля")
    public void checkLoginIncorrectPassword() {
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.setCourier(new Courier("SuperCourier","1234"));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
        courierRequest.setCourier(courier);
    }
    @After
    public void cleanData(){
        courierRequest.deleteCourier();
    }
}
