package ro.ase.agenda;

import java.util.List;

public class HolidayMessages {

    private String holiday;
    private String category;
    private List<String> messages;

    public HolidayMessages(String holiday, String category, List<String> messages) {
        this.holiday = holiday;
        this.category = category;
        this.messages = messages;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "HolidayMessages{" +
                "holiday='" + holiday + '\'' +
                ", category='" + category + '\'' +
                ", messages=" + messages +
                '}';
    }
}
