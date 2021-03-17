package task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*Здесь использовано BlockingIO т.к. расчет N-го числа Фибоначи ведется с помощю теоремы Бине.
Это очень быстрый расчет и блокировка не заметна. Если бы нахождение искомого числа было представлено итеративным
или рекурсивным методом, то был бы смысл в использовании неблокирующего IO, т.к. такой поиск занимает
значительно больше времени*/

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(53535);
        while (true) {
            try (Socket socket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    long result = 0;
                    result = fibonacciBine(Integer.parseInt(line));
                    out.println(result);
                    if ("end".equals(line)) break;
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    /*private static long fibonacci(long number) { //Медленно и рекурсивно
        if (number == 0) return 0;
        if (number == 1) return 1;
        return fibonacci(number - 1) + fibonacci(number - 2);
    }*/

    private static long fibonacciBine(long number) { //Супербыстро
        double sqrtFive = Math.sqrt(5);
        double a = (1 + sqrtFive) / 2;
        double b = (1 - sqrtFive) / 2;
        return (long) ((Math.pow(a, number) - Math.pow(b, number)) / sqrtFive);
    }
}
