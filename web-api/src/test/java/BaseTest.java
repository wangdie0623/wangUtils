import cn.wang.custom.web.api.WebApiApplication;
import cn.wang.custom.web.api.entity.User;
import cn.wang.custom.web.api.service.IUserService;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(classes = WebApiApplication.class)
@ActiveProfiles("local")
public class BaseTest {
   @Autowired
   private IUserService userService;
   @Test
   public void saveTest(){
       User user = new User();
       user.setId(1l);
       user.setName("test");
       user.setPwd("pwd");
       userService.save(user);
   }

    @Test
    public void queryTest(){
        User user = userService.queryById(1l);
        System.out.println(JSON.toJSONString(user));
    }
}
