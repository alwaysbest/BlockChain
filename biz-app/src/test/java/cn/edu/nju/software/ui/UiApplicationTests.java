package cn.edu.nju.software.ui;

import cn.edu.nju.software.ui.temp.dao.DealerItemDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UiApplicationTests {
    @Autowired
    DealerItemDao dealerItemDao;


    @Test
    public void contextLoads() {
        dealerItemDao.deleteByDealerIdAndItemId(3, "12345678901234567890123456789014");
    }

}
