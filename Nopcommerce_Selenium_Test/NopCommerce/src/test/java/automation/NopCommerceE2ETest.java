package automation;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class NopCommerceE2ETest extends BaseTest {

    @DataProvider(name = "LinkText1")
    public Object[][] getTestData1() {
        return new Object[][]{
                {"female", "Kent", "Donovan", "testingKent@gmail.com", "testing1006"}
        };
    }

    @DataProvider(name = "LinkText2")
    public Object[][] getTestData2() {
        return new Object[][]{
                {"testingKent@gmail.com", "testing1006", "Laptop"}
        };
    }

    @DataProvider(name = "LinkText3")
    public Object[][] getTestData3() {
        return new Object[][]{
                {"testingKent@gmail.com", "testing1006", "Kent", "Donovan", "testingKent@gmail.com",
                        "California", "San Jose", "123 Main", "10001", "1234567890"}
        };
    }

    @Test(dataProvider = "LinkText1", priority = 1)
    public void userRegistration(String gender, String firstName, String lastName, String email, String password) {
        driver.get(baseUrl + "register");
        getUserInfo().selectGender(gender);
        getUserInfo().enterFirstName(firstName);
        getUserInfo().enterLastName(lastName);
        getUserInfo().enterEmailId(email);
        getUserInfo().scrollToTheSection();
        getUserInfo().enterPassword(password);
        getUserInfo().enterConfirmPassword(password);
        getUserInfo().clickRegister();

        //Assertion
        Assert.assertEquals(getUserInfo().getResultMessage(), "Your registration completed",
                "Registration failed!");
        getUserInfo().clickContinue();
    }

    @Test(dataProvider = "LinkText2", priority = 2)
    public void loginAndWishlist(String email, String password, String productName) {
        driver.get(baseUrl + "login");
        getLoginInfo().enterEmailId(email);
        getLoginInfo().enterPassword(password);
        getLoginInfo().clickLogin();

        Assert.assertTrue(getLoginInfo().isLogoutBtnDisplayed(), "Logout Button is not displayed!");

        //Search Product And Add To Wishlist
        getLoginInfo().searchProductName(productName);
        getLoginInfo().clickSearch();
        getLoginInfo().scrollToTheSection();
        getLoginInfo().addToWishlist();
        getLoginInfo().openWishlist();

        Assert.assertEquals(getLoginInfo().getProductName(), "Asus Laptop",
                "Product Name listed in wishlist is incorrect");
        //Add To Cart
        getLoginInfo().selectAddToCartCheckBox();
        getLoginInfo().clickAddToCart();
        getLoginInfo().clickShoppingCart();

        Assert.assertEquals(getLoginInfo().getProductName(), "Asus Laptop",
                "Product Name listed in shopping cart is incorrect");
    }

    @Test (dataProvider = "LinkText3", priority = 3)
    public void checkout(String emailId, String password, String firstName, String lastName, String email,
                         String state, String city, String address, String zipCode, String phoneNumber)
            throws IOException {
        driver.get(baseUrl + "login");
        getCheckoutInfo().enterEmail(emailId);
        getCheckoutInfo().enterPassword(password);
        getCheckoutInfo().clickLogin();

        getCheckoutInfo().shoppingCart();
        getCheckoutInfo().clickTerms();
        getCheckoutInfo().clickCheckout();
        getCheckoutInfo().enterFirstName(firstName);
        getCheckoutInfo().enterLastName(lastName);
        getCheckoutInfo().enterEmailId(email);
        getCheckoutInfo().selectState(state);
        getCheckoutInfo().enterCity(city);
        getCheckoutInfo().enterAddress(address);
        getCheckoutInfo().scrollToTheSection();
        getCheckoutInfo().enterZipcode(zipCode);
        getCheckoutInfo().enterPhoneNumber(phoneNumber);
        getCheckoutInfo().clickContinue();
        getCheckoutInfo().clickShippingContinue();
        getCheckoutInfo().clickPaymentContinue();
        getCheckoutInfo().clickPaymentInfo();
        getCheckoutInfo().scrollToTheSection();
        getCheckoutInfo().selectConfirmOrder();

        //Assertion
        String orderId = getCheckoutInfo().getOrderId();
        Assert.assertTrue(orderId.matches("\\d+"), "Order ID is not valid digits! Got: " + orderId);
        System.out.println("Order placed successfully with ID: " + orderId);

        //Take OrderId Screenshot
        TakesScreenshot ts = (TakesScreenshot) driver;
        // Capture the screenshot as a file
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        // Define the destination file path
        File destFile = new File("./src/test/java/screenshots/orderId_screenshot.png");

        // Copy the screenshot to the destination
        FileUtils.copyFile(srcFile, destFile);
        System.out.println("Screenshot saved successfully at: " + destFile.getAbsolutePath());
    }
}

