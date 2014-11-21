import org.junit.Test;
import play.libs.ws.WS;
import play.libs.ws.WSAuthScheme;
import play.libs.ws.WSResponse;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class DemoControllerTest {

    @Test
    public void should_pass_authentication() {
        running(testServer(3333, fakeApplication()), HTMLUNIT, browser -> {
            WSResponse wsResponse = WS.url("http://localhost:3333")
                    .setAuth("username", "password", WSAuthScheme.BASIC)
                    .get()
                    .get(1000);

            assertEquals(200, wsResponse.getStatus());
            assertEquals("OK", wsResponse.getStatusText());
        });
    }

    @Test
    public void should_fail_authentication() {
        running(testServer(3333, fakeApplication()), HTMLUNIT, browser -> {
            WSResponse wsResponse = WS.url("http://localhost:3333")
                    .setAuth("username", "wrongpassword", WSAuthScheme.BASIC)
                    .get()
                    .get(1000);

            assertEquals(401, wsResponse.getStatus());
            assertEquals("Unauthorized", wsResponse.getStatusText());
        });
    }

}
