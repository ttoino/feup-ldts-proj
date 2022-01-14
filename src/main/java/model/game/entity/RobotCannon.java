package model.game.entity;

import model.Position;

import java.util.Vector;

public class RobotCannon extends Robot {
    /**
     * Creates a creature with the given parameters
     *
     * @param HP            The HP of the creature
     * @param movementRange The range of movement of the creature
     * @param flying        The ability of the unit to fly
     * @param abilities     The abilities that the unit possesses
     */
    public RobotCannon(Position position, int HP, int movementRange, boolean flying, Vector<Ability> abilities) {
        super(position, HP, movementRange, flying, abilities);
    }

    @Override
    public Type getType() {
        return Type.CANNON;
    }
}
