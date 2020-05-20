package com.view.userViews;

import com.domain.User;
import com.service.UserService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("user_view")
public class UserView extends VerticalLayout {

    @Autowired
    Session session;

    @Autowired
    UserService userService;

    private User user = session.getCurrentUser();
    private Label log = new Label("");

    public UserView() {

        HorizontalLayout header = new HorizontalLayout(log);
        header.setAlignItems(Alignment.CENTER);
        header.setFlexGrow(1, log);
        header.setPadding(true);
        header.setSpacing(true);
    }
}
