package br.com.tevitto.beleza_agendada.professional_schedule.data.enums;

public enum DayOfWeekType {
    SUNDAY(0),
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6);

    private final int number;

    DayOfWeekType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
