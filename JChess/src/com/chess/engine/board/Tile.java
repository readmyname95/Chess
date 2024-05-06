/*
 * Project: Chess
 * Name: Kerry
 * Date: 2024/1/27
 * Immutable concept: Effective Java 2nd Edition, Item 15
 * current progress: 2 guava & Next: understand 4 (knigt.java)
 */

package com.chess.engine.board;
import com.google.common.collect.ImmutableMap;

import com.chess.engine.pieces.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// Abstract class for a tile on the board 
public abstract class Tile {
    protected final int tileCoordinate;

    /**
     * A cache of empty tiles.
     */
    private static final Map<Integer, EmptyTile> EMPTY_FILES_CACHE = createAllPossibleEmptyTiles();
    
    // Constructor 
    
    private Tile(int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    
    public static Tile createTile(final int tileCoordinate, final Piece piece){
        // If the piece is not null, return an occupied tile
        // The uesrs can't change the tileCoordinate and piece. If they want to change, they need to create a new Occupiedtile. 
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_FILES_CACHE.get(tileCoordinate);
    }
    
    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile>emptyTileMap = new HashMap<>();
        
        for(int i=0; i<64; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        } 
        
        return ImmutableMap.copyOf(emptyTileMap);
    }

    // Returns true if the tile is occupied by a piece
    public abstract boolean isTileOccupied();

    // Returns the piece on the tile
    public abstract Piece getPiece();  

    // Empty tile class 
    // Returns an empty tile
    public static final class EmptyTile extends Tile {
        EmptyTile(final int coordinate) {
            super(coordinate);
        }

        // Returns false because the tile is empty
        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class OccupiedTile extends Tile {
        //This is class private
        private final Piece pieceOnTile;

        private OccupiedTile(int tileCoordinate, Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}