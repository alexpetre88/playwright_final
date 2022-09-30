package pages;

import com.microsoft.playwright.Page;

public class SignInPage {
    private final Page signInPage;
    private static final String EMAIL = "input#fld-e";
    private static final String PASSWORD = "input#fld-p1";
    private static final String SIGN_IN_BUTTON = "//button[@data-track='Sign In']";

    public SignInPage(Page page) {
        this.signInPage = page;
    }


    public void signIn(String email, String password) {
        signInPage.locator(EMAIL).fill(email);
        signInPage.locator(PASSWORD).fill(password);
        signInPage.locator(SIGN_IN_BUTTON).click();
    }
}
