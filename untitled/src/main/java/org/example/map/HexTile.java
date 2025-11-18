    package org.example.map;

    import org.example.objects.Player;
    import org.newdawn.slick.geom.Polygon;
    import org.newdawn.slick.Color;
    import org.newdawn.slick.Graphics;

    public class HexTile {

        private float x, y;
        private String type;
        private int number;
        private Player owner;

        private float[][] vertices = new float[6][2]; // координаты вершин
        private Player[] vertexOwners = new Player[6]; // кто владеет вершиной

        private float radius; // добавляем radius как поле класса

        public HexTile(float x, float y, String type, int number, Player owner) {
            this.x = x;
            this.y = y;
            this.type = type;
            this.number = number;
            this.owner = owner;

            this.radius = 50; // задаём радиус
            computeVertices(radius); // вычисляем вершины гекса
        }

        public float[][] getVertices() {
            return vertices;
        }

        public float[] getVertex(int index) {
            return vertices[index];
        }

        private void computeVertices(float radius) {
            for (int i = 0; i < 6; i++) {
                double angle = Math.toRadians(60 * i - 30);
                float px = x + radius * (float) Math.cos(angle);
                float py = y + radius * (float) Math.sin(angle);
                vertices[i][0] = px;
                vertices[i][1] = py;
            }
        }

        public boolean canBuildVillage(int vertexIndex) {
            return vertexOwners[vertexIndex] == null;
        }

        public void buildVillage(int vertexIndex, Player p) {
            vertexOwners[vertexIndex] = p;
        }

        private Polygon createHex(float cx, float cy, float radius) {
            Polygon hex = new Polygon();
            for (int i = 0; i < 6; i++) {
                hex.addPoint(vertices[i][0], vertices[i][1]);
            }
            return hex;
        }

        public void render(Graphics g) {
            Polygon hex = createHex(x, y, radius);

            g.setColor(getColorByType());
            g.fill(hex);

            g.setColor(Color.black);
            g.draw(hex);

            g.drawString("" + number, x - 5, y - 5);

            // отрисовать вершины (DEBUG)
            g.setColor(Color.black);
            for (int i = 0; i < 6; i++) {
                g.fillOval(vertices[i][0] - 3, vertices[i][1] - 3, 6, 6);

                if (vertexOwners[i] != null) {
                    g.setColor(Color.red);
                    g.fillOval(vertices[i][0] - 6, vertices[i][1] - 6, 12, 12);
                }
            }
        }
        public String getType() {
            return type;
        }

        // Метод для проверки, попал ли клик в тайл
        public boolean contains(int mouseX, int mouseY) {
            float dx = mouseX - x;
            float dy = mouseY - y;
            return dx * dx + dy * dy <= radius * radius; // теперь radius есть
        }

        public Color getColorByType() {
            switch (type) {
                case "wood": return Color.green;
                case "brick": return Color.red;
                case "sheep": return Color.white;
                case "wheat": return Color.yellow;
                case "ore": return Color.gray;
                case "desert": return Color.orange;
            }
            return Color.black;
        }

        // ROBBER
        private boolean hasRobber = false;

        public void setRobber(boolean hasRobber) {
            this.hasRobber = hasRobber;
        }

        public boolean hasRobber() {
            return hasRobber;
        }
    }
