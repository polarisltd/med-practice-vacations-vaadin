package lv.polarisit.vacationtracker.ui.view;

import java.time.LocalDate;
import java.util.Locale;

/**
 * @author Stefan Uebe
 */
@FunctionalInterface
public interface HasIntervalLabel {

    String formatIntervalLabel(LocalDate intervalStart, Locale locale);

}
