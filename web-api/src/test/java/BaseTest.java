import cn.wang.custom.web.api.WebApiApplication;
import cn.wang.custom.web.api.dao.IWUserDao;
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
    private IWUserDao dao;
    @Autowired
    private RedisUtil util;

    @Test
    public void defaultTest() {
        String phone = dao.selectMaxEmptyPhone();
        System.out.println(phone);
    }

}
