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
import java.util.Arrays;
import java.util.List;

public class UpdateResolver {

    // Here we check for updates in the background with the API.
    public static void checkForUpdate() {
        try {
            // The compiler variable sys.updatesUrl holds the URL where the updates.xml file is hosted.
            // That URL is defined on the "Installer->Auto Update Options" step.
            // The same compiler variable is used by the "Check for update" actions that are contained
            // in the update downloader.
            String updateUrl = Variables.getCompilerVariable("sys.updatesUrl");
            UpdateDescriptor updateDescriptor = UpdateChecker.getUpdateDescriptor(updateUrl, ApplicationDisplayMode.CONSOLE);

            // If getPossibleUpdateEntry returns a non-null value, the version number in the
            // updates.xml file is greater than the version number of the local installation.
            UpdateDescriptorEntry updateDescriptorEntry = updateDescriptor.getPossibleUpdateEntry();

            // only installers and single bundle archives on macOS are supported for background updates
            if (updateDescriptorEntry != null
                    && (!updateDescriptorEntry.isArchive() || updateDescriptorEntry.isSingleBundle())) {
                if (updateDescriptorEntry.isDownloaded()) {
                    // The update has been downloaded, but installation did not succeed yet.
                    executeUpdate();
                } else {
                    // An update is available for download
                    downloadAndUpdate();
                }
            }
        } catch (IOException | UserCanceledException ignored) {
            // Do nothing
        }
    }

    // Here the background update downloader is launched in the background
    private static void downloadAndUpdate() throws IOException {
        // The callback receives progress information from the update downloader
        // and changes the text on the button
        ApplicationLauncher.launchApplication("32", null, true, new ApplicationLauncher.Callback() {
            @Override
            public void exited(int exitValue) {
            }

            @Override
            public void prepareShutdown() {
            }

            @Override
            public ApplicationLauncher.ProgressListener createProgressListener() {
                return new ApplicationLauncher.ProgressListenerAdapter() {
                    boolean downloading;

                    @Override
                    public void actionStarted(String id) {
                        downloading = "downloadFile".equals(id);
                    }

                    @Override
                    public void percentCompleted(int value) {
                        if (downloading) {
                            System.out.println("Progress: " + value + " %");
                        }
                    }
                };
            }
        });

        // At this point the update downloader has returned, and we can check if the
        // "Schedule update installation" action has registered an update installer for execution
        if (UpdateChecker.isUpdateScheduled()) {
            // We execute the update immediately, but you could ask the user whether the update should be
            // installed now. The scheduling of update installers is persistent, so this will also work
            // after a restart of the launcher.
            executeUpdate();
        } else {
            System.err.println("Update could not be downloaded");
        }
    }

    // Here we execute update installation
    private static void executeUpdate() {
        // The arguments that are passed to the installer switch the default GUI mode to an unattended
        // mode with a progress bar. "-q" activates unattended mode, and "-splash Updating Engine ..."
        // shows a progress bar with the specified title.
        List<String> updaterArguments = Arrays.asList("-q", "-splash", "Updating Engine ...", "-alerts");
        UpdateChecker.executeScheduledUpdate(updaterArguments, true, null);
    }
}
