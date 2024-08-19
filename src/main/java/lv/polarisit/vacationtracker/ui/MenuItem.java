package lv.polarisit.vacationtracker.ui.menu;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MenuItem {
	String label();

	//VaadinIcon icon();
}
