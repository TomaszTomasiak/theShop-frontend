package com.form;

import com.domain.Order;
import com.domain.User;
import com.service.OrderService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.view.adminViews.MainAdminView;


public class OrderForm extends FormLayout {

    private MainAdminView mainAdminView;
    private OrderService orderService = OrderService.getInstance();
    private NumberField id = new NumberField("ID");
    private DatePicker ordered = new DatePicker("Date of creation");
    private TextField comments = new TextField("Comments");
    private NumberField cardId = new NumberField("Card ID");
    private NumberField userId = new NumberField("User ID");
    private NumberField totalValue = new NumberField("Value");
    private TextField isCompleted = new TextField("Status");
    private Binder<Order> orderBinder = new Binder<>(Order.class);
//    private Button save = new Button("Save");
//    private Button delete = new Button("Delete");

    public OrderForm(MainAdminView mainAdminView) {
        this.mainAdminView = mainAdminView;
//        HorizontalLayout buttons = new HorizontalLayout(save, delete);
//        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(ordered, comments, cardId, userId, totalValue, isCompleted);
        orderBinder.forField(id).withConverter(Double::longValue, Long::doubleValue).bind(Order::getId, Order::setId);
        orderBinder.bindInstanceFields(this);
//        save.addClickListener(event -> save());
//        delete.addClickListener(event -> delete());
    }

    public Order createdOrder() {
        return orderBinder.getBean();
    }

    public void save() {
        OrderService.getInstance().saveOrder(createdOrder());
        setOrder(null);
        mainAdminView.refresh();
    }

    public void delete() {
        OrderService.getInstance().deleteOrder(createdOrder());
        setOrder(null);
        mainAdminView.refresh();
    }

    public void setOrder(Order order) {
        orderBinder.setBean(new Order());

        if (order == null) {
            setVisible(false);
        } else {
            setVisible(true);
            comments.focus();
        }
    }
}
