package byow.Core;

/** An interface for movement commands **/
public interface CommandInput {
    public boolean hasNextInput();
    public char getNextInput();
}
