package scm.address.logic.commands;

import scm.address.model.Model;
import scm.address.model.schedule.Schedule;

public class AddScheduleCommand extends Command {

    public static final String COMMAND_WORD = "add_schedule";
    private final Schedule schedule;

    public AddScheduleCommand(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public CommandResult execute(Model model) {
        model.addSchedule(schedule);
        return new CommandResult("Added schedule: " + schedule);
    }
}
