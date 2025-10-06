package web_nopcommerce.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckOutPage extends BasePage {

    @FindBy(id = "Email")
    private WebElement txtEmail;

    @FindBy(id = "Password")
    private WebElement txtPassword;

    @FindBy(xpath = "//button[contains(@class, 'login-button')]")
    private WebElement btnLogin;

    @FindBy(linkText = "Shopping cart")
    private WebElement getShoppingCartLink;

    @FindBy(id = "termsofservice")
    private WebElement checkBoxTerms;

    @FindBy(id = "checkout")
    private WebElement checkout;

    @FindBy(id = "BillingNewAddress_FirstName")
    private WebElement txtFirstName;

    @FindBy(id = "BillingNewAddress_LastName")
    private WebElement txtLastName;

    @FindBy(id = "BillingNewAddress_Email")
    private WebElement txtEmailId;

    @FindBy(id = "BillingNewAddress_StateProvinceId")
    private WebElement ddState;

    @FindBy(id = "BillingNewAddress_City")
    private WebElement txtCity;

    @FindBy(id = "BillingNewAddress_Address1")
    private WebElement txtAddress;

    @FindBy(id = "BillingNewAddress_ZipPostalCode")
    private WebElement txtZipcode;

    @FindBy(id = "BillingNewAddress_PhoneNumber")
    private WebElement txtPhoneNumber;

    @FindBy(css = "button.new-address-next-step-button")
    private WebElement btnContinue;

    @FindBy(css = "button.shipping-method-next-step-button")
    private WebElement btnShippingContinue;

    @FindBy(css = "button.payment-method-next-step-button")
    private WebElement btnPaymentContinue;

    @FindBy(css = "button.payment-info-next-step-button")
    private WebElement btnPaymentInfo;

    @FindBy(css = "button.confirm-order-next-step-button")
    private WebElement confirmOrder;

    @FindBy(css = "div.order-number")
    private WebElement confirmationText;

    public CheckOutPage(WebDriver driver) {           // Call BasePage constructor with the WebDriver
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void enterEmail(final String emailId) { enterTheText(txtEmail, emailId); }

    public void enterPassword(final String password) { enterTheText(txtPassword, password); }

    public void clickLogin() { clickElement(btnLogin); }

    public void shoppingCart() { clickElement(getShoppingCartLink); }

    public void clickTerms() { clickElement(checkBoxTerms); }

    public void clickCheckout() { clickElement(checkout); }

    public void enterFirstName(final String firstName) { enterTheTextAfterClear(txtFirstName, firstName); }

    public void enterLastName(final String lastName) { enterTheTextAfterClear(txtLastName, lastName); }

    public void enterEmailId(final String email) { enterTheTextAfterClear(txtEmailId, email); }

    public void selectState(final String state) { selectDropDownOptionByVisibleText(ddState, state); }

    public void enterCity(final String city) { enterTheTextAfterClear(txtCity, city); }

    public void enterAddress(final String address) { enterTheTextAfterClear(txtAddress, address); }

    public void enterZipcode(final String zipCode) { enterTheTextAfterClear(txtZipcode, zipCode); }

    public void enterPhoneNumber(final String phoneNumber) { enterTheTextAfterClear(txtPhoneNumber, phoneNumber); }

    public void clickContinue() { clickElement(btnContinue); }

    public void clickShippingContinue() { clickElement(btnShippingContinue); }

    public void clickPaymentContinue() { clickElement(btnPaymentContinue); }

    public void clickPaymentInfo() { clickElement(btnPaymentInfo); }

    public void selectConfirmOrder() { clickElement(confirmOrder); }

    //Assertion
    public String getOrderId() {
        String confirmation = getTextValue(confirmationText);
        return confirmation.replace("ORDER NUMBER: ", "").trim();
    }
}
