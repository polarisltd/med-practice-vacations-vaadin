package lv.polarisit.vacationtracker.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lv.polarisit.vacationtracker.data.Contact;
import lv.polarisit.vacationtracker.service.ContactService;
import lv.polarisit.vacationtracker.ui.layouts.MainLayout;
import lv.polarisit.vacationtracker.ui.menu.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Route(value = "contact", layout = MainLayout.class)
@MenuItem(label = "Contact Entry")
public class ContactView extends HorizontalLayout {
    private final ContactService contactService;
    private final Grid<Contact> grid = new Grid<>(Contact.class);

    private final TextField firstNameField = new TextField("First Name");
    private final TextField lastNameField = new TextField("Last Name");
    private final EmailField emailField = new EmailField("Email");
    private final TextField balanceField = new TextField("Balance");
    Button addButton = new Button("Add", e -> addContact());

    @Autowired
    public ContactView(ContactService contactService) {
        this.contactService = contactService;
        configureGrid();
        configureForm();
        grid.setWidth("66%");
        FormLayout form = createFormLayout();
        form.setWidth("33%");
        add(grid, form);
        updateGrid();
    }

    private void configureForm() {
        // Add form configuration if needed
    }

    @SuppressWarnings("unchecked")
    private void configureGrid() {
        grid.setColumns("firstName", "lastName", "email", "balance");
        grid.addComponentColumn(contact ->
                new Button("Delete", e -> deleteContact(contact))
        ).setHeader("Actions");
    }

    private void updateGrid() {
        grid.setItems(contactService.getAllContacts());
    }

    private void addContact() {
        Contact contact = new Contact();
        contact.setFirstName(firstNameField.getValue());
        contact.setLastName(lastNameField.getValue());
        contact.setEmail(emailField.getValue());
        contact.setBalance(new BigDecimal(balanceField.getValue()));
        contactService.saveContact(contact);
        updateGrid();
    }

    private void deleteContact(Contact contact) {
        contactService.deleteContact(contact.getId());
        updateGrid();
    }

    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(firstNameField, lastNameField, emailField, balanceField, addButton);
        return formLayout;
    }
}