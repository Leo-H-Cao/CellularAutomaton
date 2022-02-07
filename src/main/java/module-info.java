module cellsociety_app {
    // list all imported class packages since they are dependencies
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
	requires java.xml;
	requires java.desktop;

	// allow other classes to access listed packages in your project
    exports cellsociety.cell;
    exports cellsociety.io;
    exports cellsociety.game;
    exports cellsociety.view;
    exports cellsociety;
}
