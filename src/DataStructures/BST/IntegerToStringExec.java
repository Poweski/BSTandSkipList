package DataStructures.BST;

import DataStructures.BST.IExecutor;

class IntegerToStringExec implements IExecutor<Integer, String> {
    StringBuffer line = new StringBuffer();

    @Override
    public void execute(Integer elem) {
        line.append(elem).append("; ");
    }

    @Override
    public String getResult() {
        line.delete(line.length()-2, line.length());
        return line.toString();
    }
}