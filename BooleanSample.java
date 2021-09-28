public class BooleanSample {
    public static void main(String[] args) {
        
        if (!hasPermission(123)) {
            throw new RuntimeException();
        }

        if (!hasPermission(456)) {
            throw new RuntimeException();
        }

        if (!hasPermission(789)) {
            throw new RuntimeException();
        }

    }


    public static boolean hasPermission(int i) {

        return i % 2;
    }
}