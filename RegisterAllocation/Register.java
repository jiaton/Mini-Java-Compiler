package RegisterAllocation;

import java.util.Comparator;

public class Register implements Comparable<Register> {
    String RegisterName = null;
    char type;
    int number;

    @Override
    public int compareTo(Register o) {
        if (this.type == o.type) {
            return this.number - o.number;
        } else {
            /*$t first*/
            return o.type - this.type;
        }
    }

    public Register(String registerName) {
        RegisterName = registerName;
        type = registerName.charAt(1);
        number = registerName.charAt(2);
    }
}
