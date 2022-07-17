/*
 * Copyright 2022 Kode Devs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kodedevs.kode.tools;

import com.install4j.api.context.UserCanceledException;
import com.install4j.api.launcher.ApplicationLauncher;
import com.install4j.api.launcher.Variables;
import com.install4j.api.update.ApplicationDisplayMode;
import com.install4j.api.update.UpdateChecker;
import com.install4j.api.update.UpdateDescriptor;
import com.install4j.api.update.UpdateDescriptorEntry;

import java.io.IOException;

public class AppUpdater {
    private static final String UPDATE_APPLICATION_ID = "97";

    private AppUpdater() {
    }

    public static String checkForAvailableUpdate() {
        // The compiler variable sys.updatesUrl holds the URL where the updates.xml file is hosted.
        // That URL is defined on the "Installer->Auto Update Options" step.
        // The same compiler variable is used by the "Check for update" actions that are contained
        // in the update downloader.
        String updateUrl;
        try {
            updateUrl = Variables.getCompilerVariable("sys.updatesUrl");
        } catch (IOException ex) {
            return null;
        }

        // Checking Update
        UpdateDescriptor updateDescriptor;
        try {
            updateDescriptor = UpdateChecker.getUpdateDescriptor(updateUrl, ApplicationDisplayMode.UNATTENDED);
        } catch (UserCanceledException | IOException ex) {
            return null;
        }

        UpdateDescriptorEntry updateDescriptorEntry = updateDescriptor.getPossibleUpdateEntry();
        if (updateDescriptorEntry == null) {
            // No updates found (OK).
            return null;
        }

        if (updateDescriptorEntry.isArchive() && !updateDescriptorEntry.isSingleBundle()) {
            // Only installers and single bundle archives on macOS are supported for background update
            return null;
        }

        return updateDescriptorEntry.getNewVersion();
    }

    public static void install() {
        try {
            ApplicationLauncher.launchApplication(UPDATE_APPLICATION_ID, null, false, new ApplicationLauncher.Callback() {
                @Override
                public void exited(int exitValue) {
                    // Launcher exited.
                }

                @Override
                public void prepareShutdown() {
                    // Shutdown in progress.
                }
            });
        } catch (IOException ignored) {
        }
    }
}
