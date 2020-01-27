package TypeCheck;

public class Context {
    /*
    * record the context of current layer
    * @author: TJ
    * */
    public String className;
    public String methodName;
    Context(String className, String methodName ) {
        this.className = className;
        this.methodName = methodName;
    }
    Context(String className) {
        this(className, null);
    }
}
