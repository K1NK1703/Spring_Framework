package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Daniil", "Romanov", (byte) 20);
        userService.saveUser("Stepan", "Prokofiev", (byte) 20);
        userService.saveUser("Dmitry", "Обухов", (byte) 20);
        userService.saveUser("Максим", "Осипов", (byte) 19);

        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
