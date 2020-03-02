import cs132.util.ProblemException;
import cs132.vapor.ast.VBuiltIn.Op;
import cs132.vapor.ast.VaporProgram;
import cs132.vapor.parser.VaporParser;

import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.System.err;

public class V2VM {


	public static void main(String[] args) {
		Op[] ops = {
				Op.Add, Op.Sub, Op.MulS, Op.Eq, Op.Lt, Op.LtS,
				Op.PrintIntS, Op.HeapAllocZ, Op.Error,
		};
		boolean allowLocals = true;
		String[] registers = null;
		boolean allowStack = false;

		VaporProgram tree = null;
		try {
			tree = VaporParser.run(new InputStreamReader(System.in), 1, 1,
					java.util.Arrays.asList(ops),
					allowLocals, registers, allowStack);
		} catch (ProblemException | IOException ex) {
			err.println(ex.getMessage());
		}

		if (tree != null) {

		}
	}

}
