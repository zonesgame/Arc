package arc.backend.sdl;

import arc.Files.*;
import arc.graphics.*;
import arc.graphics.gl.*;

public class SdlConfig{
    public boolean disableAudio = false;
    public int audioDeviceSimultaneousSources = 32;

    public int r = 8, g = 8, b = 8, a = 8;
    public int depth = 16, stencil = 0;
    public int samples = 0;
    public HdpiMode hdpiMode = HdpiMode.Logical;

    public int width = 640;
    public int height = 480;
    public boolean resizable = true;
    public boolean decorated = true;
    public boolean maximized = false;

    public String title = "Arc Application";
    public Color initialBackgroundColor = Color.black;
    public boolean initialVisible = true;
    public boolean vSyncEnabled = true;

    FileType windowIconFileType;
    String[] windowIconPaths;

    public void setWindowIcon(FileType fileType, String... filePaths){
        windowIconFileType = fileType;
        windowIconPaths = filePaths;
    }
}
