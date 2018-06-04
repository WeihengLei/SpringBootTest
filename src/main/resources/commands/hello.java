/**
 * Created by jack on 2017/5/4.
 */

package commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.crsh.cli.Command;
import org.crsh.cli.Usage;
import org.crsh.command.BaseCommand;
import org.crsh.command.InvocationContext;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;


@Usage("Test Command : hello say")
public class hello extends BaseCommand{
    @Usage("hello say")
    @Command
    public String say(InvocationContext context){
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) context.getAttributes().get("spring.beanfactory");

        for (String name : defaultListableBeanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
            context.getWriter().write(name);
            context.getWriter().write("\n");
        }

        return "Hello, Now Is " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}



