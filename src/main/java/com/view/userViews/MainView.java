package com.view.userViews;

import com.service.CurrencyService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route("currancy_rates")
public class MainView extends VerticalLayout {

    private Button adminButton = new Button("ADMIN", VaadinIcon.KEY.create());
    private NumberField eurField = new NumberField();
    private NumberField usdField = new NumberField();
    private NumberField gbpField = new NumberField();

    @Autowired
    private CurrencyService currencyService;

    public MainView() {

        adminButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        adminButton.addClickListener(event -> {
            adminButton.getUI().ifPresent(ui -> ui.navigate("admin/users"));
        });
        eurField.setLabel("EUR");
        eurField.setReadOnly(true);
        //eurField.setValue(currencyService.getEUR());
        usdField.setLabel("USD");
        usdField.setReadOnly(true);
        //usdField.setValue(currencyService.getUSD());
        gbpField.setLabel("GBP");
        gbpField.setReadOnly(true);
        //gbpField.setValue(currencyService.getGBP());
        HorizontalLayout currencies = new HorizontalLayout(eurField, usdField, gbpField);
        add(adminButton, currencies);
        setSizeFull();
    }
}
