package byow.Core;

import org.junit.Test;

public class TestKeyBoard {
    @Test
    public void testKeyboard() {
        Engine e = new Engine();

        e.interactWithKeyboard();

    }

    @Test
    public void testChaseMap() {
        Engine e = new Engine();
        e.interactWithKeyboardChaseMap();

        ChaseMap c = new ChaseMap(new RandomMap(5));

        c.getAvatarList();
    }


}
