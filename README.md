JStormLib v1.1
==============
JStormLib is a JAVA-Wrapper for the C++ Library StormLib by Ladislav Zezula. It's used to open
the MPQ-Archives found in games created by Blizzard Entertainment.

It uses the JNA-Library to access the native functions.
When used the library detects if you use a x86 or x64 system and loads the needed DLL.

Important files:
  - StormLib_x86.dll
  - StormLib_x64.dll

Important classes:
  - StormLibWin: Contains all function and constant mappings for the StormLib.dll
  - MPQArchive: Easy Java-Access to some MPQ-Archive operations.

Usefull Links:<br>
<a href="https://github.com/stormlib/StormLib">https://github.com/stormlib/StormLib</a><br>
<a href="http://www.zezula.net/en/mpq/stormlib.html">http://www.zezula.net/en/mpq/stormlib.html</a>

