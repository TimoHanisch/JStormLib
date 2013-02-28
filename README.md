JStormLib v1.2
==============
JStormLib is a JAVA-Wrapper for the C++ Library StormLib by Ladislav Zezula. It's used to open
the MPQ-Archives found in games created by Blizzard Entertainment.

Requires the JNA-Library (including the platform library) for accessing the native functions.
When loaded the library detects your OS-Arch and loads the x86 or x64 StormLib DLL.

Important files:
  - StormLib_x86.dll
  - StormLib_x64.dll

Important classes:
  - StormLibWin: Contains all function and constant mappings for the StormLib.dll
  - MPQArchive: Easy Java-Access to some MPQ-Archive operations.

Usefull Links:<br>
<a href="https://github.com/stormlib/StormLib">https://github.com/stormlib/StormLib</a><br>
<a href="http://www.zezula.net/en/mpq/stormlib.html">http://www.zezula.net/en/mpq/stormlib.html</a>

