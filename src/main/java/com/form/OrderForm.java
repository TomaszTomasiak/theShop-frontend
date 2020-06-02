package com.form;

import com.domain.Order;
import com.service.OrderService;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.view.adminViews.MainAdminView;

public class OrderForm extends FormLayout {

    private final MainAdminView mainAdminView;
    private final OrderService orderService = OrderService.getInstance();
    private NumberField id = new NumberField("ID");
    private DatePicker ordered = new DatePicker("Date of creation");
    private TextField comments = new TextField("Comments");
    private NumberField cardId = new NumberField("Card ID");
    private NumberField userId = new NumberField("User ID");
    private NumberField totalValue = new NumberField("Value");
    private TextField isCompleted = new TextField("Status");
    private Binder<Order> orderBinder = new Binder<>(Order.class);

    public OrderForm(MainAdminView mainAdminView) {
        this.mainAdminView = mainAdminView;
        add(id, ordered, comments, cardId, userId, totalValue, isCompleted);
        id.setVisible(false);
        orderBinder.forField(id).withConverter(Double::longValue, Long::doubleValue).bind(Order::getId, Order::setId);
        orderBinder.bindInstanceFields(this);
    }

    public Order createdOrder() {
        return orderBinder.getBean();
    }

    public void save() {
        orderService.saveOrder(createdOrder());
        setOrder(null);
        mainAdminView.refresh();
    }

    public void delete() {
        orderService.deleteOrder(createdOrder().getId());
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
