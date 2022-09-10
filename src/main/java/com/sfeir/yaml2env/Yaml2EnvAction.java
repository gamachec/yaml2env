package com.sfeir.yaml2env;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.sfeir.yaml2env.exception.Yaml2EnvException;
import com.sfeir.yaml2env.reader.YamlReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.datatransfer.StringSelection;
import java.util.List;

import static java.lang.String.format;

public class Yaml2EnvAction extends AnAction {

    @Override
    public void update(@NotNull AnActionEvent event) {
        var visible = YamlVirtualFile.of(extractVirtualFile(event))
                .isPresent();

        event.getPresentation().setEnabledAndVisible(visible);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        try {
            YamlVirtualFile.of(extractVirtualFile(event))
                    .map(YamlVirtualFile::getContent)
                    .map(YamlReader::convertToEnvList)
                    .map(Yaml2EnvAction::joinWithLineBreak)
                    .ifPresent(Yaml2EnvAction::setClipboardContent);
        } catch (Yaml2EnvException exception) {
            notifyError(event, exception);
        }
    }

    @NotNull
    private static String joinWithLineBreak(@NotNull List<String> list) {
        return String.join("\n", list);
    }

    private static void setClipboardContent(@NotNull String content) {
        CopyPasteManager.getInstance().setContents(new StringSelection(content));
    }

    private static void notifyError(@NotNull AnActionEvent e, Exception exception) {
        var notification = new Notification("Yaml2env Notifications", "Yaml conversion error",
                format("Error converting yaml : %s", exception.getMessage()), NotificationType.ERROR);
        notification.notify(e.getProject());
    }

    @Nullable
    private static VirtualFile extractVirtualFile(@NotNull AnActionEvent e) {
        return e.getData(CommonDataKeys.VIRTUAL_FILE);
    }
}
