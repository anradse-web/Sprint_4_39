package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import page.object.MainPage;
import page.object.OrderPage;
import static org.junit.Assert.assertTrue;


@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {
    private final String buttonLocation;
    private final String name; //для значения поля "имя"
    private final String surname; //для значения поля "Фамилия"
    private final String address; ////для значения поля "Адрес"
    private final String Station; //для значения поля "Станция метро"
    private final String phone; //для значения поля "Телефон"
    private final String date; //для значения "Когда привезти самокат"
    private final String option; //для значения "Срок аренды"
    private final String comment; //для значения поля "Комментарий"

    public OrderTest(String buttonLocation, String name, String surname, String address, String Station, String phone, String date, String option, String comment) {
        this.buttonLocation = buttonLocation;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.Station = Station;
        this.phone = phone;
        this.date = date;
        this.option = option;
        this.comment = comment;

    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {"header","Илья","Пупкин","ул. Малиновая, 2","Пражская","79999999999","12.05.1991","сутки","Звонить за 30 мин"},
                {"middle","Максим","Федоров","Садовая, 20","Анино","79999999999","17.12.2002","трое суток","Откроет ребенок"},
        };
    }

    @Test
    public void successfulOrderCreation() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.acceptCookies();

        if ("header".equals(buttonLocation)) {
            mainPage.clickHeaderOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillInOrderForm(name, surname, address, Station, phone, date,  option, comment);
        orderPage.confirmOrder();

        boolean isSuccess = orderPage.isOrderSuccessModalVisible();
        assertTrue("Заказ не был оформлен (окно не появилось)", isSuccess);
    }
}