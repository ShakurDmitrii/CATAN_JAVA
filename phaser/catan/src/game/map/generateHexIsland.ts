import { HexTile, HexTileKind } from "./HexTile"

type Coord = { row: number, col: number }
const HEX_DIRECTIONS: Coord[] = [
    { row: -1, col: 0 }, { row: -1, col: 1 },
    { row: 0, col: -1 }, { row: 0, col: 1 },
    { row: 1, col: -1 }, { row: 1, col: 0 }
]

const kinds = [HexTileKind.WOOD, HexTileKind.BRICK]

export function generateHexIsland(centerRow: number, centerCol: number, size: number): HexTile[] {
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

            var rnd = Math.random()
            if (rnd < prob) {
                var kind
                var lowerBound = 0.5
                if (rnd <= lowerBound) {
                    kind = HexTileKind.DESERT
                } else {
                    var d = (rnd - lowerBound) / 0.2
                    var ind = Math.round((kinds.length - 1) * d)
                    kind = kinds[ind]
                    console.log(ind)
                }
                const tile = new HexTile(nr, nc, kind)
                island.set(k, tile)
                queue.push({ row: nr, col: nc })

            }
        }
    }

    return Array.from(island.values())
}
