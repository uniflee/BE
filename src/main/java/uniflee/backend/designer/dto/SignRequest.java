package uniflee.backend.designer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignRequest {
    private String name;
    private String username;
    private String password;
}
