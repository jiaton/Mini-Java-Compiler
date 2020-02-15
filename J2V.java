import syntaxtree.Goal;
import syntaxtree.Node;
import TypeCheck.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class J2V {
	public static void main(String[] args) throws FileNotFoundException {
//		try {
//			Node goal = new MiniJavaParser(System.in).Goal();
//
//			goal.accept(new MyVisitor());
//			System.out.println("Program type checked successfully");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		//InputStream in = null;
		//in = new FileInputStream("prog.g");
		try {

			Node goal = new MiniJavaParser(System.in).Goal();

			goal.accept(new transVisitor());
			//System.out.println("Program type checked successfully");
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}
