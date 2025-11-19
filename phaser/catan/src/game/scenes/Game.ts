import { Scene } from 'phaser';
import { HexTile, HexTileKind } from '../map/HexTile';
import { MapRenderer } from '../map/MapRenderer';
import { GameState } from '../state/GameState';
import { UiRenderer } from '../ui/UiRenderer';
import { Player } from '../entities/Player';

export class Game extends Scene {
    gameState: GameState
    mapRenderer: MapRenderer
    uiRenderer: UiRenderer

    constructor() {
        super('Game')
        let tiles = []
        // for (let i = 0; i < 5; i++) {
        //     for (let j = 0; j < 7; j++) {
        //         tiles.push(new HexTile(i, j, HexTileKind.DESERT))
        //     }
        // }
        tiles = this.generateHexIsland(5, 5, 25)
        this.gameState = new GameState(tiles)
        this.gameState.players.push(new Player(0, "Ivan", 100))
        this.gameState.players.push(new Player(0, "Alice", 10))
    }

    preload() {
        this.load.setPath('assets');

        this.load.image('background', 'bg.png');
        this.load.image('logo', 'logo.png');
        this.load.image('tile', 'tile.svg');
    }

    create() {
        this.mapRenderer = new MapRenderer(this.gameState, this)
        this.uiRenderer = new UiRenderer(this.gameState, this)

        this.mapRenderer.renderMap()
        this.uiRenderer.renderUI()
    }

    generateHexIsland(centerRow: number, centerCol: number, size: number): HexTile[] {
        type Coord = { row: number, col: number }
        const HEX_DIRECTIONS: Coord[] = [
            { row: -1, col: 0 }, { row: -1, col: 1 },
            { row: 0, col: -1 }, { row: 0, col: 1 },
            { row: 1, col: -1 }, { row: 1, col: 0 }
        ]

        const island = new Map<string, HexTile>()
        const queue: Coord[] = []

        function key(r: number, c: number) { return `${r},${c}` }

        // стартовый тайл
        const centerTile = new HexTile(centerRow, centerCol, HexTileKind.DESERT)
        island.set(key(centerRow, centerCol), centerTile)
        queue.push({ row: centerRow, col: centerCol })

        while (island.size < size && queue.length > 0) {
            const current = queue.shift()!

            // перебираем соседей
            for (const dir of HEX_DIRECTIONS) {
                const nr = current.row + dir.row
                const nc = current.col + dir.col
                const k = key(nr, nc)

                if (island.has(k)) continue

                // вероятность добавить тайл зависит от расстояния до центра (для контроля формы)
                const dist = Math.sqrt((nr - centerRow) ** 2 + (nc - centerCol) ** 2)
                const maxRadius = Math.sqrt(size)  // грубая оценка радиуса
                const prob = Math.max(0, 1 - dist / maxRadius)

                if (Math.random() < prob) {
                    const tile = new HexTile(nr, nc, HexTileKind.DESERT)
                    island.set(k, tile)
                    queue.push({ row: nr, col: nc })
                }
            }
        }

        return Array.from(island.values())
    }



}
