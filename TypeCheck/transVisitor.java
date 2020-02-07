package TypeCheck;

import syntaxtree.*;
import visitor.GJNoArguDepthFirst;

import java.util.*;

import static java.lang.System.exit;


public class transVisitor extends GJNoArguDepthFirst<MyType> {
    public HashMap<String,HashMap<Integer,String>> listTable=new HashMap<>();
    public HashMap<String, Var> varTable=new HashMap<>();
    public HashMap<String,Env>  envTable=new HashMap<>();
    public HashMap<String,Integer> classSize = new HashMap<>();
    public Stack<Env> envStack = new Stack<Env>();
    public HashMap<String, String> typeTable = new HashMap<>();
    public int iflabeloffset=0;
    public int whilelabeloffset=0;
    public int intvaroffset=0;
    public int booleanvaroffset=0;
    public int arrayvaroffset=0;
    public int classvaroffset=0;
    public int MAXCLASSNUM = 1024;
    public static String ROOT = "root is absolutely no duplication";
    public void initialize() {
        typeTable.put("int", null);
        typeTable.put("boolean", null);
        typeTable.put("int[]", null);
        envStack.push(new Env(ROOT, false, null));
    }
    public void setupClassTable(TypeDeclaration n){
        if(n.f0.which==0){
            setupClassNoExtendsTable((ClassDeclaration)n.f0.choice);
        }else{
            setSuperClass((ClassExtendsDeclaration)n.f0.choice);
            setupClassExtendsTable((ClassExtendsDeclaration)n.f0.choice);
        }
    }
    public void setSuperClass(ClassExtendsDeclaration n){
        String subclass = n.f1.f0.tokenImage;
        Env env = envTable.get(subclass);
        env.superClass = n.f3.f0.tokenImage;
    }
    public void setupClassNoExtendsTable(ClassDeclaration n){
        Vector<Node>  vdnodes = n.f3.nodes;
        Vector<Node> mdnodes = n.f4.nodes;
        LinkedHashMap<String, Integer> record = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer> vtable = new LinkedHashMap<String,Integer>();
        Env env = envTable.get(n.f1.f0.tokenImage);
        env.record=record;
        env.vtable=vtable;
        int recordoffset=0;
        int vtableoffset=0;
        for(Node node : vdnodes){
            setField((VarDeclaration)node, recordoffset, env);
            recordoffset++;
        }
        for(Node node : mdnodes){
            setMethod((MethodDeclaration)node, vtableoffset, env);
            vtableoffset++;
        }
        classSize.put(n.f1.f0.tokenImage,recordoffset);
    }
    public void setupClassExtendsTable(ClassExtendsDeclaration n){
        Vector<Node>  vdnodes = n.f5.nodes;
        Vector<Node> mdnodes = n.f6.nodes;
        LinkedHashMap<String, Integer> record = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer> vtable = new LinkedHashMap<String,Integer>();
        Env env = envTable.get(n.f1.f0.tokenImage);
        env.record=record;
        env.vtable=vtable;
        int recordoffset=0;
        int vtableoffset=0;
        recordoffset=setBasicField(n,env)+1;
        vtableoffset=setBasicMethod(n,env)+1;
        for(Node node : vdnodes){
            recordoffset=setExtendsField((VarDeclaration)node, recordoffset, env);

        }
        for(Node node : mdnodes){
            vtableoffset=setExtendsMethod((MethodDeclaration)node, vtableoffset, env);

        }
        classSize.put(n.f1.f0.tokenImage,recordoffset);
    }
    public void setField(VarDeclaration n,int offset,Env env){
        String name = n.f1.f0.tokenImage;
        env.record.put(name, offset);
    }
    public void setMethod(MethodDeclaration n, int offset, Env env){
        String name = env.id + "." + n.f2.f0.tokenImage;
        env.vtable.put(name, offset);
    }
    public int setExtendsField(VarDeclaration n, int offset, Env env){
        String name = n.f1.f0.tokenImage;
        if(!env.record.containsKey(name)){
            env.record.put(name,offset);
            return ++offset;
        }else{
            return offset;
        }
    }
    public int setExtendsMethod(MethodDeclaration n, int offset, Env env){
        String name = env.id + "."+ n.f2.f0.tokenImage;
        if(env.vtable.containsKey(name)){
            env.vtableDelete(name);
            env.vtable.put(name, offset);
            return ++offset;
        }else{
            env.vtable.put(name,offset);
            return ++offset;
        }
    }
    public int setBasicField(ClassExtendsDeclaration n, Env subEnv){
        String subclass = subEnv.id;
        String superclass = subEnv.superClass;
        Env superEnv = envTable.get(superclass);
        int max = 0;
        for(Map.Entry<String, Integer> entry : superEnv.record.entrySet()){
            subEnv.record.put(entry.getKey(),entry.getValue());
            if(entry.getValue()>max){
                max=entry.getValue();
            }
        }
        return max;
    }
    public int setBasicMethod(ClassExtendsDeclaration n, Env subEnv){
        String subclass = subEnv.id;
        String superclass = subEnv.superClass;
        Env superEnv = envTable.get(superclass);
        int max = 0;
        for(Map.Entry<String, Integer> entry : superEnv.vtable.entrySet()){
            subEnv.vtable.put(entry.getKey(),entry.getValue());
            if(entry.getValue()>max){
                max=entry.getValue();
            }
        }
        return max;
    }
    public Vector<TypeDeclaration> GetClassNodes(Goal n){
        Vector<TypeDeclaration> nodes= new Vector<>();
        for(Node node : n.f1.nodes){
            nodes.add((TypeDeclaration) node);
        }
        return nodes;
    }
    public MyType checkIdentifier(Identifier n, Env env){
        if(env.varTable.containsKey(n.f0.tokenImage)){
            System.out.println("When checking identifier, the identifier is token");
            exit(-1);
        }
        return env.varTable.get(n.f0.tokenImage);
    }
    public MyType checkIdentifier(Identifier n) {                    //check whether the identifier n is in the env
        System.out.println("Checking Identifier "+n.f0.toString());
        MyType _ret = null;
        if (envStack.empty()) return null;
        Env A = envStack.pop();
        if (A.isMethod) {
            if(envStack.empty()){
                System.out.println("The Stack is empty at a method!");
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
        if(variabletype == null)	return null;
        _ret = A.getVariableType(n);
        return _ret;
    }
    public void SetMethodType(MethodDeclaration n, Env classOfMethod){
        //DistinctParameterList((FormalParameterList) n.f4.node);
        MethodType methodtype = new MethodType(MyType.toMyType(n.f1));
        methodtype.returnValue=MyType.toMyType(n.f1);

        methodtype.setParameterList((FormalParameterList) n.f4.node);

        classOfMethod.methodTable.put(classOfMethod.id+"."+n.f2.f0.tokenImage,methodtype);
    }
    public void DistinctParameterList(FormalParameterList n){
        if(n==null) return;
        Vector<Node> nodes = n.f1.nodes;
        HashSet<String> idset=new HashSet<>();
        for (Node node : nodes){
            if (!typeTable.containsKey(MyType.toMyType(((FormalParameterRest)node).f1.f0).toString())) {
                System.out.println("At the FormalParameter, the type " + MyType.toMyType(((FormalParameterRest)node).f1.f0).toString() + " is not avaliable!");
                exit(-1);
            }
            if(idset.contains(((FormalParameterRest)node).f1.f1.f0.tokenImage)){
                System.out.println("The parameters are not distincted!");
                exit(-1);
            }
            idset.add(((FormalParameterRest)node).f1.f1.f0.tokenImage);
        }
        if(!typeTable.containsKey(MyType.toMyType(n.f0.f0).toString())){
            System.out.println("At the FormalParameter, the type " + MyType.toMyType(n.f0.f0).toString() + " is not avaliable!");
            exit(-1);
        }
        if(idset.contains(n.f0.f1.f0.tokenImage)){
            System.out.println("The parameters are not distincted!");
            exit(-1);
        }

    }
    public void SetMethodList(NodeListOptional n,Env classOfMethod) {
        Vector<Node> nodes = n.nodes;
        Iterator<Node> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            Node next = iterator.next();                    // TODO: 2/1/2020 check iterator.next
            SetMethodType((MethodDeclaration) next, classOfMethod);
        }
    }
    public void addVar(Type f0,Identifier f1, Env env){
        env.addVar(f0, f1);
    }
    public void fieldExtends(String id, String idM) {            //put parent class fields to child
        Env childclass = envTable.get(id);
        Env parentclass = envTable.get(idM);
        for (HashMap.Entry<String, MyType> entry : parentclass.varTable.entrySet()) {
            if (childclass.varTable.containsKey(entry.getKey())) {
                System.out.println("When extending the class, has the same field");
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
                    System.out.println("When extending the class, method overloading!");
                    exit(-1);        //Env.has(string(id))
                }
            } else
                childclass.methodTable.put(entry.getKey(), entry.getValue());
//				childclass.methodTable.entrySet().add(entry);
        }
    }
    public void SetFields(NodeListOptional n,Env classOfFields){
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
    public void SetField(VarDeclaration n, Env classOfFields){
//        if (!typeTable.containsKey(MyType.toMyType(n.f0).toString())) {
//            System.out.println("At the VarDeclaration, the type is not avaliable!");
//            exit(-1);
//        }
        MyType IdentifierType = checkIdentifier(n.f1,classOfFields);
        if (IdentifierType != null) {
            System.out.println("At the VarDeclaration, the id is token!");
            exit(-1);
        }
        addVar(n.f0, n.f1,classOfFields);
    }
    public void setClassList(Vector<TypeDeclaration> sortedNodes) {
        /*Set environments for each node in the order of sortedNodes*/
        for (Node node : sortedNodes) {
            TypeDeclaration next = (TypeDeclaration) node;
            if (next.f0.which == 0) {
                ClassDeclaration n = (ClassDeclaration) next.f0.choice;
                if (typeTable.containsKey(n.f1.f0.toString())) {
                    System.out.println("At setClassList, the id is already token:"+n.f1.f0.toString());
                    exit(-1);
                }
                typeTable.put(n.f1.f0.toString(), null);
                Env env = new Env(n.f1.f0.toString(), false, null);    //Env(id, isMethod)
                envTable.put(env.id, env);
            } else {
                ClassExtendsDeclaration n = (ClassExtendsDeclaration) next.f0.choice;
                if (typeTable.containsKey(n.f1.f0.toString())) {
                    System.out.println("At classdeclaration (extends), the id is already token!");
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
    public Vector<TypeDeclaration> sortNodes(Vector<TypeDeclaration> nodes){
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
    public void setClassId(TypeDeclaration next){
        if (next.f0.which == 0) {
            ClassDeclaration n = (ClassDeclaration) next.f0.choice;
            if (typeTable.containsKey(n.f1.f0.toString())) {
                System.out.println("At setClassList, the id is already token!");
                exit(-1);
            }
            typeTable.put(n.f1.f0.toString(), null);
            Env env = new Env(n.f1.f0.toString(), false, null);    //Env(id, isMethod)
            envTable.put(env.id, env);
        } else {
            ClassExtendsDeclaration n = (ClassExtendsDeclaration) next.f0.choice;
            if (typeTable.containsKey(n.f1.f0.toString())) {
                System.out.println("At classdeclaration (extends), the id is already token!");
                exit(-1);
            }
            typeTable.put(n.f1.f0.toString(), n.f3.f0.toString());                    // TODO: 1/27/2020 check mytype
            Env env = new Env(n.f1.f0.toString(), false, n.f3.f0.toString());    //Env(id, idM)
            envTable.put(env.id, env);
        }
    }
    public void printvTable(){
        for(Map.Entry<String, String> entry : typeTable.entrySet()){
            Env env = envTable.get(entry.getKey());
            env.printvTable();
        }
    }
    public String getOriginName(String method, String clazz){
        char[] clist = method.toCharArray();
        char[] result = new char[method.length()];
        int where = 0;
        boolean gotit = false;
        for(char c : clist){
            if(!gotit){
                if(c=='.'){
                    gotit=true;
                }
            }else{
                result[where]=c;
                where++;
            }
        }
        method = String.valueOf(result);
        return method;
    }
    public void printAssignmentStatement(String id,String vid){
        Var var = varTable.get(id);
        System.out.println(var.jid+"=HeapAllocZ(4)");
        System.out.print(var.jid+"=["+vid+"]");             // TODO: 2/7/2020 double check
    }
    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */
    public MyType visit(Goal n) {
        MyType _ret=null;
        Vector<TypeDeclaration> nodes = GetClassNodes(n);
        Vector<TypeDeclaration> sortedNodes=sortNodes(nodes);
        setClassList(sortedNodes);
        for(TypeDeclaration node : sortedNodes){
            setupClassTable(node);
        }
        printvTable();
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }
    /**
     * f0 -> ClassDeclaration()
     *       | ClassExtendsDeclaration()
     */
    public MyType visit(TypeDeclaration n) {
        MyType _ret=null;
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
        MyType _ret=null;
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
        MyType _ret=null;
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
        MyType _ret=null;
        Env classenv = envStack.peek();
        String methodid = classenv.id+"."+n.f2.f0.toString();
        MethodType methodtype=classenv.methodTable.get(methodid);
        Env env = new Env(methodid, true, envStack.peek().id,methodtype);        //Env(id, isMethod, classofmethod(or superclass))
        //System.out.println(envStack.peek().id);
        envTable.put(env.id, env);
        envStack.push(env);
        System.out.print("func ");
        System.out.print(env.id);
        System.out.println(" "+env.methodTable.get(env.id).printInVapor());
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
        System.out.println("ret ");
        envStack.pop();
        System.out.println();
        return _ret;
    }
    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    public MyType visit(AssignmentStatement n) {
        MyType _ret=null;
        Env env=envStack.peek();
        String id=n.f0.f0.tokenImage;
        MyType mt=n.f2.accept(this);
        String newvid=mt.toString()+Var.intoffset++;
        Var var=new Var(id,newvid,mt.f0,mt.value,env.id);
        varTable.put(id,var);
        printAssignmentStatement(id,mt.vid);
        _ret=new MyType(newvid,mt.f0,mt.value);
        return _ret;
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
        MyType _ret=null;
        MyType e1=n.f2.accept(this);
        MyType e2=n.f5.accept(this);
        String listname = listTable.get(n.f0.f0.tokenImage).get(e1.value);
        System.out.println(listname + "="+e2.vid);
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
        MyType _ret=null;
        MyType e=n.f2.accept(this);
        String labels1="truebranch"+iflabeloffset;
        String labels2="falsebranch"+iflabeloffset++;
        System.out.println("if "+e.vid+" goto :"+labels1);
        n.f6.accept(this);
        System.out.println("goto :"+labels2);
        System.out.println(labels1+":");
        n.f4.accept(this);
        System.out.println(labels2+":");
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
        MyType _ret=null;
        String labeltrue="whiletrue"+whilelabeloffset;
        String labelfalse="whilefalse"+whilelabeloffset++;
        System.out.println(labeltrue+":");
        MyType e=n.f2.accept(this);
        System.out.println("if0 "+e.vid+" goto :"+labelfalse);
        n.f4.accept(this);
        System.out.println("goto :"+labeltrue);
        System.out.println(labelfalse+":");
        return _ret;
    }
    /**
     * f0 -> "System.out.println"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> ";"
     */
    public MyType visit(PrintStatement n) {
        MyType _ret=null;
        MyType e = n.f2.accept(this);
        System.out.println("PrintInt("+e.vid+")");
        return _ret;
    }
    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    public MyType visit(VarDeclaration n) {
        MyType _ret=null;
        MyType type = n.f0.accept(this);
        if(type.f0.tokenImage=="int"){
            String newvid="intvar"+intvaroffset++;
            Var var = new Var(n.f1.f0.tokenImage,newvid,type.f0,0,envStack.peek().id);
            varTable.put(n.f1.f0.tokenImage,var);
            System.out.println(newvid+"=HeapAllocZ(4)");
        }else if(type.f0.tokenImage=="boolean"){
            String newvid="booleanvar"+booleanvaroffset++;
            Var var = new Var(n.f1.f0.tokenImage,newvid,type.f0,0,envStack.peek().id);
            varTable.put(n.f1.f0.tokenImage,var);
            System.out.println(newvid+"=HeapAllocZ(4)");
        }else if(type.f0.tokenImage=="int[]"){
            String newvid = "arrayvar"+arrayvaroffset++;
            Var var = new Var(n.f1.f0.tokenImage,newvid,type.f0,0,envStack.peek().id);
            varTable.put(n.f1.f0.tokenImage,var);
            HashMap<Integer,String> arraylist = new HashMap<Integer,String>();
            listTable.put(newvid,arraylist);
        }else{
            String newvid = "classvar"+classvaroffset++;
            Var var = new Var(n.f1.f0.tokenImage,newvid,type.f0,0,envStack.peek().id);
            varTable.put(n.f1.f0.tokenImage,var);
            System.out.println(newvid+"=HeapAllocZ("+(classSize.get(n.f1.f0.tokenImage)*4+4)+")");
        }
        return _ret;
    }
}
