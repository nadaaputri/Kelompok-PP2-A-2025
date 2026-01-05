package src;

public class test {
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("DRIVER MYSQL TERDETEKSI ADA");
        } catch (ClassNotFoundException e) {
            System.out.println("DRIVER MYSQL TIDAK ADA ");
        }


    }
}
