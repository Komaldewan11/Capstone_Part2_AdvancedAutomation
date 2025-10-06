package web_nopcommerce.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public abstract class BasePage {

    protected final WebDriver driver;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.js =  (JavascriptExecutor) driver;
    }

    public void scrollToTheSection() {
        js.executeScript("window.scrollBy(0, 300);");
    }

    public void enterTheText(WebElement ele, String textValue) {
        ele.sendKeys(textValue);
    }

    public void enterTheTextAfterClear(WebElement ele, String textValue) {
        ele.clear();
        ele.sendKeys(textValue);
    }

    public void clickElement(WebElement ele) {
        ele.click();
    }

    public String getTextValue(WebElement element) {
        return element.getText();
    }

    public void selectDropDownOptionByVisibleText(WebElement ele, String ddValue) {
        Select dd = new Select(ele);
        dd.selectByVisibleText(ddValue);
    }
}
