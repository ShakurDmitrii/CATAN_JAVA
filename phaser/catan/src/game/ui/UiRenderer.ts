import { GameObjects, Scene } from "phaser"
import { GameState } from "../state/GameState"

export class UiRenderer {
    gameState: GameState
    scene: Scene
    sidebarContainer: GameObjects.Container


    constructor(gameState: GameState, scene: Scene) {
        this.gameState = gameState
        this.scene = scene
        this.createPlayerSidebar()
    }


createPlayerSidebar() {
    const sidebarWidth = 250;
    const padding = 10;
    const startY = 10;

    // Контейнер ставим в правый верхний угол
    this.sidebarContainer = this.scene.add.container(
        this.scene.scale.width - sidebarWidth,
        0
    );

    // Фон панели
    const bg = this.scene.add.rectangle(
        0,
        0,
        sidebarWidth,
        this.scene.scale.height,
        0x000000,
        0.4
    ).setOrigin(0, 0);

    this.sidebarContainer.add(bg);

    // Тексты справа
    var currentY = startY;

    this.gameState.players.forEach(player => {
        const txt = this.scene.add.text(
            padding,
            currentY,
            `${player.name} — ${player.score}`,
            {
                fontSize: "26px",
                color: "#ffffff"
            }
        );

        this.sidebarContainer.add(txt);

        currentY += 28;
    });
}


renderUI() {
    this.createPlayerSidebar()
}

}