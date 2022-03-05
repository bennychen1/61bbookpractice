package byow.Core;

import org.junit.Test;

public class TestKeyBoard {
    @Test
    public void testKeyboard() {
        Engine e = new Engine();

        e.interactWithKeyboard();

    }

    @Test
    public void testKeyBoardReader() {
        KeyboardCommandInput k = new KeyboardCommandInput();
        k.load();
    }

}
