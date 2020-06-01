package com.form;

import com.domain.Product;
import com.service.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.view.adminViews.ProductsAdminView;

public class ProductForm extends FormLayout {

    private ProductsAdminView productsAdminView;
    private ProductService productService = ProductService.getInstance();
    private TextField name = new TextField("Product name");
    private NumberField price = new NumberField("Price in PLN");
    private TextField description = new TextField("Product description");
    private NumberField groupId = new NumberField("Group ID");
    private TextField available = new TextField("Availability");
    private Button save = new Button("Save new product");
    private Button update = new Button("Update");
    private Button delete = new Button("Delete");

    private Binder<Product> binder = new Binder<>(Product.class);

    public ProductForm(ProductsAdminView productsAdminView) {
        this.productsAdminView = productsAdminView;
        HorizontalLayout buttons = new HorizontalLayout(update, save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(name, price, description, groupId, available, buttons);
        binder.bindInstanceFields(this);

        save.addClickListener(event -> save());
        update.addClickListener(event -> updateName());
        delete.addClickListener(event -> updateName());
    }

    private Product createProduct() {
        Product product = new Product();
        product.setName(name.getValue());
        return product;
    }

    private void updateName() {
        Product product = binder.getBean();
        productService.updateProduct(product);
        setProduct(null);
        productsAdminView.refresh();
    }

    private void save() {
        productService.saveProduct(createProduct());
        setProduct(null);
        productsAdminView.refresh();
    }

    public void setProduct(Product product) {
        binder.setBean(product);

        if (product == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }
}
