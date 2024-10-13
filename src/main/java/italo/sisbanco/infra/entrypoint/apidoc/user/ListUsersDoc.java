package italo.sisbanco.infra.entrypoint.apidoc.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import italo.sisbanco.core.domain.User;
import italo.sisbanco.infra.entrypoint.apidoc.APIDocConstants;
import italo.sisbanco.infra.entrypoint.dto.ErrorResponse;

@Operation(
	summary = "Responsável pela listagem de usuários." )
@ApiResponses(value= {
	@ApiResponse( 		
		responseCode = "200",
		description = "Usuários listados com sucesso.",
		content=@Content(					
			mediaType = "application/json", 
			array = @ArraySchema(schema = @Schema(implementation = User.class))) ),				
	@ApiResponse(
		responseCode = "400",
		description = APIDocConstants.MSG_400,
		content=@Content(					
			mediaType = "application/json", 
			schema = @Schema(implementation = ErrorResponse.class)))
})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListUsersDoc {
    
}
