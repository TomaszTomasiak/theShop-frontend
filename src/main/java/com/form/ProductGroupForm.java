package com.form;

import com.domain.ProductGroup;
import com.service.ProductGroupService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import com.view.adminViews.ProductsGroupAdminView;

public class ProductGroupForm extends FormLayout {

    private ProductsGroupAdminView productsGroupAdminView;
    private ProductGroupService productGroupService = ProductGroupService.getInstance();
    private TextField name = new TextField("Group name");
    private Button save = new Button("Save new group");
    private Button update = new Button("Update");
    private Binder<ProductGroup> binder = new Binder<>(ProductGroup.class);

    public ProductGroupForm(ProductsGroupAdminView productsGroupAdminView) {
        this.productsGroupAdminView = productsGroupAdminView;

        HorizontalLayout buttons = new HorizontalLayout(update, save);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(name, buttons);
        binder.bindInstanceFields(this);
        save.addClickListener(event -> {
            save();
            getUI().ifPresent(ui -> ui.navigate("useer_view"));
        });
        save.addClickListener(event -> save());
        update.addClickListener(event -> updateName());
    }



    private ProductGroup createGroup() {
        ProductGroup newGroup = new ProductGroup();
        newGroup.setName(name.getValue());
        return newGroup;
    }

    private void updateName() {
        ProductGroup productGroup = binder.getBean();
        productGroupService.updateGroup(productGroup);
        setGroup(null);
        productsGroupAdminView.refresh();
    }

    private void save() {
        productGroupService.saveGroup(createGroup());
        setGroup(null);
        productsGroupAdminView.refresh();
    }

    public void setGroup(ProductGroup productGroup) {
        binder.setBean(productGroup);

        if (productGroup == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }

}
