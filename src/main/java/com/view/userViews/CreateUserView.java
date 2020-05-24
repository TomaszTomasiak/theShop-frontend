package com.view.userViews;

import com.form.CreateUserForm;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("new_user")
public class CreateUserView extends VerticalLayout {

    private CreateUserForm form = new CreateUserForm(this);
    private H1 welcome = new H1("Welcome to theSHOP");
    private H3 space = new H3();
    private Text instruction = new Text("Please fill all bellow fields");

    public CreateUserView() {

        setAlignItems(Alignment.CENTER);
        add(space);
        add(welcome);
        add(instruction);

        add(form);
        form.setSizeFull();
        setSizeFull();
        refresh();
    }

    public void refresh() {
        setAlignItems(Alignment.CENTER);
    }

    public void info(Text text) {
        add(text);
    }
}
