package scm.address.model.view;

import java.util.List;
import java.util.stream.Collectors;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import scm.address.model.Model;
import scm.address.model.schedule.Schedule;

public class CalendarView extends GridPane {

    private YearMonth currentYearMonth;
    private GridPane calendarGrid = new GridPane();
    private List<Schedule> schedules;
    private Model model;

    public CalendarView(YearMonth yearMonth, Model model) {
        this.currentYearMonth = yearMonth;
        this.schedules = model.getScheduleList().getScheduleList();
        populateCalendar(this.currentYearMonth);
    }

    public void populateCalendar(YearMonth yearMonth) {
        calendarGrid.getChildren().clear();

        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        int numOfDaysInMonth = yearMonth.lengthOfMonth();
        DayOfWeek firstDayOfWeek = calendarDate.getDayOfWeek();

        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < dayNames.length; i++) {
            calendarGrid.add(new Label(dayNames[i]), i, 0);
        }

        int row = 1, col = firstDayOfWeek.getValue() % 7;
        for (int day = 1; day <= numOfDaysInMonth; day++, calendarDate = calendarDate.plusDays(1)) {
            StackPane dayCell = new StackPane();
            dayCell.setPrefSize(200, 200);
            Label dayLabel = new Label(String.valueOf(day));
            VBox dayCellContent = new VBox(5);
            dayCellContent.getChildren().add(dayLabel);
            dayCell.getChildren().add(dayCellContent);
            calendarGrid.add(dayCell, col++, row);

            if (col > 6) {
                col = 0;
                row++;
            }

            List<Schedule> schedulesForDay = getSchedulesForDate(calendarDate);

            for (Schedule schedule : schedulesForDay) {
                Label scheduleLabel = new Label(schedule.toString());
                dayCell.getChildren().add(scheduleLabel);
            }
        }
        this.getChildren().add(calendarGrid);
    }

    private List<Schedule> getSchedulesForDate(LocalDate date) {
        return schedules.stream()
                .filter(schedule -> schedule.getStartDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    public YearMonth getCurrentYearMonth() {
        return currentYearMonth;
    }

    public void setCurrentYearMonth(YearMonth yearMonth) {
        this.currentYearMonth = yearMonth;
        populateCalendar(yearMonth);
    }

    public Node getView() {
        return calendarGrid;
    }
}
