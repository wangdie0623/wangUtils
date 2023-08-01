import cn.wang.custom.user.module.UserModuleApplication;
import cn.wang.custom.user.module.dao.IWUserDao;
import cn.wang.custom.user.module.utils.RedisUtil;
import cn.wang.custom.user.module.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(classes = UserModuleApplication.class)
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
