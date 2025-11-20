export class HexTile {
    col: integer
    row: integer
    kind: HexTileKind
    tileSprite: string

    constructor(row: integer, col: integer, kind: HexTileKind) {
        this.row = row
        this.col = col
        this.kind = kind
        switch(kind) {
            case HexTileKind.DESERT:
                this.tileSprite = 'desert'
                break
            case HexTileKind.SEA: 
                this.tileSprite = 'empty'
                break
            case HexTileKind.BRICK: 
                this.tileSprite = 'brick'
                break
            case HexTileKind.WOOD: 
                this.tileSprite = 'wood'
                break
            default:
                this.tileSprite = 'empty'
        }
    }
}

export enum HexTileKind {
    DESERT= "DESERT",
    SEA = "SEA",
    WOOD = "WOOD",
    BRICK = "BRICK",
    SHEEP = "SHEEP",
    ORE = "ORE",
    WHEAT = "WHEAT"
}