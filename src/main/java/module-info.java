module com.leimar.tasklist.tasklist {
	requires java.base;
	requires java.desktop;
	requires java.sql;
    requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
    exports com.leimar.todolist;
}