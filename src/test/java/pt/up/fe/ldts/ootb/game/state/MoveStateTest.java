package pt.up.fe.ldts.ootb.game.state;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pt.up.fe.ldts.ootb.App;
import pt.up.fe.ldts.ootb.game.Game;
import pt.up.fe.ldts.ootb.game.LevelLoader;
import pt.up.fe.ldts.ootb.game.entity.creature.alien.Alien;
import pt.up.fe.ldts.ootb.game.entity.creature.alien.TankAlien;
import pt.up.fe.ldts.ootb.game.entity.creature.robot.CannonRobot;
import pt.up.fe.ldts.ootb.game.entity.creature.robot.Robot;
import pt.up.fe.ldts.ootb.game.entity.creature.robot.strategy.RandomStrategy;
import pt.up.fe.ldts.ootb.gui.input.InputHandler;
import pt.up.fe.ldts.ootb.gui.input.MouseClickEvent;
import pt.up.fe.ldts.ootb.gui.render.Renderer;
import pt.up.fe.ldts.ootb.util.Vector;

import java.net.URISyntaxException;

public class MoveStateTest {
    private static Game game;
    private static final Robot TEST_ROBOT =
            new CannonRobot(new Vector(0, 0), new RandomStrategy());
    private static final Alien TEST_ALIEN =
            new TankAlien(new Vector(2, 2));

    @BeforeAll
    public static void setup() {
        App app = new App(Mockito.mock(Renderer.class), Mockito.mock(InputHandler.class));

        game = new Game(app, LevelLoader.loadLevel("lvl02.lvl"));
    }

    @Test
    public void moveStateTest() {
        MoveState state = new MoveState(game);

        game.changeState(state);

        state.onMouseClick(new MouseClickEvent(new Vector(2 * 48, 1 * 48), 1));
        state.onMouseClick(new MouseClickEvent(new Vector(2 * 48, 2 * 48), 1));

        Assertions.assertInstanceOf(TEST_ALIEN.getClass(), game.getCreatureAt(new Vector(2,2)));
    }
}
