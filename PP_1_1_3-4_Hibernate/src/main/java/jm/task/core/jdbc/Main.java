package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Данил", "Ромнаов", (byte) 20);
        userService.saveUser("Степан", "Прокофьев", (byte) 20);
        userService.saveUser("Дмитрий", "Обухов", (byte) 20);
        userService.saveUser("Максим", "Осипов", (byte) 19);

        userService.getAllUsers().forEach(System.out::println);

        userService.removeUserById(1);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
