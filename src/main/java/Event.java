import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final String description;
    private final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    public Event(String description, String from, String to) throws DonnaException {
        super(description);
        if (from.trim().isEmpty() && to.trim().isEmpty()) {
            throw DonnaException.emptyEventTime();
        }
        try {
            this.from = LocalDateTime.parse(from, inputFormatter);
            this.to = LocalDateTime.parse(to, inputFormatter);
        } catch (DateTimeParseException e) {
            throw new DonnaException("Invalid date and time format! Please use dd/MM/yyyy HHmm (24hr format)"+ "\n" +
                    "Use this format for specifying the date and time for both," + "\n" +
                    "/from and /to");
        }
        this.description = description;
    }

    @Override
    public String toFileFormat() {
        String fromFormatted = from.format(inputFormatter);
        String toFormatted = to.format(inputFormatter);
        return "E | " + (this.isDone() ? "1" : "0") + " | " + this.description + " | " + fromFormatted + " | " + toFormatted;
    }

    @Override
    public String getType() {
        return "E";
    }

    @Override
    public String toString() {
        String fromFormatted = from.format(outputFormatter);
        String toFormatted = to.format(outputFormatter);
        return super.toString() //type, status & desc
                + "(from: " + fromFormatted + " to: " + toFormatted + ")";
    }
}
