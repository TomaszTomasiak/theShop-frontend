package com.view.adminViews;

import com.domain.Order;
import com.form.OrderForm;
import com.service.*;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.List;

@Route("admin_main")
public class MainAdminView extends VerticalLayout {

    private final Session session = Session.getInstance();
    private final OrderService orderService = OrderService.getInstance();
    private final TheShopService theShopService = TheShopService.getInstance();
    private List<Order> listOfOrders = new ArrayList<>();
    private NumberField valueFrom = new NumberField("Value from");
    private NumberField valueTo = new NumberField("Value from");
    private DatePicker orderedFrom = new DatePicker("Date of creation from");
    private DatePicker orderedTo = new DatePicker("Date of creation to");
    private NumberField findOrderById = new NumberField("Find order by ID");
    private Button searchValue = new Button("Search");
    private Button searchDate = new Button("Search");
    private Button logout = new Button("Log out");
    private Button users = new Button("Users page");
    private Button groups = new Button("ProductGroups page");
    private Button products = new Button("Products page");
    private Text logged = new Text("Logged as : " + session.getCurrentUser().getFirstName());
    private Grid<Order> orderGrid = new Grid<>(Order.class);
    private OrderForm orderForm = new OrderForm(this);
    private Text totalValue = new Text("");
    private Text numberOfOrders = new Text("");
    private String infoValue = "";
    private String infoNumber = "";

    public MainAdminView() {
        setAlignItems(Alignment.CENTER);
        valueFrom.setPlaceholder("Filter by value higher than");
        valueFrom.setClearButtonVisible(true);
        valueFrom.setValueChangeMode(ValueChangeMode.LAZY);
        valueFrom.addValueChangeListener(e -> updateByValue());
        valueTo.setPlaceholder("Filter by value lower than");
        valueTo.setClearButtonVisible(true);
        valueTo.setValueChangeMode(ValueChangeMode.LAZY);
        valueTo.addValueChangeListener(e -> updateByValue());
        orderedFrom.setPlaceholder("Filter by value higher than");
        orderedFrom.setClearButtonVisible(true);
        orderedFrom.addValueChangeListener(e -> updateByDate());
        orderedTo.setPlaceholder("Filter by value lower than");
        orderedTo.setClearButtonVisible(true);
        orderedTo.addValueChangeListener(e -> updateByDate());
        findOrderById.setClearButtonVisible(true);
        findOrderById.addValueChangeListener(e -> findById());
        searchDate.addClickListener(event -> updateByDate());
        searchValue.addClickListener(event -> updateByValue());
        orderGrid.setColumns("id", "ordered", "comments", "cardId, userId, totalValue, isCompleted");
        orderGrid.asSingleSelect().addValueChangeListener(event -> orderForm.setOrder(orderGrid.asSingleSelect().getValue()));
        totalValue.setText(infoValue);
        numberOfOrders.setText(infoNumber);
        HorizontalLayout searchByValue = new HorizontalLayout(valueFrom, valueTo, searchValue);
        HorizontalLayout searchByDate = new HorizontalLayout(orderedFrom, orderedTo, searchDate);
        HorizontalLayout info = new HorizontalLayout(numberOfOrders, totalValue);
        HorizontalLayout header = new HorizontalLayout(searchByDate, searchByValue, findOrderById, info);
        HorizontalLayout directions = new HorizontalLayout(users, groups, products, logout, logged);
        HorizontalLayout mainContent = new HorizontalLayout(orderGrid, orderForm);
        mainContent.setSizeFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setFlexGrow(1, logout);
        header.setPadding(true);
        header.setSpacing(true);
        directions.setFlexGrow(1, logout);
        directions.setPadding(true);
        directions.setSpacing(true);
        users.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("admin_users")));
        groups.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("admin_groups")));
        products.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("admin_products")));
        orderGrid.setSizeFull();
        add(directions, header, mainContent);
        orderForm.setOrder(null);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        this.listOfOrders = orderService.getOrders();
        orderGrid.setItems(listOfOrders);
        infoNumber = "Number of displayed orders is: " + theShopService.numberOfOrders(listOfOrders);
        infoValue = "Value of displayed orders is: " + theShopService.totalValue(listOfOrders) + " PLN";
    }

    public void updateByDate() {
        this.listOfOrders = orderService.findOrdersByDateOfOrdered(orderedFrom.getValue(), orderedTo.getValue());
        orderGrid.setItems(listOfOrders);
        infoNumber = "Number of displayed orders is: " + theShopService.numberOfOrders(listOfOrders);
        infoValue = "Value of displayed orders is: " + theShopService.totalValue(listOfOrders) + " PLN";
    }
    public void updateByValue() {
        this.listOfOrders = orderService.findOrdersWithTotalValueBeetween(valueFrom.getValue(), valueTo.getValue());
        orderGrid.setItems(listOfOrders);
        infoNumber = "Number of displayed orders is: " + theShopService.numberOfOrders(listOfOrders);
        infoValue = "Value of displayed orders is: " + theShopService.totalValue(listOfOrders) + " PLN";
    }

    public void findById() {
        List<Order> tmpList = new ArrayList<>();
        double idDouble = findOrderById.getValue();
        long id = (long) idDouble;
        Order searchedOrder = orderService.getOrder(id);
        tmpList.add(searchedOrder);
        this.listOfOrders = tmpList;
        orderGrid.setItems(listOfOrders);
    }
}
