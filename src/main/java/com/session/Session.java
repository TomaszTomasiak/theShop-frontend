package com.session;

import com.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Session {
    private User currentUser;
}
