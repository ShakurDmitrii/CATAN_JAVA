import { Scene } from "phaser";
import { GameState } from "../state/GameState";

export class MapRenderer {
    gameState: GameState
    scene: Scene

    tilesXOffset = 20
    tilesYOffset = 100
    tileRadius = 55

    constructor(gameState: GameState, scene: Scene) {
        this.gameState = gameState
        this.scene = scene
    }

    renderMap() {
        this.renderTiles()
    }

    private renderTiles() {
        this.gameState.tiles.forEach(t => {
            let [x, y] = this.calcTileCoords(t.col, t.row)
            this.scene.add
                .image(x, y, 'tile')
                .setOrigin(0.5, 0.5)
                .setDisplaySize(this.tileRadius * 2, this.tileRadius * 2)
        })
    }

    calcTileCoords(col: integer, row: integer) {
        var x = this.tileRadius * (2 * col + 1 + Number(row % 2 != 0)) + this.tilesXOffset
        var y = this.tileRadius + this.tileRadius * 1.5 * row + this.tilesYOffset
        return [x, y]
    }
}