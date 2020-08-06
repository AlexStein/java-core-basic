/**
 * Класс для трестирование объектов в ООП.
 */
public class MainOO {
    public static void main(String[] args) {

        // Массив сотрудников
        Employee[] employeesArray = new Employee[5];

        employeesArray[0] = new Employee("Иванов Иван Иванович", "Руководитель", "iii@mail.com", "88005553535", 50000, 50);
        employeesArray[1] = new Employee("Сидоров Сидор Сидорович", "Инженер", "sss@mail.com", "88005563636", 300000, 29);
        employeesArray[2] = new Employee("Петров Пётр Перович", "Курьер", "ppp@mail.com", "88005573737", 50000, 60);
        employeesArray[3] = new Employee("Михайлов Михаил Михайлович", "Ресепшионист" ,"mmm@mail.com", "88005523232", 15000, 18);
        employeesArray[4] = new Employee("Алексеев Алексей Алексеевич", "Охранник", "aaa@mail.com", "88005503030", 3000, 48);

        // Вывести почтенных господ
        for (int i = 0; i < employeesArray.length; i++) {
            if (employeesArray[i].getAge() > 40) {
                employeesArray[i].printInfo();
            }
        }
    }

}
