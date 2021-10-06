import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        int magicNumber = 10;
        System.out.println(input < magicNumber && input > 0);

        boolean b = 10 % 2 == 0 ? false : true;
        System.out.println(b);
    }

}
