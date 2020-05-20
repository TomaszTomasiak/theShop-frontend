package com.view.userViews;

import com.domain.User;
import com.form.CreateUserForm;
import com.service.UserService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("new_user")
public class CreateUserView extends VerticalLayout {

    @Autowired
    UserService userService;

    @Autowired
    Session session;

    //private User user = session.getCurrentUser();

    private CreateUserForm form = new CreateUserForm(this);
    private H1 welcome = new H1("Welcome to theSHOP");
    private H3 space = new H3();
    private Text instruction = new Text("Please fill all bellow fields");
    private Binder<User> binder = new Binder<>(User.class);


    public CreateUserView() {

        setAlignItems(Alignment.CENTER);
        add(space);
        add(welcome);
        add(instruction);

        add(form);
        form.setSizeFull();
        setSizeFull();
        //refresh();
    }

//    public void refresh() {
//        //user = session.getCurrentUser();
//        setAlignItems(Alignment.CENTER);
//    }

}
