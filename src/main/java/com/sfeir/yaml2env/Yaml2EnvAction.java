package com.sfeir.yaml2env;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.sfeir.yaml2env.reader.YamlReader;
import com.sfeir.yaml2env.reader.YamlReaderException;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class Yaml2EnvAction extends AnAction {

    private static final List<String> COMPATIBLE_EXT = List.of("yaml", "yml");

    @Override
    public void update(@NotNull AnActionEvent e) {
        var visible = getVirtualFile(e)
                .map(VirtualFile::getExtension)
                .stream()
                .anyMatch(Yaml2EnvAction::isCompatibleExtension);

        e.getPresentation().setEnabledAndVisible(visible);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        var copyPasteManager = CopyPasteManager.getInstance();
        getVirtualFile(e)
                .map(Yaml2EnvAction::getVirtualFileInputStream)
                .map(YamlReader::toEnv)
                .map(list -> String.join("\n", list))
                .map(StringSelection::new)
                .ifPresent(copyPasteManager::setContents);
    }


    private static InputStream getVirtualFileInputStream(VirtualFile virtualFile) {
        try {
            return virtualFile.getInputStream();
        } catch (IOException e) {
            throw new YamlReaderException(e);
        }
    }

    @NotNull
    private static Optional<VirtualFile> getVirtualFile(@NotNull AnActionEvent e) {
        return Optional.ofNullable(e.getData(CommonDataKeys.VIRTUAL_FILE));
    }

    private static boolean isCompatibleExtension(@NotNull String s) {
        return COMPATIBLE_EXT.contains(s.toLowerCase());
    }
}
