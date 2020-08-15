package softuni.springproject.web.view.controllers;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.models.Gender;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.web.base.ViewTestBase;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ChefsControllerTest extends ViewTestBase {
    @MockBean
    ChefsRepository chefsRepository;

    @Test
    @WithMockUser(username = "user1", password = "pwd")
    void getChefDetailsWhenChefsNameExists() throws Exception {
        String chefName = "Ivan Ivanov Chef";

        Chef chef = new Chef();
        chef.setName(chefName);
        chef.setGender(Gender.MALE);
        chef.setRecipes(new ArrayList<>());

        Mockito.when(chefsRepository.getByNameIgnoreCase(chefName))
                .thenReturn(Optional.of(chef));

        mockMvc.perform(get("/chefs/details/" + chefName))
                .andExpect(status().isOk())
                .andExpect(view().name(ChefsController.CHEFS_CHEF_DETAILS_VIEW_NAME));
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd")
    void getDetails_whenNoChefWithName_shouldReturnErrorViewWith404() throws Exception {
        String chefName = "pesho";
        mockMvc.perform(get("/chefs/details/" + chefName))
                .andExpect(status().isNotFound())
                .andExpect(view().name("custom-error"));
    }

    //

    @Test
    @WithMockUser(username = "user1", password = "pwd")
    void getChefDetailsWhenChefsNameDoesNotExists() throws Exception {
        String chefName = "Gesho";
        mockMvc.perform(get("/chefs/details/" + chefName))
                .andExpect(status().isNotFound())
                .andExpect(view().name("custom-error"));
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", authorities = "USER")
    void getChefsDetailsForm() throws Exception {
        mockMvc.perform(get("/chefs/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("chefs/create-chef.html"));
    }

    @Test
    void createChefWhenUserNotAuthenticated() throws Exception {
        mockMvc.perform(post("/chefs/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}