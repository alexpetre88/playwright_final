package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CreateAccountPage {

    private final Page createAccountPage;
    private static final String FIRST_NAME = "input#firstName";
    private static final String LAST_NAME = "input#lastName";
    private static final String EMAIL = "input#email";
    private static final String PASSWORD = "input#fld-p1";
    private static final String CONFIRM_PASSWORD = "input#reenterPassword";
    private static final String VALIDATION_MESSAGE = "span.c-input-error-message";
    private static final String PHONE = "input#phone";
    private static final String RECOVERY_PHONE_CHECKBOX = "input#is-recovery-phone";
    private static final String SUBMIT = "button.cia-form__controls__submit";

    public CreateAccountPage(Page page) {
        this.createAccountPage = page;
    }

    public void createAccount() {
        createAccountPage.locator(FIRST_NAME).fill("FirstName");
        createAccountPage.locator(LAST_NAME).fill("LastName");
        createAccountPage.locator(EMAIL).fill("alex@example.com");
        createAccountPage.locator(PASSWORD).fill("1qazXSW@");
        createAccountPage.locator(CONFIRM_PASSWORD).fill("1qazXSW@");

        assertThat(createAccountPage.locator(VALIDATION_MESSAGE)).containsText(
                "Your passwords match!",new LocatorAssertions.ContainsTextOptions().setTimeout(30000));

        createAccountPage.locator(PHONE).fill("7232432432");
        createAccountPage.locator(RECOVERY_PHONE_CHECKBOX).check(new Locator.CheckOptions().setTimeout(70000));

        assertThat(createAccountPage.locator(SUBMIT)).isEnabled(new LocatorAssertions.IsEnabledOptions().setTimeout(10000));
    }

}
