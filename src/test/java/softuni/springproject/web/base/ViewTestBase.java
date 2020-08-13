package softuni.springproject.web.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import softuni.springproject.base.TestBase;

@AutoConfigureMockMvc
public class ViewTestBase  extends TestBase {
    @Autowired
    protected MockMvc mockMvc;
}
