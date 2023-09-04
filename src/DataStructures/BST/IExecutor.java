package DataStructures.BST;

public interface IExecutor<T,R> {
    void execute(T elem);
    R getResult();
}

