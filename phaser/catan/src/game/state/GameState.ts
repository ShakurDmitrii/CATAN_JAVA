import { Player } from "../entities/Player";
import { HexTile } from "../map/HexTile";

export class GameState {
    tiles: HexTile[] = []
    players: Player[] = []
    currentPlayerIndex: integer = 0
    selectedTile: HexTile
    timeToMakeTurnMs: integer

    constructor(tiles: HexTile[]) {
        this.tiles = tiles
    }
}