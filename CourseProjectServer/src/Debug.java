import connection.SQLHelper;
import utils.Logger;

public class Debug {

    public static void main(String[] args) {
        Logger.initialize();
        Logger.logInfo("Initialization", "Application started and log has been created in \"logs\" folder");

        Server.start();

        SQLHelper sqlHelper = null;
//        try {
//            sqlHelper = SQLHelper.getInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ArrayList<Program> programs = new ArrayList<>();
//
//        try {
//            ResultSet resultSet = sqlHelper.getDataFor(Program.class);
//
//            while (resultSet.next()) {
//                programs.add(Program.parse(resultSet));
//            }
//        } catch (SQLException | IllegalAccessException | InstantiationException e) {
//            e.printStackTrace();
//        }
//
//        for (Program program : programs) {
//            System.out.println(program.toString());
//        }

    }
}
