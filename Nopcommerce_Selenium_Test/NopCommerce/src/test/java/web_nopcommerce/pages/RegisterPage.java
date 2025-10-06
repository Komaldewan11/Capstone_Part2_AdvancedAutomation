package web_nopcommerce.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage extends BasePage {

    @FindBy(id = "gender-male")
    private WebElement rdoMaleGender;

    @FindBy(id = "gender-female")
    private WebElement rdoFemaleGender;

    @FindBy(id = "FirstName")
    private WebElement txtFirstName;

    @FindBy(id = "LastName")
    private WebElement txtLastName;

    @FindBy(id = "Email")
    private WebElement txtEmail;

    @FindBy(id = "Password")
    private WebElement txtPassword;

    @FindBy(id = "ConfirmPassword")
    private WebElement txtConfirmPassword;

    @FindBy(id = "register-button")
    private WebElement btnRegister;

    @FindBy(className = "result")
    private WebElement getResultMsg;

    @FindBy(className = "register-continue-button")
    private WebElement btnContinue;

    public RegisterPage(WebDriver driver) {           // Call BasePage constructor with the WebDriver
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void selectGender(final String gender) {
        switch(gender.toLowerCase()){
            case "male" -> clickElement(rdoMaleGender);
            case "female" -> clickElement(rdoFemaleGender);
        }
    }

    public void enterFirstName(final String firstName) {
        enterTheText(txtFirstName, firstName);
    }

    public void enterLastName(final String lastName) { enterTheText(txtLastName, lastName); }

    public void enterEmailId(final String email) { enterTheText(txtEmail, email); }

    public void enterPassword(final String password) { enterTheText(txtPassword, password); }

    public void enterConfirmPassword(final String password) { enterTheText(txtConfirmPassword, password);
    }

    public void clickRegister() { clickElement(btnRegister); }

    public void clickContinue() { clickElement(btnContinue);}

    //Assertion
    public String getResultMessage() { return getTextValue(getResultMsg); }

}
