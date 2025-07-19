package hello.hello_spring.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

/*

Junit4 버전 어노테이션이어서 5(최신) 코드로 수정함.

[before]
@RunWith(SpringRunner)
public class AccountControllerTest {}

[after]
@ExtendWith(SpringExtension.class)
public class AccountControllerTest {}

 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    /*
        CASE1: .with(MOCK_USER_INFO) 방식으로 테스트용 MOCK 유저 정보 제공
        @Test
        public void index_annonymous() throws Exception {
        mockMvc.perform(get("/").with(anonymous()))

        CASE2: 또는 위 코드를 @WithMockUser 어노테이션으로 변경 가능
        @Test
        @WithMockUser(username="eunhee", roles="USER")
        public void index_annonymous() throws Exception {
        mockMvc.perform(get("/")))
    }
     */

    @Test
    @WithUser
    public void index_annonymous() throws Exception {

        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUser
    public void index_user() throws Exception {

//      .with : 이런 유저가 있다고 가정하는 Mocking
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUser
    public void admin_user() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username="eunhee", roles="ADMIN")
    public void admin_admin() throws Exception {
        mockMvc.perform(get("/admin").with(user("eunhee").roles("ADMIN")))
                .andDo(print())
                .andExpect(status().isOk());
    }
}