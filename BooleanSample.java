public class BooleanSample {
    public static void main(String[] args) {
        
        hasPermission(123);

        hasPermission(456);

        hasPermission(789);
    }


    public static boolean hasPermission(int i) {
        if (i % 2 == 0) {
            throw new RuntimeException();
        }
    }
}