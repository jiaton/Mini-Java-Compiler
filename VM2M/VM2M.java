package VM2M;

import cs132.util.ProblemException;
import cs132.util.SourcePos;
import cs132.vapor.ast.*;
import cs132.vapor.parser.VaporParser;
import cs132.vapor.ast.VBuiltIn.Op;
import cs132.vapor.ast.VOperand.Static;

import java.io.*;
import java.util.*;


public class VM2M {
    static String NoColon(String str) {
        String r = "";
        if (str.charAt(0) == ':') {

            for (int i = 1; i < str.length(); i++) {
                r = r + str.charAt(i);
            }
            return r;
        }
        return str;
    }

    static String AddReturn(String str) {
        String r = "";
        for (int i = 0; i < str.length(); i++) {

            r = r + str.charAt(i);
            if (i == str.length() - 2) r = r + "\\" + "n";
        }
        return r;
    }

    static Printer printer = new Printer();

    public static VaporProgram parseVapor(InputStream in, PrintStream err) throws IOException {
        Op[] ops = {
                Op.Add, Op.Sub, Op.MulS, Op.Eq, Op.Lt, Op.LtS,
                Op.PrintIntS, Op.HeapAllocZ, Op.Error,
        };

        boolean allowLocals = false;
        String[] registers = {
                "v0", "v1",
                "a0", "a1", "a2", "a3",
                "t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7",
                "s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7",
                "t8",
        };
        boolean allowStack = true;

        VaporProgram tree;
        try {
            tree = VaporParser.run(new InputStreamReader(in), 1, 1,
                    Arrays.asList(ops),
                    allowLocals, registers, allowStack);
        } catch (ProblemException ex) {
            err.println(ex.getMessage());
            return null;
        }

        return tree;
    }

    public static void PrintFuncStart(VFunction.Stack stack) {
        printer.println("sw $fp -8($sp)");
        printer.println("move $fp $sp");
        printer.println("subu $sp $sp " + (stack.local + stack.out + 2) * 4);
        printer.println("sw $ra -4($fp)");
    }

    public static void PrintFuncEnd(VFunction.Stack stack) {
        printer.println("lw $ra -4($fp)");
        printer.println("lw $fp -8($fp)");
        printer.println("addu $sp $sp " + (stack.local + stack.out + 2) * 4);
        printer.println("jr $ra");
    }

    public static void PrintAlloc() {
        printer.println("_heapAlloc:");
        printer.println("li $v0 9   # syscall: sbrk");
        printer.println("syscall");
        printer.println("jr $ra");
        printer.println("");
    }

    public static void PrintError() {
        printer.println("_error:");
        printer.println("li $v0 4   # syscall: print string");
        printer.println("syscall");
        printer.println("li $v0 10  # syscall: exit");
        printer.println("syscall");
        printer.println("");
    }

    public static void PrintPrintIntS() {
        printer.println("_print:");
        printer.println("li $v0 1   # syscall: print integer");
        printer.println("syscall");
        printer.println("la $a0 _newline");
        printer.println("li $v0 4   # syscall: print string");
        printer.println("syscall");
        printer.println("jr $ra");
        printer.println();
    }

    public static void main(String[] args) throws IOException {
        LinkedHashMap<SourcePos, String> labelMap = new LinkedHashMap<>();
        PrintStream err = new PrintStream(System.out);
        VaporProgram tree; //= parseVapor(System.in,err);
        //FileInputStream in = new FileInputStream(new File("VM2M/VM2M/test.vaporm"));
        tree = parseVapor(System.in, err);
        VM2MPrinter visitor = new VM2MPrinter();
        visitor.printer = printer;
        printer.println(".data");
        printer.println();
        printer.addIndentation();
        for (VDataSegment data : tree.dataSegments) {
            printer.removeIndentation();
            printer.println(data.ident + ":");
            printer.addIndentation();
            for (Static s : data.values) {
                printer.println(NoColon(s.toString()));
            }
        }
        printer.removeIndentation();
        printer.println();
        printer.println(".text");
        printer.addIndentation();
        printer.println();
        printer.println("jal Main");
        printer.println("li $v0 10");
        printer.println("syscall");
        printer.println();
        printer.removeIndentation();
        for (VFunction function : tree.functions) {
            printer.println(function.ident + ":");
            printer.addIndentation();
            for (VCodeLabel label : function.labels) {
                labelMap.put(label.sourcePos, label.ident);
            }
            visitor.labelMap = labelMap;
            VM2M.PrintFuncStart(function.stack);
            for (VInstr Instr : function.body) {
                Instr.accept(null, visitor);
            }
            labelMap = visitor.labelMap;
            for (Map.Entry<SourcePos, String> entry : labelMap.entrySet()) {
                printer.println(entry.getValue() + ":");
            }
            PrintFuncEnd(function.stack);
            printer.removeIndentation();
            printer.println();
        }
        PrintAlloc();
        PrintError();
        PrintPrintIntS();
        printer.println("");
        printer.println(".data");
        printer.println(".align 0");
        printer.println("_newline: .asciiz \"\\n\"");
        for (Map.Entry<Integer, String> entry : (Set<Map.Entry<Integer, String>>) visitor.strMap.entrySet()) {
            printer.println("_str" + entry.getKey() + ": .asciiz " + AddReturn(NoColon(entry.getValue())));
        }
    }
}
