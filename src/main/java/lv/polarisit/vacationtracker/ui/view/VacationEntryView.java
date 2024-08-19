package lv.polarisit.vacationtracker.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lv.polarisit.vacationtracker.data.Contact;
import lv.polarisit.vacationtracker.data.Vacation;
import lv.polarisit.vacationtracker.service.ContactService;
import lv.polarisit.vacationtracker.service.VacationService;
import lv.polarisit.vacationtracker.ui.layouts.MainLayout;
import lv.polarisit.vacationtracker.ui.menu.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Route(value = "vacation", layout = MainLayout.class)
@MenuItem(label = "Vacation Entry")
public class VacationEntryView extends HorizontalLayout {
    private final VacationService vacationService;
    private final ContactService contactService;
    private final Grid<Vacation> grid = new Grid<>(Vacation.class);

    private final ListBox<Contact> contactListBox = new ListBox<>();
    private final Span contactListBoxLabel = new Span("Contact");

    private final DatePicker dateStartField = new DatePicker("Start Date");
    private final DatePicker dateEndField = new DatePicker("End Date");
    private final TextField descriptionField = new TextField("Description");
    private final TextField vacationTypeField = new TextField("Vacation Type");
    Button addButton = new Button("Add", e -> addVacation());
    @Autowired
    public VacationEntryView(VacationService vacationService,
                             ContactService contactService) {
        this.vacationService = vacationService;
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
        List<Contact> contacts = contactService.getAllContacts();
        contactListBox.setItems(contacts);
        contactListBox.setItemLabelGenerator(Contact::toString);
        contactListBox.getElement().getStyle().set("max-height", "150px"); // Limit height to 5 items
        contactListBox.getElement().getStyle().set("overflow", "auto"); // Add scrollbar for overflow

    }

    @SuppressWarnings("unchecked")
    private void configureGrid() {
        grid.setColumns("contact.firstName", "contact.lastName", "dateStart", "dateEnd", "description", "vacationType");
        grid.addColumn(vacation -> ChronoUnit.DAYS.between(vacation.getDateStart(), vacation.getDateEnd()) + 1)
                .setHeader("Duration (Days)")
                .setKey("duration")
                .setSortable(true);
        grid.addComponentColumn(vacation ->
                new Button("Delete", e -> deleteVacation(vacation))
        ).setHeader("Actions");
//        grid.setColumnOrder(
//                grid.getColumnByKey("personId"),
//                grid.getColumnByKey("dateStart"),
//                grid.getColumnByKey("dateEnd"),
//                grid.getColumnByKey("duration"),
//                grid.getColumnByKey("description"),
//                grid.getColumnByKey("vacationType")
//        );
    }

    private void updateGrid() {
        grid.setItems(vacationService.getAllVacations());
    }

    private void addVacation() {
        Vacation vacation = new Vacation();
        Contact contact = contactListBox.getValue();
        vacation.setContact(contact);
        vacation.setDateStart(dateStartField.getValue());
        vacation.setDateEnd(dateEndField.getValue());
        vacation.setDescription(descriptionField.getValue());
        vacation.setVacationType(vacationTypeField.getValue().charAt(0));
        vacationService.saveVacation(vacation);
        updateGrid();
    }

    private void deleteVacation(Vacation vacation) {
        vacationService.deleteVacation(vacation.getId());
        updateGrid();
    }
    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(contactListBoxLabel,contactListBox, dateStartField, dateEndField, descriptionField, vacationTypeField,addButton);
        return formLayout;
    }
}
