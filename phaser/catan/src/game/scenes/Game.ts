import { Scene } from 'phaser';
import { MapRenderer } from '../map/MapRenderer';
import { GameState } from '../state/GameState';
import { UiRenderer } from '../ui/UiRenderer';
import { Player } from '../entities/Player';
import { generateHexIsland } from '../map/generateHexIsland';
import { HexTile, HexTileKind } from '../map/HexTile';

export class Game extends Scene {
    gameState: GameState
    mapRenderer: MapRenderer
    uiRenderer: UiRenderer

    constructor() {
        super('Game')
        var emptyTiles = []
        for (var i = 0; i < 50; i++) {
            for (var j = 0; j < 50; j++) {
                emptyTiles.push(new HexTile(i, j, HexTileKind.SEA))
            }
        }
        var tiles = emptyTiles.concat(generateHexIsland(5, 5, 25))
        this.gameState = new GameState(tiles)
        this.gameState.players.push(new Player(0, "Ivan", 100))
        this.gameState.players.push(new Player(0, "Alice", 10))
    }

    preload() {
        this.load.setPath('assets');

        this.load.image('empty', 'empty.svg');
        this.load.image('desert', 'desert.svg');
        this.load.image('brick', 'stone.svg');
        this.load.image('wood', 'wood.svg');
    }

    create() {
        this.mapRenderer = new MapRenderer(this.gameState, this)
        this.uiRenderer = new UiRenderer(this.gameState, this)

        this.mapRenderer.renderMap()
        this.uiRenderer.renderUI()
    }

}
