package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);


        userService.add(new User("name1", "lastname1", "name1@nothing.com"
                , new Car("BMW", 3)));
        userService.add(new User("name2", "lastName2", "name2@nothing.com"
                , new Car("Audi", 100)));
        userService.add(new User("name3", "lastname3", "name3@nothing.com"
                , new Car("BMW", 3)));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            try {
                System.out.println("Car model = " + user.getCar().getModel());
                System.out.println("Car series = " + user.getCar().getSeries());
            } catch (NullPointerException e) {
                System.out.println("Пешеход");
            }
            System.out.println();
        }

        try {
            System.out.println(userService.getUserByCar("BMW", 3));
        }catch (NullPointerException e){
            System.out.println("Пользователь не найден");
        }
        context.close();
    }
}
