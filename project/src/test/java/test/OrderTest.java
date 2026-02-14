package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import page.object.MainPage;
import page.object.OrderPage;
import static org.junit.Assert.assertTrue;


@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {
    private final String entryPoint;
    private final String name;
    private final String surname;
    private final String address;
    private final String station;
    private final String phone;
    private final String date;
    private final String period;
    private final String comment;

    public OrderTest(String entryPoint, String name, String surname, String address, String station, String phone, String date, String period, String comment) {
        this.entryPoint = entryPoint;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.station = station;
        this.phone = phone;
        this.date = date;
        this.period = period;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {"header","Илья","Пупкин","ул. Малиновая, 2","Пражская","79872526483","12.03.2026","сутки","Звонить за 30 мин"},
                {"bottom","Максим","Федоров","Садовая, 20","Анино","79257478899","29.02.2026","трое суток","Откроет ребенок"},
        };
    }

    @Test
    public void successfulOrderCreation() {
        MainPage mainPage = new MainPage(driver);

        mainPage.open();
        mainPage.acceptCookies();

        if ("header".equals(entryPoint)) {
            mainPage.clickHeaderOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }
        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillFirstStep(name, surname, address, station, phone);
        orderPage.fillSecondStep(date, period, comment);
        orderPage.confirmOrder();

        boolean isSuccess = orderPage.isOrderSuccessModalVisible();
        assertTrue("Заказ не был оформлен (окно не появилось)", isSuccess);
    }
}