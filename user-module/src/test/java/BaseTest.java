import cn.wang.custom.user.module.UserModuleApplication;
import cn.wang.custom.user.module.dao.IWRoleDao;
import cn.wang.custom.user.module.service.IUserService;
import cn.wang.custom.vo.AccountVo;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(classes = UserModuleApplication.class)
@ActiveProfiles("test")
public class BaseTest {

    @Autowired
    private IUserService service;
    @Autowired
    private IWRoleDao dao;


    @Test
    public void defaultTest() {
        AccountVo admin = service.queryFullInfo("admin");
        System.out.println(JSON.toJSONString(admin));
    }

}
