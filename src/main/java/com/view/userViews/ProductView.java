package com.view.userViews;

import com.domain.*;
import com.service.*;
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

    private final CurrencyService currencyService = CurrencyService.getInstance();
    private final CartService cartService = CartService.getInstance();
    private final ProductService productService = ProductService.getInstance();
    private final ItemService itemService = ItemService.getInstance();
    private final TheShopService theShopService = TheShopService.getInstance();
    private final Session session = Session.getInstance();
    private final Product theProduct = session.getProduct();

    private Button back = new Button("Back to products site");
    private Button logout = new Button("Log out");
    private Text logged = new Text("");
    private Image image = new Image("http://www.plslights.com/index.php/home/vendor_profile/get_slider/", "src/main/resources/medium-default-product.jpg");
    private H2 name = new H2("");
    private Text desc = new Text("");
    private H2 price = new H2("Piece price: ");
    private Text pricePLN = new Text("");
    private Text priceEUR = new Text("");
    private Text priceGBP = new Text("");
    private Text priceUSD = new Text("");
    private Text group = new Text("");
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
            session.setProduct(new Product());
            getUI().ifPresent(ui -> ui.navigate("user_view"));
        });

        logout.addClickListener(event -> {
            session.cleanAll();
            getUI().ifPresent(ui -> ui.navigate(""));
        });

        VerticalLayout productInfo =
                new VerticalLayout(name, desc, group, available, price, pricePLN, priceEUR, priceGBP, priceUSD);

        productInfo.setSizeFull();

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
        refresh();
    }

    public void refresh() {
        logged.setText(session.nameOfLoggedUser());
        name.setText(theProduct.getName());
        desc.setText(theProduct.getDescription());
        group.setText(groupName(theProduct));
        pricePLN.setText("PLN " + theProduct.getPrice());
        priceEUR.setText("EUR " + currencyService.valueEUR(theProduct.getPrice()));
        priceGBP.setText("GBP " + currencyService.valueGBP(theProduct.getPrice()));
        priceUSD.setText("USD " + currencyService.valueUSD(theProduct.getPrice()));
    }

    public Item newItem(double qty) {

        Item newItem = new Item();
        newItem.setProductId(session.getProduct().getId());
        int intQty = (int) qty;
        newItem.setQuantity(intQty);

        Item itemFromDataBase = theShopService.findItemWithProductIdAndQty(session.getProduct(), newItem.getQuantity());
        if(itemFromDataBase.getId() != null) {
            session.setItem(itemFromDataBase);
        } else {
            itemService.saveItem(newItem);
            Item savedItem = theShopService.findItemWithProductIdAndQty(session.getProduct(), newItem.getQuantity());
            session.setItem(savedItem);
        }
        addItemToCart(session.getItem());
        addToList(session.getItem());

        return newItem;
    }

    public void addItemToCart(Item item) {

        if (session.getCart().getUserId() == null) {
            Cart cart = new Cart();
            cart.setUserId(session.getCurrentUser().getId());
            session.setCart(cart);
            session.getCart().getItems().add(item);

        } else {
            session.getCart().getItems().add(item);
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

    public String groupName(Product product) {
        ProductGroup productGroup = ProductGroupService.getInstance().getGroup(product.getGroupId());
        return "Grupa produktów: " + productGroup.getName();
    }
}
