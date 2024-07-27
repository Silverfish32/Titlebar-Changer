package net.ittriy.config;

public class Config {
    private int theme = 1;
    private int corner = 1;
    private final titleBarColor titleBarColor = new titleBarColor();
    private final titleBarTextColor titleBarTextColor = new titleBarTextColor();
    private final titleBarTextColor titleBarStrokeColor = new titleBarTextColor();
    private boolean showTheMenu = true;
    private boolean showWarnScreen = true;

    public int getCorner() {
        return corner;
    }

    public int getTheme() {
        return theme;
    }

    public Config.titleBarColor getTitleBarColor() {
        return titleBarColor;
    }

    public Config.titleBarTextColor getTitleBarTextColor() {
        return titleBarTextColor;
    }

    public Config.titleBarTextColor getTitleBarStrokeColor() {
        return titleBarStrokeColor;
    }

    public boolean getShowTheMenu() {
        return showTheMenu;
    }

    public boolean getShowWarnScreen() {
        return showWarnScreen;
    }

    public void setCorner(int corner) {
        this.corner = corner;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public void setShowWarnScreen(boolean showWarnScreen) {
        this.showWarnScreen = showWarnScreen;
    }

    public static class titleBarColor {
        private int r = 5;
        private int g = 5;
        private int b = 5;

        public int getR() {
            return r;
        }

        public int getB() {
            return b;
        }

        public int getG() {
            return g;
        }

        public void setB(int b) {
            this.b = b;
        }

        public void setG(int g) {
            this.g = g;
        }

        public void setR(int r) {
            this.r = r;
        }
    }

    public static class titleBarTextColor {
        private int r = 0;
        private int g = 255;
        private int b = 0;

        public int getR() {
            return r;
        }

        public int getB() {
            return b;
        }

        public int getG() {
            return g;
        }


        public void setB(int b) {
            this.b = b;
        }

        public void setG(int g) {
            this.g = g;
        }

        public void setR(int r) {
            this.r = r;
        }
    }

    private static class titleBarStrokeColor {
        private int r = 0;
        private int g = 255;
        private int b = 0;

        public int getR() {
            return r;
        }

        public int getB() {
            return b;
        }

        public int getG() {
            return g;
        }

        public void setB(int b) {
            this.b = b;
        }

        public void setG(int g) {
            this.g = g;
        }

        public void setR(int r) {
            this.r = r;
        }
    }
}
