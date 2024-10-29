package uniflee.backend.global.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@OpenAPIDefinition(
        info =  @Info(title = "uniflee",
                description = "uniflee API 명세서",
                version = "V1")
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi() {

        // "/v1/**" 경로에 매칭되는 API를 그룹화하여 문서화한다.
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("uniflee API")  // 그룹 이름을 설정한다.
                .pathsToMatch(paths)     // 그룹에 속하는 경로 패턴을 지정한다.
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI();

        // username 파라미터 설정
        Parameter usernameParam = new Parameter()
                .in("query")
                .name("username")
                .description("username")
                .required(true)
                .schema(new Schema<String>().type("string").example("user123"));

        // password 파라미터 설정
        Parameter passwordParam = new Parameter()
                .in("query")
                .name("password")
                .description("password")
                .required(true)
                .schema(new Schema<String>().type("string").example("password123"));

        // /login 경로 추가
        PathItem loginPath = new PathItem()
                .post(new Operation()
                        .summary("User Login")
                        .description("Authenticates a designer with username and password and returns designer info.")
                        .parameters(Arrays.asList(usernameParam, passwordParam))
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                        .description("Login successful")
                                        .content(new Content()
                                                .addMediaType("application/json", new MediaType()
                                                        .schema(new Schema<>()
                                                                .addProperty("name", new Schema<String>().type("string").example("홍길동"))
                                                                .addProperty("profileImageUrl", new Schema<String>().type("string").example("profileImage/1234-abcd-5678-efgh.jpg"))
                                                                .addProperty("backgroundImageUrl", new Schema<String>().type("string").example("backgroundImage/1234-abcd-5678-efgh.jpg"))))))
                                .addApiResponse("401", new ApiResponse()
                                        .description("Unauthorized")
                                        .content(new Content()
                                                .addMediaType("application/json", new MediaType()
                                                        .schema(new Schema<>()
                                                                .addProperty("timeStamp", new Schema<String>().type("string").example(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                                                                .addProperty("status", new Schema<Integer>().type("integer").example(401))
                                                                .addProperty("error", new Schema<String>().type("string").example("AUTH-001"))
                                                                .addProperty("message", new Schema<String>().type("string").example("userName 또는 password가 일치하지 않습니다."))
                                                                .addProperty("path", new Schema<String>().type("string").example("/login"))))))));
        // /oauth2/authorization/kookmin 경로 추가
        PathItem oauthLoginPath = new PathItem()
                .get(new Operation()
                        .summary("OAuth2 Login")
                        .description("Redirects to the OAuth2 login page for Kookmin authorization.")
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                        .description("OAuth2 Login successful")
                                        .content(new Content()
                                                .addMediaType("application/json", new MediaType()
                                                        .schema(new Schema<>()
                                                                .addProperty("id", new Schema<Long>().type("integer").format("int64").example(1))
                                                                .addProperty("name", new Schema<String>().type("string").example("김선미"))
                                                                .addProperty("grade", new Schema<String>().type("string").example("BRONZE"))
                                                                .addProperty("username", new Schema<String>().type("string").example("hariaus"))))))
                                .addApiResponse("403", new ApiResponse()
                                        .description("Forbidden"))));

        // Paths에 추가
        Paths paths = new Paths();
        paths.addPathItem("/login", loginPath);
        paths.addPathItem("/oauth2/authorization/kookmin", oauthLoginPath);
        openAPI.setPaths(paths);

        return openAPI;
    }
}

