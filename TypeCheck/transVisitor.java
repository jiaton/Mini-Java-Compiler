package TypeCheck;

import syntaxtree.*;
import visitor.GJNoArguDepthFirst;

import java.awt.*;
import java.awt.dnd.DropTarget;
import java.util.*;

import static java.lang.System.exit;
import static java.lang.System.setOut;


public class transVisitor extends GJNoArguDepthFirst<MyType> {
	public HashMap<String, HashMap<Integer, String>> listTable = new HashMap<>();
	public HashMap<String, Var> varTable = new HashMap<>();
	public HashMap<String, Env> envTable = new HashMap<>();
	public HashMap<String, Integer> classSize = new HashMap<>();
	public Stack<Env> envStack = new Stack<Env>();
	public HashMap<String, String> typeTable = new HashMap<>();
	public ArrayList<MyType> parameterList = new ArrayList<>();
	public int iflabeloffset = 0;
	public int whilelabeloffset = 0;
	public int intvaroffset = 0;
	public int booleanvaroffset = 0;
	public int arrayvaroffset = 0;
	public int classvaroffset = 0;
	public int allocationNullOffset = 1;
	public int arrayLookupOffset = 1;
	public int MAXCLASSNUM = 1024;
	public static String ROOT = "root is absolutely no duplication";
	public Printer printer = new Printer();

	public void initialize() {
		typeTable.put("int", null);
		typeTable.put("boolean", null);
		typeTable.put("int[]", null);
		envStack.push(new Env(ROOT, false, null));
	}

	public void setupClassTable(TypeDeclaration n) {
		if (n.f0.which == 0) {
			setupClassNoExtendsTable((ClassDeclaration) n.f0.choice);
		} else {
			setSuperClass((ClassExtendsDeclaration) n.f0.choice);
			setupClassExtendsTable((ClassExtendsDeclaration) n.f0.choice);
		}
	}

	public void setSuperClass(ClassExtendsDeclaration n) {
		String subclass = n.f1.f0.tokenImage;
		Env env = envTable.get(subclass);
		env.superClass = n.f3.f0.tokenImage;
	}

	public void setupClassNoExtendsTable(ClassDeclaration n) {
		Vector<Node> vdnodes = n.f3.nodes;
		Vector<Node> mdnodes = n.f4.nodes;
		LinkedHashMap<String, Integer> record = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> vtable = new LinkedHashMap<String, Integer>();
		Env env = envTable.get(n.f1.f0.tokenImage);
		env.record = record;
		env.vtable = vtable;
		int recordoffset = 0;
		int vtableoffset = 0;
		for (Node node : vdnodes) {
			setField((VarDeclaration) node, recordoffset, env);
			recordoffset++;
		}
		for (Node node : mdnodes) {
			setMethod((MethodDeclaration) node, vtableoffset, env);
			vtableoffset++;
		}
		classSize.put(n.f1.f0.tokenImage, recordoffset);
	}

	public void setupClassExtendsTable(ClassExtendsDeclaration n) {
		Vector<Node> vdnodes = n.f5.nodes;
		Vector<Node> mdnodes = n.f6.nodes;
		LinkedHashMap<String, Integer> record = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> vtable = new LinkedHashMap<String, Integer>();
		Env env = envTable.get(n.f1.f0.tokenImage);
		env.record = record;
		env.vtable = vtable;
		int recordoffset = 0;
		int vtableoffset = 0;
		recordoffset = setBasicField(n, env) + 1;
		vtableoffset = setBasicMethod(n, env) + 1;
		for (Node node : vdnodes) {
			recordoffset = setExtendsField((VarDeclaration) node, recordoffset, env);

		}
		for (Node node : mdnodes) {
			vtableoffset = setExtendsMethod((MethodDeclaration) node, vtableoffset, env);

		}
		classSize.put(n.f1.f0.tokenImage, recordoffset);
	}

	public void setField(VarDeclaration n, int offset, Env env) {
		String name = n.f1.f0.tokenImage;
		env.record.put(name, offset);
	}

	public void setMethod(MethodDeclaration n, int offset, Env env) {
		String name = env.id + "." + n.f2.f0.tokenImage;
		env.vtable.put(name, offset);
	}

	public int setExtendsField(VarDeclaration n, int offset, Env env) {
		String name = n.f1.f0.tokenImage;
		if (!env.record.containsKey(name)) {
			env.record.put(name, offset);
			return ++offset;
		} else {
			return offset;
		}
	}

	public int setExtendsMethod(MethodDeclaration n, int offset, Env env) {
		String name = env.id + "." + n.f2.f0.tokenImage;
		if (env.vtable.containsKey(name)) {
			env.vtableDelete(name);
			env.vtable.put(name, offset);
			return ++offset;
		} else {
			env.vtable.put(name, offset);
			return ++offset;
		}
	}

	public int setBasicField(ClassExtendsDeclaration n, Env subEnv) {
		String subclass = subEnv.id;
		String superclass = subEnv.superClass;
		Env superEnv = envTable.get(superclass);
		int max = 0;
		for (Map.Entry<String, Integer> entry : superEnv.record.entrySet()) {
			subEnv.record.put(entry.getKey(), entry.getValue());
			if (entry.getValue() > max) {
				max = entry.getValue();
			}
		}
		return max;
	}

	public int setBasicMethod(ClassExtendsDeclaration n, Env subEnv) {
		String subclass = subEnv.id;
		String superclass = subEnv.superClass;
		Env superEnv = envTable.get(superclass);
		int max = 0;
		for (Map.Entry<String, Integer> entry : superEnv.vtable.entrySet()) {
			subEnv.vtable.put(entry.getKey(), entry.getValue());
			if (entry.getValue() > max) {
				max = entry.getValue();
			}
		}
		return max;
	}

	public Vector<TypeDeclaration> GetClassNodes(Goal n) {
		Vector<TypeDeclaration> nodes = new Vector<>();
		for (Node node : n.f1.nodes) {
			nodes.add((TypeDeclaration) node);
		}
		return nodes;
	}

	public MyType checkIdentifier(Identifier n, Env env) {
		if (env.varTable.containsKey(n.f0.tokenImage)) {
			printer.println("When checking identifier, the identifier is token");
			exit(-1);
		}
		return env.varTable.get(n.f0.tokenImage);
	}

	public MyType checkIdentifier(Identifier n) {

		//check whether the identifier n is in the env
//		printer.println();("Checking Identifier " + n.f0.toString());
		MyType _ret = null;
		if (envStack.empty()) return null;
		Env A = envStack.pop();
		if (A.isMethod) {
			if (envStack.empty()) {
				printer.println("The Stack is empty at a method!");
				exit(-1);
			}
			Env C = envStack.peek();
			if (C.getVariableType(n) != null) {
				envStack.push(A);
				_ret = C.getVariableType(n);
				return _ret;
			}
		}
		envStack.push(A);
		MyType variabletype = null;
		variabletype = A.getVariableType(n);
		if (variabletype == null) return null;
		_ret = A.getVariableType(n);
		return _ret;
	}

	public void SetMethodType(MethodDeclaration n, Env classOfMethod) {
		//DistinctParameterList((FormalParameterList) n.f4.node);
		MethodType methodtype = new MethodType(MyType.toMyType(n.f1));
		methodtype.returnValue = MyType.toMyType(n.f1);

		methodtype.setParameterList((FormalParameterList) n.f4.node);

		classOfMethod.methodTable.put(classOfMethod.id + "." + n.f2.f0.tokenImage, methodtype);
	}

	public void DistinctParameterList(FormalParameterList n) {
		if (n == null) return;
		Vector<Node> nodes = n.f1.nodes;
		HashSet<String> idset = new HashSet<>();
		for (Node node : nodes) {
			if (!typeTable.containsKey(MyType.toMyType(((FormalParameterRest) node).f1.f0).toString())) {
				printer.println("At the FormalParameter, the type " + MyType.toMyType(((FormalParameterRest) node).f1.f0).toString() + " is not avaliable!");
				exit(-1);
			}
			if (idset.contains(((FormalParameterRest) node).f1.f1.f0.tokenImage)) {
				printer.println("The parameters are not distincted!");
				exit(-1);
			}
			idset.add(((FormalParameterRest) node).f1.f1.f0.tokenImage);
		}
		if (!typeTable.containsKey(MyType.toMyType(n.f0.f0).toString())) {
			printer.println("At the FormalParameter, the type " + MyType.toMyType(n.f0.f0).toString() + " is not avaliable!");
			exit(-1);
		}
		if (idset.contains(n.f0.f1.f0.tokenImage)) {
			printer.println("The parameters are not distincted!");
			exit(-1);
		}

	}

	public void SetMethodList(NodeListOptional n, Env classOfMethod) {
		Vector<Node> nodes = n.nodes;
		Iterator<Node> iterator = nodes.iterator();
		while (iterator.hasNext()) {
			Node next = iterator.next();                    // TODO: 2/1/2020 check iterator.next
			SetMethodType((MethodDeclaration) next, classOfMethod);
		}
	}

	public void addVar(Type f0, Identifier f1, Env env) {
		env.addVar(f0, f1);
	}

	public void fieldExtends(String id, String idM) {            //put parent class fields to child
		Env childclass = envTable.get(id);
		Env parentclass = envTable.get(idM);
		for (HashMap.Entry<String, MyType> entry : parentclass.varTable.entrySet()) {
			if (childclass.varTable.containsKey(entry.getKey())) {
				printer.println("When extending the class, has the same field");
				exit(-1);        //Env.has(string(id))
			}
//			childclass.varTable.entrySet().add(entry);
			childclass.varTable.put(entry.getKey(), entry.getValue());
		}
	}

	public void MethodExtends(String id, String idM) {                    //put parent class methods to child
		Env childclass = envTable.get(id);
		Env parentclass = envTable.get(idM);
		for (HashMap.Entry<String, MethodType> entry : parentclass.methodTable.entrySet()) {
			if (childclass.methodTable.containsKey(entry.getKey())) {
				if (!(childclass.methodTable.get(entry.getKey()).equals(entry.getValue()))) {                // TODO: 1/27/2020 methodtype == methodtype
					printer.println("When extending the class, method overloading!");
					exit(-1);        //Env.has(string(id))
				}
			} else
				childclass.methodTable.put(entry.getKey(), entry.getValue());
//				childclass.methodTable.entrySet().add(entry);
		}
	}

	public void SetFields(NodeListOptional n, Env classOfFields) {
		Vector<Node> nodes = n.nodes;
		Iterator<Node> iterator = nodes.iterator();
		while (iterator.hasNext()) {
			Node next = iterator.next();                    // TODO: 2/1/2020 check iterator.next
			SetField((VarDeclaration) next, classOfFields);
		}
	}

	/**
	 * f0 -> Type()
	 * f1 -> Identifier()
	 * f2 -> ";"
	 */
	public void SetField(VarDeclaration n, Env classOfFields) {
//        if (!typeTable.containsKey(MyType.toMyType(n.f0).toString())) {
//            printer.println();("At the VarDeclaration, the type is not avaliable!");
//            exit(-1);
//        }
		MyType IdentifierType = checkIdentifier(n.f1, classOfFields);
		if (IdentifierType != null) {
			printer.println("At the VarDeclaration, the id is token!");
			exit(-1);
		}
		addVar(n.f0, n.f1, classOfFields);
	}

	public void setClassList(Vector<TypeDeclaration> sortedNodes) {
		/*Set environments for each node in the order of sortedNodes*/
		for (Node node : sortedNodes) {
			TypeDeclaration next = (TypeDeclaration) node;
			if (next.f0.which == 0) {
				ClassDeclaration n = (ClassDeclaration) next.f0.choice;
				if (typeTable.containsKey(n.f1.f0.toString())) {
					printer.println("At setClassList, the id is already token:" + n.f1.f0.toString());
					exit(-1);
				}
				typeTable.put(n.f1.f0.toString(), null);
				Env env = new Env(n.f1.f0.toString(), false, null);    //Env(id, isMethod)
				envTable.put(env.id, env);
			} else {
				ClassExtendsDeclaration n = (ClassExtendsDeclaration) next.f0.choice;
				if (typeTable.containsKey(n.f1.f0.toString())) {
					printer.println("At classdeclaration (extends), the id is already token!");
					exit(-1);
				}
				typeTable.put(n.f1.f0.toString(), n.f3.f0.toString());                    // TODO: 1/27/2020 check mytype
				Env env = new Env(n.f1.f0.toString(), false, n.f3.f0.toString());    //Env(id, idM)
				envTable.put(env.id, env);
			}
		}
		for (Node node : sortedNodes) {
			TypeDeclaration next = (TypeDeclaration) node;
			if (next.f0.which == 0) {
				//classdeclaration
				ClassDeclaration n = (ClassDeclaration) next.f0.choice;
				Env env = envTable.get(n.f1.f0.tokenImage);
				SetFields(n.f3, env);
				SetMethodList(n.f4, env);
			}
		}
		for (Node node : sortedNodes) {
			TypeDeclaration next = (TypeDeclaration) node;
			if (next.f0.which != 0) {
				//extends
				ClassExtendsDeclaration n = (ClassExtendsDeclaration) next.f0.choice;
				Env env = envTable.get(n.f1.f0.tokenImage);
				SetFields(n.f5, env);
				SetMethodList(n.f6, env);
				fieldExtends(n.f1.f0.toString(), n.f3.f0.toString());                    //VarExtends(id, idM)
				MethodExtends(n.f1.f0.toString(), n.f3.f0.toString());                //MethodExtends(n.f1.f0.toString(), n.f3.f0.toString());                //MethodExtends(id, idM)

			}
		}
	}

	public Vector<TypeDeclaration> sortNodes(Vector<TypeDeclaration> nodes) {
		LinkedHashMap<String, String> classTable = new LinkedHashMap<>();
//		Construct the classTable
		for (Node node : nodes) {
			TypeDeclaration next = (TypeDeclaration) node;
			if (next.f0.which == 0) {
				ClassDeclaration n = (ClassDeclaration) next.f0.choice;
				classTable.put(n.f1.f0.toString(), null);

			} else {
				ClassExtendsDeclaration n = (ClassExtendsDeclaration) next.f0.choice;
				classTable.put(n.f1.f0.toString(), n.f3.f0.toString());
			}
		}
		/*sort the class topologically*/
		LinkedHashMap<String, String> sortedClassTable = new LinkedHashMap<>();
		while (sortedClassTable.size() != classTable.size()) {
			for (Map.Entry<String, String> entry : classTable.entrySet()) {
				if (entry.getValue() == null || sortedClassTable.containsKey(entry.getValue())) {
					sortedClassTable.put(entry.getKey(), entry.getValue());
				}
			}
		}
		/*get the right order of nodes using the sortedClassTable*/
		Vector<TypeDeclaration> sortedNodes = new Vector<>();
		for (Map.Entry<String, String> entry : sortedClassTable.entrySet()) {
			for (Node node : nodes) {
				TypeDeclaration next = (TypeDeclaration) node;
				String className;
				if (next.f0.which == 0) {
					ClassDeclaration n = (ClassDeclaration) next.f0.choice;
					className = n.f1.f0.toString();
				} else {
					ClassExtendsDeclaration n = (ClassExtendsDeclaration) next.f0.choice;
					className = n.f1.f0.toString();
				}
				if (className.equals(entry.getKey())) {
					sortedNodes.add((TypeDeclaration) node);
					break;
				}

			}
		}
		return sortedNodes;
	}

	public void setClassId(TypeDeclaration next) {
		if (next.f0.which == 0) {
			ClassDeclaration n = (ClassDeclaration) next.f0.choice;
			if (typeTable.containsKey(n.f1.f0.toString())) {
				printer.println("At setClassList, the id is already token!");
				exit(-1);
			}
			typeTable.put(n.f1.f0.toString(), null);
			Env env = new Env(n.f1.f0.toString(), false, null);    //Env(id, isMethod)
			envTable.put(env.id, env);
		} else {
			ClassExtendsDeclaration n = (ClassExtendsDeclaration) next.f0.choice;
			if (typeTable.containsKey(n.f1.f0.toString())) {
				printer.println("At classdeclaration (extends), the id is already token!");
				exit(-1);
			}
			typeTable.put(n.f1.f0.toString(), n.f3.f0.toString());                    // TODO: 1/27/2020 check mytype
			Env env = new Env(n.f1.f0.toString(), false, n.f3.f0.toString());    //Env(id, idM)
			envTable.put(env.id, env);
		}
	}

	public void printvTable() {
		for (Map.Entry<String, String> entry : typeTable.entrySet()) {
			Env env = envTable.get(entry.getKey());
			env.printvTable();
		}
	}

	public String getOriginName(String method, String clazz) {
		char[] clist = method.toCharArray();
		char[] result = new char[method.length()];
		int where = 0;
		boolean gotit = false;
		for (char c : clist) {
			if (!gotit) {
				if (c == '.') {
					gotit = true;
				}
			} else {
				result[where] = c;
				where++;
			}
		}
		method = String.valueOf(result);
		return method;
	}

	public void printAssignmentStatement(String id, String vid) {
		Var var = varTable.get(id);
//		printer.println(var.jid + "=HeapAllocZ(4)");
		printer.println(var.vid + " = " + vid);             // TODO: 2/12/2020 check v or j
	}

	/**
	 * f0 -> MainClass()
	 * f1 -> ( TypeDeclaration() )*
	 * f2 -> <EOF>
	 */
	public MyType visit(Goal n) {
		MyType _ret = null;
		Vector<TypeDeclaration> nodes = GetClassNodes(n);
		Vector<TypeDeclaration> sortedNodes = sortNodes(nodes);
		setClassList(sortedNodes);
		for (TypeDeclaration node : sortedNodes) {
			setupClassTable(node);
		}
		printvTable();
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "class"
	 * f1 -> Identifier()
	 * f2 -> "{"
	 * f3 -> "public"
	 * f4 -> "static"
	 * f5 -> "void"
	 * f6 -> "main"
	 * f7 -> "("
	 * f8 -> "String"
	 * f9 -> "["
	 * f10 -> "]"
	 * f11 -> Identifier()
	 * f12 -> ")"
	 * f13 -> "{"
	 * f14 -> ( VarDeclaration() )*
	 * f15 -> ( Statement() )*
	 * f16 -> "}"
	 * f17 -> "}"
	 *
	 * @param n
	 */
	@Override
	public MyType visit(MainClass n) {
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		String className = n.f1.f0.toString();
		Env env = new Env(className, false, null);
		envTable.put(env.id, env);
		envStack.push(env);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		n.f6.accept(this);
		n.f7.accept(this);
		n.f8.accept(this);
		n.f9.accept(this);
		n.f10.accept(this);
		n.f11.accept(this);
		n.f12.accept(this);
		n.f13.accept(this);
		Env envMainMethod = new Env("main", true, className, new MethodType(new MyType("void"))); // TODO: 2/8/2020 Don't care about the method type for now
		envTable.put(envMainMethod.id, envMainMethod);
		envStack.push(envMainMethod);
		printer.println("\nfunc Main()");
		n.f14.accept(this);
		n.f15.accept(this);
		n.f16.accept(this);
		envStack.pop();
		n.f17.accept(this);
		envStack.pop();
		printer.println("ret");
		return null;
	}

	/**
	 * f0 -> ClassDeclaration()
	 * | ClassExtendsDeclaration()
	 */
	public MyType visit(TypeDeclaration n) {
		MyType _ret = null;
		n.f0.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "class"
	 * f1 -> Identifier()
	 * f2 -> "{"
	 * f3 -> ( VarDeclaration() )*
	 * f4 -> ( MethodDeclaration() )*
	 * f5 -> "}"
	 */
	public MyType visit(ClassDeclaration n) {
		MyType _ret = null;
		Env env = envTable.get(n.f1.f0.tokenImage);
		envStack.push(env);
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		envStack.pop();
		return _ret;
	}

	/**
	 * f0 -> "class"
	 * f1 -> Identifier()
	 * f2 -> "extends"
	 * f3 -> Identifier()
	 * f4 -> "{"
	 * f5 -> ( VarDeclaration() )*
	 * f6 -> ( MethodDeclaration() )*
	 * f7 -> "}"
	 */
	public MyType visit(ClassExtendsDeclaration n) {
		MyType _ret = null;
		Env env = envTable.get(n.f1.f0.tokenImage);
		envStack.push(env);
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		n.f6.accept(this);
		n.f7.accept(this);
		envStack.pop();
		return _ret;
	}

	/**
	 * f0 -> "public"
	 * f1 -> Type()
	 * f2 -> Identifier()
	 * f3 -> "("
	 * f4 -> ( FormalParameterList() )?
	 * f5 -> ")"
	 * f6 -> "{"
	 * f7 -> ( VarDeclaration() )*
	 * f8 -> ( Statement() )*
	 * f9 -> "return"
	 * f10 -> Expression()
	 * f11 -> ";"
	 * f12 -> "}"
	 */
	public MyType visit(MethodDeclaration n) {
		MyType _ret = null;
		for (Map.Entry<String, Var> entry : varTable.entrySet()) {
			entry.getValue().isField = true;
		}
		Env classenv = envStack.peek();
		String methodid = classenv.id + "." + n.f2.f0.toString();
		MethodType methodtype = classenv.methodTable.get(methodid);
		Env env = new Env(methodid, true, envStack.peek().id, methodtype);        //Env(id, isMethod, classofmethod(or superclass))
		//printer.println();(envStack.peek().id);
		envTable.put(env.id, env);
		envStack.push(env);
		printer.addIndentation();
		printer.print("\nfunc ");
		printer.print(env.id);
		printer.println(" " + env.methodTable.get(env.id).printInVapor());
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		n.f6.accept(this);
		n.f7.accept(this);
		n.f8.accept(this);
		n.f9.accept(this);
		n.f10.accept(this);
		n.f11.accept(this);
		n.f12.accept(this);
		printer.println("ret ");
		envStack.pop();
		printer.removeIndentation();
		printer.println();
		ArrayList<String> notFieldKeys = new ArrayList<>();
		for (Map.Entry<String, Var> entry : varTable.entrySet()) {
			if (entry.getValue().isField == false) {
				notFieldKeys.add(entry.getKey());
			}
		}
		for (String key : notFieldKeys) {
			varTable.remove(key);
		}

		return _ret;
	}

	/**
	 * f0 -> Type()
	 * f1 -> Identifier()
	 */
	public MyType visit(FormalParameter n) {
		MyType _ret=null;
		n.f0.accept(this);
		n.f1.accept(this);
		MyType type = MyType.toMyType(n.f0);
		String newvid;
		switch(n.f0.f0.which){
			case 0:
				newvid = "array"+arrayvaroffset++;    // TODO: 2/12/2020 check other places
				break;
			case 1:
				newvid = "boolean"+booleanvaroffset++;
				break;
			case 2:
				newvid = "int"+intvaroffset++;
				break;
			case 3:
				newvid = "class"+classvaroffset++;
				break;
			default:
				newvid = "defaultatVarDeclaration";
		}
		Var var = new Var(n.f1.f0.tokenImage, newvid, type.f0, 0, envStack.peek().id);
		varTable.put(n.f1.f0.tokenImage,var);
		return _ret;
	}

	/**
	 * f0 -> Identifier()
	 * f1 -> "="
	 * f2 -> Expression()
	 * f3 -> ";"
	 */
	public MyType visit(AssignmentStatement n) {
		MyType _ret = null;
		Env env = envStack.peek();
		String id = n.f0.f0.tokenImage;
		MyType mt = n.f2.accept(this);
		if (mt.toString().equals("int[]")) {
			HashMap<Integer, String> list = new HashMap<>();
			int arraySize = mt.value;
			for (int i = 0; i < arraySize; i++) {
				list.put(i, "array" + id + i);
			}
			listTable.put(id, list);
		}
//		String newvid = mt.toString() + Var.intoffset++;
//		Var var = new Var(id, newvid, mt.f0, mt.value, env.id);
//		varTable.put(id, var);
		Var var = varTable.get(id);
		var.value = mt.value;
		printAssignmentStatement(id, mt.vid);
		_ret = new MyType(var.vid, mt.f0, mt.value);
		return _ret;
	}

	/**
	 * f0 -> Block()
	 * | AssignmentStatement()
	 * | ArrayAssignmentStatement()
	 * | IfStatement()
	 * | WhileStatement()
	 * | PrintStatement()
	 *
	 * @param n
	 */
	@Override
	public MyType visit(Statement n) {
		return n.f0.accept(this);
	}

	@Override
	public MyType visit(NodeListOptional n) {
		if (n.present()) {
			MyType _ret = null;
			int _count = 0;
			for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
				MyType tmp = e.nextElement().accept(this);
				if (tmp != null) {
					_ret = tmp;
				}
				_count++;
			}
			return _ret;
		} else
			return null;
	}

	/**
	 * f0 -> Identifier()
	 * f1 -> "["
	 * f2 -> Expression()
	 * f3 -> "]"
	 * f4 -> "="
	 * f5 -> Expression()
	 * f6 -> ";"
	 */
	public MyType visit(ArrayAssignmentStatement n) {
		MyType _ret = null;
		MyType identifier = n.f0.accept(this);
		MyType e1 = n.f2.accept(this);
		MyType e2 = n.f5.accept(this);
		String baseAddressVid = varTable.get(identifier.getIdentifierName()).vid;
		int index = e1.value;
		String newVal = e2.vid;
		String tmpVaporName = "classvar." + classvaroffset++;
		printer.println("s = [" +
				baseAddressVid +
				"]");
		printer.println("ok = LtS(" +
				index +
				" " +
				"s" +
				")");
		printer.println("if ok goto:" + arrayLookupOffset);
		printer.println("Error(\"Array index out of bounds\")");
		printer.println(arrayLookupOffset++ + ": ok = Lts(" +
				"-1 " +
				" " +
				index +
				")");
		printer.addIndentation();
		printer.println("if ok goto :" + arrayLookupOffset);
		printer.println("Error(\"Array index out of bounds\")");
		printer.removeIndentation();
		printer.println(arrayLookupOffset++ + ": o = MulS(" +
				index + " 4)");
		printer.addIndentation();
		printer.println("d = Add(" +
				baseAddressVid + " o)");
		printer.println("classvar." + classvaroffset + " = d + 4");
		printer.println("[classvar." + classvaroffset++ + "] = " + newVal);

		printer.removeIndentation();

		return _ret;
	}

	/**
	 * f0 -> "if"
	 * f1 -> "("
	 * f2 -> Expression()
	 * f3 -> ")"
	 * f4 -> Statement()
	 * f5 -> "else"
	 * f6 -> Statement()
	 */
	public MyType visit(IfStatement n) {
		MyType _ret = null;
		MyType e = n.f2.accept(this);
		String labels1 = "truebranch" + iflabeloffset;
		String labels2 = "end_if" + iflabeloffset++;
		printer.println("if " + e.vid + " goto :" + labels1);
		printer.addIndentation();
		n.f6.accept(this);
		printer.println("goto :" + labels2);
		printer.removeIndentation();
		printer.println(labels1 + ":");
		printer.addIndentation();
		n.f4.accept(this);
		printer.removeIndentation();
		printer.println(labels2 + ":");
		return _ret;
	}

	/**
	 * f0 -> "while"
	 * f1 -> "("
	 * f2 -> Expression()
	 * f3 -> ")"
	 * f4 -> Statement()
	 */
	public MyType visit(WhileStatement n) {
		MyType _ret = null;
		String labeltrue = "whiletrue" + whilelabeloffset;
		String labelfalse = "whilefalse" + whilelabeloffset++;
		printer.println(labeltrue + ":");
		printer.addIndentation();
		MyType e = n.f2.accept(this);
		printer.println("if0 " + e.vid + " goto :" + labelfalse);
		printer.removeIndentation();
		n.f4.accept(this);
		printer.println("goto :" + labeltrue);
		printer.println(labelfalse + ":");
		return _ret;
	}

	/**
	 * f0 -> "printer.println();"
	 * f1 -> "("
	 * f2 -> Expression()
	 * f3 -> ")"
	 * f4 -> ";"
	 */
	public MyType visit(PrintStatement n) {
		MyType _ret = null;
		MyType e = n.f2.accept(this);
		printer.println("PrintIntS(" + e.vid + ")");
		return _ret;
	}

	/**
	 * f0 -> Type()
	 * f1 -> Identifier()
	 * f2 -> ";"
	 */
	public MyType visit(VarDeclaration n) {
		MyType _ret = null;
		MyType type = MyType.toMyType(n.f0);
		n.f0.accept(this);
		String newvid;
		switch(n.f0.f0.which){
			case 0:
				newvid = "array"+arrayvaroffset++;    // TODO: 2/12/2020 check other places
				break;
			case 1:
				newvid = "boolean"+booleanvaroffset++;
				break;
			case 2:
				newvid = "int"+intvaroffset++;
				break;
			case 3:
				newvid = "class"+classvaroffset++;
				break;
			default:
				newvid = "defaultatVarDeclaration";
		}
		Var var = new Var(n.f1.f0.tokenImage, newvid, type.f0, 0, envStack.peek().id);
		varTable.put(n.f1.f0.tokenImage,var);
		return _ret;
	}

	@Override
	public MyType visit(Expression n) {
		return n.f0.accept(this);
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "."
	 * f2 -> Identifier()
	 * f3 -> "("
	 * f4 -> ( ExpressionList() )?
	 * f5 -> ")"
	 */
	@Override
	public MyType visit(MessageSend n) {
		parameterList.clear();
		MyType classIdentifier = n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		String className; //the caller Class
		Boolean isThisClass = false;

		if (classIdentifier.getIdentifierName() == null) { //Class name
			className = classIdentifier.toString();
		} else if (classIdentifier.getIdentifierName().equals("this")) {  //Class name
			isThisClass = true;
			className = classIdentifier.toString();
		} else {  //identifier name
			className = varTable.get(classIdentifier.getIdentifierName()).type.toString();
		}

		String methodName = n.f2.f0.tokenImage;
		String methodId = className + "." + methodName;
		int methodOffset = envTable.get(className).vtable.get(methodId);
		String storedVaporVar;
		if (isThisClass) {
			storedVaporVar = "this";
		} else {
//			storedVaporVar = varTable.get(className).vid;
			storedVaporVar = classIdentifier.getIdentifierName();
		}

		String newClassVar = "classvar." + classvaroffset++;
		printer.println(newClassVar + " = " +
				"[" +
				storedVaporVar +
				"]");
		printer.println(newClassVar + " = " +
				"[" +
				newClassVar +
				"+" +
				methodOffset * 4
				+
				"]");
		String returnValueType = envTable.get(className).methodTable.get(methodId).returnValue.toString();
		String returnValue;
		switch (returnValueType) {
			case "int":
				returnValue = "intvar." + intvaroffset++;
				break;
			case "boolean":
				returnValue = ("booleanvar." + booleanvaroffset++);
				break;
			case "int[]":
				returnValue = ("arrayvar." + arrayvaroffset++);
				break;
			default:
				returnValue = ("classvar." + classvaroffset++);
		}

		StringBuilder parameterString = new StringBuilder();
		for (MyType parameter : parameterList) {
			parameterString.append(" ");
			parameterString.append(parameter.value);
		}

		printer.println(returnValue + " = " +
				"call " +
				newClassVar +
				"(" +
				storedVaporVar +
				" " + parameterString +
				")");
		MyType myType_ret = new MyType(returnValueType);
		myType_ret.vid = returnValue;
		return myType_ret;
	}

	/**
	 * f0 -> Expression()
	 * f1 -> ( ExpressionRest() )*
	 *
	 * @param n
	 */
	@Override
	public MyType visit(ExpressionList n) {
		MyType t = n.f0.accept(this);
		n.f1.accept(this);
		parameterList.add(t);
		return t;
	}

	/**
	 * f0 -> ","
	 * f1 -> Expression()
	 *
	 * @param n
	 */
	@Override
	public MyType visit(ExpressionRest n) {
		n.f0.accept(this);
		MyType t = n.f1.accept(this);
		parameterList.add(t);
		return t;
	}

	/**
	 * Grammar production:
	 * f0 -> IntegerLiteral()
	 * | TrueLiteral()
	 * | FalseLiteral()
	 * | Identifier()
	 * | ThisExpression()
	 * | ArrayAllocationExpression()
	 * | AllocationExpression()
	 * | NotExpression()
	 * | BracketExpression()
	 */
	@Override
	public MyType visit(PrimaryExpression n) {
		return n.f0.accept(this);
	}

    /**
     * f0 -> "true"
     *
     * @param n
     */
    @Override
    public MyType visit(TrueLiteral n) {
		n.f0.accept(this);
		MyType _ret = new MyType("boolean");
		_ret.value = 1;
		return _ret;
    }

    /**
     * f0 -> "this"
     *
     * @param n
     */
    @Override
    public MyType visit(ThisExpression n) {
		n.f0.accept(this);
		Env env = envStack.peek();
		MyType t = new MyType(env.classOfMethod);
		t.setIdentifierName("this");
		return t;
	}

    /**
     * f0 -> "new"
     * f1 -> "int"
     * f2 -> "["
     * f3 -> Expression()
     * f4 -> "]"
     *
     * @param n
     */
    @Override
    public MyType visit(ArrayAllocationExpression n) {
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		MyType t = n.f3.accept(this);
		n.f4.accept(this);
		int size = t.value;
		String vaporName = "arrayvar." + arrayvaroffset++;
		printer.println(vaporName + " = " +
				"HeapAllocZ(" +
				size * 4 + 4 +
				")");
		printer.println("[" +
				vaporName +
				"] = " +
				size); //The base address' value will store the array size
		MyType _ret = new MyType("int[]");
		_ret.vid = vaporName;
		_ret.value = size;
		return _ret;
	}

    /**
     * f0 -> "!"
     * f1 -> Expression()
     *
     * @param n
     */
    @Override
    public MyType visit(NotExpression n) {
        n.f0.accept(this);
        n.f1.accept(this);
        return new MyType("boolean");
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     *
     * @param n
     */
    @Override
    public MyType visit(BracketExpression n) {
        n.f0.accept(this);
        MyType t = n.f1.accept(this);
        n.f2.accept(this);
        return t;
    }



    /**
     * f0 -> "false"
     *
     * @param n
     */
    @Override
    public MyType visit(FalseLiteral n) {
		n.f0.accept(this);
		MyType _ret = new MyType("boolean");
		_ret.value = 0;
		return _ret;
	}

	@Override
	/*return the Identifier's type*/
	// TODO: 2020/2/11 return identifier itself (any problem?)
	public MyType visit(Identifier n) {
		n.f0.accept(this);
		MyType t = new MyType();
		Var var = varTable.get(n.f0.tokenImage);
		if (var != null) {
			t.vid = var.vid;
			t.value = var.value;
		} else {
			t.vid = n.f0.tokenImage;
		}
		t.setIdentifierName(n.f0.tokenImage);

		return t;
	}

	/**
	 * f0 -> "new"
	 * f1 -> Identifier()
	 * f2 -> "("
	 * f3 -> ")"
	 *
	 * @param n
	 */
	@Override
	public MyType visit(AllocationExpression n) {
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);

		String className = n.f1.f0.tokenImage;
		String vaporName = "classvar." + classvaroffset++;
		int sizeWithTableHead = classSize.get(className) * 4 + 4;
		printer.println(vaporName
				+ " = HeapAllocZ(" +
				+sizeWithTableHead +
				")");
		Var var = new Var(className, vaporName, null, 0, envStack.peek().id);
		varTable.put(className, var);
		printer.println("[" +
				vaporName +
				"] = :vmt_" +
				className);
		String allocNullLabel = "null" + allocationNullOffset++;
		printer.println("if " +
				vaporName +
				" goto " +
				":" +
				allocNullLabel +
				"\nError(\"null pointer\")\n" +
				allocNullLabel +
				":"
		);
		return new MyType(className);
	}

	/**
	 * f0 -> <INTEGER_LITERAL>
	 *
	 * @param n
	 */
	@Override
	public MyType visit(IntegerLiteral n) {
		n.f0.accept(this);
		MyType mt = new MyType("int");
		mt.value = Integer.parseInt(n.f0.tokenImage);
		mt.vid = n.f0.tokenImage;
		return mt;
	}

	/**
	 * f0 -> ArrayType()
	 * | BooleanType()
	 * | IntegerType()
	 * | Identifier()
	 *
	 * @param n
	 */
	@Override
	public MyType visit(Type n) {

		return n.f0.accept(this);
	}

	/**
	 * f0 -> "int"
	 * f1 -> "["
	 * f2 -> "]"
	 *
	 * @param n
	 */
	@Override
	public MyType visit(ArrayType n) {
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return new MyType("int[]");
	}

	/**
	 * f0 -> "boolean"
	 *
	 * @param n
	 */
	@Override
	public MyType visit(BooleanType n) {
		n.f0.accept(this);
		return new MyType("boolean");
	}

	/**
	 * f0 -> "int"
	 *
	 * @param n
	 */
	@Override
	public MyType visit(IntegerType n) {
		n.f0.accept(this);
		return new MyType("int");
	}

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     *
     * @param n
     */
    @Override
    public MyType visit(AndExpression n) {
        MyType t1 = n.f0.accept(this);
        n.f1.accept(this);
        MyType t2 = n.f2.accept(this);
        MyType _ret = new MyType("boolean");
        String tmpVar = "booleanvar."+booleanvaroffset++;
		printer.println(tmpVar + " = " + "MulS( " + t1.vid+" "+ t2.vid+")");
        return _ret;
    }



    /**
	 * f0 -> PrimaryExpression()
	 * f1 -> "<"
	 * f2 -> PrimaryExpression()
	 *
	 * @param n
	 */
	@Override
	public MyType visit(CompareExpression n) {
		MyType _ret;
		MyType t1 = n.f0.accept(this);
		n.f1.accept(this);
		MyType t2 = n.f2.accept(this);

		String tmpVar = "booleanvar." + booleanvaroffset++;
		printer.println(tmpVar + " = LtS(" + t1.vid +
				" " + t2.vid +
				")");

		_ret = new MyType("boolean");
		_ret.vid = tmpVar;
		return _ret;
	}

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     *
     * @param n
     */
    @Override
    public MyType visit(PlusExpression n) {
        MyType _ret=null;
        MyType t1 = n.f0.accept(this);
        n.f1.accept(this);
        MyType t2 = n.f2.accept(this);
        String tmpVar = "intvar."+ intvaroffset++;
        _ret = new MyType("int");
        _ret.vid = tmpVar;
		printer.println(tmpVar + " = Add(" +
				t1.vid +
				" " +
				t2.vid +
				")");
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     *
     * @param n
     */
    @Override
    public MyType visit(MinusExpression n) {
        MyType _ret=null;
        MyType t1 = n.f0.accept(this);
        n.f1.accept(this);
        MyType t2 = n.f2.accept(this);
        String tmpVar = "intvar."+ intvaroffset++;
        _ret = new MyType("int");
        _ret.vid = tmpVar;
		printer.println(tmpVar + " = Sub(" +
				t1.vid +
				" " +
				t2.vid +
				")");
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     *
     * @param n
     */
    @Override
    public MyType visit(TimesExpression n) {
        MyType _ret=null;
        MyType t1 = n.f0.accept(this);
        n.f1.accept(this);
        MyType t2 = n.f2.accept(this);
        String tmpVar = "intvar."+ intvaroffset++;
        _ret = new MyType("int");
        _ret.vid = tmpVar;
		printer.println(tmpVar + " = Mul(" +
				t1.vid +
				" " +
				t2.vid +
				")");
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     *
     * @param n
     */
    @Override
    public MyType visit(ArrayLookup n) {
		MyType _ret = null;
		MyType t1 = n.f0.accept(this);
		n.f1.accept(this);
		MyType t2 = n.f2.accept(this);
		int index = t2.value;
		n.f3.accept(this);
		String baseAddressOfArray = t1.vid;
		String size = "intvar." + intvaroffset++;
		printer.println(size + " = " + "[" +
				baseAddressOfArray +
				"]");
		printer.println("ok = LtS(" +
				index +
				" " +
				size +
				")");
		printer.println("if ok goto :" + arrayLookupOffset);
		printer.println("Error(\"Array index out of bounds\")");
		printer.println(arrayLookupOffset++ + ": ok = LtS(" +
				"-1" +
				" " +
				index +
				")");
		printer.addIndentation();
		printer.println("if ok goto :" + arrayLookupOffset);
		printer.println("Error(\"Array index out of bounds\")");
		printer.removeIndentation();
		printer.println(arrayLookupOffset++ + ": " + "o = MulS(" +
				index +
				" " +
				"4)");
		printer.addIndentation();
		printer.println("d = Add(" +
				baseAddressOfArray +
				" o)"
		);
		String receiverVid = "intvar." + intvaroffset++;
		printer.println(receiverVid + " = [d+4]");
		printer.removeIndentation();

		_ret = new MyType("int");
		_ret.vid = receiverVid;
		return _ret;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "."
	 * f2 -> "length"
	 *
	 * @param n
	 */
	@Override
	public MyType visit(ArrayLength n) {
		MyType _ret = null;
		MyType t = n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		String baseAddress = t.vid;
		printer.println("intvar." + intvaroffset++ + " = [" +
				baseAddress +
				"]");
		return _ret;
	}
}
