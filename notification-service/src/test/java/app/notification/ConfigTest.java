package app.notification;

import org.junit.jupiter.api.Test;

import static core.framework.test.Assertions.assertConfDirectory;

/**
 * @author zoo
 */
public class ConfigTest extends IntegrationTest {
    @Test
    void conf() {
        assertConfDirectory().overridesDefaultResources();
    }
}
