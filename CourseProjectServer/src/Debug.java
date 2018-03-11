import connection.SQLHelper;
import dao.Program;
import utils.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Debug {

    public static void main(String[] args) {
        Logger.initialize();
        Logger.info("Initialization", "Application started and log has been created in \"logs\" folder");

        SQLHelper sqlHelper;
        try {
            sqlHelper = SQLHelper.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlHelper = null;
        }
        ArrayList<Program> programs = new ArrayList<>();

        try {
            ResultSet resultSet = sqlHelper.getDataFor(new Program());

            while (resultSet.next()) {
                programs.add(Program.parse(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Program program : programs) {
            System.out.println(program.toString());
        }
    }
}
