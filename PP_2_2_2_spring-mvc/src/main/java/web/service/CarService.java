package web.service;

import web.models.Car;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Arrays;

@Service
public class CarService {

    private final List<Car> cars = Arrays.asList(
        new Car("BMW", "M4", 2022),
        new Car("BMW", "M5", 2020),
        new Car("Mercedes",  "GT", 2019),
        new Car("Toyota",  "Camry", 2015),
        new Car("Ford",  "Mustang", 2004)
    );

    public List<Car> getCars(int count) {
        if (count > cars.size()) {
            return cars;
        }
        return cars.subList(0, count);
    }
}