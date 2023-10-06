public class Main {
    public static Help help = new Help();
    public static Mem mem = new Mem();
    public static Resolution resolution = new Resolution();

    public static void main(String[] args) {
        if (args.length != 0) {
            switch (args[0].toLowerCase()) {
                case "help", "man" -> help.start(args);
                case "mem" -> mem.start(args);
                case "resolution" -> resolution.start(args);
                default -> System.out.println("Неверные аргументы");

            }
        }
        else{
            System.out.println("Аргументы не указаны. Запустите программу с указанием аргументов");
        }
    }
}
