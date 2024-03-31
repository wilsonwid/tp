package scm.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import scm.address.model.AddressBook;
import scm.address.model.ReadOnlyAddressBook;
import scm.address.model.ReadOnlyScheduleList;
import scm.address.model.ScheduleList;
import scm.address.model.person.Address;
import scm.address.model.person.Email;
import scm.address.model.person.Name;
import scm.address.model.person.Person;
import scm.address.model.person.Phone;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;
import scm.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static Schedule[] getSampleSchedules() {
        return new Schedule[] {
            new Schedule(new Title("Make cookies"), new Description("Chocolate cookies"),
                LocalDateTime.parse("2024-01-03 16:00", Schedule.DATE_TIME_FORMATTER),
                LocalDateTime.parse("2024-01-03 18:00", Schedule.DATE_TIME_FORMATTER)
                ),
            new Schedule(new Title("Make homework"), new Description("Computer Science 101"),
                LocalDateTime.parse("2024-01-03 20:00", Schedule.DATE_TIME_FORMATTER),
                LocalDateTime.parse("2024-01-03 22:00", Schedule.DATE_TIME_FORMATTER))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlyScheduleList getSampleScheduleList() {
        ScheduleList sampleScheduleList = new ScheduleList();
        for (Schedule sampleSchedule : getSampleSchedules()) {
            sampleScheduleList.addSchedule(sampleSchedule);
        }
        return sampleScheduleList;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
