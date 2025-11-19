export class Player {
    id: integer
    name: string
    score: integer

    constructor(id: integer, name: string, score: integer) {
        this.id = id
        this.score = score
        this.name = name
    }
}