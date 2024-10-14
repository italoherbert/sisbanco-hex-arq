package italo.sisbanco.infra.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import italo.sisbanco.core.domain.User;
import italo.sisbanco.core.ports.in.UserService;
import italo.sisbanco.infra.entrypoint.dto.user.SavePasswordUserRequest;
import italo.sisbanco.mocks.UserMocks;

@SpringBootTest
public class UserControllerTests {
    
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup( webApplicationContext )
                .build();
    }    

    @Test
    @DisplayName("Deve registrar o usuário com sucesso.")
    void listUsersWithSuccessTest() throws Exception {
        List<User> users = UserMocks.createUserList( 5 );

        when( userService.list() ).thenReturn( users );
        
        mockMvc.perform( MockMvcRequestBuilders
                .get( "/api/sisbanco/v1/users" ) )
            .andExpect( status().isOk() );

        verify( userService, times( 1 ) ).list();           
    }

    @Test
    @DisplayName("Deve alterar um usuário com sucesso.")
    void alterPasswordUserWithSuccessTest() throws Exception {
        SavePasswordUserRequest request = UserMocks.createSavePasswordRequest();

        UUID userId = UUID.randomUUID();

        String content = new ObjectMapper().writeValueAsString( request );

        mockMvc.perform( 
                patch( "/api/sisbanco/v1/users/"+userId+"/alter-password" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content ) )
            .andExpect( status().isOk() );
        
        verify( userService, times( 1 ) ).alterPassword( userId, request.getPassword() );
    }

}
