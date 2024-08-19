/*
 * Copyright 2020, Stefan Uebe
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package lv.polarisit.vacationtracker.ui.layouts;

import com.vaadin.flow.component.sidenav.SideNav;
import lv.polarisit.vacationtracker.ui.view.ContactView;
import lv.polarisit.vacationtracker.ui.view.DashboardView;
import lv.polarisit.vacationtracker.ui.view.VacationCalendarView;
import lv.polarisit.vacationtracker.ui.view.VacationEntryView;


public class MainLayout extends AbstractLayout {
    @Override
    protected void createMenuEntries(SideNav nav) {
        addMenu(nav, DashboardView.class);
        addMenu(nav, ContactView.class);
        addMenu(nav, VacationEntryView.class);
        addMenu(nav, VacationCalendarView.class);
    }
}

