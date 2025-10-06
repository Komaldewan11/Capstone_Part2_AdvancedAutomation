package automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import web_nopcommerce.config.EnvironmentVariables;
import web_nopcommerce.pages.CheckOutPage;
import web_nopcommerce.pages.LoginPage;
import web_nopcommerce.pages.RegisterPage;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected String baseUrl = "https://demo.nopcommerce.com/";

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(EnvironmentVariables.WAIT_IMPLICIT));
        driver.manage().window().maximize();
    }

    public RegisterPage getUserInfo() {
        return new RegisterPage(driver);
    }

    public LoginPage getLoginInfo() {
        return new LoginPage(driver);
    }

    public CheckOutPage getCheckoutInfo() {
        return new CheckOutPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
