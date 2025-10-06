package web_nopcommerce.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    @FindBy(id = "Email")
    private WebElement txtEmail;

    @FindBy(id = "Password")
    private WebElement txtPassword;

    @FindBy(xpath = "//button[contains(@class, 'login-button')]")
    private WebElement btnLogin;

    @FindBy(linkText = "Log out")
    private WebElement getLogoutLink;

    @FindBy(id = "small-searchterms")
    private WebElement searchProduct;

    @FindBy(css = "button.search-box-button")
    private WebElement btnSearch;

    @FindBy(css = "button.add-to-wishlist-button")
    private WebElement addWishlist;

    @FindBy(linkText = "Wishlist")
    private WebElement getWishlistLink;

    @FindBy(className = "product-name")
    private WebElement getProductName;


    @FindBy(xpath = "//input[@name='addtocart']")
    private WebElement chkbxAddToCart;

    @FindBy(xpath = "//button[@name='addtocartbutton']")
    private WebElement addToCart;

    @FindBy(linkText = "Shopping cart")
    private WebElement getShoppingCartLink;

    public LoginPage(WebDriver driver) {           // Call BasePage constructor with the WebDriver
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void enterEmailId(final String email) { enterTheText(txtEmail, email); }

    public void enterPassword(final String password) { enterTheText(txtPassword, password); }

    public void clickLogin() { clickElement(btnLogin); }

    public void searchProductName (final String productName) { enterTheText(searchProduct, productName); }

    public void clickSearch () { clickElement(btnSearch); }

    public void addToWishlist() { clickElement(addWishlist); }

    public void openWishlist() { clickElement(getWishlistLink); }

    public void selectAddToCartCheckBox() { clickElement(chkbxAddToCart); }

    public void clickAddToCart() { clickElement(addToCart); }

    public void clickShoppingCart() { clickElement(getShoppingCartLink); }

    //Assertions
    public boolean isLogoutBtnDisplayed() { return getLogoutLink.isDisplayed(); }

    public String getProductName() { return getTextValue(getProductName); }
}
