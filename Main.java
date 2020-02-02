import syntaxtree.Goal;
import syntaxtree.Node;
import TypeCheck.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Main {
	public static void main(String[] args) {
		try {
			InputStream in = new FileInputStream(args[1]);
			Node goal = new MiniJavaParser(in).Goal();
			System.out.println("visit starting");
			goal.accept(new MyVisitor());
		} catch (ParseException | FileNotFoundException e) {
			e.printStackTrace();
		}


	}
}
