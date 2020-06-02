package com.service;

import com.config.CompanyConfig;
import com.domain.ProductOnCart;
import com.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private CompanyConfig companyConfig;

    private final Session session = Session.getInstance();

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildNewEmail(String message) {
        List<ProductOnCart> list = session.getListOfProductsOnCart();

        Context context = new Context();

        context.setVariable("theShop_url", "http://localhost:4601");
        context.setVariable("client_name", clientName());
        context.setVariable("company_name", companyConfig.getCompanyName());
        context.setVariable("company_mail", companyConfig.getCompanyMail());
        context.setVariable("company_website", companyConfig.getCompanyWebsite());
        context.setVariable("message", message);
        context.setVariable("button", "Payment");
        context.setVariable("goodbay_message", goodbay());
        context.setVariable("application_functionality", list);
        context.setVariable("is_list", true);

        return templateEngine.process("mail/mail-template", context);
    }

    private String clientName() {
        String firstName = session.getCurrentUser().getFirstName();
        String lastName = session.getCurrentUser().getLastName();
        return firstName + " " + lastName;
    }

    private String goodbay() {
        String resume = "\n";
        String goodbay = "Thank you for your order\n" +
                "Have a nice day!";
        return resume + goodbay;
    }
}
