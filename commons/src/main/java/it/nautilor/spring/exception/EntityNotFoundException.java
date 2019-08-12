package it.nautilor.spring.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String s) {
        super(s);
    }


    public EntityNotFoundException(String id, Class clazz) {
        super(String.format("Entity of type %s not found with id [%s]", clazz.getName(), id));
    }

    public EntityNotFoundException(Class clazz) {
        super(String.format("Entity of type %s", clazz.getName()));
    }

    public EntityNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public EntityNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public EntityNotFoundException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
