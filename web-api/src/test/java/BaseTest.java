import cn.wang.custom.web.api.WebApiApplication;
import cn.wang.custom.web.api.utils.RedisUtil;
import cn.wang.custom.web.api.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(classes = WebApiApplication.class)
@ActiveProfiles("local")
public class BaseTest {

    @Autowired
    private IUserService service;
    @Autowired
    private RedisUtil util;

    @Test
    public void defaultTest() {
        util.put("xx", "3333");
        System.out.println(util.get("xx"));
        System.out.println("-----------");
        util.removeKey("xx");
        util.setInvalidTimeMinute("xx",1l);
        System.out.println(util.get("xx"));
    }

}
