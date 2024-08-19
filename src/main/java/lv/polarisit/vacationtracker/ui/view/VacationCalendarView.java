package lv.polarisit.vacationtracker.ui.view;

import com.vaadin.flow.router.Route;
import elemental.json.JsonObject;
import lv.polarisit.vacationtracker.data.Contact;
import lv.polarisit.vacationtracker.data.Vacation;
import lv.polarisit.vacationtracker.service.Service;
import lv.polarisit.vacationtracker.service.VacationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.stefan.fullcalendar.*;
import lv.polarisit.vacationtracker.ui.dialogs.DemoDialog;
import lv.polarisit.vacationtracker.ui.layouts.MainLayout;
import lv.polarisit.vacationtracker.ui.menu.MenuItem;
import lv.polarisit.vacationtracker.ui.view.AbstractCalendarView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route(value = "vacation-calendar", layout = MainLayout.class)
@MenuItem(label = "Vacation Calendar")
public class VacationCalendarView extends AbstractCalendarView {

    private static final Logger LOGGER = LoggerFactory.getLogger(VacationCalendarView.class);
    private VacationService vacationService;
    private Map<Long, String> personColorMap = null;

    @Override
    protected FullCalendar createCalendar(JsonObject defaultInitialOptions) {
        this.vacationService = Service.get(VacationService.class);
        List<Vacation> vacations = this.vacationService.getAllVacations();
        List<Entry> entries = vacations.stream().map(vacation -> {
            Entry entry = new Entry();
            Contact contact = vacation.getContact();
            entry.setTitle(contact.getFirstName() + " " + contact.getLastName() + " (" + contact.getId() + ")");
            entry.setStart(vacation.getDateStart().atStartOfDay());
            entry.setEnd(vacation.getDateEnd().plusDays(1).atStartOfDay());
            entry.isAllDay();
            String color = generateColorForPerson(contact.getId());
            LOGGER.info("Person: " + contact.getId() + " Color: " + color);
            entry.setColor(color);
            return entry;
        }).toList();

        defaultInitialOptions.put("initialView", "dayGridYear");
        defaultInitialOptions.put("firstDay", 1); // Set Monday as the first day of the week

        return FullCalendarBuilder.create()
                .withInitialOptions(defaultInitialOptions)
                .withInitialEntries(entries)
                .withEntryLimit(5)
                .build();
    }

    private String generateColorForPerson(Long personId) {
        if (personColorMap == null) {
            personColorMap = new HashMap<>();
        }
        if (personColorMap.containsKey(personId)) {
            return personColorMap.get(personId);
        }

        int hash = personId.hashCode();
        int r = (hash & 0xFF0000) >> 16;
        int g = (hash & 0x00FF00) >> 8;
        int b = hash & 0x0000FF;

        r = (r + (int) (Math.random() * 128)) % 256;
        g = (g + (int) (Math.random() * 128)) % 256;
        b = (b + (int) (Math.random() * 128)) % 256;

        String color = String.format("#%02X%02X%02X", r, g, b);
        personColorMap.put(personId, color);
        return color;
    }

    @Override
    protected String createDescription() {
        return "A simple demo, showing the basic interaction events with the calendar and allow basic modification of entries.";
    }

    @Override
    protected void onEntryClick(EntryClickedEvent event) {
        System.out.println(event.getClass().getSimpleName() + ": " + event);

        if (event.getEntry().getDisplayMode() != DisplayMode.BACKGROUND && event.getEntry().getDisplayMode() != DisplayMode.INVERSE_BACKGROUND) {
            DemoDialog dialog = new DemoDialog(event.getEntry(), false);
            dialog.setSaveConsumer(this::onEntryChanged);
            dialog.setDeleteConsumer(e -> onEntriesRemoved(Collections.singletonList(e)));
            dialog.open();
        }
    }

    @Override
    protected void onTimeslotsSelected(TimeslotsSelectedEvent event) {
        super.onTimeslotsSelected(event);

        ResourceEntry entry = new ResourceEntry();

        entry.setStart(event.getStart());
        entry.setEnd(event.getEnd());
        entry.setAllDay(event.isAllDay());
        entry.setCalendar(event.getSource());

        DemoDialog dialog = new DemoDialog(entry, true);
        dialog.setSaveConsumer(e -> onEntriesCreated(Collections.singletonList(e)));
        dialog.setDeleteConsumer(e -> onEntriesRemoved(Collections.singletonList(e)));
        dialog.open();
    }
}