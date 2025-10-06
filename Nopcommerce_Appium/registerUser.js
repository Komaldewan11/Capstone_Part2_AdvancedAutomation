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
    await driver.get("https://demo.nopcommerce.com/register/");
    await driver.sleep(5000);

    await driver.execute("window.scrollBy(0, 300);");

    // ---- Add User Details ----
    const genderMale = await driver.waitForElementByCssSelector("#gender-male", 3000);
    await genderMale.click();
    const firstName = await driver.waitForElementByCssSelector("#FirstName", 3000);
    await firstName.type("Appium0924");
    const lastName = await driver.waitForElementByCssSelector("#LastName", 3000);
    await lastName.type("Testingweb");
    const emailId = await driver.waitForElementByCssSelector("#Email", 3000);
    await emailId.type("zby0246axe@gmail.com");

    await driver.execute("window.scrollBy(0, 600);");

    const password = await driver.waitForElementByCssSelector("#Password", 3000);
    await password.type("Testing@4029");

    const confirmPassword = await driver.waitForElementByCssSelector("#ConfirmPassword", 3000);
    await confirmPassword.type("Testing@4029");
    await confirmPassword.sendKeys(wd.SPECIAL_KEYS.Enter);      //This Enter clicking the submit button
    await driver.sleep(8000);                                   // wait for success page

    // const registerBtn = await driver.waitForElementByCssSelector("#register-button", 5000);
    // await registerBtn.click();

    const successMsg = await driver.waitForElementByCssSelector(".result", 10000);
    const msgText = await successMsg.text();
    console.log("Registration result:", msgText);
    assert.strictEqual(msgText, "Your registration completed");

    const continueBtn = await driver.waitForElementByCssSelector(".register-continue-button", 10000);
    await continueBtn.click();
    console.log("Clicked Continue button");

    //Validate Result
    console.log('Registered user Successfully!');
    } catch (err) {
    // Log if any error occurs during the test
    console.error('Failed to register user', err);
  } finally {
    // Quit the session
    await driver.quit();
  }
}

// Execute the test
main();