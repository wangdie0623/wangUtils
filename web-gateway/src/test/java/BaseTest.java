import cn.wang.custom.WebGatewayApplication;
import cn.wang.custom.feign.UserModuleFeign;
import cn.wang.custom.query.UserQuery;
import cn.wang.custom.vo.AccountVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(classes = WebGatewayApplication.class)
@ActiveProfiles("dev")
public class BaseTest {

    @Autowired
    private UserModuleFeign service;


    @Test
    public void defaultTest() {
        UserQuery query = new UserQuery();
        query.setName("admin");
        AccountVo info = service.getUserFullInfo(query);
        System.out.println(info);
    }

}
