import cn.wang.custom.web.api.WebApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(classes = WebApiApplication.class)
@ActiveProfiles("local")
public class BaseTest {

    @Test
    public void defaultTest() {

    }

}
