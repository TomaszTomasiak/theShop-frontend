package com.form;

import com.domain.Cart;
import com.domain.Item;
import com.domain.ProductOnCart;
import com.service.ItemService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.view.userViews.CartView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsOnCartForm extends FormLayout {

    @Autowired
    private ItemService itemService;

    private Session session = Session.getInstance();
    private Text productInfo = new Text("");
    private Text priceInfo = new Text("");
    private TextField price = new TextField();
    private TextField product = new TextField();
    private NumberField qty = new NumberField("pieces");
    private TextField value = new TextField();
    private Button update = new Button("Update");
    private Button remove = new Button("Remove");
    private Binder<ProductOnCart> binder = new Binder<>(ProductOnCart.class);


    private CartView cartView;


    public ProductsOnCartForm(CartView cartView) {
        this.cartView = cartView;

        productInfo.setText(product.getValue());
        priceInfo.setText(price.getValue());
        update.addClickListener(event -> updateOnCart());
        remove.addClickListener(event -> removeFromCart());

        VerticalLayout info = new VerticalLayout(productInfo, priceInfo, qty);
        HorizontalLayout buttons = new HorizontalLayout(update, remove);
        add(info, buttons);
        binder.bindInstanceFields(this);

    }

    public void updateOnCart() {
        ProductOnCart productOnCart = binder.getBean();
        updateItemOnCart(productOnCart);
        updateProductsOnCardtList(productOnCart);
        setProductOnCart(null);
        cartView.refresh();
    }

    public void removeFromCart() {
        ProductOnCart productOnCart = binder.getBean();
        removeItemFromCart(productOnCart);
        session.getListOfProductsOnCart().remove(productOnCart);
        setProductOnCart(null);
        cartView.refresh();
    }


    public void updateItemOnCart(ProductOnCart productOnCart) {
        Cart currentCart = session.getCart();
        List<Item> itemsOnCart = currentCart.getItems().stream()
                .filter(item -> item.getProductId().equals(productOnCart.getProduct().getId()))
                .collect(Collectors.toList());

        currentCart.getItems().remove(itemsOnCart.get(0));

        if (itemService.findItemWithProductIdAndQty(productOnCart.getProduct(), productOnCart.getQty()).getId() != null) {
            currentCart.getItems().add(itemService.findItemWithProductIdAndQty(productOnCart.getProduct(), productOnCart.getQty()));
        } else {
            Item newItem = new Item();
            newItem.setProductId(productOnCart.getProduct().getId());
            newItem.setQuantity(productOnCart.getQty());
            currentCart.getItems().add(newItem);
        }
        session.getListOfProductsOnCart().add(productOnCart);
    }

    public void removeItemFromCart(ProductOnCart productOnCart) {

        Cart currentCart = session.getCart();
        List<Item> itemsOnCart = currentCart.getItems().stream()
                .filter(item -> item.getProductId().equals(productOnCart.getProduct().getId()))
                .collect(Collectors.toList());
        currentCart.getItems().remove(itemsOnCart.get(0));
    }

    public void setProductOnCart(ProductOnCart productOnCart) {
        binder.setBean(productOnCart);
        if (productOnCart == null) {
            setVisible(false);
        } else {
            setVisible(true);
            qty.focus();
        }
    }

    public void updateProductsOnCardtList(ProductOnCart productOnCart) {
        List<ProductOnCart> list = session.getListOfProductsOnCart().stream()
                .filter(p -> p.getProduct().equals(productOnCart.getProduct()))
                .collect(Collectors.toList());

        session.getListOfProductsOnCart().remove(list.get(0));
        session.getListOfProductsOnCart().add(productOnCart);
    }
}
