import annotations.PlaywrightPage;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.AccountNavigationPage;
import pages.CreateAccountPage;
import pages.HomePage;
import pages.SignInPage;
import services.EnvironmentReaderService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.Arrays;

@ExtendWith(TestWatcherExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlaywrightRunner {
    protected Page page;
    protected BrowserContext browserContext;
    protected Browser browser;
    protected Playwright playwright;

    @PlaywrightPage
    protected CreateAccountPage createAccountPage;
    @PlaywrightPage
    protected AccountNavigationPage accountNavigationPage;

    @PlaywrightPage
    protected HomePage homePage;

    @PlaywrightPage
    protected SignInPage signInPage;

    @BeforeAll
    public void init(){
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setUp() {
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        browserContext = browser.newContext(new Browser.NewContextOptions()
                .setPermissions(Arrays.asList("geolocation"))
                .setRecordVideoDir(Paths.get("videos/"))
        );
        browserContext.setDefaultTimeout(100000);
        browserContext.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(false));

        page = browserContext.newPage();
        initPage(this, page);
    }

    private void initPage(Object object, Page page) {
        Class<?> clazz = object.getClass().getSuperclass();
        for(Field field : clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(PlaywrightPage.class)) {
                Class<?>[] type = {Page.class};
                try {
                    field.set(this, field.getType().getConstructor(type).newInstance(page));
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                    System.out.println("Did not manage to call constructor for playwright page with name " + field.getName());
                }
            }
        }
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        browserContext.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("traces/" + testInfo.getDisplayName().replace("()", "") + ".zip")));
        browserContext.close();
        browser.close();
    }

    protected String getProperty(String key) {
        return EnvironmentReaderService.getProperty(key);
    }
}
