@echo off
set JLINK_VM_OPTIONS=--add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base
set DIR=%~dp0
"%DIR%\java" %JLINK_VM_OPTIONS% -m com.mycompany.racesimulation/racesimulation.Main %*
