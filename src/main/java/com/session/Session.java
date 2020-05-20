package com.session;

import com.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Session {
    private static Session session;
    private User currentUser;

    private Session() {
        currentUser = new User();
    }

    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }
}
