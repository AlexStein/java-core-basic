import java.util.Locale;

/**
 * Класс, описывающий сотрудника
 * ФИО, должность, email, телефон, зарплата, возраст
 */
public class Employee {


//    /** Поле Имя */
//    private String firstName;
//    /** Поле Отчество */
//    private String middleName;
//    /** Поле Фамилия */
//    private String lastName;

    /** Поле ФИО */
    private String fullName;

    /** Поле Должность */
    private String jobPosition;
    /** Поле Email */
    private String email;
    /** Поле Телефон */
    private String phoneNumber;

    /** Поле Заработная плата */
    private float salary;
    /** Поле Возраст */
    private int age;

    public Employee(String fullName, String jobPosition, String email, String phoneNumber, float salary, int age) {
        this.fullName = fullName;
        this.jobPosition = jobPosition;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.age = age;
    }

    public void printInfo() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {

        return String.format(Locale.forLanguageTag("RU"),
                "Сотрудник %s, в должности %s, Контактные данные %s, Возраст %d",
                this.fullName, this.jobPosition, this.getContacts(), this.age);
    }

    public String getFullName() {
        return fullName;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getContacts() {
        String contacts = "";

        if (!this.getEmail().isEmpty()) {
            contacts += String.format("Email: %s", this.getEmail());
        }

        if (!this.getPhoneNumber().isEmpty()) {
            if (!contacts.isEmpty()) {
                contacts += ", ";
            }
            contacts += String.format("Телефон: %s", this.getPhoneNumber());
        }

        return contacts;
    }

    public float getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }
}
