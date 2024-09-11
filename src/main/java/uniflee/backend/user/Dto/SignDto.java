package uniflee.backend.user.Dto;

import lombok.Getter;
import lombok.Setter;
import uniflee.backend.global.domain.Address;

@Getter
@Setter
public class SignDto {
    private String name;
    private String username;
    private String password;
    private Address address;
}
