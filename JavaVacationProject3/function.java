package JavaVacationProject3;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @author Peisen Zhang
 * @version 15.0
 * @date 2021/1/22 15:50
 */
public class function {
    private String name = null;
    private String password = null;
    private int ID = 0;
    public String name2="wang";
    private String player_winner= null;
    Deque<String> deque1 = new LinkedList<>();
    Deque<String> deque2 = new LinkedList<>();
    List<String> card = new ArrayList<>();
    Map<String,Integer>  map =new HashMap<>();
    private String begin_time = null;
    private String exit_time =null;
    boolean flag=true;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    Scanner scanner = new Scanner(System.in);
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void zhuce() throws SQLException {
        System.out.print("请输入用户名：");
        name=scanner.next();
        setName(name);
        System.out.print("请输入用户密码:");
        password=scanner.next();
        setPassword(password);

        do {
        if(resultSet==null){
            System.out.print("请输入用户ID:");

            do {
                try {
                    ID = scanner.nextInt();
                    flag = false;

                } catch (InputMismatchException e) {
                    System.out.print("输入格式错误,请输入整数:");
                    scanner.next();
                }
            } while (flag);
            setID(ID);
        }
        else{
            System.out.print("此ID已被注册，请重新输入:");
            do {
                try {
                    ID = scanner.nextInt();
                    flag = false;

                } catch (InputMismatchException e) {
                    System.out.print("输入格式错误,请输入整数:");
                    scanner.next();
                }
            } while (flag);
            setID(ID);
        }
        connection = JDBCTools.getConnection();
        String sqltest = "SELECT * from user where ID1=?";
        statement = connection.prepareStatement(sqltest);
        statement.setInt(1,getID());
        resultSet = statement.executeQuery();
        }while (resultSet.next());


        try {
            connection = JDBCTools.getConnection();
            String sql ="INSERT INTO user(player_username,player_password,play_createtime,ID1)"+
                    "VALUE (?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,getName());
            statement.setString(2,getPassword());
            statement.setString(3, df.format(new Date()));
            statement.setInt(4,getID());
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCTools.release(connection,statement,resultSet);
        }
        setID(0);
        setName(null);
        setPassword(null);
    }

    public void login() throws SQLException {
        resultSet=null;

            if(resultSet==null){
                System.out.print("请输入登录ID:");
                do {
                    try {
                        ID = scanner.nextInt();
                        setID(ID);
                        connection = JDBCTools.getConnection();
                        String sqltest = "SELECT * from user where ID1=?";
                        statement = connection.prepareStatement(sqltest);
                        statement.setInt(1,getID());
                        resultSet = statement.executeQuery();
                        if(resultSet.next())
                        flag = false;
                        else {
                            do {
                                 System.out.print("此ID不存在,请重新输入:");

                                ID = scanner.nextInt();
                                setID(ID);
                                connection = JDBCTools.getConnection();
                                String sqltest1 = "SELECT * from user where ID1=?";
                                statement = connection.prepareStatement(sqltest1);
                                statement.setInt(1,getID());
                                resultSet = statement.executeQuery();
                               } while(!resultSet.next());
                               flag=false;
                             }
                    }
                    catch (InputMismatchException e) {
                        System.out.print("此ID不存在,请重新输入:");
                        scanner.next();
                    }
                } while (flag);

            }
            if(flag==false)
            {
                flag=true;
                System.out.print("请输入登陆密码:");
                do {
                    if(resultSet!=null){
                    password = scanner.next();
                    setPassword(password);
                    connection = JDBCTools.getConnection();
                    String sqltest2 = "SELECT * from user where player_password=? and ID1=?";
                    statement = connection.prepareStatement(sqltest2);
                    statement.setString(1,getPassword());
                    statement.setInt(2,getID());
                    resultSet = statement.executeQuery();
                    }
                    if(resultSet.next())
                        flag = false;
                    else{
                       do {
                           System.out.print("密码错误,请重新输入:");
                           password = scanner.next();
                           setPassword(password);
                           connection = JDBCTools.getConnection();
                           String sqltest3 = "SELECT * from user where player_password=? and ID1=?";
                           statement = connection.prepareStatement(sqltest3);
                           statement.setString(1, getPassword());
                           statement.setInt(2, getID());
                           resultSet = statement.executeQuery();
                       }while(!resultSet.next());
                       flag=false;
                    }
                } while (flag);

            }
        JDBCTools.release(connection,statement,resultSet);

    }

    public void zhuxiao() throws SQLException {
        System.out.println("请登录要注销的用户ID及密码");
        login();
        System.out.print("请确定是否注销：");
        String choice = null;
        choice = scanner.next();
        switch (choice){
            case "是":
                try {
                    connection = JDBCTools.getConnection();
                    String sql1 = "DELETE from record where game_ID1=?";
                    statement =connection.prepareStatement(sql1);
                    statement.setInt(1,getID());
                    statement.executeUpdate();
                    String sql ="DELETE from user where ID1=?";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1,getID());

                    int i = statement.executeUpdate();

                    if(i>0){
                        System.out.println("注销成功！");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }finally {
                    JDBCTools.release(connection,statement,resultSet);
                }
                break;
            case "否":
                System.out.println("退出注销功能！");
                break;
        }
    }

    public void search() throws SQLException {
        do {
            if (getID() != 0) {
                resultSet=null;

                if(resultSet==null){
                    System.out.print("请输入用户ID:");
                    do {
                        try {
                            ID = scanner.nextInt();
                            setID(ID);
                            connection = JDBCTools.getConnection();
                            String sqltest = "SELECT * from user where ID1=?";
                            statement = connection.prepareStatement(sqltest);
                            statement.setInt(1,getID());
                            resultSet = statement.executeQuery();
                            if(resultSet.next()){
                                flag = false;
                            String sql1 = "SELECT * FROM user INNER JOIN record ON ID1=game_ID1 where ID1=?";
                            statement =connection.prepareStatement(sql1);
                            statement.setInt(1,getID());
                            resultSet=statement.executeQuery();
                            while (resultSet.next()) {
                                int gameId1 = resultSet.getInt("game_ID1");
                                String gameUserID1 = resultSet.getString("game_user1ID");
                                String gameUserID2 = resultSet.getString("game_user2ID");
                                String game_winner_id = resultSet.getString("game_winner_ID");
                                String gameUser1cards = resultSet.getString("game_user1cards");
                                String gameUser2cards = resultSet.getString("game_user2cards");
                                String begintime = resultSet.getString("game_begintime");
                                String game_exittime = resultSet.getString("game_exittime");
                                System.out.println("此用户ID:" + gameId1);
                                System.out.println("对局状况:");
                                System.out.println("玩家一用户名:"+gameUserID1+"  玩家二用户名:"+gameUserID2+"  玩家一手牌:"+gameUser1cards+"  玩家二手牌:"+gameUser2cards+"  赢家:"+game_winner_id+"  游戏开始时间:"+begintime+"  游戏结束时间:"+game_exittime);
                            }
                            }
                            else {
                                do {
                                    System.out.print("此ID不存在,请重新输入:");

                                    ID = scanner.nextInt();
                                    setID(ID);
                                    connection = JDBCTools.getConnection();
                                    String sqltest1 = "SELECT * from user where ID1=?";
                                    statement = connection.prepareStatement(sqltest1);
                                    statement.setInt(1,getID());
                                    resultSet = statement.executeQuery();
                                    if(resultSet.next()){
                                        flag = false;
                                        String sql1 = "SELECT * FROM user INNER JOIN record ON ID1=game_ID1 where ID1=?";
                                        statement =connection.prepareStatement(sql1);
                                        statement.setInt(1,getID());
                                        resultSet=statement.executeQuery();
                                        while (resultSet.next()) {
                                            int gameId1 = resultSet.getInt("game_ID1");
                                            String gameUserID1 = resultSet.getString("game_1userID");
                                            String gameUserID2 = resultSet.getString("game_2userID");
                                            String game_winner_id = resultSet.getString("game_winner_ID");
                                            String gameUser1cards = resultSet.getString("game_user1cards");
                                            String gameUser2cards = resultSet.getString("game_user2cards");
                                            String begintime = resultSet.getString("game_begintime");
                                            String game_exittime = resultSet.getString("game_exittime");
                                            System.out.println("此用户ID:" + gameId1);
                                            System.out.println("对局状况:");
                                            System.out.println("玩家一用户名:"+gameUserID1+"  玩家二用户名:"+gameUserID2+"  玩家一手牌:"+gameUser1cards+"  玩家二手牌:"+gameUser2cards+"  赢家:"+game_winner_id+"  游戏开始时间:"+begintime+"  游戏结束时间:"+game_exittime);
                                        }
                                    }
                                } while(!resultSet.next());
                                flag=false;
                            }
                        }
                        catch (InputMismatchException e) {
                            System.out.print("此ID不存在,请重新输入:");
                            scanner.next();
                        }
                    } while (flag);
                }
            } else {
                System.out.println("请先登录");
                login();
                delay();
             break;
            }
        }while(flag);

    }

    public void create(){
        String[] color = {"方片", "梅花", "红心", "黑桃"};
        String[] number = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        begin_time=df.format(new Date());
        setBegin_time(begin_time);
        System.out.println("-----------------开始创建扑克牌------------------");
        System.out.println("加载中..........");
        delay();
        int a=0,b=0;
        for (String s1 : number) {
            for (String s2 : color) {
                card.add(s2 + s1);
                a++;
                map.put(card.get(b), a);
                b++;
            }
        }

        System.out.println("-----------------创建扑克牌成功------------------");
        System.out.println("游戏正式开始");
        delay();
        System.out.println("-------------------开始洗牌---------------------");
        Collections.shuffle(card);
        for (int j = 0; j < 10; j++) {
            System.out.print(".");
            delay();
        }
        System.out.println();
        System.out.println("-------------------洗牌结束---------------------");

        delay();
        System.out.println("-------------------开始发牌---------------------");
        int i = 0, k = 0;
        for (int j = 1; j <= 4; j++) {
            if (j % 2 != 0) {
                deque1.add(card.get(j-1));
                i++;
            } else {
                deque2.add(card.get(j-1));
                k++;
            }
        }
        delay();
        System.out.println("-------------------发牌结束---------------------");
    }

    public void compare() throws SQLException {
        int max1=0,max2=0;
        String KeyMax="";
        connection = JDBCTools.getConnection();
        String sql1 = "SELECT * FROM user  where ID1=?";
        statement = connection.prepareStatement(sql1);
        String playerUsername=null;
        if(map.get(deque1.getFirst())>=map.get(deque1.getLast())) {
            max1 = map.get(deque1.getFirst());
        }
        else{
            max1 = map.get(deque1.getLast());
        }

        if(map.get(deque2.getFirst())>=map.get(deque2.getLast())){
            max2 = map.get(deque2.getFirst());
        }
        else{
            max2 = map.get(deque2.getLast());
        }
        if(max1>max2){
            for (Map.Entry<String, Integer> entry : map.entrySet()){
                if(max1==entry.getValue())
                    KeyMax=entry.getKey();
            }
            statement.setInt(1, getID());
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                playerUsername = resultSet.getString("player_username");
            }
            setPlayer_winner(playerUsername);
            System.out.print("玩家一胜利！  ");
            System.out.println("最大牌面为"+"["+KeyMax+"]");
        }
        else if(max1==max2){
            for (Map.Entry<String, Integer> entry : map.entrySet()){
                if(max1==entry.getValue())
                    KeyMax=entry.getKey();
            }
            statement.setInt(1, getID());
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                playerUsername = resultSet.getString("player_username");
            }
            setPlayer_winner(playerUsername+"  wang");
            System.out.print("双方平局    ");
            System.out.println("双方最大牌面都为"+"["+KeyMax+"]");
        }
        else{
            for (Map.Entry<String, Integer> entry : map.entrySet()){
                if(max2==entry.getValue())
                    KeyMax=entry.getKey();
            }
            setPlayer_winner("wang");
            System.out.print("玩家二胜利！  ");
            System.out.println("最大牌面为"+"["+KeyMax+"]");
        }
    }

    public void show(){
        System.out.println("玩家一的手牌："+deque1);
        System.out.println("玩家二的手牌："+deque2);
        exit_time=df.format(new Date());
        setExit_time(exit_time);
        delay();
    }

    public void menu(){
        System.out.println("主菜单\n"+
                "1)注册\n" +
                "2)登录\n" +
                "3)退出登录\n" +
                "4)注销\n" +
                "5)查找\n" +
                "6)退出");
    }

    public void menuSon() throws SQLException {
        System.out.println("子菜单\n" +
                "1)开始游戏对局\n" +
                "2)查找用户\n" +
                "3)退出子菜单  ");
        System.out.print("选择你要使用的功能:");
        int choice = 0;
        choice =scanner.nextInt();
        switch (choice){
            case 1:game();
                   break;
            case 2:search();
                   break;
            case 3:break;
        }
    }

    public void delay(){
        long i = 2000000000;
        while (i >= 0)
            i--;
    }

    public void game() throws SQLException {
        System.out.println("请ID为"+getID()+"的玩家"+"和"+"玩家"+"wang做好准备!\n游戏马上开始!");
        delay();
        create();
        compare();
        delay();
        show();
        store();
        delay();
    }

    public void store() throws SQLException {
        connection = JDBCTools.getConnection();
            String sql1 = "SELECT * FROM user  where ID1=?";
            statement = connection.prepareStatement(sql1);
            statement.setInt(1, getID());
            resultSet = statement.executeQuery();



                try {
                    while (resultSet.next()) {
                    String playerUsername = resultSet.getString("player_username");
                    String sql = "INSERT INTO record(game_ID1,game_user1ID,game_user2ID,game_winner_ID,game_user1cards,game_user2cards,game_begintime,game_exittime)" +
                            "VALUE (?,?,?,?,?,?,?,?)";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, getID());
                    statement.setString(2, playerUsername);
                    statement.setString(3, "wang");
                    statement.setString(4,getPlayer_winner() );
                    statement.setString(5, String.valueOf(deque1));
                    statement.setString(6, String.valueOf(deque2));
                    statement.setString(7, getBegin_time());
                    statement.setString(8, getExit_time());
                    statement.executeUpdate();
                    } }catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    JDBCTools.release(connection, statement, resultSet);
                }
            }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getExit_time() {
        return exit_time;
    }

    public void setExit_time(String exit_time) {
        this.exit_time = exit_time;
    }

    public String getPlayer_winner() {
        return player_winner;
    }

    public void setPlayer_winner(String player_winner) {
        this.player_winner = player_winner;
    }
}
