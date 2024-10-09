package uniflee.backend.user.Dto;

import java.util.Map;

public interface OAuth2UserInfo {

    String getUsername();
    String getName();

    public class KookMinUserInfo implements OAuth2UserInfo {
        private final Map<String, Object> attributes;

        public KookMinUserInfo(Map<String, Object> attributes) {
            this.attributes = attributes;
        }

        @Override
        public String getUsername() {
            return (String) attributes.get("username");
        }

        @Override
        public String getName() {
            return (String) attributes.get("displayName");
        }
    }

}
