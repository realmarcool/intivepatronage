package pl.marcool.intivepatronage.services;

public class MyExceptions extends RuntimeException {
    public int errorCode;

    public MyExceptions(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
