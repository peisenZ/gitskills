package JavaVacationProject3;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Peisen Zhang
 * @version 15.0
 * @date 2021/1/22 16:54
 */
public class play {
    public static void main(String[] args) throws SQLException {
        int choice = 0;
        function function = new function();
        while(choice!=6){
            function.menu();
            System.out.print("请输入你要使用的功能:");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    function.zhuce();
                    System.out.println("注册成功,请登录！");
                    function.delay();
                    break;
                case 2:
                    function.login();
                    System.out.println("登陆成功！");
                    function.delay();
                    function.menuSon();

                    break;
                case 3:
                    System.out.println("退出登录成功！");
                    function.delay();
                    break;
                case 4:
                    function.zhuxiao();
                    function.delay();
                    break;
                case 5:
                    function.search();
                    function.delay();
                    break;
                case 6:
                    System.out.println("退出成功！");
                    break;
            }
            continue;
        }
    }
}