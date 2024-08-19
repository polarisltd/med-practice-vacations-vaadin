package lv.polarisit.vacationtracker.ui.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import lv.polarisit.vacationtracker.data.Vacation;
import lv.polarisit.vacationtracker.service.VacationService;
import lv.polarisit.vacationtracker.ui.layouts.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.stefan.table.Table;
import org.vaadin.stefan.table.TableRow;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Route(value = "", layout = MainLayout.class)
@lv.polarisit.vacationtracker.ui.menu.MenuItem(label = "Dashboard")
public class DashboardView extends VerticalLayout {

    private final VacationService vacationService;

    @Autowired
    public DashboardView(VacationService vacationService) {
        this.vacationService = vacationService;
        add(new H1("Vacation Summary"));

        List<Vacation> vacations = vacationService.getAllVacations();
        Map<Integer, Map<String, Long>> summary = vacations.stream()
                .collect(Collectors.groupingBy(
                        vacation -> vacation.getDateStart().getYear(),
                        Collectors.groupingBy(
                                vacation -> vacation.getContact().getFirstName() + " " + vacation.getContact().getLastName() + " (" + vacation.getContact().getId() + ")",
                                Collectors.summingLong(vacation -> ChronoUnit.DAYS.between(vacation.getDateStart(), vacation.getDateEnd().plusDays(1)))
                        )
                ));

        summary.forEach((year, personSummary) -> {
            add(new H2("Year: " + year));
            Table table = new Table();
            TableRow headerRow = new TableRow();
            headerRow.addHeaderCells("Person", "Total Duration (Days)");
            table.addRows(headerRow);

            personSummary.forEach((person, totalDuration) -> {
                TableRow row = new TableRow();
                row.addCells(person, String.valueOf(totalDuration));
                table.addRows(row);
            });


            add(table);
        });
    }
}