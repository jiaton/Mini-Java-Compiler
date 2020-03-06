package RegisterAllocation;

public class Printer {
    public static int numberOfIndentation = 0;

    public void addIndentation() {
        numberOfIndentation++;
    }

    public void removeIndentation() {
        numberOfIndentation--;
    }

    public static void resetIndentation() {
        Printer.numberOfIndentation = 0;
    }

    public void println(String string) {
        StringBuilder indentation = new StringBuilder();
        for (int i = 0; i < this.numberOfIndentation; i++) {
            indentation.append("\t");
        }
        System.out.println(indentation + string);
    }

    public void print(String string) {
        System.out.print(string);
    }

    public void println(String string, int numberOfIndentation) {
        StringBuilder indentation = new StringBuilder();
        for (int i = 0; i < numberOfIndentation; i++) {
            indentation.append("\t");
        }
        System.out.println(indentation + string);
    }

    public void println() {
        System.out.println();
    }
}
