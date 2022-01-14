package model.game.terrain;

import model.Position;

/**
 * A type of terrain, a "forest" with specific properties related to the moment to moment gameplay
 */
public class TerrainForest extends Terrain {
    /**
     * True if the "forest" is burning, false otherwise
     */
    private boolean burning = false;

    /**
     * See {@link Terrain#Terrain}
     */
    public TerrainForest(Position pos) {
        super(pos);
    }

    /**
     * Sets the terrain on fire, or stops it
     *
     * @param burning The current state of the city (true if burning, false otherwise)
     */
    public void setBurning() {
        this.burning = true;
    }

    /**.
     * @return True if <i>the terrain is burning</i>
     */
    public boolean isBurning() {
        return this.burning;
    }
}
