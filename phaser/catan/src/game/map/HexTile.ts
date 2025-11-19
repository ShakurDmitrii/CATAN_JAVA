export class HexTile {
    col: integer
    row: integer
    kind: HexTileKind

    constructor(row: integer, col: integer, kind: HexTileKind) {
        this.row = row
        this.col = col
        this.kind = kind
    }
}

export enum HexTileKind {
    SEA,
    DESERT,
    //OTHERS...
}