package com.sfeir.yaml2env;

import com.intellij.openapi.vfs.VirtualFile;
import com.sfeir.yaml2env.exception.Yaml2EnvException;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class YamlVirtualFile {

    public static final List<String> COMPATIBLE_EXT = List.of("yaml", "yml");

    private final InputStream inputStream;

    private YamlVirtualFile(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public static Optional<YamlVirtualFile> of(@Nullable VirtualFile virtualFile) {
        return Optional.ofNullable(virtualFile)
                .filter(YamlVirtualFile::isYamlVirtualFile)
                .map(YamlVirtualFile::getVirtualFileInputStream)
                .map(YamlVirtualFile::new);
    }

    private static boolean isYamlVirtualFile(VirtualFile vf) {
        return Optional.ofNullable(vf.getExtension())
                .map(String::toLowerCase)
                .map(COMPATIBLE_EXT::contains)
                .orElse(false);
    }

    private static InputStream getVirtualFileInputStream(VirtualFile virtualFile) {
        try {
            return virtualFile.getInputStream();
        } catch (IOException e) {
            throw new Yaml2EnvException(format("Unable to open file '%s'", virtualFile.getName()), e);
        }
    }

    public InputStream getContent() {
        return inputStream;
    }
}
