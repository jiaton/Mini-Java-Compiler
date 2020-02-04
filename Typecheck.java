import syntaxtree.Goal;
import syntaxtree.Node;
import TypeCheck.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Typecheck {
	public static void main(String[] args) {
		try {
			InputStream in = new FileInputStream(args[1]);
			Node goal = new MiniJavaParser(in).Goal();

			goal.accept(new MyVisitor());
			System.out.println("Program type checked successfully");
		} catch (ParseException | FileNotFoundException e) {
			e.printStackTrace();
		}


	}
}
