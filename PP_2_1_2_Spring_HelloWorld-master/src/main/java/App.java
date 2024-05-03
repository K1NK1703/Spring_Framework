import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);

        HelloWorld hBean_1 =
                (HelloWorld) applicationContext.getBean("helloworld");
        HelloWorld hBean_2 =
                (HelloWorld) applicationContext.getBean("helloworld");

        System.out.println(hBean_1 == hBean_2);

        Cat cBean_1 = (Cat) applicationContext.getBean("cat");
        Cat cBean_2 = (Cat) applicationContext.getBean("cat");

        System.out.println(cBean_1 == cBean_2);
    }
}