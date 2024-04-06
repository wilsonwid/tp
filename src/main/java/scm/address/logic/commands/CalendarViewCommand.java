package scm.address.logic.commands;

import java.time.YearMonth;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scm.address.model.Model;
import scm.address.model.view.CalendarView;

/**
 * Command to display a calendar view.
 */
public class CalendarViewCommand extends Command {

    public static final String COMMAND_WORD = "calendar_view";
    public static final String MESSAGE_SUCCESS = "Displaying calendar for the current month.";

    @Override
    public CommandResult execute(Model model) {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.setTitle("Calendar");

            CalendarView calendarView = new CalendarView(YearMonth.now(), model);

            Scene scene = new Scene(calendarView, 600, 400);
            stage.setScene(scene);
            stage.show();
        });

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
