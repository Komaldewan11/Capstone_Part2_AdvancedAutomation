// Import required modules
const wd = require('wd'); // WebDriver client for Node.js
const assert = require('assert'); // Node.js built-in assertion module

const PORT = 4723; // Default Appium server port

// Desired capabilities for the Appium session
const config = {
  platformName: 'Android',
  deviceName: 'emulator-5554', 
  automationName: 'UiAutomator2',
  appPackage: "com.android.chrome",
  appActivity: "com.google.android.apps.chrome.Main",
  noReset: true,              // Keep Chrome data/cache
  autoGrantPermissions: true  // Skip permission popups
};

// Create a remote WebDriver session
const driver = wd.promiseChainRemote('127.0.0.1', PORT);

// Main function to run the test
async function main() {
  try {
    // Initialize the session with desired capabilities
    await driver.init(config);
    await driver.setImplicitWaitTimeout(5000);

  // ---- Step 1: Handle popup in NATIVE_APP ----
    try {
      console.log("Looking for Chrome popup...");
      const noThanksBtn = await driver.waitForElementById("com.android.chrome:id/negative_button", 10000);
      await noThanksBtn.click();
      console.log("Dismissed Chrome popup");
    } catch (e) {
      console.log("No popup appeared, continuing...");
    }

    // ---- Step 2: Switch to WebView (CHROME) ----
    const contexts = await driver.contexts();           //list of available contexts[eg: Native_APP, 'WEBVIEW_chrome']
    console.log("Available contexts:", contexts);

    const chromeContext = contexts.find(c => c.toLowerCase().includes("chrom"));
    if (!chromeContext) throw new Error("No Chrome web context found!");
    await driver.context(chromeContext);              //switch the session into the chosen web context

    //Test 1: Register the user in nopCommerce website
    await driver.get("https://demo.nopcommerce.com/login/");
    await driver.sleep(5000);

    const username = await driver.waitForElementByCssSelector("#Email", 3000);
    await username.type("zby0246axe@gmail.com");
    const password = await driver.waitForElementByCssSelector("#Password", 3000);
    await password.type("Testing@4029");
    await password.sendKeys(wd.SPECIAL_KEYS.Enter);      //This Enter clicking the submit button
    await driver.sleep(8000);                                   // wait for success page
    console.log("Logged in successfully");

    // const loginBtn = await driver.waitForElementByCssSelector("button.login-button", 5000);
    // await loginBtn.click();

    await driver.execute("window.scrollBy(0, 600);");
    await driver.sleep(3000);

    //Test 2: Navigate and add product to the cart
    const electronicsLink = await driver.waitForElementByCssSelector(".home-page-category-grid .item-box .title a", 5000);
    await electronicsLink.click();
    await driver.execute("window.scrollBy(0, 600);");
    await driver.sleep(3000);

    const cameraPhoto = await driver.elementByXPath(
    "//div[@class='sub-category-item']//a[contains(@href,'/camera-photo')]", 5000);
    await cameraPhoto.click();
    await driver.sleep(3000);

    await driver.execute("window.scrollBy(0, 600);");
    await driver.sleep(3000); 
    await driver.execute("window.scrollBy(0, 600);");  

    // ---- Open first product ----
    const firstProduct = await driver.waitForElementByCssSelector(".buttons .product-box-add-to-cart-button", 5000);
    await firstProduct.click();
    console.log("Opened product page");

    await driver.execute("window.scrollBy(0, 600);");
    await driver.sleep(3000); 
    await driver.execute("window.scrollBy(0, 600);"); 
    await driver.sleep(3000); 
    await driver.execute("window.scrollBy(0, 600);"); 

    // ---- Add to cart ----
    const addToCartBtn = await driver.waitForElementByCssSelector("button.add-to-cart-button", 5000);
    await addToCartBtn.click();

    const successBar = await driver.waitForElementByCssSelector(".bar-notification.success", 10000);
    const successText = await successBar.text();
    assert(successText.includes("The product has been added"));

    console.log("Product added to cart successfully");
    } catch (err) {
    // Log if any error occurs during the test
    console.error('Unable to navigate product and adding to the cart', err);
  } finally {
    // Quit the session
    await driver.quit();
  }
}

// Execute the test
main();