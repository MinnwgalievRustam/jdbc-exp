package run;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/skillbox?useSSL=false&serverTimezone=UTC";
        String user = "bestuser";
        String pass = "bestuser";

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT course_name, COUNT(subscription_date) AS 'Количество проданных курсов',\n" +
                    "TIMESTAMPDIFF(MONTH,MIN(subscription_date),MAX(subscription_date)) + 1 AS 'Счетчик месяцев',\n" +
                    "round(count(subscription_date)/(TIMESTAMPDIFF(MONTH, MIN(subscription_date), MAX(subscription_date)) + 1), 2) AS 'Среднее количество продаж'\n" +
                    "FROM purchaselist GROUP BY 1");
            while (resultSet.next()){
                String countCourse = resultSet.getString("Среднее количество продаж");
                String courseName = resultSet.getString("course_name");
                System.out.println(courseName + " --> " + countCourse);
            }
            resultSet.close();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
