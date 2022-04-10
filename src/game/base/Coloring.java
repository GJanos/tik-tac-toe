package game.base;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * a temat megszabo interface, szineket tarol egy helyen
 */
public interface Coloring {

    Map<String, Color> darkTheme  = new HashMap<String, Color>() {{
        put("backC", new Color(0,0,0));
        put("textC", new Color(255,215,0));
        put("importantC",  new Color(255,69,0));
        put("disappearC", new Color(220, 20, 60));
        put("winC", new Color(128,0,0));
        put("highlightC", new Color(238,16,16));
    }};

    /// todo make theme 2 as lightmode

}
