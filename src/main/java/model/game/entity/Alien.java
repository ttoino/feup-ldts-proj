package model.game.entity;

import model.Position;

import java.util.Vector;

public abstract class Alien extends Entity{

    Alien(Position pos, boolean flying, Integer movementRange, Vector<Ability> abilities) {
        super(pos, flying, movementRange, abilities);
    }
}
