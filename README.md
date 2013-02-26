JStormLib
=========

A JAVA-Wrapper for the C++ Library Stormlib. Uses the JNA-Library to access the native functions.
When used the library detects if you use a x86 or x64 system and loads the needed DLL.

Important files:
  - StormLib_x86.dll
  - StormLib_x64.dll

Important classes:
  - StormLibWin: Contains all function and constant mappings for the StormLib.dll
  - MPQArchive: Easy Java-Access to some MPQ-Archive operations.
