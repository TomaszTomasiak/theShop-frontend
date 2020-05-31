package com.view.userViews;

import com.domain.*;
import com.service.CartService;
import com.service.CurrencyService;
import com.service.ItemService;
import com.service.ProductService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("product_view")
public class ProductView extends VerticalLayout {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    private Session session = Session.getInstance();

    private Button back = new Button("Back to products site");
    private Button logout = new Button("Log out");
    private Text logged = new Text("Logged: " + session.getCurrentUser().getFirstName() + " " + session.getCurrentUser().getLastName());
    private Image image = new Image("http://www.plslights.com/index.php/home/vendor_profile/get_slider/", "src/main/resources/medium-default-product.jpg");
    private H2 name = new H2(session.getProduct().getName());
    private Text desc = new Text(session.getProduct().getDescription());
    private H1 price = new H1("Piece price: ");
    private Text pricePLN = new Text("PLN " + session.getProduct().getPrice());
    private Text priceEUR = new Text("EUR " + currencyService.valueEUR(session.getProduct().getPrice()));
    private Text priceGBP = new Text("GBP " + currencyService.valueGBP(session.getProduct().getPrice()));
    private Text priceUSD = new Text("USD " + currencyService.valueUSD(session.getProduct().getPrice()));
    private Text group = new Text(session.getProduct().getName());
    private Text available = new Text("");
    private NumberField qty = new NumberField();
    private Button buyNow = new Button("Buy now");
    private Button addToCart = new Button("Add to cart");

    private Binder<Product> bidnderProduct = new Binder<>(Product.class);
    private Binder<Item> bidnderItem = new Binder<>(Item.class);

    public ProductView() {

        HorizontalLayout header = new HorizontalLayout(back, logout, logged);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setFlexGrow(1, back);
        header.setPadding(true);
        header.setSpacing(true);

        back.addClickListener(event -> {
            session.setProduct(null);
            getUI().ifPresent(ui -> ui.navigate("user_view"));
        });

        logout.addClickListener(event -> {
            session.cleanAll();
            getUI().ifPresent(ui -> ui.navigate(""));
        });

        VerticalLayout productInfo =
                new VerticalLayout(name, desc, group, available, price, pricePLN, priceEUR, priceGBP, priceUSD);

        HorizontalLayout content = new HorizontalLayout(image, productInfo);
        add(content);
        productInfo.setAlignItems(Alignment.CENTER);
        content.setAlignItems(Alignment.CENTER);

        HorizontalLayout buyLeyout = new HorizontalLayout(qty, buyNow);
        add(buyLeyout);

        addToCart.addClickListener(event -> {
            if (qty.getValue() > 0) {
                newItem(qty.getValue());
                getUI().ifPresent(ui -> ui.navigate("cart_view"));
            }
        });

        buyNow.addClickListener(event -> {
            if (qty.getValue() > 0) {
                newItem(qty.getValue());
                saveCart();
                getUI().ifPresent(ui -> ui.navigate("order_view"));
            }
        });
    }

    public Item newItem(double qty) {

        Item item = new Item();
        item.setProductId(session.getProduct().getId());
        int intQty = (int) qty;
        item.setQuantity(intQty);
        //itemService.saveItem(item);
        session.setItem(item);
        addItemToCart(session.getItem());
        addToList(item);

        return item;
    }

    public void addItemToCart(Item item) {

        if (session.getCart().getItems().size() > 0) {
            session.getCart().getItems().add(item);

        } else {
            Cart cart = new Cart();
            cart.setUserId(session.getCurrentUser().getId());
            cart.getItems().add(item);
            session.setCart(cart);
        }
    }

    public void saveCart() {
        cartService.saveCart(session.getCart());
    }

    public ProductOnCart addToList(Item item) {
        ProductOnCart productOnCart = new ProductOnCart();
        productOnCart.setProduct(productService.getProduct(item.getProductId()));
        productOnCart.setQty(item.getQuantity());
        session.getListOfProductsOnCart().add(productOnCart);
    return productOnCart;
    }
}
